import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { PsaService } from 'src/app/home/services/psa.service';

@Component({
  selector: 'app-actionowner',
  templateUrl: './actionowner.component.html',
  styleUrls: ['./actionowner.component.scss']
})
export class ActionownerComponent implements OnInit {
  value: boolean;
  table: any;
  id: any;
  status: any;
  addb = true;
  masterList: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize: number = 5;
  sid: any;

  ActionOwner: any;

  actionOwnerList: any;
  actionOwnerlist: any;
  setActionOwner: any;


  textfield: any
  actionowner: any;
  editname: any;
  key: string;
  constructor(private psa: PsaService,private toast:NotificationService) { }
  

  ngOnInit() {
    this.getActionOwner()

  }
  //get all//
  getActionOwner() {

    var Requestdata = {

      actionOwnerList: [{



      }],
      "transactionType": "getall"
    }
    console.log("success", Requestdata)
    let td;
    this.psa.getactionOwner(Requestdata).subscribe(responce => {
      this.actionOwnerlist = responce;

      console.log("Get response", this.actionOwnerlist)
      console.log("Get response", this.actionOwnerlist.actionOwnerList)
      td = this.actionOwnerlist.actionOwnerList;
      this.table = td;
      console.log('Get action', td);
    })
    
  }


  //save//

  setActionowner(a) {
    var count=0;
    var str = a.addactionowner.replace(/[\. ,:-]+/g, "");
  
    this.table.map(d=>{
      var one=d.actionowner.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })
  
    if (count == 1) {
        
      this.toast.error("Duplicates are not allowed")
      
    }
    else{
    console.log("save", a.addactionowner);

    var reqData =
    {
      "actionOwnerList": [{
        "actionowner": a.addactionowner,
        "status": "true"


      }],
      "transactionType": "save"
    }
    console.log("success", reqData);

    this.psa.setActionOwner(reqData).subscribe((response: any) => {
      this.actionOwnerlist = response;
      console.log("Save response", this.actionOwnerlist, this.actionOwnerlist.message);

      if (this.actionOwnerlist.message == "action owner details saved successfully") {
        // swal("Action Owner saved successfully", "", "success");
        this.toast.success("Action owner details has saved successfully")
        this.getActionOwner();
        this.value = false;
      }

    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")
        this.value = false;
        this.actionOwnerlist = this.actionOwnerlist.actionOwnerlist;
        this.getActionOwner();

      });
    }
  }

  updateactionowner(upactionowner) {
    
    console.log('Table', this.table);
    var count = 0
    
    this.table.map(d=>{
      var one=d.actionowner.replace(/[\. ,:-]+/g, "");

      var str = upactionowner.actionowner.replace(/[\. ,:-]+/g, "");
      // console.log('STR', str);
      // console.log('ONE', one);
      
      if (one.toLowerCase() == str.toLowerCase() && d.actionownerId != this.sid) {
        // console.log(one, 'Matched with ', str);
        
        count = 1
       
      }
    })
    if (count == 1) {

      this.getActionOwner()
      console.log("getlist",this.table)
      this.noedit = false;
      this.value=false;
      this.addb=true;
      this.toast.error("Duplicates are not allowed")
    
    } else {
    // this.searchfield=false;
  
    console.log("updating value is", upactionowner.actionowner);
    var updateRequestData =
    {
      "actionOwnerList": [
        {
          "actionownerId": this.sid,
          "actionowner": upactionowner.actionowner,
          "status": "true"


        }],
      "transactionType": "update"
    }
    console.log("request sent", updateRequestData)
    this.addb = true;
    this.psa.updateactionowner(updateRequestData).subscribe(res => {
      this.masterList = res;
      console.log(this.masterList);
      this.getActionOwner();
      console.log("success update")
      // swal(this.masterList.message,"", 'success')
      this.toast.success("Action owner details has updated successfully")
      this.noedit = false;
    },
      error => {
        console.log("error", error)
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")
        this.getActionOwner();
        this.noedit = false;
      })
    }
    //  this. getActionOwner();
     this.searchfield=false;
    this.key="";
  }

  edit(id,name, status) {
    console.log("edit", this.table)
    this.sid = id;
    this.noedit = true;
    this.value = false;
    this.status=status
    this.addb = false;
    this.searchfield = true;
    this.editname=name
  }
  cancelbulist() {
    this.value = false;
    this.addb = true;
    this.getActionOwner();
  }
  clear() {
    this.actionowner = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key="";
    this.getActionOwner();
  }
  
  navigateTo(){
    this.psa.navigateToDashboard();
  }
  p: number;
  searchPage(){
    this.p=1;
    }


}
