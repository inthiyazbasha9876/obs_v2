import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LmsmastersRoutingModule } from './lmsmasters-routing.module';
import { HolidayComponent } from './holiday/holiday.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { LmsPipe } from '../../pipes/lms.pipe';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_LABEL_GLOBAL_OPTIONS, MatInputModule } from '@angular/material';
import { LeavetypeComponent } from './leavetype/leavetype.component';
@NgModule({
  declarations: [HolidayComponent,LmsPipe, LeavetypeComponent],
  imports: [
    CommonModule,
    LmsmastersRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    MatSelectModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule
  ]
})
export class LmsmastersModule { }
