import { Component, OnInit } from '@angular/core';
import { LeaveserviceService } from '../lmsservices/leaveservice.service';
import { AuthService } from 'src/app/services/auth.service';
import { HrmsService } from '../../services/hrms.service';

@Component({
  selector: 'app-leavesummary',
  templateUrl: './leavesummary.component.html',
  styleUrls: ['./leavesummary.component.scss']
})
export class LeavesummaryComponent implements OnInit {

  //declarations
  san: string;
  role: any;
  appliedLeaves: any
  encryptSecretKey: any = "OJAS-OBS"
  teamleaves: any
  len: any
  //boolean declarations
  roleuser: boolean = false;
  rolemanager: boolean = false;
  summary: any = true
  //leave caluculations
  cl: any = 0
  sl: any = 0
  ml: any = 0
  lop: any = 0
  cf: any = 0
  authService: any;
  userData: any;
  eid: string;
  calenderHolidays: any = []
  constructor(private lms: LeaveserviceService, private authfun: AuthService, private hrms: HrmsService) { }

  ngOnInit() {
    this.san = this.authfun.decryptData(sessionStorage.getItem('UserName'));
    this.role = this.authfun.decryptData(sessionStorage.getItem('Role'));
    this.getHolidays()
    this.getLeaveBalance()
    this.getleaveinfo()
    this.user();
  }

  user() {
    if (this.role == 'ROLE_USER') {
      this.roleuser = true;
    }
    else if (this.role == 'ROLE_MANAGER') {
      this.rolemanager = true
      this.getreporties()
    }
  }


  getLeaveBalance() {
    var reqObj = {
      "leaveBalance": {
        "empId": this.san
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
      //this.lop=this.lop-resObj.leaveBalList.lossOfPay
      this.lop = resObj.leaveBalList.lossOfPay
      this.cf = resObj.leaveBalList.totalCompOff - resObj.leaveBalList.consumedCompOff
    })
  }

  getleaveinfo() {
    var reqObj = {
      "leaveInfo": {
        "empId": this.san
      },
      "transationType": "getallleaveinfo"
    }
    console.log("req", reqObj)
    var resObj
    this.lms.lmsget(reqObj).subscribe(res => {
      resObj = res
      this.appliedLeaves = resObj.leaveInfo
      this.len = this.appliedLeaves.length
      console.log("leave list", this.appliedLeaves);
    })
  }

  getreporties() {
    var response1
    var response
    var leaves
    var one = []
    var teamdata
    var requestobj = {
      "employeeInfo": [{
        "reportingManager": this.san
      }],
      "transactionType": "getreporties"
    }
    var reqObj = {
      "leaveInfo": {
        "applyTo": this.san
      },

      "transationType": "getbymanager"

    }
    this.lms.lmsget(reqObj).subscribe(res => {
      response = res
      leaves = response.leaveInfo
      console.log("leaves", leaves);
      this.hrms.getempinfo(requestobj).subscribe(res => {
        response1 = res
        teamdata = response1.employeeInfo
        console.log("teamdata", teamdata);
        teamdata.map(t => leaves.map(l => {
          if (l.empId == t.employeeId) {
            one.push(new Object({ empInfo: t, leaveInfo: l }))
          }
        }))
        this.teamleaves = one
        console.log("team leavs", this.teamleaves);

      })
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
    this.lms.lmsholidayget(reqObj).subscribe(res => {
      console.log(res);
      response = res
      for (let i in response.holidayslist) {
        if (this.isDateAfterToday(response.holidayslist[i].holidayDate)) {
          this.calenderHolidays.push(response.holidayslist[i])
        }
      }
      this.calenderHolidays.sort(function(a, b) {
        a = new Date(a.holidayDate);
        b = new Date(b.holidayDate);
        return a<b ? -1 : a>b ? 1 : 0;
    });
      console.log("calender", this.calenderHolidays);
    })
  }

  isDateAfterToday(date) {
    var CurrentDate = new Date();
    return date > this.formatDate(CurrentDate);
  }
  deatils() {
    this.summary = false
  }
  goback() {
    this.summary = true
  }

  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }
}
