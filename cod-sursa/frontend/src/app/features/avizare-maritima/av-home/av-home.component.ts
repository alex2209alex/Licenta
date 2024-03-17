import { Component, OnInit } from '@angular/core';
import { AvService } from '../shared/av.service';
import { AvListItem } from '../shared/av-list-item.model';
import * as dayjs from "dayjs";
import { Router } from "@angular/router";
import { AvDocumentStatus } from "../shared/av-document-status";
import { UserHelper } from "../../../shared/guard/user-helper";
import { MCStatus } from "../shared/mc-status";

@Component({
  selector: 'ge-av-home',
  templateUrl: './av-home.component.html',
  styleUrls: ['./av-home.component.css']
})
export class AvHomeComponent implements OnInit {
  items: AvListItem[] = [];

  constructor(private apiService: AvService, private router: Router, private userHelper: UserHelper) {
  }

  ngOnInit(): void {
    this.apiService.search({documentStatus: 0}).subscribe(
      (resData: AvListItem[]) => {
        this.items = resData;
      }
    );
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
}
