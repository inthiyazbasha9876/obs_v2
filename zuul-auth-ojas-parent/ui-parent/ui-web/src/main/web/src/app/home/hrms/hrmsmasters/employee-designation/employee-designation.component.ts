import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-employee-designation',
  templateUrl: './employee-designation.component.html',
  styleUrls: ['./employee-designation.component.scss']
})
export class EmployeeDesignationComponent implements OnInit {
  employee: any;
  deleteRes: any;
  updatedRes: any;
  empDesignationlist: any;
  empDesignationDetails: any;
  empDesignationRes: any;
  designation:any;
  id:any;
  private pageSize: number = 5;
//   public data1=[];
//   public show:boolean = false;
//   public buttonName:any = 'Show';



   value: boolean;
  empid: any;
  key: string;
  editname: any;
//   data; 
//  employee:any;
//  employeeDesignation;
//   isEditable:boolean = false;
//   reverse: boolean = false;

  

  constructor(private hrms:HrmsService,private toast:NotificationService) {
   
   }
//    employeedesignationlist:Employeedesignation[]=[{
     
//     "employeeDesignation": "Python"
//    },
//  {
//    "employeeDesignation": "IDM"
//  },
//  {
//    "employeeDesignation": "Service Now"
//  },
//  {
//    "employeeDesignation": "UI"
//  },
//  {
//    "employeeDesignation": "Java"
//  }]




//  saveBu(){
//   debugger;
//   this.value = false;
//   this.data = {
//     "employeeDesignation":this.employee,
   
//   }
//   this.employeedesignationlist.unshift(this.data);

//   this.employeeDesignation='';
 

// }
// editData(blist){
//   console.log(blist);
  
//   this.employee = blist.employeeDesignation;

// }
// saveData(){
//   var editD = {
//     "employeeDesignation":this.employee,
   
//   }
//   console.log(editD);
// }
// deleterow(index){
//   debugger;
//   if(index !== -1){
//     this.employeedesignationlist.splice(0,1);
//   }
//   else {
//   this.employeedesignationlist.splice(index,1);
//   }
// }

// cancelbulist(){
//   this.value=false;
  
// }
  ngOnInit() {
    this.getEmpDesignation();
   }
//   handleClick(event: Event) {
//     console.log('Button clicked', event)
//   }
//   toggle() {
//     this.show = !this.show;

   
//     if(this.show)  
//       this.buttonName = "Hide";
//     else
//       this.buttonName = "Show";
//   }
// ads API code here
setEmpDesignation(){

  var count=0;
  var str = this.employee.replace(/[\. ,:-]+/g, "");

  this.empDesignationlist.map(d=>{
    var one=d.designation.replace(/[\. ,:-]+/g, "")
    if (one.toLowerCase() == str.toLowerCase()) {
      count = 1
    }
  })

  if (count == 1) {
      
    this.toast.error("Duplicates are not allowed")
    
  }
  else
{
  
  var requestData = {
    "designation":[
            {
            "id":this.id,
            "designation":this.employee
    }
    ],
    "sessionId":"3121",
    "transactionType":"save"
}

  this.hrms.setEmployeeDesignation(requestData).subscribe((response:any) =>{
    this.empDesignationRes = response;
    console.log(this.empDesignationRes);
    if(this.empDesignationRes.message == "record added Successfully"){
      
      // swal(this.empDesignationRes.message , "","success");
      this.toast.success(this.empDesignationRes.message)
      this.getEmpDesignation();
    }
  },
  error => 
  {
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed")

  })
}
  this.id="",
  this.employee="",
  this.value = false;

}
getEmpDesignation(){
  var request = {
    "designation":[
           
    ],
    "sessionId":"3121",
    "transactionType":"getall"
}
   this.hrms.getEmployeeDesignation(request).subscribe(res =>{
    this.empDesignationDetails = res;
    this.empDesignationlist = this.empDesignationDetails.listDesignation;
    // console.log("emp designatoin",this.empDesignationlist);
   })
}
noedit: boolean;
addb=true;
edit(id,name){
this.empid=id;
this.noedit = true;
this.value=false;
this.addb=false;
this.editname=name
}
cancel() {
  this.noedit=false;
  this.addb=true;
  this.key=""
  this.getEmpDesignation();
    }
saveUpdatedValues(bulist){

  var count = 0
  var str = bulist.designation.replace(/[\. ,:-]+/g, "");
  this.empDesignationlist.map(d=>{
    var one=d.designation.replace(/[\. ,:-]+/g, "");
    if (one.toLowerCase() == str.toLowerCase() && d.id != this.empid) {
      count = 1
    }
  })
  if (count == 1) {
    this.toast.error("Duplicates are not allowed")
  
    this.getEmpDesignation();
    this.noedit = false;
    this.value=false;
  
  } else
  {



  console.log(bulist);
  var updateRequestData =
  {
    "designation":[
            {
            "id":this.empid,
            "designation":bulist.designation
    }
    ],
    "sessionId":"3121",
    "transactionType":"update"
}
this.addb=true;
  this.hrms.updateEmpolyeeDesignation(updateRequestData).subscribe((res:any) =>{
    this.updatedRes = res;
    console.log(this.updatedRes);
    if(this.updatedRes.message == "record updated Successfully "){
      this.noedit = false;
      // swal(this.updatedRes.message , "","success");
      this.toast.success(this.updatedRes.message)
      this.getEmpDesignation();
    }
  },
  error => 
  {
    this.noedit = false;
  // swal("Duplicates are not allowed","","error");
  this.toast.error("Duplicates are not allowed")
    this.getEmpDesignation();
  })
  
}
this.key=""
}

deleteDesignation(bulist){
  var deleteRequest = {
      "designationrequest" :{
              "id" : bulist.id,
              "designation" : bulist.designation
      },
      "transactionType" : "delete",
      "sessionId" : "132"
  }
  this.hrms.deleteEmpolyeeDesignation(deleteRequest).subscribe(data =>{
    this.deleteRes = data;
    if(this.deleteRes.statusMessage == "Successfully record deleted"){
      // swal(this.deleteRes.statusMessage , "","success");
      this.toast.success(this.deleteRes.statusMessage)
      this.getEmpDesignation();
    }
  })
}
cancelbulist(){
  this.value=false;
   
  }
  clear(){
    this.employee="";
  }
  navigateTo(){
    this.hrms.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
  }
