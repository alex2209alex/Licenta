import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BuletinPilotajRoutingModule } from './buletin-pilotaj-routing.module';
import { BpHomeComponent } from './bp-home/bp-home.component';
import { TranslateModule } from '@ngx-translate/core';
import { BpEditComponent } from './bp-edit/bp-edit.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgSelectModule } from "@ng-select/ng-select";


@NgModule({
  declarations: [
    BpHomeComponent,
    BpEditComponent
  ],
  imports: [
    CommonModule,
    BuletinPilotajRoutingModule,
    TranslateModule,
    FormsModule,
    NgSelectModule,
    ReactiveFormsModule
  ]
})
export class BuletinPilotajModule { }
