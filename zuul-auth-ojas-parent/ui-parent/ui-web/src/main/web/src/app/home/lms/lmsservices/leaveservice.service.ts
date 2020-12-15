import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LeaveserviceService {
  navigateToDashboard() {
    this._router.navigate(['home/dashboard']);
  }

  host_url: string;

  constructor(private http: HttpClient,private _router: Router) {
    this.host_url = `${environment.host_url}`
  }
  //leave type strats//

  leavetypeget(LeaveType) {
     return this.http.post(this.host_url+'/obs/lms/get', LeaveType);
  }

  leavetypeset(LeaveType) {
    return this.http.post(this.host_url+'/obs/lms/set', LeaveType);
  }

  lmsholidayget(reqObj){
    return this.http.post(this.host_url+'/obs/master/holidays/get', reqObj);
  }
  lmsholidayset(reqObj){
    return this.http.post(this.host_url+'/obs/master/holidays/set', reqObj);
  }

  lmsget(reqObj){
     return this.http.post(this.host_url+'/obs/lms/get',reqObj)  
  }
  lmsset(reqObj){
     return this.http.post(this.host_url+'/obs/lms/set',reqObj)
  }


  getLeaveType(req){
    return this.http.post(this.host_url+"/obs/master/leavetype/get",req)
  }
  setLeaveType(req){
    return this.http.post(this.host_url+"/obs/master/leavetype/set",req)
  }
  updateLeaveType(req){
    return this.http.post(this.host_url+"/obs/master/leavetype/set",req)
  }
}
