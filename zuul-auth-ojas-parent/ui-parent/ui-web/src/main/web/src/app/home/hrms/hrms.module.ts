import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HrmsRoutingModule } from './hrms-routing.module';

// import { DashboardComponent } from '../dashboard/dashboard.component';
import { EmployeeComponent } from './employee/employee.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EmployeeeditComponent } from './employee/employeeedit/employeeedit.component';
import { EducationComponent } from './employee/employeeedit/education/education.component';
import { ProfessionalComponent } from './employee/employeeedit/professional/professional.component';
import { KyeComponent } from './employee/employeeedit/kye/kye.component';
import { ResignationComponent } from './employee/employeeedit/resignation/resignation.component';
import { DependentsComponent } from './employee/employeeedit/dependents/dependents.component';
import { BasicInfoComponent } from './employee/employeeedit/basic-info/basic-info.component';
import { NgxPaginationModule } from 'ngx-pagination';


import { OrgStructureComponent } from './org-structure/org-structure.component';
import { TreeViewModule } from '@syncfusion/ej2-angular-navigations';
import { ChartsModule } from 'ng2-charts';
import { EmpreportsComponent } from './empreports/empreports.component';
import { MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatInputModule } from '@angular/material';
import { SearchemployeePipe } from '../pipes/searchemployee.pipe';
import { SharedModule } from 'src/app/shared/shared.module';
import { ProfileViewComponent } from './employee/employeeedit/profile-view/profile-view.component';




@NgModule({
  declarations: [
    // DashboardComponent,
    EmployeeComponent,
    EmployeeeditComponent,
    BasicInfoComponent,
    EducationComponent,
    ProfessionalComponent,
    KyeComponent,
    ResignationComponent,
    DependentsComponent,
    OrgStructureComponent,
    EmpreportsComponent,
    SearchemployeePipe,
    ProfileViewComponent,
    
    
  ],
  imports: [
    CommonModule,
    HrmsRoutingModule,
    FormsModule,
    NgxPaginationModule,
    TreeViewModule,
    ReactiveFormsModule,
    ChartsModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    SharedModule
  ]
})
export class HrmsModule { }
