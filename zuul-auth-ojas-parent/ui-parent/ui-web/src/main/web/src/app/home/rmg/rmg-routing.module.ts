import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RmgComponent } from './rmg.component';
import { BenchEmployeesComponent } from './bench-employees/bench-employees.component';
import { BookingRequestComponent } from './booking-request/booking-request.component';
import { ApproveComponent } from './approve/approve.component';
import { RejectComponent } from './reject/reject.component';
import { ProposalsComponent } from './proposals/proposals.component';
import { EmployeeexperienceComponent } from './employeeexperience/employeeexperience.component';
import { RmgreportsComponent } from './rmgreports/rmgreports.component';
import { DeployedEmployeesComponent } from './deployed-employees/deployed-employees.component';



const routes: Routes = [{
  path: '',
  component: RmgComponent,
  children:
    [
      { path: '', redirectTo: 'benchresource', pathMatch: 'full' },
      { path: 'benchresource', component: BenchEmployeesComponent },
      { path: 'bookingrequest', component: BookingRequestComponent },
      { path: 'approvals', component: ApproveComponent },
      { path: 'rejections', component: RejectComponent },
      { path: 'proposals', component: ProposalsComponent },
      { path: 'deployedresource', component: DeployedEmployeesComponent }

    ]
},
{ path: 'experience', component: EmployeeexperienceComponent },
{ path:'rmgreports', component:RmgreportsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RmgRoutingModule { }
