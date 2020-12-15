import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.scss']
})
export class SkillsComponent implements OnInit {
  sid: any;
  key: string;
  editname: any;

  
  constructor(private hrms:HrmsService,private toast:NotificationService) { }

  ngOnInit() {
  this.getSkillInfo();

  }

//skill starts
skill_name:any;
skill_id:any;

employee_id:any;
created_by:any;
skillReq:any;
SkillArr :any;
skillinfolist:any;
skillInfoList:any;
deleteSkilldetails:any;
deleteSkillarr:any;
skillbyid:any;
SkillDetails:any;
update_by:any;
updateArr:any;
updateRes:any;
value:boolean;

masterList:any;
id:any;
private pageSize: number = 5;

//method save skillInfo
setSkillInfo()



{
 
  var count=0;
  var str = this.skill_name.replace(/[\. ,:-]+/g, "");
  this.skillInfoList.map(d=>{
    var one=d.skill_name.replace(/[\. ,:-]+/g, "")
    if (one.toLowerCase() == str.toLowerCase()) {
      count = 1
    }
  })
  if (count == 1) {
      
    this.toast.error("Duplicates are not allowed")
    
  }
  else{

  

var reqData={
  "listOfSkill" : [{
          "skill_id" : this.skill_id,
          "skill_name": this.skill_name,
          
  }],
  "transactionType" : "save"
}
this.hrms.setSkillMaster(reqData).subscribe((responce:any)=>{
  this.skillReq = responce;
  console.log(this.skillReq);
  if(this.skillReq.message == "Successfully record added")
  {
  
    // swal(this.skillReq.message, "","success");
    this.toast.success(this.skillReq.message);
    this.getSkillInfo();
  }
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");

  this.SkillArr = this.skillReq.listOfSkill;
  this.getSkillInfo();
 
});
  }
this.skill_id="",
this.skill_name="",
this.value=false;

}

//method get skillInfo
getSkillInfo(){

var Requestdata={
  "listOfSkill" : [{
  
          
  }],
  "transactionType" : "getAll"
}
this.hrms.getSkillmaster(Requestdata).subscribe(responce=>{
this.skillinfolist=responce;
this.skillInfoList=this.skillinfolist.listOfSkill;
console.log(this.skillInfoList)
})

}
noedit: boolean;
searchfield=false;
addb=true;
edit(id,name){
this.sid=id;
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
  this.getSkillInfo();
    }


skillUpdatemaster(skilltable){
   
this.searchfield = false;

console.log("reee",skilltable)

var count = 0
var str = skilltable.skill_name.replace(/[\. ,:-]+/g, "");
this.skillInfoList.map(d=>{
  var one=d.skill_name.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.id != this.sid) {
    count = 1
  }
})

if (count == 1) {
  this.toast.error("invalid")

  this.getSkillInfo();
  this.noedit = false;
  this.value=false;
  this.addb=true;
} else {




  


  var updateRequestData = 
  {
    "listOfSkill" : [{
            "id" : this.sid,
            "skill_name" : skilltable.skill_name
            
    }],
    "transactionType" : "update"
}

this.addb=true;
  this.hrms.updateSkillMaster(updateRequestData).subscribe((res:any) =>{
    this.masterList = res;
    console.log(this.masterList);
     if(this.masterList.message == "Successfully record updated"){
      //  swal(this.masterList.message, "","success");
      this.toast.success(this.masterList.message);
       this.getSkillInfo();
       this.noedit = false;
     }
    },
    error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");
  this.getSkillInfo();
  this.noedit = false;
  
  })

}
  this.key=""
}

cancelbulist(){
   this.value=false;
  
   }
   clear(){
    this.skill_name="";
  }




  
  navigateTo(){
    this.hrms.navigateToDashboard();
  }
  p: number;
  searchPage(){
    this.p=1;
    }
  } 
