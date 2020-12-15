import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PsamastersRoutingModule } from './psamasters-routing.module';
import { ActionownerComponent } from './actionowner/actionowner.component';
import { ActiontypeComponent } from './actiontype/actiontype.component';
import { BillingtypeComponent } from './billingtype/billingtype.component';
import { BudgetlistComponent } from './budgetlist/budgetlist.component';
import { C2hComponent } from './c2h/c2h.component';
import { ContractcompanyComponent } from './contractcompany/contractcompany.component';
import { CountryComponent } from './country/country.component';
import { DeliverylocationComponent } from './deliverylocation/deliverylocation.component';
import { DocumentstageComponent } from './documentstage/documentstage.component';
import { DocumenttypeComponent } from './documenttype/documenttype.component';
import { GeolocationComponent } from './geolocation/geolocation.component';
import { GstlocationComponent } from './gstlocation/gstlocation.component';
import { InterviewmodeComponent } from './interviewmode/interviewmode.component';
import { InterviewresultComponent } from './interviewresult/interviewresult.component';
import { LocationtypeComponent } from './locationtype/locationtype.component';
import { PermstatusComponent } from './permstatus/permstatus.component';
import { ProjecttaskComponent } from './projecttask/projecttask.component';
import { ProjecttechstackComponent } from './projecttechstack/projecttechstack.component';
import { RatetypeComponent } from './ratetype/ratetype.component';
import { SarstatusComponent } from './sarstatus/sarstatus.component';
import { ServicecategoryComponent } from './servicecategory/servicecategory.component';
import { ServicetypeComponent } from './servicetype/servicetype.component';
import { SezlocationComponent } from './sezlocation/sezlocation.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { PsafilterPipe } from '../../pipes/psafilter.pipe';
import { ProjecttypeComponent } from './projecttype/projecttype.component';




@NgModule({
  declarations: [ActionownerComponent,
    ActiontypeComponent,
    BillingtypeComponent,
    BudgetlistComponent,
    C2hComponent,
    ContractcompanyComponent,
    CountryComponent,
    DeliverylocationComponent,
    DocumentstageComponent,
    DocumenttypeComponent,
    GeolocationComponent,
    GstlocationComponent,
    InterviewmodeComponent,
    InterviewresultComponent,
    LocationtypeComponent,
    PermstatusComponent,
    ProjecttaskComponent,
    ProjecttechstackComponent,
    RatetypeComponent,
    SarstatusComponent,
    ServicecategoryComponent,
    ServicetypeComponent,
    SezlocationComponent,
    PsafilterPipe,
    ProjecttypeComponent
  ],
  imports: [
    CommonModule,
    PsamastersRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,

  ]
})
export class PsamastersModule { }
