import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-interviewmode',
  templateUrl: './interviewmode.component.html',
  styleUrls: ['./interviewmode.component.scss']
})
export class InterviewmodeComponent implements OnInit {
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
interviewModearr: any;
interviewmodelist: any;
interviewMode: string;
key: string;
editname: any;

constructor(private psa:PsaService,private toast:NotificationService) { }

ngOnInit() {
 this.getInterViewMode();
}


//save
setInterviewModel(s)
{

var count=0;
var str = s.addinterviewMode.replace(/[\. ,:-]+/g, "");

this.table.map(d=>{
  var one=d.interviewMode.replace(/[\. ,:-]+/g, "")
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
"interviewmodeList":[{
  //"interviewmodeId":this.sid,
  "interviewMode":s.addinterviewMode,
  "status":"True"
  
}],
 "transactionType":"save"
  
}
console.log("success",reqData);

this.psa.setInterviewMode(reqData).subscribe((response:any)=>{
this.interviewmodelist = response;
console.log("Save response",this.interviewmodelist,this.interviewmodelist.message);

if(this.interviewmodelist.message == "record saved successfully")
{

  // swal(this.interviewmodelist.message, "","success");
  this.toast.success(this.interviewmodelist.message);
  this.getInterViewMode();
}

},
error => 
{
// swal("Duplicates are not allowed","","error");
this.toast.error("Duplicates are not allowed");



});
}
//this.id="",
this.interviewMode="",
this.value=false;


}
//save close

//getall
getInterViewMode(){


var Requestdata={
  "interviewmodeList" : [{
         
  }],
  "transactionType" : "getall"
}
console.log("success",Requestdata)
this.psa.getInterviewMode(Requestdata).subscribe(responce=>{
this.interviewmodelist=responce;

console.log("Get response",this.interviewmodelist)
console.log("Get response",this.interviewmodelist.interviewmodeList)
this.table=this.interviewmodelist.interviewmodeList;
})

}
//get all close

updateinterviewMode(upinterviewMode){

this.searchfield=false;


console.log("reee",upinterviewMode)

var count = 0
var str = upinterviewMode.interviewMode.replace(/[\. ,:-]+/g, "");
this.table.map(d=>{
 var one=d.interviewMode.replace(/[\. ,:-]+/g, "");
 if (one.toLowerCase() == str.toLowerCase() && d.interviewmodeId != this.sid) {
   count = 1
 }
})
if (count == 1) {
 this.toast.error("Duplicates are not allowed")

 this.getInterViewMode();
 this.noedit = false;
 this.value=false;
this.addb=true;
} else{
console.log("updating value is",upinterviewMode);
var updateRequestData = 
{
"interviewmodeList":[{
  "interviewmodeId":this.sid,
  "interviewMode":upinterviewMode.interviewMode,
  "status":this.status
  
}],
 "transactionType":"update"
  
}
console.log("request sent",updateRequestData)
this.addb=true;
this.psa.updateInterviewMode(updateRequestData).subscribe((res:any) =>{
  this.masterList = res;
  console.log(this.masterList,res);
   if(this.masterList.message == "updated successfully"){
    //  swal(this.masterList.message, "","success");
    this.toast.success("Interview mode has updated successfully");
     this.getInterViewMode();
     this.noedit = false;
   }
   
   
  },
  error => 
{
  console.log("error")
// swal("Duplicates are not allowed","","error");
this.toast.error("Duplicates are not allowed");
this.getInterViewMode();
this.noedit = false;
})
}
this.getInterViewMode();
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
this.getInterViewMode();
}
clear(){
 this.interviewMode="";
}
cancel() {
this.noedit=false;
this.searchfield=false;
this.addb=true;
this.key=""
this.getInterViewMode();
  }
 
  navigateTo(){
    this.psa.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
}
