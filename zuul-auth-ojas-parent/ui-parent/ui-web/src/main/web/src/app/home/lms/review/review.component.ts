import { Component, OnInit } from '@angular/core';
import { takeUntil } from 'rxjs/operators';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { LeaveserviceService } from '../lmsservices/leaveservice.service';
import { HrmsService } from '../../services/hrms.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent implements OnInit {


  //declarations 
  loginuser: any
  appliedLeaves: any
  empdata: any
  teamdata: any
  role: any
  reqEmp: any
  myimage: any
  pendingapplications: any = []
  completedapplications: any = []
  //boolean declaration
  rolemanager: any = false
  roleuser: any = false
  managercard: any = false
  pending: any = true
  completed: any = false
  approveReject: any = false
  rejectForm: any = false
  withdraw: any = false
  rejectComments = new FormGroup({
    comments: new FormControl('', Validators.required),
  });

  private unsubscribe = new Subject();
  constructor(private lms: LeaveserviceService, private hrms: HrmsService, private auth: AuthService,private toastr:NotificationService) {
    this.loginuser = this.auth.decryptData(sessionStorage.getItem('UserName'))
    this.role = this.auth.decryptData(sessionStorage.getItem('Role'))
    this.roleUser()
  }

  ngOnInit() {

  }

  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

  roleUser() {
    if (this.role == "ROLE_USER") {
      this.rolemanager = false
      this.roleuser = true
      this.managercard = false
      this.reqEmp = this.loginuser
      this.approveReject = false
      this.rejectForm = false
      this.withdraw=true
      this.getappliedleaves()
    } else if (this.role == "ROLE_MANAGER") {
      this.myApplications()
    }
    else{
      this.myApplications()
    }
  }


  myApplications() {
    this.reqEmp = this.loginuser
    this.rolemanager = true
    this.roleuser = true
    this.managercard = false
    this.pending = true
    this.completed = false
    this.approveReject = false
    this.rejectForm = false
    this.withdraw = true
    this.getappliedleaves()
  }

  teamApplications() {
    this.roleuser = false
    this.managercard = true
    this.pending = false
    this.completed = false
    this.approveReject = true
    this.rejectForm = false
    this.withdraw = false
    this.getteamappliedleaves()
  }

  getappliedleaves() {
    this.appliedLeaves = null
    this.pendingapplications = []
    this.completedapplications = []
    var response
    var response1
    var reqObj = {
      "leaveInfo": {
        "empId": this.reqEmp
      },

      "transationType": "getallleaveinfo"

    }
    var empinfo =
    {

      "employeeInfo": [{
        "employeeId": this.reqEmp
      }],
      "transactionType": "getbyid"
    }
    console.log(reqObj)
    this.lms.lmsget(reqObj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      response = res
      var obj = response.leaveInfo
      this.hrms.getempinfo(empinfo).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
        response1 = res
        this.empdata = response1.employeeInfo[0]
        console.log("empdata", this.empdata)
        this.appliedLeaves = obj.map(d => new Object({ empInfo: this.empdata, leaveInfo: d }))
        this.appliedLeaves.map(d => {
          if (d.leaveInfo.status == "pending")
            this.pendingapplications.push(d)
          else
            this.completedapplications.push(d)
        })
        this.appliedLeaves = this.pendingapplications
        console.log("final obj", this.appliedLeaves)
      })
    })
  }

  getteamappliedleaves() {
    this.appliedLeaves = null
    this.pendingapplications = []
    this.completedapplications = []
    var response
    var leaves
    var one = []
    var response1
    var requestobj = {
      "employeeInfo": [{
        "reportingManager": this.reqEmp
      }],
      "transactionType": "getreporties"
    }
    var reqObj = {
      "leaveInfo": {
        "applyTo": this.reqEmp
      },

      "transationType": "getbymanager"

    }
    this.lms.lmsget(reqObj).subscribe(res => {
      response = res
      leaves = response.leaveInfo
      console.log("leaves",leaves);
      this.hrms.getempinfo(requestobj).subscribe(res => {
        response1 = res
        this.teamdata = response1.employeeInfo
        console.log("teamdata",this.teamdata);
        this.teamdata.map(t => leaves.map(l => {
          if (l.empId == t.employeeId) {
            one.push(new Object({ empInfo: t, leaveInfo: l }))
          }
        }))
        this.appliedLeaves = one
        console.log("final resobj",this.appliedLeaves)
        this.appliedLeaves.map(d => {
          if (d.leaveInfo.status == "pending")
            this.pendingapplications.push(d)
          else
            this.completedapplications.push(d)
        })
        this.appliedLeaves = this.pendingapplications
        console.log("final teaminfo", this.appliedLeaves)
      })
    })
  }


  approve(e) {
    console.log("approve application", e);
    var today = new Date()
    var updatedon = this.formatDate(today) + " " + today.toLocaleTimeString()
    var response
    var reqObj = {
      "leaveInfo": {
        "id": e.id,
        "empId": e.empId,
        "status": "approved",
        "updatedBy": this.loginuser,
        "updatedOn": updatedon,
        "comment": this.rejectComments.value.comments
      },
      "transationType": "updatestatus"
    }
    console.log("aprove reqObj", reqObj);
    this.lms.lmsset(reqObj).subscribe(res => {
      console.log(res);
      this.toastr.success('Leave approved successfully')
      this.teamApplications()
    }, err => {
      console.log(err)
      this.toastr.info("Failed to approve leave")
      this.teamApplications()
    })

  }

  reject() {
    this.rejectForm = true
    this.approveReject = false
  }

  rejectcancel() {
    this.rejectForm = false
    this.approveReject = true
  }

  rejectsubmit(e) {
    console.log("reject application", e)
    var today = new Date()
    var updatedon = this.formatDate(today) + " " + today.toLocaleTimeString()
    var response
    var reqObj = {
      "leaveInfo": {
        "id": e.id,
        "empId": e.empId,
        "status": "rejected",
        "updatedBy": this.loginuser,
        "updatedOn": updatedon,
        "comment": this.rejectComments.value.comments
      },
      "transationType": "updatestatus"
    }
    console.log("approve reqObj", reqObj);
    this.lms.lmsset(reqObj).subscribe(res => {
      console.log(res);
      this.toastr.success('Leave has been rejected')
      this.teamApplications()
    }, err => {
      console.log(err)
      this.toastr.info("Failed to reject leave")
      this.teamApplications()
    })
  }

  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }

  withdrawapp(e){
    console.log("withdraw application",e);
    var today = new Date()
    var updatedon = this.formatDate(today) + " " + today.toLocaleTimeString()
    var response
    var reqObj = {
      "leaveInfo": {
        "id": e.id,
        "empId": e.empId,
        "status": "withdrawn",
        "updatedBy": this.loginuser,
        "updatedOn": updatedon,
      },
      "transationType": "updatestatus"
    }
    console.log("approve reqObj", reqObj);
    this.lms.lmsset(reqObj).subscribe(res => {
      console.log(res);
      this.toastr.success('Leave application withdrawn successfully')
      this.teamApplications()
    }, err => {
      console.log(err)
      this.toastr.info("Leave application withdrawn failed")
      this.teamApplications()
    })
  }

  getpendingapplications() {
    console.log("all leaves in active funtion", this.appliedLeaves)
    this.appliedLeaves = this.pendingapplications
    this.pending = true
    this.completed = false
    console.log("peding apps", this.appliedLeaves)
  }

  getcompletedapplications() {
    console.log("all leaves in active funtion", this.appliedLeaves)
    this.appliedLeaves = this.completedapplications
    this.pending = false
    this.completed = true
    console.log("peding applications", this.appliedLeaves)
  }

}
