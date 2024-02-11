import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AvizareMaritimaRoutingModule } from './avizare-maritima-routing.module';
import { AvHomeComponent } from './av-home/av-home.component';
import { TranslateModule } from '@ngx-translate/core';
import { AvEditComponent } from './av-edit/av-edit.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgbInputDatepicker, NgbTimepicker, NgbTooltip } from "@ng-bootstrap/ng-bootstrap";


@NgModule({
  declarations: [
    AvHomeComponent,
    AvEditComponent
  ],
  imports: [
    CommonModule,
    AvizareMaritimaRoutingModule,
    ReactiveFormsModule,
    TranslateModule,
    NgSelectModule,
    NgbInputDatepicker,
    NgbTimepicker,
    NgbTooltip
  ]
})
export class AvizareMaritimaModule { }
