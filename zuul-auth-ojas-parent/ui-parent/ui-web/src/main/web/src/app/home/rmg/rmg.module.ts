import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RmgRoutingModule } from './rmg-routing.module';
import { RmgComponent } from './rmg.component';
import { ApproveComponent } from './approve/approve.component';
import { BenchEmployeesComponent } from './bench-employees/bench-employees.component';
import { BookingRequestComponent } from './booking-request/booking-request.component';
import { EmployeeexperienceComponent } from './employeeexperience/employeeexperience.component';
import { ProposalsComponent } from './proposals/proposals.component';
import { RejectComponent } from './reject/reject.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_LABEL_GLOBAL_OPTIONS, MatInputModule } from '@angular/material';
import { NgxPaginationModule } from 'ngx-pagination';
// import { RmgPipe } from '../pipes/rmg.pipe';
import {MatRadioModule} from '@angular/material/radio';
import { RmgreportsComponent } from './rmgreports/rmgreports.component';
import { DeployedEmployeesComponent } from './deployed-employees/deployed-employees.component';
import { RmgpipePipe } from '../pipes/rmgpipe.pipe';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [RmgComponent, ApproveComponent, BenchEmployeesComponent, BookingRequestComponent, EmployeeexperienceComponent, ProposalsComponent, RejectComponent, RmgreportsComponent, DeployedEmployeesComponent,RmgpipePipe],
  imports: [
    CommonModule,
    RmgRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    NgxPaginationModule,
    MatRadioModule,
    SharedModule
  ]
})
export class RmgModule { }
