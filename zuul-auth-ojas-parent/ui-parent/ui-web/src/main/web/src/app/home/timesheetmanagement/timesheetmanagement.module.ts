import { SharedModule } from 'src/app/shared/shared.module';
import { TmsPipe } from './../pipes/tms.pipe';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TimesheetmanagementRoutingModule } from './timesheetmanagement-routing.module';
import { TimesheetmanagementComponent } from './timesheetmanagement.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material';
// import { TmsPipe } from '../pipes/tms.pipe';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  declarations: [TimesheetmanagementComponent],
  imports: [
    CommonModule,
    TimesheetmanagementRoutingModule,
    RouterModule,
    FormsModule,
    MatSelectModule,
     MatFormFieldModule,
     MatDatepickerModule,
     ReactiveFormsModule,
     MatDatepickerModule,
     MatButtonModule,
     MatFormFieldModule,
     MatNativeDateModule,
     MatInputModule,
     NgxPaginationModule,
     SharedModule
  ]
})
export class TimesheetmanagementModule { }
