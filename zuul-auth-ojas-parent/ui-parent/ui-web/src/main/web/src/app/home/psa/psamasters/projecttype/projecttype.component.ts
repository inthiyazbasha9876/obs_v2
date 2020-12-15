import { Component, OnInit } from '@angular/core';

import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { PsaService } from 'src/app/home/services/psa.service';

@Component({
  selector: 'app-projecttype',
  templateUrl: './projecttype.component.html',
  styleUrls: ['./projecttype.component.scss']
})
export class ProjecttypeComponent implements OnInit {
  value: boolean;
  table: any;
  id: any;
  status: any;
  addb = true;
  masterList: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize: number = 5;
  sid: any;

  projecttype: any;

  projecttypeList: any;
  projecttypelist: any;
 


  textfield: any
 
  editname: any;
  key: string;
  constructor(private psa: PsaService,private toast:NotificationService) { }
  

  ngOnInit() {
    this.getProjectType()

  }
  //get all//
  getProjectType() {

    var Requestdata = 
    {
      "projectTypeList":[{
      
      }],
      "transactionType":"getall"
    }
    console.log("success", Requestdata)
    let td;
    this.psa.getProjectType(Requestdata).subscribe(responce => {
      this.projecttypelist = responce;

      console.log("Get response", this.projecttypelist)
      console.log("Get response", this.projecttypelist.body.projectTypeList)
      td = this.projecttypelist.projecttypeList;
      this.table = this.projecttypelist.body.projectTypeList;
      console.log('Get project', this.table);
    })
    
  }


  //save//

  setProjectTypeSave(a) {
   var count=0;
     var str = a.addaprojecttype.replace(/[\. ,:-]+/g, "");
  
     this.table.map(d=>{
      var one=d.projectType.replace(/[\. ,:-]+/g, "")
       if (one.toLowerCase() == str.toLowerCase()) {
       count = 1
       }
    })
  
    if (count == 1) {
        
       this.toast.error("Duplicates are not allowed")
      
     }
    else{
     console.log("save", a.addprojecttype);

    var reqData =
    {
      "projectTypeList":[{
        "projectType":a.addaprojecttype,
        "status":true
      }],
      "transactionType":"save"
    }
    console.log("success", reqData);

    this.psa.setProjectType(reqData).subscribe((response: any) => {
      this.projecttypelist = response;
      console.log("Save response", this.projecttypelist, this.projecttypelist.message);

     
        // swal("Action Owner saved successfully", "", "success");
        this.toast.success("project type details saved successfully")
        this.getProjectType();
        this.value = false;
      

    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")
        this.value = false;
        this.projecttypelist = this.projecttypelist.actionOwnerlist;
        this.getProjectType();

      });
    }
  }

  updateprojecttypedata( updateProjectType,id) {
    
    console.log('Table', this.table);
    var count = 0
    
    this.table.map(d=>{
      var one=d.projectType.replace(/[\. ,:-]+/g, "");

      var str = updateProjectType.projecttype.replace(/[\. ,:-]+/g, "");
      // console.log('STR', str);
      // console.log('ONE', one);
      
      if (one.toLowerCase() == str.toLowerCase() && d.actionownerId != this.sid) {
        // console.log(one, 'Matched with ', str);
        
        count = 1
       
      }
    })
    if (count == 1) {

      this. getProjectType()
      console.log("getlist",this.table)
      this.noedit = false;
      this.value=false;
      this.addb=true;
      this.toast.error("Duplicates are not allowed")
    
    } else {
    // this.searchfield=false;
  
    console.log("updating value is", updateProjectType.projecttype);
    var updateRequestData =
    {
      "projectTypeList":[{
        "id":id,
        "projectType": updateProjectType.projecttype,

        "status":true
      }],
      "transactionType":"update"
    }
    console.log("request sent", updateRequestData)
    this.addb = true;
    this.psa. updateProjectType(updateRequestData).subscribe(res => {
      this.masterList = res;
      console.log(this.masterList);
      this.getProjectType();
      console.log("success update")
      // swal(this.masterList.message,"", 'success')
      this.toast.success(this.masterList.message)
      this.noedit = false;
    },
      error => {
        console.log("error", error)
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")
        this.getProjectType();
        this.noedit = false;
      })
    }
    //  this. getActionOwner();
     this.searchfield=false;
    this.key="";
  }

  edit(id,name, status) {
    console.log("edit", id,this.table)
    this.sid = id;
    this.noedit = true;
    this.value = false;
    this.status=status
    this.addb = false;
    this.searchfield = true;
    this.editname=name
  }
  cancelbulist() {
    this.value = false;
    this.addb = true;
    this.getProjectType();
  }
  clear() {
    this.projecttype = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key="";
    this.getProjectType();
  }
  
  navigateTo(){
    this.psa.navigateToDashboard();
  }
  p: number;
  searchPage(){
    this.p=1;
    }


}
