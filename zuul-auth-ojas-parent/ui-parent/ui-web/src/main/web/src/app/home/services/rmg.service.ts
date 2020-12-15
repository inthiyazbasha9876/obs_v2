import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RmgService {


  host_url: any;




  constructor(private http: HttpClient, private _router: Router) {
    this.host_url = `${environment.host_url}`
  }

  getAllResource(resourceData) {
    return this.http.post(this.host_url + '/obs/rmg/get', resourceData);
  }
  getResourcesbyId(resourcedata) {
    return this.http.post(this.host_url + '/obs/rmg/get', resourcedata);
  }
  saveresources(saveobj) {
    return this.http.post(this.host_url +'/obs/rmg/set', saveobj);
    // return this.http.post('http://192.168.2.221:7100/set', saveobj);

  }
  updateresources(updatedobj) {
    return this.http.post(this.host_url + '/obs/rmg/set', updatedobj);
  }
  deleteresources(deleteobj) {
    return this.http.post(this.host_url + '/obs/rmg/get', deleteobj);
  }
  releaseRes(obj) {
    return this.http.post(this.host_url + "/obs/rmg/set", obj)
  }


  //RMG emp-Experience Start
  setempExperience(empExperienceReqObj) {
    return this.http.post(this.host_url + '/obs/master/rmgemployeeexperience/set', empExperienceReqObj);

  }
  getempExperience(getempExperienceReqObj) {
    return this.http.post(this.host_url + '/obs/master/rmgemployeeexperience/get', getempExperienceReqObj);

  }
  updatempExperience(updategetempExperienceReqObj) {
    return this.http.post(this.host_url + '/obs/master/rmgemployeeexperience/set', updategetempExperienceReqObj);

  }

  //RMG emp-Experience End

  //based on skills starts
  getAllSkills(getskillsobj) {
    return this.http.post(this.host_url + '/obs/rmg/get', getskillsobj);
  }
  //based on skills ends

  navigateToDashboard() {
    this._router.navigate(['home/hrms/dashboard']);
  }


  getAllEmp(reqobj) {
    return this.http.post(this.host_url + '/obs/rmg/get', reqobj);
  }

  getEmpByProjectId(obj) {
    return this.http.post(this.host_url + '/obs/rmg/get', obj);
  }

  getEmpAvailability(obj){
    return this.http.post(this.host_url + '/obs/rmg/get', obj);
  }

}
