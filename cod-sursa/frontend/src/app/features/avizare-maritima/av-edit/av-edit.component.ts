import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ShipService } from '../../shared/ship.service';
import { PortService } from '../../shared/port.service';
import { catchError, forkJoin, Observable, throwError } from 'rxjs';
import { Generic } from '../../shared/generic.model';
import { ActivatedRoute, Router } from '@angular/router';
import {
  NgbDate,
  NgbDateParserFormatter,
  NgbDateStruct,
  NgbTimepickerConfig,
  NgbTimeStruct
} from "@ng-bootstrap/ng-bootstrap";
import { MyNgbDateParserFormatter } from "../../../shared/ng-bootstrap/my-ngb-date-parser-formatter";
import { ToastrService } from 'ngx-toastr';
import { TranslateService } from "@ngx-translate/core";
import { AvizareMaritima } from "../shared/av.model";
import * as dayjs from "dayjs";
import { AvService } from "../shared/av.service";
import { CargoService } from "../../shared/cargo.service";
import { DeclaredCargo } from "../shared/declared-cargo.model";

@Component({
  selector: 'ge-av-edit',
  templateUrl: './av-edit.component.html',
  styleUrls: ['./av-edit.component.css'],
  providers: [NgbTimepickerConfig,
    {provide: NgbDateParserFormatter, useClass: MyNgbDateParserFormatter},
  ]
})
export class AvEditComponent implements OnInit {
  idMaritimeNotice: number | null = null;
  isEditMode: boolean = false;
  item: AvizareMaritima | null = null;
  myForm: FormGroup;
  isLoading: boolean = false;
  isSaving: boolean = false;
  ships: Generic[] = [];
  ports: Generic[] = [];
  cargos: Generic[] = [];
  minDate: NgbDate;

  private sources: (Observable<Generic[]> | Observable<AvizareMaritima>)[] = [
    this.shipService.getAll(),
    this.portService.getAll(),
    this.cargoService.getAll()
  ];

  constructor(
    private fb: FormBuilder,
    private shipService: ShipService,
    private activatedRoute: ActivatedRoute,
    private portService: PortService,
    private cargoService: CargoService,
    private router: Router,
    private config: NgbTimepickerConfig,
    private toastr: ToastrService,
    private translate: TranslateService,
    private apiService: AvService
  ) {
    this.myForm = this.fb.group({
      'estimatedArrivalDate': [null, [Validators.required]],
      'estimatedArrivalTime': [null, [Validators.required]],
      'ship': [null, [Validators.required]],
      'port': [null, [Validators.required]],
      'cargos': this.fb.array([])
    });
    this.config.spinners = false;
    const today = new Date();
    this.minDate = new NgbDate(today.getFullYear(), today.getMonth() + 1, today.getDate());
  }

  ngOnInit(): void {
    this.isLoading = true;
    if (this.activatedRoute.snapshot.params['id']) {
      this.idMaritimeNotice = Number(this.activatedRoute.snapshot.params['id']);
      this.isEditMode = true;
      this.sources.push(this.apiService.findById(this.idMaritimeNotice));
    }
    if (this.isEditMode) {
      forkJoin(this.sources)
        .pipe(
          catchError((error) => {
            this.isLoading = false;
            this.goToList();
            return throwError(() => error);
          })
        )
        .subscribe(resData => {
          this.ships = resData[0] as Generic[];
          this.ports = resData[1] as Generic[];
          this.cargos = resData[2] as Generic[];
          this.item = resData[3] as AvizareMaritima;
          this.prefillForm();
          this.isLoading = false;
        });
    } else {
      forkJoin(this.sources)
        .pipe(
          catchError((error) => {
            this.isLoading = false;
            this.goToList();
            return throwError(() => error);
          })
        )
        .subscribe(resData => {
          this.ships = resData[0] as Generic[];
          this.ports = resData[1] as Generic[];
          this.cargos = resData[2] as Generic[];
          this.isLoading = false;
        });
    }
  }

  get controls() {
    return {
      estimatedArrivalDate: this.myForm.get('estimatedArrivalDate')!,
      estimatedArrivalTime: this.myForm.get('estimatedArrivalTime')!,
      ship: this.myForm.get('ship')!,
      port: this.myForm.get('port')!,
      cargos: this.myForm.controls["cargos"] as FormArray
    }
  }

