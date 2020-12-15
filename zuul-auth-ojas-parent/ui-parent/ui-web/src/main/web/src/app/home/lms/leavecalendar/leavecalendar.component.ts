import { Component, OnInit } from '@angular/core';
import { LeaveserviceService } from '../lmsservices/leaveservice.service';
import { AuthService } from 'src/app/services/auth.service';
import { ExcelService } from '../../services/excel.service';

@Component({
  selector: 'app-leavecalendar',
  templateUrl: './leavecalendar.component.html',
  styleUrls: ['./leavecalendar.component.scss']
})
export class LeavecalendarComponent implements OnInit {

  
  monthdays: any
  dateStart: any
  today: any
  loginEmp: any
  details:any=false
  appliedleaves: any = []
  _MS_PER_DAY = 1000 * 60 * 60 * 24;

  randomsting: any
  displayMonth:any
  string = []
  leaveDetails:any=[]
  leaveApplications:any=[]
  selectedDate:any

  constructor(private lms: LeaveserviceService, private auth: AuthService,private excel: ExcelService) { 
    this.loginEmp = this.auth.decryptData(sessionStorage.getItem('UserName'))
    this.today = new Date()
    this.getempleaveapplictions()   
  }

  ngOnInit() {
  }

  getDaysInMonth(month, year) {
    
    console.log(this.appliedleaves,"basha");
    
    var date = new Date(Date.UTC(year, month, 1));
    var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    var monthdays = []
    var days = [];
    var count=0
    if (date.getDay() == 0) {
      this.dateStart = new Array(date.getDay() + 6)
    } else {
      this.dateStart = new Array(date.getDay() - 1)
    }
    while (date.getMonth() === month) {
      var obj={
        "date":null,
        "comment":"",
        "leaveId":null
      }
      if(this.appliedleaves.length>0){
        for(let i in this.appliedleaves){
          if(this.formatDate(this.appliedleaves[i].leaveDate)==this.formatDate(date))
          {
            obj.date=new Date(date)
            obj.comment="onLeave"
            obj.leaveId=this.appliedleaves[i].leaveId
            days.push(obj);
            count++
          }
        }
      }else{
        obj.date=new Date(date)
        obj.comment="onWorking"
        days.push(obj);
        count++;
      }
      if(count==0){
        obj.date=new Date(date)
        obj.comment="onWorking"
        days.push(obj);
      }else{
        count=0
      }
      
      if (new Date(date).getDay() <= 0) {
        monthdays.push(days)
        days = []
      }
      if (new Date(date).getDate() == lastDay.getDate()) {
        monthdays.push(days)
      }
      date.setDate(date.getDate() + 1);
    }
    this.monthdays = monthdays
    this.displayMonth=this.monthdays[0][0].date  
    console.log("final obj",this.monthdays);  
  }

  getNextMonth() {
    this.details=false
    console.log(this.monthdays[0][0].date);
    if (this.monthdays[0][0].date.getMonth() < 11) {
      this.getDaysInMonth(this.monthdays[0][0].date.getMonth() + 1, this.monthdays[0][0].date.getFullYear())
    } else {
      var month = 0
      this.getDaysInMonth(month, this.monthdays[0][0].date.getFullYear() + 1)
    }
    
  }

  getPreviousMonth() {
    this.details=false
    console.log(this.monthdays[0][0].date);
    if (this.monthdays[0][0].date.getMonth() != 0) {
      this.getDaysInMonth(this.monthdays[0][0].date.getMonth() - 1, this.monthdays[0][0].date.getFullYear())
    } else {
      var month = 11
      this.getDaysInMonth(month, this.monthdays[0][0].date.getFullYear() - 1)
    }
  }

  dateSelected(e) {
    this.details=false
    console.log("date selectd", e);
    this.getDaysInMonth(e.value.getMonth(), e.value.getFullYear())
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
      this.leaveApplications=response.leaveInfo
      this.leaveApplications.map(d => {
        if (d.status == "approved")
          this.getapplieddates(new Date(d.fromDate), new Date(d.toDate),d)
      })
      console.log("res", this.appliedleaves);
      this.getDaysInMonth(this.today.getMonth(), this.today.getFullYear())  
    },err=>{
      this.getDaysInMonth(this.today.getMonth(), this.today.getFullYear())  
    })

  }

  getapplieddates(a, b,e) {
    console.log("leave type",e);
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
    var ndays = Math.floor((utc2 - utc1) / this._MS_PER_DAY) + 1
    var n = 0;
    while (n < ndays) {
      var from = new Date(a.getFullYear(), a.getMonth(), a.getDate() + n)
      var obj=new Object({leaveDate:from,leaveId:e.id})
      this.appliedleaves.push(obj)
      n++;
    }
  }

  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }

  getDetails(e){
    console.log(e,this.leaveApplications);
    
    this.details=true
    if(this.leaveApplications.length>0){
      console.log("leave details",e,this.leaveApplications)
      this.leaveDetails=this.leaveApplications.filter(d=>d.id==e.leaveId)
      console.log("filetered data",this.leaveDetails);
      this.selectedDate=e.date
    }else{
      this.selectedDate=e.date
    }
   
  }

  download_excel() {  
    var xlFile='leave details ('+this.formatDate(this.selectedDate)+')'
    this.excel.exportAsExcelFile(this.leaveDetails, xlFile);    
  }
}
