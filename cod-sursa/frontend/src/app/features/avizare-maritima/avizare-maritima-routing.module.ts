import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AvHomeComponent } from "./av-home/av-home.component";
import { AvEditComponent } from './av-edit/av-edit.component';
import { AvDetailsComponent } from "./av-details/av-details.component";
import { AvResolveComponent } from "./av-resolve/av-resolve.component";
import { AuthGuard } from "../../shared/guard/auth-guard";
import { Role } from "../../shared/guard/role";

const routes: Routes = [
  {
    path: '',
    component: AvHomeComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
    data: {
      hasAnyRole: [Role.ROLE_AGENT_NAVA, Role.ROLE_DISPECER_ANR, Role.ROLE_DISPECER_APMC]
    }
  },
  {
    path: 'new',
    component: AvEditComponent,
    canActivate: [AuthGuard],
    data: {
      hasAnyRole: [Role.ROLE_AGENT_NAVA]
    }
  },
  {
    path: ':id/edit',
    component: AvEditComponent,
    canActivate: [AuthGuard],
    data: {
      hasAnyRole: [Role.ROLE_AGENT_NAVA]
    }
  },
  {
    path: ':id',
    component: AvDetailsComponent,
    canActivate: [AuthGuard],
    data: {
      hasAnyRole: [Role.ROLE_AGENT_NAVA, Role.ROLE_DISPECER_ANR, Role.ROLE_DISPECER_APMC]
    }
  },
  {
    path: ':id/resolve',
    component: AvResolveComponent,
    canActivate: [AuthGuard],
    data: {
      hasAnyRole: [Role.ROLE_DISPECER_ANR, Role.ROLE_DISPECER_APMC]
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AvizareMaritimaRoutingModule {
}
