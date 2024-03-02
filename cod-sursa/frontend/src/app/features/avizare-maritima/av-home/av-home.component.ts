import { Component, OnInit } from '@angular/core';
import { AvService } from '../shared/av.service';
import { AvListItem } from '../shared/av-list-item.model';
import * as dayjs from "dayjs";
import { Router } from "@angular/router";

@Component({
  selector: 'ge-av-home',
  templateUrl: './av-home.component.html',
  styleUrls: ['./av-home.component.css']
})
export class AvHomeComponent implements OnInit {
  items: AvListItem[] = [];

  constructor(private apiService: AvService, private router: Router) {
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
}
