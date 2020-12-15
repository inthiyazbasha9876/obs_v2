import { Component, OnInit } from '@angular/core';
import { LeaveserviceService } from '../../lmsservices/leaveservice.service';
import { FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-leavetype',
  templateUrl: './leavetype.component.html',
  styleUrls: ['./leavetype.component.scss']
})
export class LeavetypeComponent implements OnInit {
  leaveTypedata: any;
 

  value: boolean;
  id: any;
  status: any;
  addb = true;
  masterList: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize: number = 5;
  sid: any;
  key: string;
  editname: any;
  leaveTypeName: any;
  
  constructor(private lms: LeaveserviceService, private fb: FormBuilder, private toast: NotificationService,private router:Router) {
  }
  ngOnInit() {
    this.getLeaveType();
  }
  //getall
  getLeaveType() {

    var Requestdata ={
      "leaveTypeList": [
          {}
      ],
      "transactionType": "GetAll"
  }
    console.log("success", Requestdata)
    this.lms.getLeaveType(Requestdata).subscribe(responce => {
      var leavetypelist:any= responce;
      console.log("Get response", leavetypelist)
      console.log("Get response", leavetypelist.leaveTypeList)
      this.leaveTypedata = leavetypelist.leaveTypeList;
    })
  }
  //get all close

  //save
  setLeaveType() {
    var count=0;
    var str = this.leaveTypeName.replace(/[\. ,:-]+/g, "");
  
    this.leaveTypedata.map(d=>{
      var one=d.leaveTypeName.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })
  
    if (count == 1) {
        
      this.toast.error("Duplicates are not allowed")
      
    }
    else{
    var reqData =
    {
      "leaveTypeList":[{
        
      "leaveTypeName":this.leaveTypeName,
      "status":true
      
      }],	
      "transactionType":"save"
    }
    console.log("success", reqData);

    this.lms.setLeaveType(reqData).subscribe((response: any) => {
      var leavetypelist:any= response;

      console.log("Save response",leavetypelist, leavetypelist.message);

      if (leavetypelist.message == "Leave type details saved successfully") {

        this.toast.success(leavetypelist.message);
        this.getLeaveType();
      }

    },
      error => {
        this.toast.error("Duplicates are not allowed");
        this.getLeaveType();

      });
    }
    this.id = "",
      this.leaveTypeName = "",
      this.value = false;

  }
  //save close

  updateLeaveType(uprequest) {

    this.searchfield = false;

console.log("reee",uprequest)

var count = 0
var str = uprequest.leaveTypeName.replace(/[\. ,:-]+/g, "");
this.leaveTypedata.map(d=>{
  var one=d.leaveTypeName.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.leaveTypeId != this.sid) {
    count = 1
  }
})
if (count == 1) {
  this.toast.error("Duplicates are not allowed")
  this.getLeaveType();
  this.noedit = false;
  this.value=false;
 this.addb=true;
} else
{
    var updateRequestData =
    {
      "leaveTypeList": [
          {
             
              "leaveTypeId":this.sid,
              "leaveTypeName":uprequest.leaveTypeName,
              "status":this.status
             
          }
      ],
      "transactionType": "update"
  }
    console.log("success", updateRequestData)
    this.addb = true;
    this.lms.updateLeaveType(updateRequestData).subscribe((res: any) => {
      this.masterList = res;
      console.log(this.masterList);
      if (this.masterList.message == "LeaveType details updated successfully") {
        this.toast.success(this.masterList.message)
        this.getLeaveType();
        
        this.noedit = false;
      }
    },
      error => {
        this.toast.error("Duplicates are not allowed");
        this.getLeaveType();
        this.noedit = false;
      })
    }
    this.key=""
  }


  edit(row) {
    console.log("edit", row.leaveTypeId)
    this.sid = row.leaveTypeId;
    this.noedit = true;
    this.value = false;
    this.status = row.status
    this.addb = false;
    this.searchfield = true;
    this.editname=row.leaveTypeName
  }




  cancelbulist() {
    this.value = false;
    this.addb = true;
    this.getLeaveType();
  }
  clear() {
    this.leaveTypeName = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key=""
    this.getLeaveType();
  }
 
  navigateTo(){
    this.lms.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
} 
