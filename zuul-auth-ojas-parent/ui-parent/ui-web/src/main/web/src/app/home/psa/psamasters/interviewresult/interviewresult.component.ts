import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-interviewresult',
  templateUrl: './interviewresult.component.html',
  styleUrls: ['./interviewresult.component.scss']
})
export class InterviewresultComponent implements OnInit {
  InterviewResultList: any;
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
  InterviewResultType: string;
  InterviewResultArr: any;
  InterviewResult: any;
  interviewResult: string;
  key: string;
  editname: any;
  // sartypeList: any;

  constructor(private psa:PsaService,private toast:NotificationService) { }

  ngOnInit() {
   this.getInterviewResultInfo();
   console.log("ngOnInit");
   
  }
  //getall
getInterviewResultInfo(){
//console.log("servicetype info function called");

var Requestdata={

  "interviewresultList":[{
  
  
  }], 
  "transactionType":"getall"
  }
console.log("success",Requestdata)
this.psa.getInterviewResults(Requestdata).subscribe(responce=>{
this.InterviewResult=responce;
//this.sarstatusList=this.sarstatusList.servicetypeList;
console.log("Get response",this.InterviewResultList)
console.log("Get response",this.InterviewResult.interviewresultList)
this.table=this.InterviewResult.interviewresultList;
})
console.log("InterviewResult info function called");
}
//get all close

//save
setInterviewResult()
{
  var count=0;
  var str = this.interviewResult.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.interviewResult.replace(/[\. ,:-]+/g, "")
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

  "interviewresultList":[{
    "interviewResult":this.interviewResult,
    "status":true
    
  
  }], 
  "transactionType":"save"
    
}
console.log("success",reqData);

this.psa.setInterviewResults(reqData).subscribe((response:any)=>{
  this.InterviewResultList = response;
  console.log("Save response",this.InterviewResultList,this.InterviewResultList.message);
 
  if(this.InterviewResultList.message == "Interview Result saved successfully")
  {
  
    // swal("Interview result has saved successfully", "","success");
    this.toast.success("Interview result has saved successfully");
    this.getInterviewResultInfo();
  }
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");

  this.InterviewResultArr = this.InterviewResultList.interviewresultList;
  this.getInterviewResultInfo();
 
});
  }
this.id="",
this.interviewResult="",
this.value=false;
  
}
//save close

updateInterviewResult(InterviewResulttable){
 
 console.log("InterviewResulttable",InterviewResulttable);

 var count = 0
 var str = InterviewResulttable.interviewResult.replace(/[\. ,:-]+/g, "");
 this.table.map(d=>{
   var one=d.interviewResult.replace(/[\. ,:-]+/g, "");
   if (one.toLowerCase() == str.toLowerCase() && d.interviewresultId != this.sid) {
     count = 1
   }
 })
 
 if (count == 1) {
   this.toast.error("Duplicates are not allowed")
 
   this.getInterviewResultInfo();
   this.noedit = false;
   this.value=false;
   this.addb=true;
 } else
 {
  var updateRequestData = 
  {
    "interviewresultList":[{
      "interviewresultId":this.sid,
      "interviewResult":InterviewResulttable.interviewResult,
      "status":this.status
      
    
    }], 
    "transactionType":"update"
    
}
console.log("success",updateRequestData)
this.addb=true;
  this.psa.updatInterviewResults(updateRequestData).subscribe((res:any) =>{
    this.masterList = res;
    console.log(this.masterList);
     if(this.masterList.message == "interview result updated successfully"){
      //  swal("Interview Result has updated succesfully", "","success");
      this.toast.success("Interview Result has updated succesfully")
       this.getInterviewResultInfo();
       this.noedit = false;
     }
     this.getInterviewResultInfo();
    },
    error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed")
  this.getInterviewResultInfo();
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
 this.getInterviewResultInfo();
  }
  clear(){
   this.interviewResult="";
 }
 cancel() {
  this.noedit=false;
  this.searchfield=false;
  this.addb=true;
  this.key=""
  this.getInterviewResultInfo();
    }
 
    navigateTo(){
      this.psa.navigateToDashboard();
    }
 
    p: number;
    searchPage(){
      this.p=1;
      }
} 
