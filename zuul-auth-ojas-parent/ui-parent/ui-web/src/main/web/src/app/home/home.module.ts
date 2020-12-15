import { DashboardComponent } from './dashboard/dashboard.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { Ng2PicaModule } from 'ng2-pica';
import { NotificationComponent } from '../services/toastr-notification/toastr-notification.component';
import { ChangepasswordComponent } from './changepassword/changepassword.component';
import { MustMatchDirective } from './hrms/_helpers/must-match.directive';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PersonaldetailsComponent } from './personaldetails/personaldetails.component';
import { DirectoryComponent } from './directory/directory.component';
import { CareersComponent } from './careers/careers.component';

import { NgxPaginationModule } from 'ngx-pagination';
import { SearchDirectoryPipe } from './pipes/search-directory.pipe';



@NgModule({
  declarations:
    [
      HomeComponent,
      NotificationComponent,
      ChangepasswordComponent,
      MustMatchDirective,
      PersonaldetailsComponent,
      DirectoryComponent,
      CareersComponent,
      SearchDirectoryPipe,
    

      
      
    ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    Ng2PicaModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    
  ]
})
export class HomeModule { }
