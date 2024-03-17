import { Component, OnInit } from '@angular/core';
import { AvService } from '../shared/av.service';
import { AvListItem } from '../shared/av-list-item.model';
import * as dayjs from "dayjs";
import { Router } from "@angular/router";
import { AvDocumentStatus } from "../shared/av-document-status";
import { UserHelper } from "../../../shared/guard/user-helper";
import { MCStatus } from "../shared/mc-status";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Generic } from "../../shared/generic.model";
import { TranslateService } from "@ngx-translate/core";
import { catchError, throwError } from "rxjs";

@Component({
  selector: 'ge-av-home',
  templateUrl: './av-home.component.html',
  styleUrls: ['./av-home.component.css']
})
export class AvHomeComponent implements OnInit {
  items: AvListItem[] = [];
  documentStatuses: Generic[] = [];
  myForm: FormGroup;
  isLoading: boolean = false;

  constructor(private apiService: AvService,
              private router: Router,
              private userHelper: UserHelper,
              private fb: FormBuilder,
              private translate: TranslateService) {
    this.myForm = this.fb.group({
      'documentStatus': [null]
    });
  }

  ngOnInit(): void {
    this.prefillDocumentStatusControl();
    this.isLoading = true;
    this.apiService.search({documentStatus: null}).pipe(
      catchError((error) => {
        this.isLoading = false;
        return throwError(() => error);
      })
    ).subscribe(
      (resData: AvListItem[]) => {
        this.items = resData;
        this.isLoading = false;
      }
    );
  }

  get controls() {
    return {
      documentStatus: this.myForm.get('documentStatus')!
    }
  }

  public formatDateTime(dateTime: string | null): string | null {
    if (dateTime !== null) {
      return dayjs(dateTime).format('DD-MM-YYYY HH:mm')
    }
    return null;
  }

  public goToResolve(id: number) {
    this.router.navigate(['avizare-maritima', id, 'resolve']);
  }

  public showResolveBtn(avItem: AvListItem): boolean {
    if (avItem.documentStatus === AvDocumentStatus.AWAITING && this.userHelper.isDispecerANR()) {
      return true;
    }
    return avItem.documentStatus === AvDocumentStatus.ANR_APPROVED && this.userHelper.isDispecerAPMC();
  }

  public showAddBtn(): boolean {
    return this.userHelper.isAgentNava();
  }

  showEditBtn(avItem: AvListItem): boolean {
    return this.userHelper.isAgentNava() && avItem.documentStatus === AvDocumentStatus.REJECTED;
  }

  showCancelBtn(avItem: AvListItem): boolean {
    return this.userHelper.isAgentNava() && avItem.maritimeCallStatus === MCStatus.PLANNED;
  }

  goToEdit(id: number) {
    this.router.navigate(['avizare-maritima', id, 'edit']);
  }

  goToCancel(id: number) {
    this.router.navigate(['avizare-maritima', id, 'cancel']);
  }

  onClickSearch() {
    this.isLoading = true;
    this.apiService.search(
      {
        documentStatus: this.controls.documentStatus.value?.id ?? null
      }
    ).pipe(
      catchError((error) => {
        this.isLoading = false;
        return throwError(() => error);
      })
    ).subscribe(
      (resData: AvListItem[]) => {
        this.items = resData;
        this.isLoading = false;
      }
    );
  }

  private prefillDocumentStatusControl() {
    this.documentStatuses.push({
      id: 0,
      label: this.translate.instant("avm.status-0")
    });
    this.documentStatuses.push({
      id: 1,
      label: this.translate.instant("avm.status-1")
    });
    this.documentStatuses.push({
      id: 2,
      label: this.translate.instant("avm.status-2")
    });
    this.documentStatuses.push({
      id: 3,
      label: this.translate.instant("avm.status-3")
    });
    this.documentStatuses.push({
      id: 4,
      label: this.translate.instant("avm.status-4")
    });
  }
}
