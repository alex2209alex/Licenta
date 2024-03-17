import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BuletinPilotajRoutingModule } from './buletin-pilotaj-routing.module';
import { BpHomeComponent } from './bp-home/bp-home.component';
import { TranslateModule } from '@ngx-translate/core';
import { BpEditComponent } from './bp-edit/bp-edit.component';


@NgModule({
  declarations: [
    BpHomeComponent,
    BpEditComponent
  ],
  imports: [
    CommonModule,
    BuletinPilotajRoutingModule,
    TranslateModule
  ]
})
export class BuletinPilotajModule { }
