import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-deliverylocation',
  templateUrl: './deliverylocation.component.html',
  styleUrls: ['./deliverylocation.component.scss']
})
export class DeliverylocationComponent implements OnInit {

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
  deliveryLocarr: any;
  deliveryLoclist: any;
  deliverylocationName: string;
  key: string;
  editname: any;

  constructor(private psa:PsaService,private toast:NotificationService ) { }

  ngOnInit() {
   this.getdeliverylocation();
  }
  //getall
  getdeliverylocation(){


var Requestdata={
  "deliverylocationList":[{
  
    
  
  
    }],	
    "transactionType":"getall"
  }
  
console.log("success",Requestdata)
this.psa.getdeliverylocation(Requestdata).subscribe(responce=>{
this.deliveryLoclist=responce;

console.log("Get response",this.deliveryLoclist)

this.table=this.deliveryLoclist.deliverylocationList;
})

}
//get all close 

//save
setdeliverylocation()
{
 
  var count=0;
  var str = this.deliverylocationName.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.deliverylocationName.replace(/[\. ,:-]+/g, "")
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
	"deliverylocationList":[{
		"deliverylocationId":this.id,
		"deliverylocationName":this.deliverylocationName,
		"status":true
		
	}],
   "transactionType":"save"
		
}
console.log("success",reqData);

this.psa.setdeliverylocation(reqData).subscribe((response:any)=>{
  this.deliveryLoclist = response;
  console.log("Save response",this.deliveryLoclist,this.deliveryLoclist.message);
 
  if(this.deliveryLoclist.message == "service details has saved successfully")
  {
  
    // swal("Delivery Location has saved successfully", "","success");
    this.toast.success("Delivery Location has saved successfully");
    this.getdeliverylocation();
  }
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");

  this.deliveryLocarr = this.deliveryLoclist.deliverylocationList;
  this.getdeliverylocation();
 
});
this.id="",
this.deliverylocationName="",
this.value=false;
  }
}
//save close

updatedeliverylocation(deliverylocation){
  
 this.searchfield=false;


 console.log("reee",deliverylocation)
 
 var count = 0
 var str = deliverylocation.deliverylocationName.replace(/[\. ,:-]+/g, "");
 this.table.map(d=>{
   var one=d.deliverylocationName.replace(/[\. ,:-]+/g, "");
   if (one.toLowerCase() == str.toLowerCase() && d.deliverylocationId != this.sid) {
     count = 1
   }
 })
 if (count == 1) {
   this.toast.error("Duplicates are not allowed")
 
   this.getdeliverylocation();
   this.noedit = false;
   this.value=false;
  this.addb=true;
 } else{
  console.log("updating value is",deliverylocation);
  var updateRequestData = 
  {
	"deliverylocationList":[{
		"deliverylocationId":this.sid,
		"deliverylocationName":deliverylocation.deliverylocationName,
		"status":this.status
		
	}],
   "transactionType":"update"
		
}
console.log("request sent",updateRequestData)
this.addb=true;
  this.psa.updatedeliverylocation(updateRequestData).subscribe((res:any) =>{
    this.masterList = res;
    console.log(this.masterList,res);
     if(this.masterList.message == "service details has updated successfully"){
      // swal("Delivery Location has updated successfully", "","success");
      this.toast.success("Delivery Location has updated successfully");
       this.getdeliverylocation();
       this.noedit = false;
     }
     console.log("success update")
     
    },
    error => 
  {
    console.log("error")
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");
  this.getdeliverylocation();
  this.noedit = false;
  })
}
this.key="";
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
 this.getdeliverylocation();
  }
  clear(){
   this.deliverylocationName="";
 }
 cancel() {
  this.noedit=false;
  this.searchfield=false;
  this.addb=true;
  this.getdeliverylocation();
  this.key=""
    }
 
    navigateTo(){
      this.psa.navigateToDashboard();
    }
 
    p: number;
    searchPage(){
      this.p=1;
      }
}
