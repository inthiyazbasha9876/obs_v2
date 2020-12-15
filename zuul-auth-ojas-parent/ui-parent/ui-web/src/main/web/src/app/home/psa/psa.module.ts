import { RmgPipe } from './../pipes/rmg.pipe';
import { RmgModule } from './../rmg/rmg.module';
import { RmgpipePipe } from './../pipes/rmgpipe.pipe';

import { TmsPipe } from './../pipes/tms.pipe';
import { TimesheetmanagementComponent } from './../timesheetmanagement/timesheetmanagement.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PsaRoutingModule } from './psa-routing.module';
import { PsaComponent } from './psa.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';

import { ContractComponent } from './contract/contract.component';
import { CustomerComponent } from './customer/customer.component';

import { ContactPSAComponent } from './contact-psa/contact-psa.component';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';


import { MatDatepickerModule } from '@angular/material/datepicker';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { ProjectPSAComponent } from './project-psa/project-psa.component';

import { MatNativeDateModule, MAT_LABEL_GLOBAL_OPTIONS, MatInputModule } from '@angular/material';
import { PsareportsComponent } from './psareports/psareports.component';
import { CustomersearchpipePipe } from '../pipes/customersearchpipe.pipe';
import { ContractsearchPipe } from '../pipes/contractsearch.pipe';
import { SearchProjectPipe } from '../pipes/search-project.pipe';
import { ContactsearchPipe } from '../pipes/contactsearch.pipe';
import { SharedModule } from 'src/app/shared/shared.module';
import { ResourceBillingComponent } from './resource-billing/resource-billing.component';
import { PsadashboardComponent } from './psadashboard/psadashboard.component';
import { ChartsModule } from 'ng2-charts';
import { ScheduleComponent } from './schedule/schedule.component';
import { TmsinpsaComponent } from './tmsinpsa/tmsinpsa.component';
import { PeopleinpsaComponent } from './peopleinpsa/peopleinpsa.component';




@NgModule({
  declarations:
    [
      PsaComponent,
      ContactPSAComponent,
      ContractComponent,
      CustomerComponent,
      ProjectPSAComponent,
      PsareportsComponent,
      CustomersearchpipePipe,
      ContractsearchPipe,
      SearchProjectPipe,
      ContactsearchPipe,
      ResourceBillingComponent,
      PsadashboardComponent,
      ScheduleComponent,
      TmsinpsaComponent,
      PeopleinpsaComponent,
  
      
      
    
      
    ],

  imports:
    [
      CommonModule,
      PsaRoutingModule,
      FormsModule,
      ReactiveFormsModule,
      NgxPaginationModule,
      MatSelectModule,
      MatFormFieldModule,
      MatDatepickerModule,
      MatNativeDateModule,
      MatInputModule,
      SharedModule,
      ChartsModule,
  
    ]
})
export class PsaModule { }
