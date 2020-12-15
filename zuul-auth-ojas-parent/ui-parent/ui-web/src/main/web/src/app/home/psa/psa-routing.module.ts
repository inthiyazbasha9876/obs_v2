
import { PeopleinpsaComponent } from './peopleinpsa/peopleinpsa.component';
import { TmsinpsaComponent } from './tmsinpsa/tmsinpsa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PsaComponent } from './psa.component';
import { CustomerComponent } from './customer/customer.component';

import { ContractComponent } from './contract/contract.component';

import { ContactPSAComponent } from './contact-psa/contact-psa.component';
import { ProjectPSAComponent } from './project-psa/project-psa.component';
import { PsareportsComponent } from './psareports/psareports.component';
import { ResourceBillingComponent } from './resource-billing/resource-billing.component';
import { PsadashboardComponent } from './psadashboard/psadashboard.component';
import { ScheduleComponent } from './schedule/schedule.component';

const routes: Routes = [
  {
    path: '', component:PsaComponent,
    children: [{ path: '', redirectTo: 'customer', pathMatch: 'full' },
    { path: 'customer', component: CustomerComponent },
    { path: 'contact', component: ContactPSAComponent },
    { path: 'contract', component: ContractComponent },
    { path: 'project', component: ProjectPSAComponent },
    { path: 'resourceBilling', component: ResourceBillingComponent },
    { path: 'reports', component: PsadashboardComponent },
    { path: 'schedule', component: ScheduleComponent },
    { path: 'tmsInPsa', component: TmsinpsaComponent },
    { path: 'peopleInPsa', component: PeopleinpsaComponent }

    ]
  },
  { path: 'psamaster', loadChildren: () => import('./psamasters/psamasters.module').then(m => m.PsamastersModule) },
  { path: 'psareports', component: PsareportsComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PsaRoutingModule { }
