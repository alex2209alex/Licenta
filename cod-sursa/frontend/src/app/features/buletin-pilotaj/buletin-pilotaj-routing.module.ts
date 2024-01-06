import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BpHomeComponent } from "./bp-home/bp-home.component";

const routes: Routes = [
  {
    path: '',
    component: BpHomeComponent,
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BuletinPilotajRoutingModule {
}
