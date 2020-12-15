import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-rolemanagement',
  templateUrl: './rolemanagement.component.html',
  styleUrls: ['./rolemanagement.component.scss']
})
export class RolemanagementComponent implements OnInit {

  deleteRoleDetails: any;
  updateRoleDetails: any;
  value: boolean;
  roleManagemenList: any;
  getRoleDetails: any;
  setRoleDetails: any;
  roleName: any;
  roleArr: any;
  private pageSize: number = 5;
  rid: any;
  editname: any;
  key1: string;
  // addText:any;
  // key: string = 'name'; //set default
  // reverse: boolean = false;
  // Employee:any;
  // value: boolean;
  constructor(private hrms: HrmsService,private toast:NotificationService) { }
  // @ViewChild('empform') form:any;
  // public fieldArray:any=['CEO','Manager','Associate','Employee','TeamLeader']

  // public dataJson=

  // [
  //   {  'status':'CEO'   },
  //   {  'status':'Manager'   },
  //   {  'status':'Associate'   },
  //   {  'status':'Employee'   },
  //   {  'status':'TeamLeader'   },

  //  ]
  //  data:any;
  ngOnInit() {
    this.getRole();
  }

  //  addFieldValue() {
  //       this.fieldArray.push(this.data)
  //       this.data = {};
  //        this.router.navigate(['/'])
  //   }  
  //        updateFieldValue(data:any){
  //         var obj={status:data}
  //         this.dataJson.push(obj);
  //        }
  //        resetFieldValue(){
  //          if (this.form.valid) {
  //            debugger;
  //             this.form.reset();
  //           }
  //        }
  //        deleteFieldValue(index) {
  //         debugger;     
  //       this.dataJson.splice(index, 1);
  //        }
  //   sort(key){
  //     this.key = key;
  //     this.reverse = !this.reverse;
  //   }

  //   saveText(data:any){
  //     var obj={status:data}
  // this.dataJson.push(obj);
  // this.addText='';
  //   }
  //   cancelbulist(){
  //     this.value=false;

  //   }
  setRole(a) {

    var count=0;
    var str = a.addText.replace(/[\. ,:-]+/g, "");
  
    this.roleManagemenList.map(d=>{
      var one=d.roleName.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })
  
    if (count == 1) {
        
      this.toast.error("Duplicates are not allowed")
      
    }
    else
{


    var request = {

      "roleManagement": [
        {
          "roleName": a.addText
        }],
      "transactionType": "save"

    }
    this.hrms.setRoleManagement(request).subscribe((res: any) => {
      this.setRoleDetails = res;
      this.roleArr = this.setRoleDetails.roleManagementList;
      console.log(this.setRoleDetails);
      this.value = false;
      if (this.setRoleDetails.message == "Successfully Role Management record saved") {
        // swal(this.setRoleDetails.message, "", "success");
        this.toast.success(this.setRoleDetails.message)
        this.getRole();
      }
    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")

        this.value = false;


        this.roleName = "";

        this.getRole();
      });
    }
  }
  getRole() {
    var request = {

      "transactionType": "getAll"
    }

    this.hrms.getRoleManagement(request).subscribe(res => {
      this.getRoleDetails = res;
      this.roleManagemenList = this.getRoleDetails.roleManagementList;
      // console.log("roleeeeeeeeeeeeeee",this.roleManagemenList);
    })
  }
  noedit: boolean;
  searchfield=false;
  addb=true;
  edit(id,name) {
    this.searchfield=true;
    this.rid = id;
    this.noedit = true;
    this.addb=false;
    this.value=false;
    this.editname=name
  }
  cancel() {
    this.searchfield=false;
    this.noedit = false;
    this.addb=true;
    this.key1=""
    this.getRole();
  }
  saveUpdateValue(field) {

    this.searchfield=false;
    console.log("reee",field)
    var count = 0
    var str = field.roleName.replace(/[\. ,:-]+/g, "");
    this.roleManagemenList.map(d=>{
      var one=d.roleName.replace(/[\. ,:-]+/g, "");
      if (one.toLowerCase() == str.toLowerCase() && d.id !=this.rid) {
        count = 1
      }
    })
    if (count == 1) {
      this.toast.error("Duplicates are not allowed")
    
      this.getRole();
      this.noedit = false;
      this.value=false;
    
    } else

    var request = {

      "roleManagement": [
        {
          "id": this.rid,
          "roleName": field.roleName
        }],
      "transactionType": "update"

    }
    this.addb=true;
    this.hrms.updateRoleManagement(request).subscribe((res: any) => {
      this.updateRoleDetails = res;
      console.log(this.updateRoleDetails);
      if (this.updateRoleDetails.message == "Success fully record updated") {
        this.noedit = false;
        // swal(this.updateRoleDetails.message, "", "success");
        this.toast.success(this.updateRoleDetails.message);
        this.getRole();
      }
    },
      error => {
        this.noedit = false;
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")
        this.getRole();
        this.value = false
      })
      this.key1=""
  }
  deleteRole(field) {
    var request = {
      "roleManagement": {
        "id": field.id,
        "roleName": field.roleName
      },
      "sessionId": 12345,
      "transactiontype": "delete"
    }
    this.hrms.dleteRoleManagement(request).subscribe(res => {
      this.deleteRoleDetails = res;
      console.log(this.deleteRoleDetails);
      if (this.deleteRoleDetails.statusMessage == "Success fully record deleted")
        // swal(this.deleteRoleDetails.statusMessage, "", "success");
        this.toast.success(this.deleteRoleDetails.statusMessage);
      this.getRole();
    })
  }

  cancelbulist() {
    this.value = false;

  }
  clear() {
    this.roleName = "";
  }
  navigateTo(){
    this.hrms.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
  }
