import { Component, OnInit } from '@angular/core';
import { LeaveserviceService } from '../../lmsservices/leaveservice.service';
import { FormBuilder, Validators } from '@angular/forms';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-holiday',
  templateUrl: './holiday.component.html',
  styleUrls: ['./holiday.component.scss']
})
export class HolidayComponent implements OnInit {

  pageSize: any = 5
  holidaylist: any
  holidayId: any

  editDate: any
  editName: any
  noedit: any = false
  addb = true;
  value: any = false;
  hdate: any
  hname: any
  key: string;
  constructor(private lms: LeaveserviceService, private fb: FormBuilder, private toastr: NotificationService,private router:Router) {
    this.getholidays()
  }


  ngOnInit() {
  }


  getholidays() {
    var resopnse
    var req = {
      "holidayslist": [
        {}
      ],
      "transactionType": "getall"
    }
    this.lms.lmsholidayget(req).subscribe(res => {
      console.log("res", res);
      resopnse = res
      this.holidaylist = resopnse.holidayslist
    })
  }

  edit(e) {
    console.log(e);
    this.holidayId = e.holidayId
    this.editDate = e.holidayDate
    this.editName = e.holidayName
    this.noedit = true
    this.addb = false;
  }


  cancel() {
    this.noedit = false
    this.addb = true;
    this.key=""
  }

  updateholiday(e) {
    console.log(e, this.holidayId);
    var count = 0
    this.holidaylist.map(d => {
      if (d.holidayDate == this.formatDate(new Date(e.hDate)) && d.holidayId != this.holidayId) {
        count = 1
      }
    })
    if (count == 1) {
      this.getholidays()
      this.noedit = false;
      this.value = false;
      this.addb = true;
      this.toastr.error("Duplicates are not allowed")

    } else {
      var response
      var reqObj = {
        "holidayslist": [
          {
            "holidayId": this.holidayId,
            "holidayDate": this.formatDate(new Date(e.hDate)),
            "holidayName": e.hName,
            "status": true
          }
        ],
        "transactionType": "update"
      }
      this.lms.lmsholidayset(reqObj).subscribe(res => {
        console.log(res);
        response = res
        this.toastr.success(response.message)
        this.getholidays()
        this.noedit = false;
        this.value = false;
        this.addb = true;
      }, err => {
        console.log(err);
        this.toastr.error("Holiday not updated")
        this.getholidays()
        this.noedit = false;
        this.value = false;
        this.addb = true;
      })

    }
    this.key=""

  }

  setActionowner(e) {
    console.log("save data", e, this.holidaylist);
    var count = 0;
    if (this.holidaylist != null) {

      this.holidaylist.map(d => {
        if (d.holidayDate == this.formatDate(new Date(e.holidayDate))) {
          count = 1
        }
      })
      if (count == 1) {
        this.toastr.error("Duplicates are not allowed")
      }
      else {
       this.save(e)
      }
    } else {
      this.save(e)
    }
  }

  save(e) {
    console.log("hello");
    var response
    var reqObj = {
      "holidayslist": [
        {
          "holidayDate": this.formatDate(e.holidayDate),
          "holidayName": e.holidayName,
          "status": true
        }
      ],
      "transactionType": "save"
    }
    console.log("holiday set", reqObj);
    this.lms.lmsholidayset(reqObj).subscribe(res => {
      console.log(res);
      response = res
      this.toastr.success(response.message)
      this.value = false
      this.getholidays()
    },
      err => {
        console.log(err);
        this.toastr.error("holiday not added")
      })
  }

  cancelbulist() {
    this.value = false
  }

  clear() {
    this.hdate = null
    this.hname = ""
  }


  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }

  navigateTo(){
    this.router.navigate(['home/hrms/dashboard']);
  }
  p: number;
  searchPage(){
    this.p=1;
    }
}
