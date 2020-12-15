import { DashboardComponent } from './dashboard/dashboard.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';
import { AuthGuard } from '../guards/auth.guard';
import { ChangepasswordComponent } from './changepassword/changepassword.component';
import { PersonaldetailsComponent } from './personaldetails/personaldetails.component';
import { DirectoryComponent } from './directory/directory.component';
import { CareersComponent } from './careers/careers.component';

const routes: Routes = [
  {
    path: '', component: HomeComponent, canActivate: [AuthGuard], canDeactivate: [AuthGuard],
    children: [
      { path: 'hrms', loadChildren: () => import('./hrms/hrms.module').then(m => m.HrmsModule) },
      { path: 'tms', loadChildren: () => import('./timesheetmanagement/timesheetmanagement.module').then(m => m.TimesheetmanagementModule) },
      { path: 'psa', loadChildren: () => import('./psa/psa.module').then(m => m.PsaModule) },
      { path: 'rmg', loadChildren: () => import('./rmg/rmg.module').then(m => m.RmgModule) },
      { path: 'lms', loadChildren: () => import('./lms/lms.module').then(m => m.LmsModule) },
      { path: 'changepassword', component: ChangepasswordComponent },
      { path: 'personaldetails', component: PersonaldetailsComponent },
      { path: 'directory', component: DirectoryComponent },
      { path: 'careers', component: CareersComponent },
      { path: 'dashboard', loadChildren: () =>  import('./dashboard/dashboard.module').then(m => m.DashboardModule) }
    ]
  },
  { path: '**', component: HomeComponent },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
