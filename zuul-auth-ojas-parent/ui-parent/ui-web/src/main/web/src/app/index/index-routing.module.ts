import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { LoginGuard } from '../guards/login.guard';
import { IndexComponent } from './index.component';

const routes: Routes = [
  { path: '',component:IndexComponent,canActivate:[LoginGuard],children:[
    {path:'',redirectTo:'login',pathMatch:'full'},
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'forgotpassword', component: ForgotpasswordComponent }
  ] },
 
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IndexRoutingModule { }
