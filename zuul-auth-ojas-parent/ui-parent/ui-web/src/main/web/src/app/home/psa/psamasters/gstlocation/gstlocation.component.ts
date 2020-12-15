import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-gstlocation',
  templateUrl: './gstlocation.component.html',
  styleUrls: ['./gstlocation.component.scss']
})
export class GstlocationComponent  implements OnInit {

  gstLocationTypeList: any;
  value: boolean;
  table: any;
  id: any;
  //serviceType: any;
  status: any;
  addb=true;
  masterList: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize: number = 5;
  sid: any;
  //ServiceArr: any;
  gstLocationType: string;
  gstLocationArr: any;
  gstCode: string;
  gstlocationName: any;
  key: string;
  editname: any;
  // gstLocationTypeList: any;

  constructor(private psa:PsaService,private toast:NotificationService) { }

  ngOnInit() {
   this.getGstLocationInfo();
  }
  //getall
  getGstLocationInfo(){
//console.log("servicetype info function called");

var Requestdata={
  "gstlocationList":[{

  
  

  }], 
  "transactionType":"getAll"
}
console.log("req data",Requestdata)
this.psa. getGstLocation(Requestdata).subscribe(responce=>{
this.gstLocationTypeList=responce;
//this.gstLocationList=this.gstLocationList.servicetypeList;
console.log("Get response",this.gstLocationTypeList)
console.log("Get response",this.gstLocationTypeList.gstlocationList)
this.table=this.gstLocationTypeList.gstlocationList;
})
console.log("sartype info function called");
}
//get all close

//save

setgstLocationtype(s)

{
  var count=0;
  var str = s.addgstLocation.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.gstlocationName.replace(/[\. ,:-]+/g, "")
    if (one.toLowerCase() == str.toLowerCase()) {
      count = 1
    }
  })

  if (count == 1) {
      
    this.toast.error("Duplicates are not allowed")
    
  }
  else

  {

var reqData=
{

  "gstlocationList":[{

    "gstlocationName":s.addgstLocation,
    "gstcode":s.addgstLocation1,
    "status":true
  
    }], 
    "transactionType":"save"
    
}
console.log("success gst req ",reqData);

this.psa.setGstLocation(reqData).subscribe((response:any)=>{
  this.gstLocationTypeList = response;
  console.log("Save response",this.gstLocationTypeList,this.gstLocationTypeList.message);

  if(this.gstLocationTypeList.message == "service details has saved successfully")
  {
  
    // swal("GST Status has saved successfully", "","success");
    this.toast.success("GST Status has saved successfully");
    this.getGstLocationInfo();
  }
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");

  // this.gstLocationArr = this.gstLocationTypeList.gstLocationTypeList;
  // this.getGstLocationInfo();
 
});
  }
this.id="",
this.gstlocationName="",
this.gstCode="",
this.value=false;

}
//save close

updategstLocationtype(gstLocationtable){
  
 console.log("updating value",gstLocationtable)
 
 var count = 0
 var str = gstLocationtable.gstLocation_type.replace(/[\. ,:-]+/g, "");
 this.table.map(d=>{
   var one=d.gstlocationName.replace(/[\. ,:-]+/g, "");
   if (one.toLowerCase() == str.toLowerCase() && d.c2hstatusId != this.sid) {
     count = 1
   }
 })
 if (count == 1) {
   this.toast.error("Duplicates are not allowed")
 
   this.getGstLocationInfo();
   this.noedit = false;
   this.value=false;
   this.addb=true;
 } else{

  console.log("gstLocationtable",gstLocationtable);
  var updateRequestData = 
  {
    "gstlocationList":[{

      "gstlocationId":this.sid,
    "gstlocationName":gstLocationtable.gstLocation_type,
    "gstcode":gstLocationtable.gstLocation_type1,
    "status":this.status
  
    }], 
    "transactionType":"update"
    
}
console.log("success",updateRequestData)
this.addb=true;
  this.psa. updateGstLocation(updateRequestData).subscribe((res:any) =>{
    this.masterList = res;
    console.log(this.masterList);
     if(this.masterList.message == "service details has updated successfully"){
      //  swal("GST Details updated succesfully", "","success");
      this.toast.success("GST Details updated succesfully");
       this.getGstLocationInfo;
       this.noedit = false;
     }
     this.getGstLocationInfo();
    },
    error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");
  this.getGstLocationInfo();
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
 this.getGstLocationInfo();
  }
  clear(){
   this.gstLocationType="";
   this.gstCode="";
 }
 cancel() {
  this.noedit=false;
  this.searchfield=false;
  this.addb=true;
  this.key=""
  this.getGstLocationInfo();
    }
 
    navigateTo(){
      this.psa.navigateToDashboard();
    }
 
    p: number;
    searchPage(){
      this.p=1;
      }
}
