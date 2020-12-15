import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';

import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import * as moment from 'moment';
import { DataService } from 'src/app/home/services';

@Component({
  selector: 'app-dependents',
  templateUrl: './dependents.component.html',
  styleUrls: ['./dependents.component.scss']
})
export class DependentsComponent implements OnInit {
  public eid: any;
  loggeduser: any;

  today;
  ippd: any = 5;
  encryptedUsername: string;
  encryptedRole: string;
  rolemanagerflag: boolean = true;
  role;
  setUserRole;

  constructor(private hrms: HrmsService, private dataservice: DataService, private authService: AuthService,private toast:NotificationService) {
    this.eid = this.dataservice.paramId;
    // this.loggeduser= sessionStorage.getItem('UserName');
    this.encryptedUsername = sessionStorage.getItem('UserName');
    this.loggeduser = this.authService.decryptData(this.encryptedUsername);
    this.encryptedRole = sessionStorage.getItem('Role');
    this.role = this.authService.decryptData(this.encryptedRole);
    this.today = moment().format('YYYY-MM-DD');

  }


  ngOnInit() {
    this.getEmpRole();
    this.getdependentData();
    this.getempdata();
    // this.role=sessionStorage.getItem("Role");


    if (this.role == "ROLE_MANAGER") {
      this.rolemanagerflag = false;
    }

    this.setUserRole = sessionStorage.getItem("setUserRole");
    console.log('role to check user', this.setUserRole);
    if (this.setUserRole == "true") {
      this.rolemanagerflag = true;

    }

  }

  user: boolean = false;

  getEmpRole() {
    if (this.role == "ROLE_USER") {
      this.user = true;
    }
    console.log("Role", this.user);
  }

  empbasic: any;
  empbasicinfo: any;
  getempdata() {

    var empinfo =
    {

      "employeeInfo": [{
        "employeeId": this.eid

      }],
      "transactionType": "getbyid"

    }
    this.hrms.getempinfo(empinfo).subscribe(res => {
      this.empbasic = res;
      this.empbasicinfo = this.empbasic.employeeInfo;
      console.log(this.empbasicinfo);
    })
  }

  public dependent_details: any;
  dependents: any;
  formdata: any;
  dependent_Name: any;
  relation: any;
  date_Of_Birth: any;
  created_By: any;
  employee_Id: any;
  updated_by: any;
  updateRes: any;
  updated_By: number;
  employee_dependentdetailsById: any
  deleteRes: any;
  dependent_dtdetails: any



  addnewdepartment(newUserFormDependent) {
    newUserFormDependent.reset();
    this.createdByDependent = true;
    this.isupdateDependent = false;

  }

  getdependentData() {
    var requestData = {
      "dependentDetails": [{

        "employee_Id": this.eid
      }],

      "transactionType": "getbyid"
    }
    // {
    //   "dependentDetails" : [
    //                                           {

    //                                           }
    //                  ], 
    //                  "transactionType":"getall",
    //                 "sessionId" : "any String" 
    // }

    this.hrms.getdependentdetails(requestData).subscribe(response => {
      this.dependent_details = response;
      this.dependents = this.dependent_details.getDependentDetailsList;

      console.log(this.dependent_details);

    })
  }



  onsavedependentdata() {
    var user = "user";
    this.isupdateDependent = false;
    var requestData = {
      "dependentDetails": [
        {
          "dependent_Name": this.dependentdetailss.dependent_Name,
          "relation": this.dependentdetailss.relation,
          "date_Of_Birth": this.dependentdetailss.date_Of_Birth,
          "employee_Id": this.eid,

          "created_By": this.loggeduser

        }
      ],
      "transactionType": "save",
      "sessionId": "any String"
    }

    this.hrms.savedependentdetails(requestData).subscribe(response => {
      this.dependent_details = response;
console.log(this.dependent_details,"test")
      if (this.dependent_details.message == "DependentDetails have saved successfully") {
        // swal(this.dependent_details.message, "", "success");
        this.toast.success(this.dependent_details.message)
        this.getdependentData();
      }
      this.getdependentData();
    })
   // DependentDetails have saved successfully
  }
  createdByDependent = true;

  isupdateDependent = false;
  public dependentdetailss = {


    "id": "",
    "dependent_Name": "",
    "relation": "",
    "date_Of_Birth": "",
    "employee_Id": "",
    "updated_By": "",
    "created_By": ""

  }

  updatedependentdata() {

    var user = "user";
    var updatedependentdataobj = {
      "dependentDetails": [
        {
          "id": this.dependentdetailss.id,
          "dependent_Name": this.dependentdetailss.dependent_Name,
          "relation": this.dependentdetailss.relation,
          "date_Of_Birth": this.dependentdetailss.date_Of_Birth,
          "employee_Id": this.dependentdetailss.employee_Id,
          "updated_By": this.loggeduser
        }


      ],
      "transactionType": "update",
      "sessionId": "any String"
    }

    this.hrms.updatedependentdetails(updatedependentdataobj).subscribe(res => {
      this.updateRes = res;
console.log("res",this.updateRes,this.updateRes.message);

      if (this.updateRes.message == "DependentDetails are updated successfully") {
        // swal(this.updateRes.message, "", "success");
        this.toast.success(this.updateRes.message)

        this.getdependentData();

      }
      this.getdependentData();
    })

  }
  deletedependentdata(dependent) {

    var deletedependentdata = {
      "dependentDetails": [
        {
          "id": dependent.id,
          "updated_By": this.loggeduser
        },


      ],
      "transactionType": "delete",
      "sessionId": "any String"
    }
    this.hrms.deletedependentdetails(deletedependentdata).subscribe(res => {
      this.deleteRes = res;
      if (this.deleteRes.message == "DependentDetails are deleted successfully") {
        // swal(this.deleteRes.message, "", "success");
        this.toast.success(this.deleteRes.message)
        this.getdependentData();
      }
      //this.getdependentData();
    })

  }




  getdependentdetailsById(dependent) {
    this.isupdateDependent = true;
    this.createdByDependent = false;
    var ddid = dependent.id;
    var dependentdetailsByid = {
      "dependentDetails": [
        {
          "id": ddid
        }
      ],
      "transactionType": "getbyid",
      "sessionId": "any String"
    }
    this.hrms.getdependentdetails(dependentdetailsByid).subscribe(response => {
      this.employee_dependentdetailsById = response;
      this.dependent_dtdetails = this.employee_dependentdetailsById.getDependentDetailsList;
      this.dependentdetailss = this.dependent_dtdetails[0]


    })

  }
  //dependent detils ends



}
