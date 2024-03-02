import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AvHomeComponent } from "./av-home/av-home.component";
import { AvEditComponent } from './av-edit/av-edit.component';
import { AvDetailsComponent } from "./av-details/av-details.component";
import { AvResolveComponent } from "./av-resolve/av-resolve.component";

const routes: Routes = [
  {
    path: '',
    component: AvHomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'new',
    component: AvEditComponent
  },
  {
    path: ':id',
    component: AvDetailsComponent
  },
  {
    path: ':id/resolve',
    component: AvResolveComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AvizareMaritimaRoutingModule { }
