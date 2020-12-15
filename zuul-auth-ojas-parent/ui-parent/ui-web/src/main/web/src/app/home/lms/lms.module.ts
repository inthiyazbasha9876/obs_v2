import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LmsRoutingModule } from './lms-routing.module';
import { LmsComponent } from './lms.component';
import { ApplyleaveComponent } from './applyleave/applyleave.component';
import { LeavesummaryComponent } from './leavesummary/leavesummary.component';
import { ReviewComponent } from './review/review.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_LABEL_GLOBAL_OPTIONS, MatInputModule } from '@angular/material';
import { LeavecalendarComponent } from './leavecalendar/leavecalendar.component';
@NgModule({
  declarations: [LmsComponent, ApplyleaveComponent, LeavesummaryComponent, ReviewComponent, LeavecalendarComponent],
  imports: [
    CommonModule,
    LmsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule
  ]
})
export class LmsModule { }
