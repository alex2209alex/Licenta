import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'avizare-maritima',
    pathMatch: 'full'
  },
  {
    path: 'avizare-maritima',
    loadChildren: () => import('./features/avizare-maritima/avizare-maritima.module').then(m => m.AvizareMaritimaModule)
  },
  {
    path: 'buletin-pilotaj',
    loadChildren: () => import('./features/buletin-pilotaj/buletin-pilotaj.module').then(m => m.BuletinPilotajModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
