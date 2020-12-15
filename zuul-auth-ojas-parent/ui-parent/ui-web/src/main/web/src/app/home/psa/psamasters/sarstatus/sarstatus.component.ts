import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-sarstatus',
  templateUrl: './sarstatus.component.html',
  styleUrls: ['./sarstatus.component.scss']
})
export class SarstatusComponent  implements OnInit {
  sartypeList: any;
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
  sarType: string;
  SarArr: any;
  sarStatus: any;
  key: string;
  editname: any;
  // sartypeList: any;

  constructor(private psa:PsaService,private toast:NotificationService) { }

  ngOnInit() {
   this.getSarTypeInfo();
  }
  //getall
getSarTypeInfo(){
console.log("servicetype info function called");

var Requestdata={
  "sarstatusList":[{
    
  
  }], 
  "transactionType":"getall"
}
console.log("req data",Requestdata)
this.psa.getsarstaustype(Requestdata).subscribe(responce=>{
this.sartypeList=responce;
//this.sarstatusList=this.sarstatusList.servicetypeList;
console.log("Get response",this.sartypeList)
console.log("Get response",this.sartypeList.sarstatusList)
this.table=this.sartypeList.sarstatusList;
})

}
//get all close

//save

setsarstaustype()
{

  var count=0;
  var str = this.sarStatus.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.sarStatus.replace(/[\. ,:-]+/g, "")
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

    "sarstatusList":[{
    "sarStatus":this.sarStatus,
    "status":true
  
    
    }], 
    "transactionType":"save"
    
}
console.log("success",reqData);

this.psa.setsarstaustype(reqData).subscribe((response:any)=>{
  this.sartypeList = response;
  console.log("Save response",this.sartypeList,this.sartypeList.message);

  if(this.sartypeList.message == "sarStatus details saved successfully")
  {
  
    // swal("sarStatus details saved successfully", "","success");
    this.toast.success("sarStatus details saved successfully");
    this.getSarTypeInfo();
  }
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");

  this.SarArr = this.sartypeList.sartypeList;
  this.getSarTypeInfo();
 
});
  }
this.id="",
this.sarStatus="",
this.value=false;

}
//save close

updatesarstatustype(sarStatustable){

  console.log("sarStatustable",sarStatustable);
  var count = 0
  var str = sarStatustable.sarStatus.replace(/[\. ,:-]+/g, "");
  console.log("table data",this.table);
  this.table.map(d=>{
    var one=d.sarStatus.replace(/[\. ,:-]+/g, "");
    if (one.toLowerCase() == str.toLowerCase() && d.sarstatusId != this.sid) {
      count = 1
      console.log("if count", count);
    }
  })
console.log("count",count)
  if (count == 1) {
    this.toast.error("Duplicates are not allowed")
  
    this.getSarTypeInfo();
    this.noedit = false;
    this.value=false;
  this.addb=true
  } else
 
  
{
  
  var updateRequestData = 
  {
    "sarstatusList":[{
      "sarstatusId":this.sid,
    "sarStatus":sarStatustable.sarStatus,
    "status":false
  }], 
    "transactionType":"update"
    
}
console.log("success",updateRequestData)
this.addb=true;
  this.psa.updatesarstatustype(updateRequestData).subscribe((res:any) =>{
    this.masterList = res;
    console.log(this.masterList,"sarUpdate");
     if(this.masterList.message == "sarStatus Details updated Successfully"){
      //  swal("sarStatus Details updated Successfully", "","success");
      this.toast.success("sarStatus Details updated Successfully");
       this.getSarTypeInfo;
       this.noedit = false;
     }
     this.getSarTypeInfo();
    },
    error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");
  this.getSarTypeInfo();
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
 this.getSarTypeInfo();
  }
  clear(){
   this.sarStatus="";
 }
 cancel() {
  this.noedit=false;
  this.searchfield=false;
  this.addb=true;
  this.key=""
  this.getSarTypeInfo();
    }
 
    navigateTo(){
      this.psa.navigateToDashboard();
    }
 
    p: number;
    searchPage(){
      this.p=1;
      }
 }
