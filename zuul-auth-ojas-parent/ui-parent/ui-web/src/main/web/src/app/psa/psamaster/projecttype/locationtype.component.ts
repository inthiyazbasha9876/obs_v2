import { Component, OnInit } from '@angular/core';

import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { PsaService } from 'src/app/home/services/psa.service';

@Component({
  selector: 'app-locationtype',
  templateUrl: './locationtype.component.html',
  styleUrls: ['./locationtype.component.scss']
})
export class LocationtypeComponent implements OnInit {

 
  
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
  locationtype: any;
  locationtypearr: any;
  locationTypeList: string;
  locationType: string;
  addlocationtype: string;
  key: string;
  editname: any;

  constructor(private psa:PsaService,private toast:NotificationService) { }

  ngOnInit() {
   this.getlocationType();
  }
  //getall
  getlocationType(){
console.log("getlocation called");


var Requestdata={
  "locationTypeList": [
      {
        
      }
  ],
  "transactionType": "getall"
}
console.log("success",Requestdata)
this.psa.getlocationType(Requestdata).subscribe(responce=>{
this.locationtype=responce;

console.log("Get response",this.locationtype)
console.log("Get response",this.locationtype.locationTypeList)
this.table=this.locationtype.locationTypeList;
})

}
//get all close

//save
setlocationType(s)
{

  var count=0;
  var str = s.addlocationtype.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.locationType.replace(/[\. ,:-]+/g, "")
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
	"locationTypeList":[{
		"locationtypeId":this.sid,
		"locationType":s.addlocationtype,
		"status":true
		
	}],
   "transactionType":"save"
		
}



console.log("reqdata",reqData);

this.psa.setlocationType(reqData).subscribe((response:any)=>{
  this.locationtype = response;
  console.log("Save response",this.locationtype);
 
  if(this.locationtype.message == "Location type details has saved successfully")
  {
  
    // swal("Locationlist has saved successfully", "","success");
    this.toast.success("Locationlist has saved successfully");
    this.getlocationType();
  }
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");

  this.locationtypearr = this.locationtype.locationTypeList;
  this.getlocationType();
 
});
  }
this.id="",
this.addlocationtype="",
this.value=false;

}
//save close

updatelocationType(locationtypeTable){
  
 // this.searchfield=false;

  console.log("locationtypeTable",locationtypeTable);
  


var count = 0
var str = locationtypeTable.locationType1.replace(/[\. ,:-]+/g, "");
this.table.map(d=>{
  var one=d.locationType.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.locationtypeId != this.sid) {
    count = 1
  }
})

if (count == 1) {
  this.toast.error("Duplicates are not allowed")

  this.getlocationType();
  this.noedit = false;
  this.value=false;
  this.addb=true;

} else{
  var updateRequestData = 
  {
	"locationTypeList":[{
		"locationtypeId":this.sid,
		"locationType":locationtypeTable.locationType1,
		"status":this.status
		
	}],
   "transactionType":"update"
		
}



console.log("success",updateRequestData)
this.addb=true;
  this.psa.updatelocationType(updateRequestData).subscribe((res:any) =>{
    this.masterList = res;
    console.log(this.masterList);
     if(this.masterList.message == "LocationType details has updated successfully"){
      //  swal("Location List has updated successfully", "","success");
      this.toast.success(this.masterList.message)
       this.getlocationType;
       this.noedit = false;
     }
     this.getlocationType();
    },
    error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed")
  this.getlocationType();
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
 this.getlocationType();
  }
  clear(){
   this.locationType="";
 }
 cancel() {
  this.noedit=false;
  this.searchfield=false;
  this.addb=true;
  this.key=""
  this.getlocationType();
    }
 
    navigateTo(){
      this.psa.navigateToDashboard();
    }

    p: number;
    searchPage(){
      this.p=1;
      }
}