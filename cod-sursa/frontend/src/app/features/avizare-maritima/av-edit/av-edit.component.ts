import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ShipService } from '../../shared/ship.service';
import { PortService } from '../../shared/port.service';
import { catchError, forkJoin, throwError } from 'rxjs';
import { Generic } from '../../shared/generic.model';
import { Router } from '@angular/router';

@Component({
  selector: 'ge-av-edit',
  templateUrl: './av-edit.component.html',
  styleUrls: ['./av-edit.component.css']
})
export class AvEditComponent implements OnInit {
  myForm: FormGroup;
  isLoading: boolean = false;
  ships: Generic[] = [];
  ports: Generic[] = [];

  private sources = [
    this.shipService.getAll(),
    this.portService.getAll()
  ];

  constructor(
    private fb: FormBuilder,
    private shipService: ShipService,
    private portService: PortService,
    private router: Router
  ) {
    this.myForm = this.fb.group({
      'estimatedArrivalDate': [null, [Validators.required]],
      'estimatedArrivalTime': [null, [Validators.required]],
      'ship': [null, [Validators.required]],
      'port': [null, [Validators.required]]
    });
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
      this.ships = resData[0];
      this.ports = resData[1];
      this.isLoading = false;
    });
  }

  get controls() {
    return {
      estimatedArrivalDate: this.myForm.get('estimatedArrivalDate')!,
      estimatedArrivalTime: this.myForm.get('estimatedArrivalTime')!,
      ship: this.myForm.get('ship')!,
      port: this.myForm.get('port')!
    }
  }

  goToList() {
    this.router.navigate(['avizare-maritima']);
  }
}
