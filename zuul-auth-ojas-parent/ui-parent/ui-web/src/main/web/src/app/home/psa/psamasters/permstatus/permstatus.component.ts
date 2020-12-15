import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-permstatus',
  templateUrl: './permstatus.component.html',
  styleUrls: ['./permstatus.component.scss']
})
export class PermstatusComponent  implements OnInit {
  permdata:any;
  permstatuslist: any;
  tabledata: any;
  permstatuslistdata: any;
  permstatus: any;
  permstatusId: any;
  savepermlist: any;
  permStatusList: any;
  PermArr: any;
  permstatusList: Object;
  addb: boolean=true;
  pid: any;
  pstatus: any;
  permsList: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize:number=5;
  permstatusdata: string;
  value:boolean;
  key: string;
  editname: any;
  constructor(private psa:PsaService,private toast:NotificationService) { }

  ngOnInit() {
    this.getpermstatus1();
  }


getpermstatus1(){

var req={
  
    "permStatusList": [
        {
          
        }
    ],
    "transactionType": "getall"
}

// console.log(req,"swatg7")

this.psa.getpermstatus(req).subscribe(res=>{
  this.permdata=res
  this.permstatuslistdata = this.permdata.permStatusList;
  console.log("NARAYANA",this.permstatuslistdata); 
  this.tabledata=this.permstatuslistdata;
  console.log(this.tabledata,"bfhdafghagj");
  

})

  }

setpermstatus(p)
{
  
  var count=0;
  var str = p.addpermstatus.replace(/[\. ,:-]+/g, "");

  this.tabledata.map(d=>{
    var one=d.permstatus.replace(/[\. ,:-]+/g, "")
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
	"permStatusList":[{
		"permstatusId":this.permstatusId,
		"permstatus":p.addpermstatus,
		"status":true
		
	}],
   "transactionType":"save"
		
}
console.log("success",reqData);

this.psa.savepermstatus(reqData).subscribe(resp=>{
  this.permstatuslist = resp;
  console.log("Save response",this.permstatuslist,this.permstatuslist.message);
 
 
  // swal(this.permstatuslist.message, "","success");
  this.toast.success(this.permstatuslist.message);
    this.getpermstatus1();
 
  
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");

  this.PermArr = this.permstatuslist.permStatusList;
  this.getpermstatus1();
 
});
  }
this.permstatusdata="";
this.value=false;

}




cancelbulist(){
  this.value=false;
  this.addb=true;
 this.getpermstatus1();
  }
  
  clear(){
    this.permstatus="";
  }
  cancel() {
    this.noedit=false;
    this.searchfield=false;
    this.addb=true;
    this.key=""
    this.getpermstatus1();
      }


  updatepermstatus(permtable){
  

   
     console.log("servicetable",permtable);
     this.searchfield = false;

console.log("reee",permtable)

var count = 0
var str = permtable.permstatusdata.replace(/[\. ,:-]+/g, "");
this.tabledata.map(d=>{
  var one=d.permstatus.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.permstatusId != this.pid) {
    count = 1
  }
})

if (count == 1) {
  this.toast.error("Duplicates are not allowed")

  this.getpermstatus1();
  this.noedit = false;
  this.value=false;
  this.addb=true;
} else{
     var updateRequestData = 
     {
     "permStatusList":[{
       "permstatusId":this.pid,
       "permstatus":permtable.permstatusdata,
       "status":this.pstatus
       
     }],
      "transactionType":"update"
       
   }
   console.log("success",updateRequestData)
   this.addb=true;
     this.psa.updatepermstatus(updateRequestData).subscribe(res =>{
       this.permsList=res;
       console.log(this.permsList);
    
          // swal(this.permsList.message, "","success");
          this.toast.success(this.permsList.message);
    
          this.noedit = false;
        
        this.getpermstatus1();
       },
       error => 
     {
    //  swal("Duplicates are not allowed","","error");
    this.toast.error("Duplicates are not allowed");
     this.getpermstatus1();
     this.noedit = false;
     })
    }
     this.key=""
     }




  edit(permstatusId,name,status){
    this.pid=permstatusId;
    this.noedit = true;
    this.value=false;
    this.pstatus=status
    this.addb=false;
    this.searchfield=true;
    this.editname=name;
    }


 
    navigateTo(){
      this.psa.navigateToDashboard();
    }
 
    p: number;
    searchPage(){
      this.p=1;
      }
}