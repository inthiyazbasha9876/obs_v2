import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as CryptoJS from 'crypto-js';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  accessToken: any;
  responseData: any;
  multiple_roles: any;
  roles: any = []
  errorMsg: boolean;
  constructor(private http: HttpClient,private routerNavigate:Router) { }
  public isAuthenticate(): boolean {
    //method return true or false based on login credential
    const userData = sessionStorage.getItem('userData');

    if (userData && userData.length > 0) {
      return true;
    }
    else {
      return false;
    }
  }

  url: any = `${environment.host_url}`

  public loginservice(postData) {

    var userObj = { username: postData.UserName, password: postData.UserPassword }
    return this.http.post(this.url + '/obs/login', userObj);
  }

  public SignUp(postData) {
    //registraion api
  }
  public async logOutAction() {
    //session/local storage clear

    return true;
  }

  public async getUserdata() {

    const userData = sessionStorage.getItem('userData');
    return JSON.parse(userData)
  }

  public getToken(): string {
    return sessionStorage.getItem('userData');
  }

  public getEmployeeData(): any {
    var res = this.http.get('http://192.168.7.64:8089/backend/user');
    return res;
  }


  public sendOtp(req) {
    return this.http.post(this.url + '/obs/forgot/set', req);
  }

  public updatePassword(req) {
    return this.http.post(this.url + '/obs/forgot/set', req);
  }

  public ResetPassword(req) {
    return this.http.post(this.url + '/obs/ResetPassword/set', req);

  }


  encryptSecretKey = "OJAS-OBS";
  encryptedvalue: any;
  decryptedvalue: any;

  encryptData(data) {

    try {

      return CryptoJS.AES.encrypt(JSON.stringify(data), this.encryptSecretKey).toString();

    } catch (e) {
      console.log(e);
    }
  }

  decryptData(data) {

    try {

      if(data.includes("ROLE")|| !isNaN(data)){

        if (this.logOutAction()) {
          this.routerNavigate.navigate(['index'])
        }
        sessionStorage.removeItem("setUserRole");

      }else{
        const bytes = CryptoJS.AES.decrypt(data, this.encryptSecretKey);
        if (bytes.toString()) {
          return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
        }
        return data;
      }
      
    } catch (e) {
      console.log(e);
    }
  }

}