  castToFormGroup(control: AbstractControl) {
    return control as FormGroup;
  }

  addCargo() {
    const cargoForm = this.fb.group({
      cargo: [null, Validators.required],
      quantity: [null, [Validators.required, Validators.min(0)]]
    });
    this.controls.cargos.push(cargoForm);
  }

  onClickDeleteCargo(index: number) {
    this.controls.cargos.removeAt(index);
  }

  onClickCancel() {
    this.goToList();
  }

  onClickSubmit() {
    if (this.myForm.invalid) {
      this.myForm.markAllAsTouched();
      this.showError(this.translate.instant('app.titlu-eroare'), this.translate.instant('app.formular-invalid'))
      return;
    }
    this.isSaving = true;
    if (this.isEditMode) {
      this.apiService.update(this.buildFormData(), this.idMaritimeNotice!)
        .pipe(
          catchError(err => {
            this.isSaving = false;
            return err;
          })
        )
        .subscribe(() => {
          this.isSaving = false;
          this.showInformation(this.translate.instant('app.titlu-informare'), this.translate.instant('app.mesaj-op-succes'))
          this.goToList();
        });
    } else {
      this.apiService.add(this.buildFormData())
        .pipe(
          catchError(err => {
            this.isSaving = false;
            return err;
          })
        )
        .subscribe(() => {
          this.isSaving = false;
          this.showInformation(this.translate.instant('app.titlu-informare'), this.translate.instant('app.mesaj-op-succes'))
          this.goToList();
        });
    }
  }

  private goToList() {
    this.router.navigate(['avizare-maritima']);
  }

  private showInformation(title: string, message: string) {
    this.toastr.info(message, title);
  }

  private showError(title: string, message: string) {
    this.toastr.error(message, title);
  }

  private buildFormData(): AvizareMaritima {
    const item: AvizareMaritima = new AvizareMaritima();
    item.ship = this.controls.ship.value;
    item.port = this.controls.port.value;
    item.estimatedArrivalDateTime = this.buildDateTime(this.controls.estimatedArrivalDate.value, this.controls.estimatedArrivalTime.value);
    for (let cargoForm of this.controls.cargos.controls) {
      const cargoGroup = this.castToFormGroup(cargoForm);
      const declaredCargo: DeclaredCargo = new DeclaredCargo();
      declaredCargo.cargo = cargoGroup.controls['cargo'].value;
      declaredCargo.quantity = cargoGroup.controls['quantity'].value;
      item.cargos.push(declaredCargo);
    }
    return item;
  }

  private buildDateTime(date: NgbDateStruct, time: NgbTimeStruct): string | null {
    if (date && time) {
      let dateTime = dayjs().set('year', date.year);
      dateTime = dateTime.set('month', date.month - 1);
      dateTime = dateTime.set('date', date.day);
      dateTime = dateTime.set('hour', time.hour);
      return dateTime.set('minute', time.minute).startOf('minute').toISOString();
    }
    return null;
  }

  private prefillForm(): void {
    this.controls.port.setValue(this.item?.port);
    this.controls.ship.setValue(this.item?.ship);
    this.controls.estimatedArrivalDate.setValue(this.fromStringToNgbDateStruct(this.item?.estimatedArrivalDateTime!));
    this.controls.estimatedArrivalTime.setValue(this.fromStringToNgbTimeStruct(this.item?.estimatedArrivalDateTime!));
    if (this.item?.cargos) {
      this.prefillCargo(this.item?.cargos);
    }
  }

  private fromStringToNgbDateStruct(dateTime: string): NgbDateStruct {
    const date = dayjs(dateTime);
    return {
      year: date.year(),
      month: date.month() + 1,
      day: date.date()
    }
  }

  private fromStringToNgbTimeStruct(dateTime: string): NgbTimeStruct {
    const date = dayjs(dateTime);
    return {
      hour: date.hour(),
      minute: date.minute(),
      second: 0
    }
  }

  private prefillCargo(declaredCargos: DeclaredCargo[]) {
    for (const dc of declaredCargos) {
      const cargoForm = this.fb.group({
        cargo: [dc.cargo, Validators.required],
        quantity: [dc.quantity, [Validators.required, Validators.min(0)]]
      });
      this.controls.cargos.push(cargoForm);
    }
  }
}
