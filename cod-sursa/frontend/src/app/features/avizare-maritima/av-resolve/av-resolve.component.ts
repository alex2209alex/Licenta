import { Component } from '@angular/core';
import { AvizareMaritima } from "../shared/av.model";
import { AvService } from "../shared/av.service";
import { ActivatedRoute, Router } from "@angular/router";
import { catchError, throwError } from "rxjs";
import * as dayjs from "dayjs";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { TranslateService } from "@ngx-translate/core";
import { Resolution } from "../../shared/resolution.model";

@Component({
  selector: 'ge-av-resolve',
  templateUrl: './av-resolve.component.html',
  styleUrls: ['./av-resolve.component.css']
})
export class AvResolveComponent {
  idMaritimeNotice: number = -1;
  isLoading: boolean = true;
  maritimeNotice: AvizareMaritima | null = null;
  myForm: FormGroup;
  isSaving: boolean = false;

  constructor(
    private apiService: AvService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private translate: TranslateService,
    private fb: FormBuilder) {
    this.myForm = this.fb.group({
      'resolution': ["1", [Validators.required]],
      'rejectionReason': [null]
    });
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.idMaritimeNotice = Number(this.activatedRoute.snapshot.params['id']);
    this.apiService.findById(this.idMaritimeNotice)
      .pipe(
        catchError((error) => {
          this.isLoading = false;
          this.goToList();
          return throwError(() => error);
        })
      ).subscribe(
      item => {
        this.maritimeNotice = item;
        this.isLoading = false;
      }
    );
  }

  get controls() {
    return {
      resolution: this.myForm.get('resolution')!,
      rejectionReason: this.myForm.get('rejectionReason')!
    }
  }

  public formatDateTime(dateTime: string | null): string | null {
    if (dateTime !== null) {
      return dayjs(dateTime).format('DD-MM-YYYY HH:mm')
    }
    return null;
  }

  public goToList() {
    this.router.navigate(['avizare-maritima']);
  }

  public onClickSave() {
    if (this.controls.resolution.value === "0" && (!this.controls.rejectionReason.value || this.controls.rejectionReason.value.trim().length === 0)) {
      this.showError(this.translate.instant('app.titlu-eroare'), this.translate.instant('app.eroare-respingere'))
      return;
    }
    this.isSaving = true;
    this.apiService.resolve(this.buildFormData(), this.idMaritimeNotice)
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

  private showError(title: string, message: string) {
    this.toastr.error(message, title);
  }

  private showInformation(title: string, message: string) {
    this.toastr.info(message, title);
  }

  private buildFormData(): Resolution {
    const resolution = new Resolution();
    resolution.resolution = this.controls.resolution.value;
    resolution.rejectionReason = this.controls.rejectionReason.value;
    return resolution;
  }
}
