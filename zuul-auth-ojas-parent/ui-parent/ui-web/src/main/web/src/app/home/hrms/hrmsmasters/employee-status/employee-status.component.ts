import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { Employeestatus } from './employee-status';


@Component({
  selector: 'app-employee-status',
  templateUrl: './employee-status.component.html',
  styleUrls: ['./employee-status.component.scss']
})
export class EmployeeStatusComponent implements OnInit {

  value: boolean;
  data;
  employee: any;
  employeeStatus;
  // sortedCollection: any[];

  employeestatus: any;
  isEditable: boolean = false;
  reverse: boolean = false;
  order: string = 'employeeStatus';
  private pageSize: number = 5;




  employeestatuslist: Employeestatus[] =
    [
      {
        'employeeStatus': 'Application Development',
      },
      {
        'employeeStatus': 'Support',
      },
      {
        'employeeStatus': 'IDM'
      }


    ]
  errrrr: void;
  eid: any;
  editname: any;
  key: string;
  constructor( private hrms: HrmsService,private toast:NotificationService) {
    // this.sortedCollection = orderpipe.transform(this.employeestatuslist, 'employeeStatus');
    //console.log(this.sortedCollection);
    // private orderpipe: OrderPipe,

  }

  ngOnInit() {
    this.getEmployeeStatusData();
  }

  //Master data for Employee Status
  employee_statuslist: any;
  employee_Status: any;

  getEmployeeStatusData() {
    var request = {

      "transactionType": "getall"
    }
    this.hrms.getEmployeeStatusMaster(request).subscribe(response => {
      this.employee_Status = response;
      this.employee_statuslist = this.employee_Status.employeeStatusList;
      console.log("Employee Status");
      console.log(this.employee_statuslist);
    })

  }

  //Employee Status Save 
  employee_status_data: any
  employee_status_response: any
  employee_status_message: any
  setEmployeeStatusData() {
    var count=0;
    var str = this.employee_status_data.replace(/[\. ,:-]+/g, "");
  
    this.employee_statuslist.map(d=>{
      var one=d.status.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })
  
    if (count == 1) {
        
      this.toast.error("Duplicates are not allowed")
      
    }
    else{
    var request =
    {
      "employeeStatus": [
        {
          "status": this.employee_status_data
        }

      ],
      "transactionType": "save"
    }

    this.hrms.saveEmployeeStatusMaster(request).subscribe((response: any) => {
      this.employee_status_response = response;
      console.log("msgggggggggg", this.employee_status_response)

      if (this.employee_status_response.message == "Record successfully saved") {
        // swal(this.employee_status_response.message, "", "success");
        this.toast.success(this.employee_status_response.message)
        this.getEmployeeStatusData();
      }

    },
      error => {
        this.errrrr = error
        console.log("errrrrrrrrrrrrrrrrrrrrrrrrrrr", error)


        if (error.error.message == "Data must not be null") 
        {
          //  swal("must not be null", "", "error"); 
          this.toast.error("must not be null")
           this.getEmployeeStatusData(); 
          }
        if (error.error.message == "DuplicateKeyException") { 
          // swal("Duplicates are not allowed", "", "error"); 
          this.toast.error("Duplicates are not allowed")
          this.getEmployeeStatusData(); }


      })
    }
    this.employee_status_data = "";

    this.value = false;
  }

  //Employee Status update
  employee_status_update: any
  noedit: boolean;
  searchfield=false;
  addb=true;
  edit(id,name) {
    this.eid = id;
    this.noedit = true;
    this.value=false;
    this.addb=false;
    this.searchfield=true;
    this.editname=name
  }
  cancel() {
    this.noedit=false;
    this.searchfield=false;
    this.addb=true;
    this.key=""
    this.getEmployeeStatusData();
      }
  updateEmployeeStatusData(employee) {
    this.searchfield=false;
    console.log("reee",employee)
var count = 0
var str = employee.status.replace(/[\. ,:-]+/g, "");
this.employee_statuslist.map(d=>{
  var one=d.status.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.id != this.eid) {
    count = 1
  }
})
if (count == 1) {
  this.toast.error("Duplicates are not allowed")

  this.getEmployeeStatusData();
  this.noedit = false;
  this.value=false;

} else 
{
    var request =
    {
      "employeeStatus": [
        {
          "id": this.eid,
          "status": employee.status
        }
      ],
      "transactionType": "update"
    }
    this.addb=true;
    this.hrms.updateEmployeeStatusMaster(request).subscribe((response: any) => {
      this.employee_status_update = response;
      if (this.employee_status_update.message == "Record Successfully updated") {
        this.noedit = false;
        // swal(this.employee_status_update.message, "", "success");
        this.toast.success(this.employee_status_update.message)
        this.getEmployeeStatusData();
      }
    },
      error => {
        this.noedit = false;
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")
        this.getEmployeeStatusData();
      })
    this.value = false;
}
this.key=""
  }


  saveBu() {
    debugger;
    this.value = false;
    this.data = {
      "employeeStatus": this.employeestatus,

    }
    this.employeestatuslist.unshift(this.data);

    this.employeeStatus = '';

  }
  editData(blist) {
    console.log(blist);
    //this.listDetails = blist;
    this.employee = blist.employeeStatus;

  }
  saveData() {
    var editD = {
      "employeeStatus": this.employee
    }
    console.log(editD);
  }
  deleterow(index) {
    debugger;
    if (index !== -1) {
      this.employeestatuslist.splice(0, 1);
    }
    else {
      this.employeestatuslist.splice(index, 1);
    }
  }

  cancelbulist() {
    this.value = false;

  }
  setOrder(value: string) {
    if (this.order === value) {
      this.reverse = !this.reverse;
    }

    this.order = value;

  }
  navigateTo(){
    this.hrms.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
  }
