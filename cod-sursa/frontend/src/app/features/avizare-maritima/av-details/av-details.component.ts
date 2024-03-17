import { Component } from '@angular/core';
import { AvService } from "../shared/av.service";
import { ActivatedRoute, Router } from "@angular/router";
import { AvizareMaritima } from "../shared/av.model";
import { catchError, throwError } from "rxjs";
import * as dayjs from "dayjs";

@Component({
  selector: 'ge-av-details',
  templateUrl: './av-details.component.html',
  styleUrls: ['./av-details.component.css']
})
export class AvDetailsComponent {
  idMaritimeNotice: number = -1;
  showCancelBtn: boolean = false;
  isLoading: boolean = true;
  maritimeNotice: AvizareMaritima | null = null;

  constructor(private apiService: AvService, private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.idMaritimeNotice = Number(this.activatedRoute.snapshot.params['id']);
    if (this.router.url.includes('cancel')) {
      this.showCancelBtn = true;
    }
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

  public formatDateTime(dateTime: string | null): string | null {
    if (dateTime !== null) {
      return dayjs(dateTime).format('DD-MM-YYYY HH:mm')
    }
    return null;
  }

  public goToList() {
    this.router.navigate(['avizare-maritima']).then();
  }

  onCancel() {
    this.apiService.cancel(this.idMaritimeNotice).subscribe(() => {
      this.goToList();
    });
  }
}
