import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-servicetype',
  templateUrl: './servicetype.component.html',
  styleUrls: ['./servicetype.component.scss']
})
export class ServicetypeComponent implements OnInit {
  servicetypelist: any;
  value: boolean;
  table: any;
  id: any;
  serviceType: any;
  status: any;
  addb = true;
  masterList: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize: number = 5;
  sid: any;
  ServiceArr: any;
  serviceTypeName: any;
  key: string;
  editname: any;

  constructor(private psa: PsaService,private toast:NotificationService) { }

  ngOnInit() {
    this.getServiceTypeInfo();
  }
  //getall
  getServiceTypeInfo() {
    //console.log("servicetype info function called");

    var Requestdata = {
      "servicetypeList": [{


      }],
      "transactionType": "getall"
    }
    console.log("success", Requestdata)
    this.psa.getservicetype(Requestdata).subscribe(responce => {
      this.servicetypelist = responce;
      //this.servicetypelist=this.servicetypelist.servicetypeList;
      console.log("Get response", this.servicetypelist)
      console.log("Get response", this.servicetypelist.servicetypeList)
      this.table = this.servicetypelist.servicetypeList;
    })
    console.log("servicetype info function called");
  }
  //get all close

  //save
  setservicetype() {
    var count=0;
    var str = this.serviceTypeName.replace(/[\. ,:-]+/g, "");
  
    this.table.map(d=>{
      var one=d.serviceTypeName.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })
  
    if (count == 1) {
        
      this.toast.error("Duplicates are not allowed")
      
    }
    else{
    var reqData =
    {
      "servicetypeList":[{
        
      "serviceTypeName":this.serviceTypeName,
      "status":true
      
      }],	
      "transactionType":"save"
    }
    console.log("success", reqData);

    this.psa.setservicetype(reqData).subscribe((response: any) => {
      this.servicetypelist = response;
      console.log("Save response", this.servicetypelist, this.servicetypelist.message);

      if (this.servicetypelist.message == "Service Type details has saved successfully") {

        // swal(this.servicetypelist.message, "", "success");
        this.toast.success(this.servicetypelist.message);
        this.getServiceTypeInfo();
      }

    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed");

        this.ServiceArr = this.servicetypelist.servicetypeList;
        this.getServiceTypeInfo();

      });
    }
    this.id = "",
      this.serviceType = "",
      this.value = false;

  }
  //save close

  updateservicetype(servicetable) {

    this.searchfield = false;

console.log("reee",servicetable)

var count = 0
var str = servicetable.serviceTypeName.replace(/[\. ,:-]+/g, "");
this.table.map(d=>{
  var one=d.serviceTypeName.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.id != this.sid) {
    count = 1
  }
})
if (count == 1) {
  this.toast.error("Duplicates are not allowed")
  this.getServiceTypeInfo();
  this.noedit = false;
  this.value=false;
 this.addb=true;
} else
{
    console.log("servicetable", servicetable);
    var updateRequestData =
    {
      "servicetypeList": [
          {
             
              "id":this.sid,
              "serviceTypeName":servicetable.serviceTypeName,
              "status":this.status
             
          }
      ],
      "transactionType": "update"
  }
    console.log("success", updateRequestData)
    console.log(servicetable,"id")
    this.addb = true;
    this.psa.updateservicetype(updateRequestData).subscribe((res: any) => {
      this.masterList = res;
      console.log(this.masterList);
      if (this.masterList.message == "service details has updated successfully") {
        // swal("service details has updated successfully","", "success");
        this.toast.success("service details has updated successfully")
        this.getServiceTypeInfo();
        this.getServiceTypeInfo;
        this.noedit = false;
      }
    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed");
        console.log(error);
        
        this.getServiceTypeInfo();
        this.noedit = false;
      })
    }
    this.key=""
  }


  edit(id, name,status) {
    console.log("edit", id)
    this.sid = id;
    this.noedit = true;
    this.value = false;
    this.status = status
    this.addb = false;
    this.searchfield = true;
    this.editname=name
  }




  cancelbulist() {
    this.value = false;
    this.addb = true;
    this.getServiceTypeInfo();
  }
  clear() {
    this.serviceTypeName = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key=""
    this.getServiceTypeInfo();
  }
 
  navigateTo(){
    this.psa.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
} 
