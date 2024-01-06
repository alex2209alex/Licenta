import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AvHomeComponent } from "./av-home/av-home.component";

const routes: Routes = [
  {
    path: '',
    component: AvHomeComponent,
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AvizareMaritimaRoutingModule { }
