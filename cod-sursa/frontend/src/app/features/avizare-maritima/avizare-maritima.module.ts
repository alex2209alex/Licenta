import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AvizareMaritimaRoutingModule } from './avizare-maritima-routing.module';
import { AvHomeComponent } from './av-home/av-home.component';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  declarations: [
    AvHomeComponent
  ],
  imports: [
    CommonModule,
    AvizareMaritimaRoutingModule,
    TranslateModule
  ]
})
export class AvizareMaritimaModule { }
