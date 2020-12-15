import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HolidayComponent } from './holiday/holiday.component';
import { LeavetypeComponent } from './leavetype/leavetype.component';


const routes: Routes = [{path:'holiday',component:HolidayComponent},{path:'leavetype',component:LeavetypeComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LmsmastersRoutingModule { }
