import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HrmsmastersRoutingModule } from './hrmsmasters-routing.module';
import { CostcenterComponent } from './costcenter/costcenter.component';
import { BusinessunitComponent } from './businessunit/businessunit.component';
import { SbubusinessunitComponent } from './sbubusinessunit/sbubusinessunit.component';
import { EmployeeEducationComponent } from './employee-education/employee-education.component';
import { StateListComponent } from './state-list/state-list.component';
import { ResourcetypeComponent } from './resourcetype/resourcetype.component';
import { EmployeeStatusComponent } from './employee-status/employee-status.component';
import { SeparationtypeComponent } from './separationtype/separationtype.component';
import { RolemanagementComponent } from './rolemanagement/rolemanagement.component';
import { EmployeeDesignationComponent } from './employee-designation/employee-designation.component';
import { PassportCentersComponent } from './passport-centers/passport-centers.component';
import { SkillsComponent } from './skills/skills.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { FilterPipe } from '../../pipes/filter.pipe';



@NgModule({
  declarations: [
    CostcenterComponent,
    BusinessunitComponent,
    SbubusinessunitComponent,
    EmployeeEducationComponent,
    StateListComponent,
    ResourcetypeComponent,
    EmployeeStatusComponent,
    SeparationtypeComponent,
    RolemanagementComponent,
    EmployeeDesignationComponent,
    PassportCentersComponent,
    SkillsComponent,
    FilterPipe
    
  ],
  imports: [
    CommonModule,
    HrmsmastersRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,

  ]
})
export class HrmsmastersModule { }
