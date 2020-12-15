import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-resourcetype',
  templateUrl: './resourcetype.component.html',
  styleUrls: ['./resourcetype.component.scss']
})
export class ResourcetypeComponent implements OnInit {
  getResourceDetails: any;
  getResourceList: any[];
  addb = true;
  value: boolean;
  isEditable: boolean = false;
  setResourceTypeReq;
  noedit: boolean;
  private pageSize: number = 5;
  resourceTypeName: any;
 
  searchfield = false;
  reid: any;
  key: string;
  editname: any;
  
  constructor(private hrms: HrmsService, private toast: NotificationService) {
}

 ngOnInit() {
    this.getResource();
}
//get resources
getResource(){

    var getresrequest ={
      "resourceTypes": [
        {

        }
    ],
    "transactionType": "getAll"
}

this.hrms.getResourceType(getresrequest).subscribe(res=>{
  this.getResourceDetails = res;
  console.log( "resourse detail", this.getResourceDetails)
  this.getResourceList = this.getResourceDetails.employmentDetailsList;
  console.log("getall",this.getResourceList)

})
 //get resource end;

}

//set resourses


setResource(e){

  var count=0;
  var str = e.resourceTypeName.replace(/[\. ,:-]+/g, "");

  this.getResourceList.map(d=>{
    var one=d.resourceTypeName.replace(/[\. ,:-]+/g, "")
    if (one.toLowerCase() == str.toLowerCase()) {
      count = 1
    }
  })

  if (count == 1) {
      
    this.toast.error("Duplicates are not allowed")
    
  }
  else{
    var requestsaveData ={
    "resourceTypes":[{
      "resourceTypeName": e.resourceTypeName
    }],
    "transactionType": "save"
  }
  this.hrms.setResourceType(requestsaveData).subscribe((response: any)=>{
    this.setResourceTypeReq = response;
    console.log("save request",this.setResourceTypeReq)

    if (this.setResourceTypeReq.statusMessage == "Resource Type saved successfully") {
    
      this.toast.success(this.setResourceTypeReq.statusMessage);
      this.resourceTypeName = null;
      this.getResource();
    }
  },

  error => {

    this.toast.error("Duplicates are not allowed");
  })
}

  this.getResource();
  this.value = false;

}


// save request end




// Update request start

updateResResp: any;
updateResRespListArr: any


updateResource(rtlist){
  
this.searchfield = false;

console.log("reee",rtlist)

var count = 0
var str = rtlist.resourceTypeName.replace(/[\. ,:-]+/g, "");
this.getResourceList.map(d=>{
  var one=d.resourceTypeName.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.id != this.reid) {
    count = 1
  }
})

if (count == 1) {
  this.toast.error("Duplicates are not allowed")

  this.getResource()
  this.noedit = false;
  this.value=false;

} else {



  var updateResRequestData =
  {
    "resourceTypes": [
      {
        "id": this.reid,
        "resourceTypeName": rtlist.resourceTypeName

      }
    ],
    "transactionType": "update"
  }

  this.hrms.updateResourceType(updateResRequestData).subscribe((res:any)=>{
    this.updateResResp = res;
this.updateResRespListArr=this.updateResResp.employmentDetailsList;
console.log("req",this.updateResResp);
this.addb = true;
if (this.updateResResp.statusMessage == "Resource Type updated successfully") {
  this.noedit=false;
  this.value=false;
  // swal(this.updateResResp.statusMessage, "","success");
  this.toast.success(this.updateResResp.statusMessage);
  this.getResource();
}
},
// this.resourceTypeName = "";
 error => {

    // swal("Duplicates are not allowed","","error");
    this.toast.error("Invalid");
    this.getResource();
    this.noedit = false;
    this.value = false;
  
  })
 
}
// this.value=false;
// this.resourceTypeName="";
this.key=""
}

//update request end





clear() {
  this.resourceTypeName = "";
}

cancelrtlist() {
  this.value = false;
}

edit(id,name) {
  this.reid = id;
  console.log("edit",this.reid);
  
  this.noedit = true;
  this.value = false;
  this.addb = false;
  this.searchfield = true;
  this.editname=name
}
cancel() {
  this.noedit = false;
  this.searchfield = false;
  this.addb = true;
  this.key=""
  this.getResource();
}




navigateTo(){
  this.hrms.navigateToDashboard();
}

p: number;
searchPage(){
  this.p=1;
  }
}
