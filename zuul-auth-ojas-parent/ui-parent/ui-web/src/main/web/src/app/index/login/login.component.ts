import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { LocationStrategy } from '@angular/common';

declare var $: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  OBSLoginForm: FormGroup;
  forgotForm;
  userid: any;

  responseData: any;
  multiple_roles: any;
  roles: any = []
  status
  val: any = "password";
  eye1 = true;
  username: any;
  encryptSecretKey = "OJAS-OBS";
  encryptedvalue: any;
  decryptedvalue: any;
  encryptedlocalstoragedata: string;
  role: any;
  encryptedusername: any;
  encryptedUsername: string;
  msg:any
  loading: boolean = false;

  uniqCaptcha:any
  constructor(private fb: FormBuilder, private authService: AuthService, private routerNavigate: Router, private locationStrategy: LocationStrategy) {
    this.OBSLoginForm = this.fb.group({
      'UserName': [null, (Validators.required, Validators.pattern('[0-9]*'))],
      'UserPassword': [null, Validators.required],
      'Captcha': [null, Validators.required],
      // 'ReCaptcha': [null, Validators.required],
    });
    this.userid = this.OBSLoginForm;
    this.forgotForm = new FormGroup({
      'eid': new FormControl('', [Validators.required, Validators.pattern('[0-9]*[A-Z]*')])
    });
    this.loading = false;
  }

  eye() {
    if (this.eye1 == true) {
      this.val = "text";
      this.eye1 = false;
    }
    else {
      this.val = "password";
      this.eye1 = true;
    }

  }
  captchastatus:boolean = false;
  LoginAction(formData: any) {
    this.loading = true;
    this.captchastatus=false;
    if (formData.Captcha!=this.uniqCaptcha) {
      this.msg = "Invalid Captcha";
      this.captchastatus=true;
      this.loading=false 
    } else {
      this.captchastatus=false;
      console.log("one");
      
      this.authService.loginservice(formData).subscribe(res => {
        console.log("login", res);

        this.multiple_roles = res['authorities']
        this.roles.push(this.multiple_roles.split(" ", 1));

        this.roles.push(this.multiple_roles);
        sessionStorage.setItem('userData', res['authorization']);
        // sessionStorage.setItem("UserName", formData.UserName);
        this.encryptedusername = this.authService.encryptData(formData.UserName)
        sessionStorage.setItem("UserName", this.encryptedusername);
        // sessionStorage.setItem("Role", this.roles[0]);

        console.log("loginaction", this.roles[0]);
        this.encryptedvalue = this.authService.encryptData(this.roles[0])
        console.log("encrypt", this.encryptedvalue);
        sessionStorage.setItem("Role", this.encryptedvalue)
        this.encryptedlocalstoragedata = sessionStorage.getItem('Role');
        this.role = this.authService.decryptData(this.encryptedlocalstoragedata);
        // this.username = sessionStorage.getItem('UserName');
        this.encryptedUsername = sessionStorage.getItem('UserName');
        this.username = this.authService.decryptData(this.encryptedUsername);
        console.log("decrypted username", this.username);


        if(res['pwdStatus']==false){
          this.routerNavigate.navigate(['home/changepassword'])
        }else{
          if (this.role == "ROLE_USER") {
            sessionStorage.setItem("setUserRole", "true");
            this.routerNavigate.navigate([`home/hrms/employee/employeeedit/${this.username}`])
            this.loading = false;
            // this.routerNavigate.navigate(['home/dashboard']);
          }
          else if (this.role == "ROLE_MANAGER") {
            this.routerNavigate.navigate([`home/hrms/employee`])
            this.loading = false;
            // this.routerNavigate.navigate(['home/dashboard']);
          }
          else {
            //this.routerNavigate.navigate(['dashboard']);
            this.routerNavigate.navigate(['home/dashboard']);
            this.loading = false;
  
          }
  

        }
        
      },

        err => {
          console.log(err)
          console.log("error");
          this.status = true;
          this.loading = false;
        })
    }
  }

  ngOnInit() {
   this.random()
    $(document).ready(function () {
      $('.login-content [data-toggle="flip"]').click(function () {
        $('.login-box').toggleClass('flipped');
        return false;
      });
    });


    this.OBSLoginForm = this.fb.group({
      'UserName': [null, (Validators.required, Validators.pattern('[0-9]*'))],
      'UserPassword': [null, Validators.required],
      'Captcha': [null, Validators.required],
      // 'ReCaptcha': [null, Validators.required],
    });
    this.userid = this.OBSLoginForm;
    this.forgotForm = new FormGroup({
      'eid': new FormControl('', [Validators.required, Validators.pattern('[0-9]*[A-Z]*')])
    });

    sessionStorage.removeItem("error")
  }

  link() {
    if (this.OBSLoginForm.invalid)
      return false;

  }
  //jquery
  isError: boolean = false;
  errMsg;
  mailResp;

  sendMail() {
    console.log('forgot clicked');
    let req = {
      'forgotPassword':
      {
        'employeeId': this.forgotForm.value.eid
      }
      ,
      'transactionType': 'sendMail'
    };
    this.routerNavigate.navigate(['index/forgotpassword']);
    this.authService.sendOtp(req).subscribe(res => {
      this.mailResp = res;


    },
      err => {
        this.mailResp = err;
        this.isError = true;
        this.errMsg = this.mailResp.error.message;
        this.authService.errorMsg = this.isError;
        console.log('Error state in login : ', this.authService.errorMsg);


      }
    );

  }
  number(e) {
    var key = e.keyCode
    if (key >= 48 && key <= 57)
      return true
    else
      return false
  }

  random() {
    this.OBSLoginForm.controls.Captcha.reset()
    var result = '';
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$&=';
    for (var i = 0; i < 6; i++) {
     result +=characters.charAt(Math.floor(Math.random() * characters.length)) ;
    }
    this.uniqCaptcha = result;
    this.image(this.uniqCaptcha)
  }

  image(e){
    var canvas = document.getElementById("captcha") as HTMLCanvasElement;
    var ctx = canvas.getContext("2d");
    var gradient = ctx.createLinearGradient(0, 0, canvas.width, 0);
    gradient.addColorStop(0.2, this.randomcolor());
    gradient.addColorStop(0.5, this.randomcolor());
    gradient.addColorStop(1.0, this.randomcolor()); 
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.font = 'italic 38px Marker Felt, monospace';
    ctx.fillStyle = gradient;
    ctx.fillText(e, 10,40);
    }

    randomcolor(){
      var myArray = ['#1b262c', '#151965', '#2d132c','#443737','#202040','#000272','#01024e','#44000d','#ff4d00','#b030b0','#a72693','#ff0000','#212121','#000000']; 
      var rand = myArray[(Math.random() * myArray.length) | 0]
      // var letters = '0123456789ABCDEF';
      // var color = '#';
      // for (var i = 0; i < 6; i++) {
      //   color += letters[Math.floor(Math.random() * 16)];
      // }
      return rand;
    }

    fun(){
      this.captchastatus=false
    }
}
