import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-contractcompany',
  templateUrl: './contractcompany.component.html',
  styleUrls: ['./contractcompany.component.scss']
})
export class ContractcompanyComponent implements OnInit {
  comcontdata: any;
  comcontlistdata: any;
  comcontabledata:any;
  concomlist: any;
  concomArr: any;
  contcomstatusdata: any;
  value1: boolean;
  btnhide: boolean=true;
  cid: any;
  cvalue:any;
  updateconcomList: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize:number=5;
  value: any;
  contractCompany: string;
  editname: any;
  key: string;


  constructor(private psa:PsaService,private toast:NotificationService) { }

  ngOnInit() {
    this.getcomcontract();

  }
  comcontable(comcontable: any) {
    throw new Error("Method not implemented.");
  }


  getcomcontract(){

    var comconreq={
      "companyList":[{

       }],
      "transactionType":"getall"
  }
   console.log(comconreq,"swatg7")
    
    this.psa.getcomcon(comconreq).subscribe(res=>{
      this.comcontdata=res;
      this.comcontlistdata = this.comcontdata.comapnayList;
    
      this.comcontabledata=this.comcontlistdata;
      console.log(this.comcontabledata,"bfhdafghagj");

      for(let i in this.comcontabledata){
        if(this.comcontabledata[i].status==true){
          this.comcontabledata[i].status="Active";
        }else if(this.comcontabledata[i].status==false){
          this.comcontabledata[i].status="InActive";
        }
        }
      
    
    })
    
  }


  setcontractcom(c)
{

  var count=0;
  var str = c.addcontcom.replace(/[\. ,:-]+/g, "");

  this.comcontabledata.map(d=>{
    var one=d.companyName.replace(/[\. ,:-]+/g, "")
    if (one.toLowerCase() == str.toLowerCase()) {
      count = 1
    }
  })

  if (count == 1) {
      
    this.toast.error("Duplicates are not allowed")
    
  }
  else{
var contcomreqData=
{
    "companyList":[{
        "companyName":c.addcontcom,
        "status":"true"
       
    }],
    "transactionType":"save"
}

console.log("success",contcomreqData);

this.psa.savecomcon(contcomreqData).subscribe(respon=>{
  this.concomlist = respon;
  console.log("Save response",this.concomlist,this.concomlist.message);
 
 
  // swal(this.concomlist.message, "","success");
  this.toast.success(this.concomlist.message);
    this.getcomcontract();
 
  
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed")

  this.concomArr = this.concomlist.companyList;
  this.getcomcontract();
 
});
  }
this.contcomstatusdata="";
this.value1=false;
  
}
updatecompanycontract(comcontable){
  
  this.searchfield = false;

console.log("reee",comcontable)

var count = 0
var str = comcontable.contcomstatusdata.replace(/[\. ,:-]+/g, "");
this.comcontabledata.map(d=>{
  var one=d.companyName.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.id != this.cid) {
    count = 1
  }
})
if (count == 1) {
  this.toast.error("Duplicates are not allowed")

  this.getcomcontract();
  this.noedit = false;
  this.value=false;
  this.btnhide=true;
} else{
   var updateconcomRequestData = 
   {
   "companyList":[{
     "id":this.cid,
     "companyName":comcontable.contcomstatusdata,
     "status":this.cvalue
     
   }],
    "transactionType":"update"
     
 }
 console.log("success",updateconcomRequestData)


 this.btnhide=true;
   this.psa.updatecomcon(updateconcomRequestData).subscribe(res =>{
     this.updateconcomList=res;
     
  // swal(this.updateconcomList.message, "","success");
  this.toast.success(this.updateconcomList.message);
         this.getcomcontract;
        this.noedit = false;
      
      this.getcomcontract();
     },
     error => 
   {
  //  swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");
   this.getcomcontract();
   this.noedit = false;
   })
  }
   this.searchfield=false;
   this.key=""
   }


  clear(){
    this.contractCompany="";
  }

  edit(id,name,value){
    console.log("edit",id)
    this.cid=id;
    this.noedit = true;
    this.cvalue=value;
    this.value1=false;
    this.btnhide=false;
    this.searchfield=true;
    this.editname=name
    }


cancelsavelist(){
  this.value1=false;
  this.btnhide=true;
 this.getcomcontract();
  }
  cancel() {
    this.noedit=false;
    this.searchfield=false;
    this.btnhide=true;
    this.getcomcontract();
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