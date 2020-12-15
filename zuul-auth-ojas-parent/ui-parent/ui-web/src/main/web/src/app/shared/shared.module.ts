import { TmsPipe } from './../home/pipes/tms.pipe';
import { RmgPipe } from './../home/pipes/rmg.pipe';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReplacePipe } from '../home/pipes/replace.pipe';



@NgModule({
  declarations: [
    ReplacePipe, RmgPipe, TmsPipe
  ],
  exports:[ReplacePipe, RmgPipe, TmsPipe],
  imports: [
    CommonModule,
  ]
})
export class SharedModule { }
