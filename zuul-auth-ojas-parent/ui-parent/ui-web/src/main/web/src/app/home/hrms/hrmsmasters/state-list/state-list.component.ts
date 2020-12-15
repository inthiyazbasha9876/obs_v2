import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-state-list',
  templateUrl: './state-list.component.html',
  styleUrls: ['./state-list.component.scss']
})
export class StateListComponent  implements OnInit {


  deleteStateRes: any;
  updateRes: any;
  statelist: any;
  StateList: any;
  StateDetails: any;
  StateRes: any;
  value: boolean;
  private pageSize: number = 5;
  stateId: any;
  editname: any;
  key: string;
  p: number;
  
  constructor(private hrms: HrmsService,private toast:NotificationService) {

  }

  ngOnInit() {
    this.getStateListData();
  }
  getStateListData() {
    var request = {

      "states":
        [],

      "sessionId": "1234",
      "transactionType": "getAll"

    }
    this.hrms.getStateListMaster(request).subscribe(res => {
      this.StateDetails = res;
      this.StateList = this.StateDetails.statesList;
      console.log("ststelistgetAll", this.StateDetails);
    })
  }
  noedit: boolean;
  searchfield=false;
  addb=true;
  edit(id,name) {
    this.searchfield=true;
    this.stateId = id;
    this.noedit = true;
    this.value=false;
    this.addb=false;
    this.editname=name
  }
  cancel() {
this.noedit=false;
this.searchfield=false;
this.addb=true;
this.key=""
this.getStateListData();
  } 

   updateStateData(state) {
    this.searchfield=false;
    console.log("reee",state)
var count = 0
var str = state.stateName.replace(/[\. ,:-]+/g, "");
this.StateList.map(d=>{
  var one=d.stateName.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.id !=  this.stateId) {
    count = 1
  }
})
if (count == 1) {
  this.toast.error("Duplicates are not allowed")

  this.getStateListData();
  this.noedit = false;
  this.value=false;

} else {
    var requestData = {

      "states":
        [{
          "stateName": state.stateName,
          "id": this.stateId
        }],

      "sessionId": "1234",
      "transactionType": "update"

    }
    this.hrms.updateStateListMaster(requestData).subscribe(response => {
      this.StateRes = response;
      this.addb=true;
      console.log("satauslistupdate", this.StateRes);
      if (this.StateRes.message == "Successfully record updated") {
        this.value = false;
        this.noedit = false;
        // swal(this.StateRes.message, "", "success");
        this.toast.success(this.StateRes.message)
        this.getStateListData();
      }
    },
      error => {
        this.addb=true;
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")
        this.getStateListData();
        this.noedit = false;
      })
    }
    this.key=""
  }


  stateName: any;
  cancelstatelist() {

    this.value = false;
  }
  clear() {
    this.statelist = "";
  }

  setState(aa) {
    var count=0;
    var str = aa.statelist.replace(/[\. ,:-]+/g, "");
  
    this.StateList.map(d=>{
      var one=d.stateName.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })
  
    if (count == 1) {
        
      this.toast.error("Duplicates are not allowed")
      
    }
    else{
    var requestData = {

      "states":
        [{
          "stateName": aa.statelist,
        }],
      "transactionType": "save"

    }

    this.hrms.saveStateListMaster(requestData).subscribe((response: any) => {
      this.StateRes = response;
      console.log("ststeListsave", this.StateRes);
      if (this.StateRes.message == "Successfully record added") {
        this.value = false;
        // swal("Record Added", "", "success");
        this.toast.success("Record Added");
        this.getStateListData();
      }
    },
      error => {
        // swal("Record is Already Exists", "", "error");
        this.toast.error("Record is Already Exists")

        this.statelist = ""
        this.getStateListData();
      })

    this.statelist = ""
    this.value = false
    }

  }




  deleteSate(state) {
    var deleteRequest = {
      "states": {
        "id": state.id,
        "stateName": state.stateName
      },
      "transactionType": "delete",
      "sessionId": "132"
    }
    this.hrms.deleteStateList(deleteRequest).subscribe(data => {
      this.deleteStateRes = data;
      if (this.deleteStateRes.statusMessage == "Successfully record deleted") {
        // swal(this.deleteStateRes.statusMessage, "", "success");
        this.toast.success(this.deleteStateRes.statusMessage)
        // this.getStatus();
      }
    })

  }


  navigateTo(){
    this.hrms.navigateToDashboard();
  }

  searchPage(){
    this.p=1;
    }
  }
