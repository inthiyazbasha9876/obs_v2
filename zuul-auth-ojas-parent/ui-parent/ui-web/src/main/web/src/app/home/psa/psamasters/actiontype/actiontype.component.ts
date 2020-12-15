import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-actiontype',
  templateUrl: './actiontype.component.html',
  styleUrls: ['./actiontype.component.scss']
})
export class ActiontypeComponent implements OnInit {

  actiontypelist: any;
  table: any;
  id:any;
  actiontypeListarr:any;
  actiontype:string;
  value:boolean;
  addb=true;
  private pageSize: number = 5;
  sid:any;
  noedit:boolean;
  searchfield:boolean;
  status:any;
  editname: any;
  key: string;

  constructor(private psa: PsaService,private toast:NotificationService) { }

  ngOnInit() {
    this.getActionType();

  }

//getAll
  getActionType() {
    var actionType =
    {
      "actionTypeList": [
        {


        }
      ],
      "transactionType": "getall"
    }


    this.psa.getActionType(actionType).subscribe(res => {

      this.actiontypelist = res;
      console.log("Get response", this.actiontypelist)
      console.log("Get response", this.actiontypelist.actionTypeList)
      // for(let i in this.actiontypelist.actionTypeList){


      //   if(this.actiontypelist.actionTypeList[i].status==true){
      //     this.actiontypelist.actionTypeList[i].status="Active"
      //   }
      //   }
      this.table = this.actiontypelist.actionTypeList;

    }

    )
  }



  //save
setActionType()
{

  var count=0;
  var str = this.actiontype.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.actiontype.replace(/[\. ,:-]+/g, "")
    if (one.toLowerCase() == str.toLowerCase()) {
      count = 1
    }
  })

  if (count == 1) {
      
    this.toast.error("Duplicates are not allowed")
    
  }
  else
 {
var actionTypereqData=
{
	"actionTypeList":[{
    
    
    "actiontype":this.actiontype,
		"status":true
		
	}],
   "transactionType":"save"
		
}
console.log("success",actionTypereqData);

this.psa.setActionType(actionTypereqData).subscribe((response:any)=>{
  this.actiontypelist = response;
  console.log("Save response",this.actiontypelist,this.actiontypelist.message);
 
//   if(this.actiontypelist.message == "action type details saved successfully")
//   {
  
//     swal("action type details saved successfully", "","success");
//     this.getActionType()
//   }
  
// },
if(this.actiontypelist.message == "record saved successfully")
  {
  
    // swal("record saved successfully", "","success");
    this.toast.success("record saved successfully");
    this.getActionType()
  }
  
},
error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed");

  this.actiontypeListarr = this.actiontypelist.actionTypeList[0];
  this.getActionType()
 
});
this.id="",
this.actiontype="",
this.value=false;
 }
}


// getById

edit(id,name, status) {
  console.log("edit", id)
  this.sid = id;
  this.noedit = true;
  this.value = false;
  this.status = status
  this.addb = false;
  this.searchfield = true;
  this.editname=name
}


//update Action Type

actionList:any;

updateActionType(actionTypeName){
  var count = 0
  var str = actionTypeName.actiontype.replace(/[\. ,:-]+/g, "");
  this.table.map(d=>{
    var one=d.actiontype.replace(/[\. ,:-]+/g, "");
    if (one.toLowerCase() == str.toLowerCase() && d.id != this.sid) {
      count = 1
      console.log("count in if",count)
    }
  })
  console.log("count in ",count)
  if (count == 1) { 
    this.getActionType();
    this.noedit = false;
    this.value=false;
    this.addb=true;
    this.toast.error("Duplicates are not allowed")
  
  
  
  } else{

  var updateActionreq={
    "actionTypeList": [
        {
            "id":this.sid,
            "actiontype": actionTypeName.actiontype,
            "status": true
        }
    ],
    "transactionType": "update"
}
this.addb = true;
console.log("req",updateActionreq);

this.psa.updateActionType(updateActionreq).subscribe(res=>{
  console.log(res)
this.actionList=res;
console.log(this.actionList,"upDate");

if (this.actionList.message == "updated successfully") {
  // swal("updated successfully", "", "success");
  this.toast.success("Action type updated successfully");
  this.getActionType();
  this.noedit = false;
}


},
error => {
  console.log("error")
  // swal("Duplicates are not allowed", "", "error");
  this.toast.error("Duplicates are not allowed");
  this.getActionType();
  this.noedit = false;
})
}
this.searchfield = false;
this.key=""
}

cancelbulist() {
  this.value = false;
  this.addb = true;
  this.getActionType();
}


clear() {
  this.actiontype = "";
}


cancel() {
  this.noedit = false;
  this.searchfield = false;
  this.addb = true;
  this.getActionType();
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