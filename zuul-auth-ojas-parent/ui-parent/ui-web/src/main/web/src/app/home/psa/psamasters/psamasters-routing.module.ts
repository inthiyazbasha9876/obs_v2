import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

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
import { ActionownerComponent } from './actionowner/actionowner.component';
import { ProjecttypeComponent } from './projecttype/projecttype.component';



const routes: Routes = [{ path: 'action-owner', component: ActionownerComponent },
{ path: 'actionType', component: ActiontypeComponent },
{ path: 'billing-type', component: BillingtypeComponent },
{ path: 'budgetlist', component: BudgetlistComponent },
{ path: 'c2h', component: C2hComponent },
{ path: 'contractcompany', component: ContractcompanyComponent },
{ path: 'country', component: CountryComponent },
{ path: 'deliverylocation', component: DeliverylocationComponent },
{ path: 'documentstage', component: DocumentstageComponent },
{ path: 'documentType', component: DocumenttypeComponent },
{ path: 'geolocation', component: GeolocationComponent },
{ path: 'gstlocation', component: GstlocationComponent },
{ path: 'interviewmode', component: InterviewmodeComponent },
{ path: 'interviewresult', component: InterviewresultComponent },
{ path: 'ProjectType', component:  ProjecttypeComponent },
{ path: 'locationtype', component: LocationtypeComponent },
{ path: 'permstatus', component: PermstatusComponent },
{ path: 'projecttask', component: ProjecttaskComponent },
{ path: 'projecttechstack', component: ProjecttechstackComponent },
{ path: 'ratetype', component: RatetypeComponent },
{ path: 'sarstatus', component: SarstatusComponent },
{ path: 'servicecategory', component: ServicecategoryComponent },
{ path: 'servicetype', component: ServicetypeComponent },
{ path: 'sezlocation', component: SezlocationComponent }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PsamastersRoutingModule { }
