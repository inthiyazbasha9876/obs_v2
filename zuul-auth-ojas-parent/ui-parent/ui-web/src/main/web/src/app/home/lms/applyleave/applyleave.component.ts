import { Component, OnInit } from '@angular/core';
import { takeUntil } from 'rxjs/operators';
import { HrmsService } from '../../services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { LeaveserviceService } from '../lmsservices/leaveservice.service';
import { AuthService } from 'src/app/services/auth.service';
import { Subject } from 'rxjs';
import { FormGroup, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-applyleave',
  templateUrl: './applyleave.component.html',
  styleUrls: ['./applyleave.component.scss']
})
export class ApplyleaveComponent implements OnInit {
  //local storage declarations
  cl: any = 0
  sl: any = 0
  ml: any = 0
  cf: any = 0
  lop: any = 0
  encryptSecretKey: any = "OJAS-OBS"
  _MS_PER_DAY = 1000 * 60 * 60 * 24;
  //declarartions

  leavetypelist: any = []
  leaveApplication = new FormGroup({
    leaveType: new FormControl('', Validators.required),
    fromDate: new FormControl('', Validators.required),
    toDate: new FormControl('', Validators.required),
    fromSession: new FormControl('Session1'),
    toSession: new FormControl('Session2'),
    applyTo: new FormControl(''),
    ccTo: new FormControl(''),
    numberOfDays: new FormControl(''),
    balance: new FormControl(''),
    reason: new FormControl('', Validators.required),
    fileName: new FormControl(''),
    fileData: new FormControl(''),
  });

  tomin: any
  fromMax: any = null
  loginEmp: any
  appliedleaves: any = []
  r_manager: any
  r_name: any
  ltype: any = 0
  empall: any
  empdata: any
  leavetype: any = 'leaveapply'
  empJoiningDate: any
  calenderHolidays: any = []
  //boolean declarations
  leavegrant: any = true
  applyBtn: any = false
  private unsubscribe = new Subject();
  constructor(private hrms: HrmsService, private toastr: NotificationService, private lms: LeaveserviceService, private authfun: AuthService) {
    this.loginEmp = this.authfun.decryptData(sessionStorage.getItem('UserName'))
    this.getempinfo()
    this.getallEmp()
  }

  ngOnInit() {
    this.getHolidays()
    this.getleaves()
    this.leaveapplication()
    this.getempleaveapplictions()
    this.getEmpOnBoarding() 
  }

  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

