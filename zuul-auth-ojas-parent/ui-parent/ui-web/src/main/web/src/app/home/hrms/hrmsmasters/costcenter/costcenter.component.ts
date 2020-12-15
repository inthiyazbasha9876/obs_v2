import { Component, OnInit } from '@angular/core';

import { NgForm } from '@angular/forms';
import { HttpRequest } from '@angular/common/http';

// import swal from 'sweetalert';
import { AuthService } from 'src/app/services/auth.service';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';


@Component({
  selector: 'app-costcenter',
  templateUrl: './costcenter.component.html',
  styleUrls: ['./costcenter.component.scss']
})
export class CostcenterComponent implements OnInit {
  
  deletedDetails: any;
  id: any;
  costCenterCode: any;
  updateRes: any;
  coscentergetlist: any;
  costCenterList: any;
  costcenterRes: any;
  value:boolean;
  key:any;
  private pageSize: number = 5;

  role;
  hide;

  bulist: any;
  costCenterid: any;
  addbb=true;
  encryptedRole: any;
  editname: any;
  p: number;
  constructor(private hrms: HrmsService,private authService: AuthService,private toast:NotificationService) {
  }
  page(){
    this.p=1;
  }
  ngOnInit() {
    this.getCostCenter();
    // this.role = sessionStorage.getItem("Role");
    this.encryptedRole=sessionStorage.getItem('Role');
    this.role=this.authService.decryptData(this.encryptedRole);
    if (this.role == "ROLE_ADMIN") {
      this.hide = true;
    }
  
  }

   



  cancelbulist() {
    this.value = false;

  }
  clear() {
    this.costCenterCode = "";

    
  }


  setCostcenterData(a) {
    var requestData = {


      "costCenter": [{


        "costCenterCode": a.costCenterCode

      },
      ],

      "sessionId": "124",


      "transactionType": "save"

    }
    this.hrms.setCostcenter(requestData).subscribe((responce: any) => {
      this.costcenterRes = responce;
      console.log(this.costcenterRes);
      if (this.costcenterRes.message == "Successfully record added") {
        
        // swal(this.costcenterRes.message, "", "success");
        this.toast.success(this.costcenterRes.message)
        this.value = false;
        this.getCostCenter();
        this.costCenterCode = "";
        this.addbb=true;
      }
    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")
        // this.getCostCenter()
      })
    // this.value = false;
    this.key="" // to clear search field
  }
  getCostCenter() {
    var request =
    {


      "costCenter": [{


      }],

      "sessionId": "124",


      "transactionType": "get"

    }
    this.hrms.getCostcenter(request).subscribe(res => {
      this.costCenterList = res;
      this.coscentergetlist = this.costCenterList.listOfCostCenter;
      console.log("id of costcenter",this.costCenterList);
    })
  }
  noedit: boolean;
  searchfield=false;
  edit(id,name){
this.costCenterid=id;
this.noedit = true;
this.value=false;
this.searchfield=true;
this.addbb=false;
this.editname=name

  }

  cancel() {
    this.noedit=false;
    this.searchfield=false;
    this.getCostCenter();
    this.addbb=true;
    this.key=""
      }

  saveUpdateValues(bulist) {
    console.log(bulist);
    this.searchfield=false;
    var updateRequestData = {
      "costCenter": [{
        "id": this.costCenterid,
        "costCenterCode": bulist.costCenterCode
      }],

      "sessionId": "123",
      "transactionType": "update"
    }

    this.hrms.updateCostCenter(updateRequestData).subscribe((res: any) => {
      this.updateRes = res;
    this.addbb=true;
      console.log(this.updateRes);
      if (this.updateRes.message == "Successfully record updated") {
        this.noedit = false;
        // swal(this.updateRes.message, "", "success");
        this.toast.success(this.updateRes.message)
        this.getCostCenter();
        
      }
    },
      error => {
        this.noedit = false;
        // swal("Record Already Exist", "", "error");
        this.toast.error("Duplicates are not allowed")
        this.getCostCenter();
        this.addbb=true;

      })
      this.key="" //to clear search field
  }
  //   deleteCostCenter(bulist) {
  //   var deleteReq = {
  //     "costCenter" : {
  //              "id" : bulist.id,
  //              "costCenterCode" : bulist.costCenterCode
  //     },
  //     "sessionId" : "123",
  //             "transactionType" : "delete"
  // }
  // this.hrms.deleteCostCenter(deleteReq).subscribe(res =>{
  // this.deletedDetails = res;
  // console.log(this.deletedDetails);
  // if(this.deletedDetails.statusMessage == "Successfully record deleted"){
  // swal(this.deletedDetails.statusMessage, "","success");
  // this.getCostCenter();
  // }
  // })

  //   }
  navigateTo(){
    this.hrms.navigateToDashboard();
  }
  searchPage(){
    this.p=1;
    }
  }
