import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TimesheetmanagementComponent } from './timesheetmanagement.component';

const routes: Routes = [{ path: '', component: TimesheetmanagementComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TimesheetmanagementRoutingModule { }
