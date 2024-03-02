import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AvizareMaritimaRoutingModule } from './avizare-maritima-routing.module';
import { AvHomeComponent } from './av-home/av-home.component';
import { TranslateModule } from '@ngx-translate/core';
import { AvEditComponent } from './av-edit/av-edit.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgbInputDatepicker, NgbTimepicker, NgbTooltip } from "@ng-bootstrap/ng-bootstrap";
import { AvDetailsComponent } from './av-details/av-details.component';
import { AvResolveComponent } from './av-resolve/av-resolve.component';

@NgModule({
  declarations: [
    AvHomeComponent,
    AvEditComponent,
    AvDetailsComponent,
    AvResolveComponent
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
