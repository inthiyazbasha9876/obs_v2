import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-sezlocation',
  templateUrl: './sezlocation.component.html',
  styleUrls: ['./sezlocation.component.scss']
})
export class SezlocationComponent  implements OnInit {

  
  value: boolean;
  table: any;
  id: any;
  status: any;
  addb=true;
  sezlocres: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize: number = 5;
  sid: any;
  sezlocationList: string;
  editname: any;
  key: string;

  constructor(private psa:PsaService,private toast:NotificationService) { }

  ngOnInit() {
   this.getSezLoc();
  }

  //getall
  getSezLoc(){


var Requestdata={
	"sezlocationList":[{

	}],	
	"transactionType":"getAll"
}
console.log("req data",Requestdata)
this.psa.getSezLoc(Requestdata).subscribe(responce=>{
this.sezlocres=responce;
console.log("Get response",this.sezlocres.sezlocationList)
this.table=this.sezlocres.sezlocationList;
})

}
//get all close

//save
setSezLoc(s)
{ 
  console.log("s",s)
  var count=0;
  var str = s.addsezlocation.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.sezlocationName.replace(/[\. ,:-]+/g, "")
    if (one.toLowerCase() == str.toLowerCase()) {
      count = 1
    }
  })

  if (count == 1) {
      
    this.toast.error("invalid")
    
  }
  else
 {


var reqData=
{
	"sezlocationList":[{
		"sezlocationId":this.sid,
		"sezlocationName":s.addsezlocation,
		"status":true
		
	}],
   "transactionType":"save"
		
}


console.log("Requested",reqData);

this.psa.setSezLoc(reqData).subscribe((response:any)=>{
  this.sezlocres = response;
  console.log("Response",this.sezlocres);
 
  if(this.sezlocres.message == "sez location saved successfully")
  {
  
    // swal("Sez Location has saved successfully", "","success");
    this.toast.success("Sez Location has saved successfully");
    this.getSezLoc();
  }
  
},
error => 
  {
  // swal("Duplicates are not allowed edit","","error");
  this.toast.error("Duplicates are not allowed");
 
  this.getSezLoc();
 
});
 }
this.id="",
this.sezlocationList="",
this.value=false;

}
//save close

updateSezLoc(sezlocTable){
  
 this.searchfield=false;

  console.log("updating value ",sezlocTable);
 

var count = 0
var str = sezlocTable.sezloc1.replace(/[\. ,:-]+/g, "");
this.table.map(d=>{
  var one=d.sezlocationName.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.sezlocationId != this.sid) {
    count = 1
  }
})
if (count == 1) {
  this.toast.error("Duplicates are not allowed")

  this.getSezLoc();
  this.noedit = false;
  this.value=false;
  this.addb=true;
} else{
  var updateRequestData = 
  {
	"sezlocationList":[{
		"sezlocationId":this.sid,
		"sezlocationName":sezlocTable.sezloc1,
		"status":this.status
		
	}],
   "transactionType":"update"
		
}



console.log("Requested",updateRequestData)
this.addb=true;
  this.psa.updateSezLoc(updateRequestData).subscribe((res:any) =>{
    this.sezlocres = res;
    console.log(this.sezlocres);
     if(this.sezlocres.message == "sez location updated successfully"){
      //  swal("Sez Location has updated successfully", "","success");
      this.toast.success("Sez Location has updated successfully");
       this.getSezLoc();
       this.noedit = false;
     }
     
    },
    error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");
  this.getSezLoc();
  this.noedit = false;
  })
}
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
 this.getSezLoc();
  }
  clear(){
   this.sezlocationList="";
 }
 cancel() {
  this.noedit=false;
  this.searchfield=false;
  this.addb=true;
  this.key=""
  this.getSezLoc();
    }
 
    navigateTo(){
      this.psa.navigateToDashboard();
    }

    p: number;
    searchPage(){
      this.p=1;
      }
}
