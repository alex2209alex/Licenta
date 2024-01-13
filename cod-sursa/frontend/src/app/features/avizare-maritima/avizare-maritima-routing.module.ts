import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AvHomeComponent } from "./av-home/av-home.component";
import { AvEditComponent } from './av-edit/av-edit.component';

const routes: Routes = [
  {
    path: '',
    component: AvHomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'new',
    component: AvEditComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AvizareMaritimaRoutingModule { }