  getallEmp() {
    var response
    var empinfo =
    {
      "employeeInfo": [{

      }],
      "transactionType": "getall"
    }

    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      console.log("all employe", res)
      response = res
      var data = response.employeeInfo
      this.empall = data.map(d => new Object({ name: d.firstname + " " + d.lastname, email: d.officialEmail }))
      console.log("filter data", this.empall);

    })
  }

  getempinfo() {
    var empinfo =
    {

      "employeeInfo": [{
        "employeeId": this.loginEmp
      }],
      "transactionType": "getbyid"
    }
    let emp_info
    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      emp_info = res
      this.empdata = emp_info.employeeInfo
      this.r_manager = this.empdata[0].reportingManager
      this.getreportingmanager(this.r_manager)

    })
  }

  getreportingmanager(e) {
    var obj = {
      "employeeInfo": [{
        "employeeId": e
      }],
      "transactionType": "getbyid"
    }
    console.log(e)
    let data
    this.hrms.getempinfo(obj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      data = res
      var fs = data.employeeInfo[0].firstname
      var ls = data.employeeInfo[0].lastname
      this.r_name = fs[0].toUpperCase() + fs.slice(1) + " " + ls[0].toUpperCase() + ls.slice(1)
      console.log("one", this.r_name)
    })
  }


  leaveapplication() {
    this.leavegrant = true
    this.leavetypelist = ['Loss of Pay', 'Casual Leave', 'Maternity Leave', 'Comp Off', 'Sick Leave']
    this.leavetype = 'leaveapply'
    this.tomin = null
    this.fromMax = null
    this.clear()
  }

  leavegrantapplication() {
    this.leavegrant = false
    this.leavetype = 'leavegrant'
    this.leavetypelist = ['Maternity Leave', 'Comp Off']
    this.tomin = null
    this.fromMax = null
    this.clear()
  }

  getleaves() {
    var reqObj = {
      "leaveBalance": {
        "empId": this.loginEmp
      },
      "transationType": "getallleavebal"
    }
    console.log("req", reqObj)
    var resObj
    this.lms.lmsget(reqObj).subscribe(res => {
      resObj = res
      this.cl = resObj.leaveBalList.totalCasualLeave - resObj.leaveBalList.consumedCasualLeave
      this.sl = resObj.leaveBalList.totalSickLeave - resObj.leaveBalList.consumedSickLeave
      this.ml = resObj.leaveBalList.totalMaternityLeave - resObj.leaveBalList.consumedMaternityLeave
      this.lop = this.lop - resObj.leaveBalList.lossOfPay
      this.cf = resObj.leaveBalList.totalCompOff - resObj.leaveBalList.consumedCompOff
    })
  }

  leaveTypeSelected(e) {
    if (this.leavetype == "leaveapply") {
      if (e.value == "Comp Off") {
        this.leaveApplication.controls.balance.setValue(this.cf)
      } else if (e.value == "Loss of Pay") {
        this.leaveApplication.controls.balance.setValue(this.lop)
      } else if (e.value == "Casual Leave") {
        this.leaveApplication.controls.balance.setValue(this.cl)
      } else if (e.value == "Maternity Leave") {
        this.leaveApplication.controls.balance.setValue(this.ml)
      } else if (e.value == "Sick Leave") {
        this.leaveApplication.controls.balance.setValue(this.sl)
      }
      this.leaveApplication.controls.applyTo.setValue(this.r_name)
      this.ltype = 1
      this.numberOfdaysCal()
    } else {
      var currdate = new Date()
      if (e.value == "Comp Off") {
        console.log("compoff");
        this.leaveApplication.controls.applyTo.setValue(this.r_name)
        this.ltype = 1
        this.numberOfdaysCal()
      } else if (e.value == "Maternity Leave") {
        var diff = this.dateDiffInDays(new Date(this.empJoiningDate), currdate)
        if (diff >= 151) {
          this.leaveApplication.controls.applyTo.setValue(this.r_name)
          this.ltype = 1
          this.numberOfdaysCal()
        } else {
          this.leaveApplication.disable()
          this.applyBtn = true
          this.toastr.info("You are not avail to apply maternity leaves")
        }

      }

    }

  }

  clear() {
    this.leaveApplication.enable()
    this.leaveApplication.reset()
    this.leaveApplication.clearValidators()
    this.leaveApplication.clearAsyncValidators()
    this.leaveApplication.controls.fromSession.setValue('Session1')
    this.leaveApplication.controls.toSession.setValue('Session2')
    this.ltype = 0
    this.applyBtn = false
  }

  fromSelection(e) {
    var c = 0
    var a = 0
    console.log("applied", this.appliedleaves)
    if (this.leavetype == "leaveapply") {
      this.calenderHolidays.map((d) => {
        if (d == this.formatDate(e.target.value)) {
          c++
        }
      })
      if (new Date(e.target.value).getDay() == 0 || new Date(e.target.value).getDay() == 6 || c > 0) {
        this.leaveApplication.controls.fromDate.setErrors({ 'incorrect': true })
        this.toastr.info("You cann't apply leave for holidays")
        this.tomin = this.leaveApplication.value.fromDate
      } else {
        for (let i in this.appliedleaves) {
          if (this.appliedleaves[i] == this.formatDate(e.target.value)) {
            a++
          }
        }
        if (a > 0) {
          this.leaveApplication.controls.fromDate.setErrors({ 'incorrect': true })
          this.toastr.info("You had already avail holiday for this day")
          this.tomin = this.leaveApplication.value.fromDate
        } else {
          this.leaveApplication.controls.fromDate.setErrors(null)
          this.tomin = this.leaveApplication.value.fromDate
          this.numberOfdaysCal()
        }

      }
    } else {
      this.tomin = this.leaveApplication.value.fromDate
      this.numberOfdaysCal()
    }

  }

  toSelection(e) {
    var c = 0
    var a = 0
    console.log("applied", this.appliedleaves)
    if (this.leavetype == "leaveapply") {
      this.calenderHolidays.map((d) => {
        if (d == this.formatDate(e.target.value)) {
          c++
        }
      })
      if (new Date(e.target.value).getDay() == 0 || new Date(e.target.value).getDay() == 6 || c > 0) {
        this.leaveApplication.controls.toDate.setErrors({ 'incorrect': true })
        this.toastr.info("You cann't apply leave for holidays")
        this.fromMax = this.leaveApplication.value.toDate
      } else {
        for (let i in this.appliedleaves) {
          if (this.appliedleaves[i] == this.formatDate(e.target.value)) {
            a++
          }
        }
        if (a > 0) {
          this.leaveApplication.controls.toDate.setErrors({ 'incorrect': true })
          this.toastr.info("You had already avail holiday for this day")
          this.fromMax = this.leaveApplication.value.toDate
        } else {
          this.leaveApplication.controls.toDate.setErrors(null)
          this.fromMax = this.leaveApplication.value.toDate
          this.numberOfdaysCal()
        }

      }
    } else {
      this.fromMax = this.leaveApplication.value.toDate
      this.numberOfdaysCal()
    }
  }

  fromSession() {
    this.numberOfdaysCal()
  }

  toSession() {
    this.numberOfdaysCal()
  }

  numberOfdaysCal() {
    console.log(this.leaveApplication)
    if (this.leaveApplication.value.fromDate != null && this.leaveApplication.value.toDate != null) {
      var from = new Date(this.leaveApplication.value.fromDate)
      var to = new Date(this.leaveApplication.value.toDate)
      var fsession = this.leaveApplication.value.fromSession
      var tsession = this.leaveApplication.value.toSession
      if (this.ltype == 1) {
        if (this.formatDate(from) == this.formatDate(to)) {
          if (fsession == "Session1" && tsession == "Session2") {
            this.leaveApplication.controls.numberOfDays.setValue(this.dateDiffInDays(from, to))
          } else if (fsession == "Session1" && tsession == "Session1" || fsession == "Session2" && tsession == "Session2") {
            this.leaveApplication.controls.numberOfDays.setValue(this.dateDiffInDays(from, to) - 0.5)
          } else if (fsession == "Session2" && tsession == "Session1") {
            this.leaveApplication.controls.numberOfDays.setValue(this.dateDiffInDays(from, to) - 1)
            this.toastr.info("you had selected invalid sessions")
          }
        } else {
          if (fsession == "Session1" && tsession == "Session2") {
            this.leaveApplication.controls.numberOfDays.setValue(this.dateDiffInDays(from, to))
          } else if (fsession == "Session1" && tsession == "Session1" || fsession == "Session2" && tsession == "Session2") {
            this.leaveApplication.controls.numberOfDays.setValue(this.dateDiffInDays(from, to) - 0.5)
          } else if (fsession == "Session2" && tsession == "Session1") {
            this.leaveApplication.controls.numberOfDays.setValue(this.dateDiffInDays(from, to) - 1)
          }
        }
      }

    } else {
      this.leaveApplication.controls.numberOfDays.setValue(0)
    }
  }

  applyLeave(e) {
    console.log("leaveapplication", e)
    var today = new Date()
    var reqObj
    var response
    var dateAndtime = this.formatDate(today) + " " + today.toLocaleTimeString()
    if (this.leavetype == 'leaveapply') {
      if (e.numberOfDays > e.balance && e.leaveType != "Loss of Pay") {
        this.toastr.info("You don't have enough leave balance")
      } else {
        reqObj = {
          "leaveInfo": {
            "empId": this.loginEmp,
            "leaveType": e.leaveType,
            "fromDate": this.formatDate(e.fromDate),
            "toDate": this.formatDate(e.toDate),
            "session1": e.fromSession,
            "session2": e.toSession,
            "countNumOfDays": e.numberOfDays,
            "leaveReason": e.reason,
            "ccTo": e.ccTo,
            "applyType": this.leavetype,
            "applyTo": this.empdata[0].reportingManager,
            "contactDetails": this.empdata[0].personalMobileNo,
            "attachment": e.fileData,
            "flag": true,
            "fileName": e.fileName,
            "status": "pending",
            "appliedOn": dateAndtime
          },
          "transationType": "save"
        }
      }
      console.log("request for leav apply", reqObj)
    }
    if (this.leavetype == 'leavegrant') {
      reqObj = {
        "leaveInfo": {
          "empId": this.loginEmp,
          "leaveType": e.leaveType,
          "fromDate": this.formatDate(e.fromDate),
          "toDate": this.formatDate(e.toDate),
          "session1": e.fromSession,
          "session2": e.toSession,
          "countNumOfDays": e.numberOfDays,
          "leaveReason": e.reason,
          "ccTo": e.ccTo,
          "applyType": this.leavetype,
          "applyTo": this.empdata[0].reportingManager,
          "contactDetails": this.empdata[0].personalMobileNo,
          "flag": true,
          "status": "pending",
          "appliedOn": dateAndtime
        },
        "transationType": "save"
      }
      console.log("request for leav apply", reqObj)
    }
    if (reqObj != null) {
      this.lms.lmsset(reqObj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
        console.log("applied leave", res)
        response = res
        this.toastr.success('Leave applied successfully')
        this.clear()
        this.getleaves()
      }, err => {
        console.log(err)
        this.toastr.info("Your leave hadn't applied")
      })
    }




  }



  fileSelected(evt) {
    //this.submitBtn = false
    var files = evt.target.files;
    var file = files[0];
    console.log("filename", file.name)
    this.leaveApplication.controls.fileName.setValue(file.name)
    if (files && file) {
      var reader = new FileReader();
      reader.onload = this.handleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }

  }

  handleReaderLoaded(readerEvt) {
    console.log(readerEvt)
    var binaryString = readerEvt.target.result;
    var fileData = btoa(binaryString);
    console.log(fileData);
    this.leaveApplication.controls.fileData.setValue(fileData)
  }

  dateDiffInDays(a, b) {
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
    var ndays = Math.floor((utc2 - utc1) / this._MS_PER_DAY) + 1
    var n = 0;
    var c = 0;
    var r = 0;
    var al = 0
    if (this.leavetype == 'leaveapply') {
      while (n < ndays) {
        var from = new Date(a.getFullYear(), a.getMonth(), a.getDate() + n)
        this.appliedleaves.map(d => {
          if (d == this.formatDate(from)) {
            al++
          }
        })
        this.calenderHolidays.map((d) => {
          if (d == this.formatDate(from)) {
            r++
          }
        })
        if (from.getDay() == 0 || from.getDay() == 6) {
          c++;
        }
        n++;
      }
      console.log(ndays, c, r, al)
      return ndays - c - r - al;
    } else {
      return ndays
    }

  }

  getapplieddates(a, b) {
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
    var ndays = Math.floor((utc2 - utc1) / this._MS_PER_DAY) + 1
    var n = 0;
    while (n < ndays) {
      var from = new Date(a.getFullYear(), a.getMonth(), a.getDate() + n)
      this.appliedleaves.push(this.formatDate(from))
      n++;
    }
  }

  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }

  getEmpOnBoarding() {
    var requestobj =
    {
      "employmentDetails": [{
        "employeeId": this.loginEmp
      }],
      "transactionType": "getAll"
    }
    let empOnboarding
    this.hrms.getonboardingdetails(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      console.log("onboarding details", res)
      empOnboarding = res
      this.empJoiningDate = empOnboarding.employmentDetailsList[0].joiningDate
      console.log("Employee Joining Date", this.empJoiningDate)
    })
  }


  getempleaveapplictions() {
    var response
    var reqObj = {
      "leaveInfo": {
        "empId": this.loginEmp
      },

      "transationType": "getallleaveinfo"
    }
    this.lms.lmsget(reqObj).subscribe(res => {
      response = res
      var obj = response.leaveInfo
      obj.map(d => {
        if (d.status == "approved")
          this.getapplieddates(new Date(d.fromDate), new Date(d.toDate))
      })
      console.log("res", this.appliedleaves);
    })
  }


  getHolidays() {
    var response
    var reqObj = {
      "holidayslist": [
        {

        }
      ],
      "transactionType": "getall"
    }
    this.lms.lmsholidayget(reqObj).subscribe(res=>{
      console.log(res);
      response=res
      this.calenderHolidays=response.holidayslist.map(d=>d.holidayDate)
      console.log("calender",this.calenderHolidays);
      
    })
  }
}
