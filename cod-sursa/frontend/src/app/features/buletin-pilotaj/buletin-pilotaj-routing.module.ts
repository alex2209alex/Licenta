import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BpHomeComponent } from "./bp-home/bp-home.component";
import { AuthGuard } from "../../shared/guard/auth-guard";
import { Role } from "../../shared/guard/role";
import { BpEditComponent } from "./bp-edit/bp-edit.component";

const routes: Routes = [
  {
    path: '',
    component: BpHomeComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
    data: {
      hasAnyRole: [Role.ROLE_AGENT_NAVA, Role.ROLE_DISPECER_ANR, Role.ROLE_DISPECER_APMC, Role.ROLE_DISPECER_PILOTAJ]
    }
  },
  {
    path: 'new',
    component: BpEditComponent,
    canActivate: [AuthGuard],
    data: {
      hasAnyRole: [Role.ROLE_AGENT_NAVA]
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BuletinPilotajRoutingModule {
}
