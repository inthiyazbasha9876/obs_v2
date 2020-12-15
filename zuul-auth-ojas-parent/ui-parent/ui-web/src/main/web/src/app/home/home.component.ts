import { Component, OnInit, LOCALE_ID } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { HrmsService } from './services/hrms.service';
import { Router } from '@angular/router';
import { Ng2PicaService } from 'ng2-pica';
import { NotificationService } from '../services/toastr-notification/toastr-notification.service';
import swal from 'src/assets/sweetalert';
declare var $: any;
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  loggeduser;
  role;
  empbasicinfo: any;
  pic: any;
  profilepic: any;
  initials: any;
  userlogo: any;
  pic1: any;
  txt: any;
  empbid: any;
  imagedata: any
  fName
  lName
  managersList: any
  managerBtn: any = false
  constructor(private authService: AuthService, private hrms: HrmsService, private routerNavigate: Router, private ng2PicaService: Ng2PicaService, private toast: NotificationService) {
    this.loggeduser = this.authService.decryptData(sessionStorage.getItem('UserName'));
    this.role = this.authService.decryptData(sessionStorage.getItem('Role'));
    this.getAllEmployee()
    console.log("Role", this.role);
    if (this.role != "ROLE_USER" || this.role != "ROLE_MANAGER") {
      history.pushState(null, null, location.href);
      if (location.href.includes('dashboard')) {
        window.onpopstate = function () {
          history.go(0);
        };
      }
    }
    if (this.role == "ROLE_USER" || this.role == "ROLE_MANAGER") {
      history.pushState(null, null, location.href);
      if (location.href.includes('employee')) {
        window.onpopstate = function () {
          history.go(0);
        };
      }
    }
    this.setIdleTimeout(900000, function() {
      sessionStorage.clear()
      swal({
        title: "Sorry!",
        text: "Session Expired, Please login again",
        closeOnClickOutside: false,
        buttons: [
          ,
          'Ok!'
        ],
        dangerMode: true,
      })
        .then((close) => {
          if (close) {
            window.location.reload()
          }
        });
    }, function() {
    });
  }

  ngOnInit() {

    this.getImage();
  }

  getImage() {
    console.log("empid", this.loggeduser);

    var empbasic
    var empinfo =
    {

      "employeeInfo": [{
        "employeeId": this.loggeduser

      }],
      "transactionType": "getbyid"

    }
    this.hrms.getempinfo(empinfo).subscribe(res => {
      empbasic = res;
      this.empbasicinfo = empbasic.employeeInfo[0];
      console.log("Frstname lastname", this.empbasicinfo);
      this.empbid = this.empbasicinfo.id;
      sessionStorage.setItem("IdEmp", this.empbid);
      console.log("Getbyid Employee Info Object :", this.empbasicinfo.id);
      this.pic = this.empbasicinfo.image;
      this.fName = this.empbasicinfo.firstname;
      this.lName = this.empbasicinfo.lastname;
      if (this.pic != null) {
        this.profilepic = "data:image/jpeg;base64," + this.pic;
        this.pic1 = true;
        this.txt = false;
      } else {
        const f = this.fName[0];
        const l = this.lName[0];
        this.initials = f[0].toUpperCase() + l[0].toUpperCase();
        this.txt = true;
        this.pic1 = false;

        console.log("The First Letter of Fitst Name : ", f[0]);
        console.log("The First Letter of Last Name : ", l[0]);
        console.log("First and Last initails letters: ", this.initials);
      }

    });
  }

  logout() {
    swal({
      title: "Are you sure?",
      text: "Want to Logout?",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {


          if (this.authService.logOutAction()) {
            this.routerNavigate.navigate(['index'])
          }
          sessionStorage.removeItem("setUserRole");



        }
        else {

          this.toast.info('You did not logout', 'Info')
        }
      });



  }

  imageCompress(e) {
    console.log(e)
    this.ng2PicaService.resize(e.target.files, 300, 300).subscribe((result) => {
      console.info(result);
      this.fileSelected(result)
    }, error => {
      console.error(error);
    });
  }


  fileSelected(evt) {
    var files = evt;
    var file = files;
    if (files && file) {
      var reader = new FileReader();
      reader.onload = this.handleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }

  }

  handleReaderLoaded(readerEvt) {
    console.log(readerEvt)
    var binaryString = readerEvt.target.result;
    this.imagedata = btoa(binaryString);
    console.log("final image", this.imagedata);
    this.profilePicUpdation(this.imagedata)
  }



  profilePicUpdation(e) {
    var request =
    {
      "employeeInfo": [{
        "employeeId": this.loggeduser,
        "image": e
      }],
      "transactionType": "picUpdate"
    }
    console.log(request, "for request object")
    this.hrms.updateProfilepic(request).subscribe(res => {
      console.log(res);
      // this.toastr.successToastr("Profile pic updated successfully","Success",{
      //   showCloseButton:true,
      //   animate:'slideFromRight'
      // })
      this.toast.success("Profile pic updated successfully", "Success")
      this.getImage()
    }, err => {
      // this.toastr.infoToastr("Profile pic not updated","Info",{
      //   showCloseButton:true,
      //   animate:'slideFromRight'
      // })
      this.toast.info("Profile pic not updated", "Info")
    })
  }
  managerRole2() {
    sessionStorage.removeItem("setUserRole");
  }
  managerRole3() {
    sessionStorage.setItem("setUserRole", "true");
  }


  getAllEmployee() {
    var response
    var list = new Set()
    var reqObj = {
      "employeeInfo": [{
      }],
      "transactionType": "getAllInfo"
    }
    this.hrms.getempinfo(reqObj).subscribe(res => {
      console.log("res", res);
      response = res
      response.employeeInfo.map(d =>
        list.add(d.reportingManager))
      console.log(list, "list");

      this.managersList = Array.from(list)
      for (let i in this.managersList) {
        if (this.loggeduser == this.managersList[i]) {
          this.managerBtn = true
        }
      }
    })
  }

  setIdleTimeout(millis, onIdle, onUnidle) {
    let timeout: any = 0;
    startTimer();

    function startTimer() {
      timeout = setTimeout(onExpires, millis);
      document.addEventListener("mousemove", onActivity);
      document.addEventListener("keypress", onActivity);
    }

    function onExpires() {
      timeout = 0;
      onIdle();
    }

    function onActivity() {
      if (timeout) clearTimeout(timeout);
      else onUnidle();
      document.removeEventListener("mousemove", onActivity);
      document.removeEventListener("keypress", onActivity);
      setTimeout(startTimer, 1000);
    }
  }
}
