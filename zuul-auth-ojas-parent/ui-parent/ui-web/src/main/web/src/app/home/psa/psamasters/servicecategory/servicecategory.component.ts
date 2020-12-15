import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-servicecategory',
  templateUrl: './servicecategory.component.html',
  styleUrls: ['./servicecategory.component.scss']
})
export class ServicecategoryComponent implements OnInit {
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
  servicecategorylist: any;
  servicategoryName: string;
  serviceategoryName:any;
  key: string;
  editname: any;

  constructor(private psa:PsaService,private toast:NotificationService) { }

  ngOnInit() {
   this.getServiceCategory();
  }
  //getall
  getServiceCategory(){


var Requestdata={
  "servicecategoryList": [
      {

      }
  ],
  "transactionType": "getall"
}
console.log("success",Requestdata)
this.psa.getServiceCategory(Requestdata).subscribe(responce=>{
this.servicecategorylist=responce;


this.table=this.servicecategorylist.servicecategoryList;
})

}
//get all close 

//save
setServiceCategory()
{
  var count=0;
  var str = this.serviceategoryName.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.serviceategoryName.replace(/[\. ,:-]+/g, "")
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
	"servicecategoryList":[{
		"serviceategoryId":this.id,
		"serviceategoryName":this.serviceategoryName,
		"serviceStatus":true
		
	}],
   "transactionType":"save"
		
}
console.log("success",reqData);

this.psa.setServiceCategory(reqData).subscribe((response:any)=>{
  this.servicecategorylist = response;
  console.log("Save response",this.servicecategorylist,this.servicecategorylist.message);
 
  if(this.servicecategorylist.message == "service details has saved successfully")
  {
  
    // swal("Service Category details has saved successfully", "","success");
    this.toast.success("Service Category details has saved successfully")
    this.getServiceCategory();
  }
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed")

  this.interviewModearr = this.interviewmodelist.interviewmodeList;
  this.getServiceCategory();
 
});
  }
  
this.id="",
this.serviceategoryName="",
this.value=false;

}
//save close

updateServiceCategory(s){
  
 this.searchfield=false;
 

 console.log("reee",s)
 
 var count = 0
 var str = s.serviceategoryName.replace(/[\. ,:-]+/g, "");
 
 this.table.map(d=>{
   var one=d.serviceategoryName.replace(/[\. ,:-]+/g, "");

   if (one.toLowerCase() == str.toLowerCase() && d.serviceategoryId != this.sid) {
     count = 1
   }
 })
 console.log("couont data" ,count);
 
 if (count == 1) {
   this.toast.error("Duplicates are not allowed")
 
   this.getServiceCategory();
   this.noedit = false;
   this.value=false;
   this.addb=true;
 } else
 {
  var updateRequestData = 
  {
	"servicecategoryList":[{
		"serviceategoryId":this.sid,
		"serviceategoryName":s.serviceategoryName,
		"serviceStatus":this.status
		
	}],
   "transactionType":"update"
		
}
console.log("request sent",updateRequestData)
this.addb=true;
  this.psa.updateServiceCategory(updateRequestData).subscribe((res:any) =>{
    this.masterList = res;
    console.log(this.masterList,"res");
     if(this.masterList.message == "service details has updated successfully"){
      // swal("Service Category details has updated successfully", "","success");
      this.toast.success("Service Category Details updated Successfully")
       this.getServiceCategory();
       this.noedit = false;
     }
     this.getServiceCategory();
    

    },
    error => 
  {
    console.log("error")
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed")
  this.getServiceCategory();
  this.noedit = false;
  })
}
this.servicategoryName="";
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
      this.editname=name
      this.searchfield=true;
      }








cancelbulist(){
  this.value=false;
  this.addb=true;
 this.getServiceCategory();
  }
  clear(){
   this.servicategoryName="";
 }
 cancel() {
  this.noedit=false;
  this.searchfield=false;
  this.addb=true;
  this.key=""
  this.getServiceCategory();
    }
 
    navigateTo(){
      this.psa.navigateToDashboard();
    }

    p: number;
    searchPage(){
      this.p=1;
      }
} 
