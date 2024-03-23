import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { NgbTimepickerConfig } from "@ng-bootstrap/ng-bootstrap";
import { catchError, forkJoin, Observable, throwError } from "rxjs";
import { Generic } from "../../shared/generic.model";
import { CompanyType } from "../../shared/company-type";
import { CompanyService } from "../../shared/company.service";
import { Router } from "@angular/router";
import { MaritimeCall } from "../shared/maritime-call.model";
import { MaritimeCallService } from "../shared/maritime-call.service";

@Component({
  selector: 'ge-bp-edit',
  templateUrl: './bp-edit.component.html',
  styleUrls: ['./bp-edit.component.css']
})
export class BpEditComponent implements OnInit {
  isLoading: boolean = false;
  myForm: FormGroup;
  pilotageCompanies: Generic[] = [];
  cargoOperatingCompanies: Generic[] = [];
  maritimeCalls: MaritimeCall[] = [];

  private sources: (Observable<Generic[]> | Observable<MaritimeCall[]>)[] = [
    this.companyService.getAllOfType(CompanyType.PILOTAGE),
    this.companyService.getAllOfType(CompanyType.CARGO_OPERATOR),
    this.maritimeCallService.findAllThatCanHaveNewPilotageBulletin()
  ];

  constructor(private fb: FormBuilder,
              private config: NgbTimepickerConfig,
              private companyService: CompanyService,
              private maritimeCallService: MaritimeCallService,
              private router: Router
  ) {
    this.myForm = this.fb.group({
      'pilotageCompany': [null, [Validators.required]],
      'cargoOperatingCompany': [null],
      'maritimeCall': [null, [Validators.required]],
      'estimatedStartTime': [null, [Validators.required]],
      'startLocation': [null, [Validators.required]],
      'stopLocation': [null, [Validators.required]],
    });
    this.config.spinners = false;
  }

  ngOnInit(): void {
    this.isLoading = true;
    forkJoin(this.sources)
      .pipe(
        catchError((error) => {
          this.isLoading = false;
          this.goToList();
          return throwError(() => error);
        })
      )
      .subscribe(resData => {
        this.pilotageCompanies = resData[0];
        this.cargoOperatingCompanies = resData[1];
        this.maritimeCalls = resData[2] as MaritimeCall[];
        this.isLoading = false;
      });
  }

  get controls() {
    return {
      pilotageCompany: this.myForm.get('pilotageCompany')!,
      cargoOperatingCompany: this.myForm.get('cargoOperatingCompany'),
      maritimeCall: this.myForm.get('maritimeCall')!,
      estimatedStartTime: this.myForm.get('estimatedStartTime')!,
      startLocation: this.myForm.get('startLocation')!,
      stopLocation: this.myForm.get('stopLocation')!,
    }
  }

  private goToList() {
    this.router.navigate(['buletin-pilotaj']);
  }
}
