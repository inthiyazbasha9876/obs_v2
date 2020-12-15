import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-projecttechstack',
  templateUrl: './projecttechstack.component.html',
  styleUrls: ['./projecttechstack.component.scss']
})
export class ProjecttechstackComponent implements OnInit {

  value: boolean;
  table: any;
  id: any;
  status: any;
  addb=true;
  masterList: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize: number = 5;
  sid: any;
  
projecttype:any;
  projectTypeList: string;
  projecttypearr: any;
  technology: string;
  editname: any;
  key: string;
 

  constructor(private psa:PsaService,private toast:NotificationService) { }

  ngOnInit() {
   this. getProjectTechStack();
  }
  //getall
  getProjectTechStack(){
console.log("getproject called");


var Requestdata={
  "projectTechStackList": [
      {
          
      }
  ],
  "transactionType": "getall"
}
console.log("success",Requestdata)
this.psa.getProjectTechStack(Requestdata).subscribe(responce=>{
this.projecttype=responce;

console.log("Get response",this.projecttype)
console.log("Get response",this.projecttype.projectTechStackList)
this.table=this.projecttype.projectTechStackList;
})

}
//get all close

//save
setProject()
{
  var count=0;
  var str = this.technology.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.technology.replace(/[\. ,:-]+/g, "")
    if (one.toLowerCase() == str.toLowerCase()) {
      count = 1
    }
  })

  if (count == 1) {
      
    this.toast.error("Duplicates are not allowed")
    
  }
  else{
var reqData=
{
 "projectTechStackList": [
      {
      "technology":this.technology,
      "status":"true"
         
      }
  ],
  "transactionType": "save"
}




console.log("reqdata",reqData);

this.psa.setProjectTechStack(reqData).subscribe((response:any)=>{
  console.log(response,"res");

  this. projecttype= response;
  console.log("Save response",this. projecttype);
 
  if(this. projecttype.message == "Project Tech Stack details has saved successfully")
  {
  
    // swal("Projecttechstack has saved successfully", "","success");
    this.toast.success("Projecttechstack has saved successfully")
    this. getProjectTechStack();
  }
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");

  if(this.projecttype.message == "service details has saved successfully")
  this.projecttypearr = this. projecttype.projectTypeList;
  this. getProjectTechStack();
 
});
  }
this.id="",
this.technology="",
this.value=false;

}
//save close

UpdateProject(projecttypeTable){
  
 

  console.log("ratetypeTable",projecttypeTable);
  this.searchfield = false;

console.log("Updating value",projecttypeTable)

var count = 0
var str = projecttypeTable.technology.replace(/[\. ,:-]+/g, "");
this.table.map(d=>{
  var one=d.technology.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.id != this.sid) {
    count = 1
  }
})

if (count == 1) {
  this.toast.error("Duplicates are not allowed")

  this. getProjectTechStack();
  this.noedit = false;
  this.value=false;
  this.addb=true;
} else{
  var updateRequestData = 
  {
    "projectTechStackList": [
        {
            "id": this.sid,
            "technology": projecttypeTable.technology,
            "status": false
        }
    ],
    "transactionType": "update"
}

console.log("success",updateRequestData)
this.addb=true;
  this.psa.UpdateProjectTechstack(updateRequestData).subscribe((res:any) =>{
    this.masterList = res;
    console.log(res);
     if(this.masterList.message == "service details has updated successfully"){
      //  swal("Project tech stack has updated successfully", "","success");
      this.toast.success("Project tech stack has updated successfully");
       this.getProjectTechStack();
       this.noedit = false;
     }
     this.getProjectTechStack();
    },
    error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");
  this.getProjectTechStack();
  this.noedit = false;
  })
}
  this.searchfield=false;
  this.key=""
  }


    edit(id,name,status){
      console.log("edit",id)
      this.sid=id;
      this.noedit = true;
      this.value=false;
      this.status=status
      this.addb=false;
      this.searchfield=true;
      this.editname=name
      }




cancelbulist(){
  this.value=false;
  this.addb=true;
 this.getProjectTechStack();
  }
  clear(){
   this.technology="";
 }
 cancel() {
  this.noedit=false;
  this.searchfield=false;
  this.addb=true;
  this.key=""
  this.getProjectTechStack();
    }
 
    navigateTo(){
      this.psa.navigateToDashboard();
    }
 
    p: number;
    searchPage(){
      this.p=1;
      }
}
