import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-sbubusinessunit',
  templateUrl: './sbubusinessunit.component.html',
  styleUrls: ['./sbubusinessunit.component.scss']
})
export class SbubusinessunitComponent  implements OnInit {
  updateSubbusinessDetails: any;
  costCenterId: any;
  businessUnitId: any;
  coscentergetlist: any;
  costCenterList: any;
  businessUnitList: any = [];
  businessunitDetails: any;
  subBusinessUnitlist: any;
  subbusinessunitDetails: any;
  subbusinessunitRes: any;
  subbusinessunit: any;
  businessunit: any;
  costcenter: any;
  requestData: { "businessUnit": any; "costCenter": any; "subbusinessUnit": any; "sbuHead": any; };
  value: boolean;
  getSubbusinessinfo: any;
  id: any;
  private pageSize: number = 5;
  isEditable: boolean = false;
  reverse: boolean = false;
  selectedBusinessunit: any;
  savesbu: any;
  businessUnitName: any;
  getbusinessname: any;
  sid: any;
  sbuHead: string;
  empList: any;
  empId: {}[];
  sbuids: {}[];
  empbasic: any;
  editname: any;
  key: string;
  name: any;
  sbuHeads:[];
  sbuResp:any;

  constructor(private hrms: HrmsService, private toast: NotificationService) {
    this.getBusinessunit();
    this.getSubBusinessUnit();
    this.getempdata();

  }

  ngOnInit() {

  }
  cancelbulist() {
    this.value = false;

  }
  clear() {

    this.subbusinessunit = "";
    this.businessUnitName = "";
    this.costCenterId = "";
    this.sbuHead = "";
  }

  savebu: any;
list:any
  setSubbusinessunit(sbu) {
    
    this.list=sbu
    var count=0;
    var str = this.list.subbusinessunit.replace(/[\. ,:-]+/g, "");
    this.subBusinessUnitlist.map(d=>{
      var one=d.name.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })
    if (count == 1) {
        
      this.toast.error("Duplicates are not allowed")
      
    }
    else{
      var requestData = {
        "subBusinessUnitModel": [{
          "businessUnitId": this.businessUnitId,
          "name": this.subbusinessunit,
          "sbuHead": sbu.firstname
        }],

        "transactionType": "save"
      }
      this.hrms.setSubbusinessunit(requestData).subscribe((response: any) => {
        this.subbusinessunitRes = response;
        console.log(this.subbusinessunitRes);

        if (this.subbusinessunitRes.message == "Successfully record added") {
          // swal(this.subbusinessunitRes.message, "", "success");
          this.toast.success(this.subbusinessunitRes.message);
          this.getSubBusinessUnit();
        }
      },
        error => {
          // swal("Please enter valid data", "", "error");
          this.toast.error("Please enter valid data")
          this.getSubBusinessUnit();

        })
    }
    this.value = false;
    this.costCenterId = "",
      this.businessUnitId = "",
      this.subbusinessunit = "",
      this.sbuHead = ""
  }

  getSubBusinessUnit() {
    var request = {
      "subBusinessUnitModel": [
        {
        }
      ],
      "transactionType": "getAll"
    }
    this.getSBUHeads();
    this.hrms.getSubbusinessUnit(request).subscribe(res => {
      this.subbusinessunitDetails = res;
      this.subBusinessUnitlist = this.subbusinessunitDetails.subBusinessUnitList;
      console.log("subulistttttttt", this.subBusinessUnitlist);

      // for (let i = 0; i <= this.subBusinessUnitlist.length; i++) {


      //   let bu = this.businessUnitList.find(bul => bul.id == this.subBusinessUnitlist[i].businessUnitId)
      //   this.subBusinessUnitlist[i].businessUnitId = bu.businessUnitName;


      // }


    })
  }

  getBusinessunit() {
    var request = {
      "businessUnit": [{

      }],
      "transactionType": "getAll"
    }

    this.hrms.getBusinesinfo(request).subscribe(res => {
      this.businessunitDetails = res;
      this.businessUnitList = this.businessunitDetails.businessUnitList;
      console.log("businessUnitListiiiiiiiiiiiiii", this.businessUnitList);
    })
  }

  getCostCenter() {
    var request = {
      "costCenter": [{
      }],
      "sessionId": "123",

      "transactionType": "get"
    }
    this.hrms.getCostcenter(request).subscribe(res => {
      this.costCenterList = res;
      this.coscentergetlist = this.costCenterList.listOfCostCenter;
      console.log(this.costCenterList);
    })
  }
  noedit: boolean;
  addb = true;
  searchfield = false;
  edit(i, name) {
    this.sid = i;
    this.noedit = true;
    this.value = false;
    this.searchfield = true;
    this.addb = false;
    this.editname = name

  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key = ""
    this.getCostCenter();
    this.getBusinessunit();
    this.getSubBusinessUnit();
  }
 saveUpdatedValues(bulist) {
    this.searchfield = false;
    var count = 0
    var str = bulist.name.replace(/[\. ,:-]+/g, "");
    this.subBusinessUnitlist.map(d=>{
      var one=d.name.replace(/[\. ,:-]+/g, "");
      if (one.toLowerCase() == str.toLowerCase() && d.id != this.sid) {
        count = 1
      }
    })
    
    if (count == 1) {
      this.toast.error("Duplicates are not allowed")
    
      this.getSubBusinessUnit();
      this.noedit = false;
      this.value=false;
      this.addb=true;
    }
else    {
    var request = {
      "subBusinessUnitModel": [{

        "id": this.sid,
        "name": bulist.name,
        "businessUnitId": bulist.businessUnitId,
        "sbuHead": bulist.firstname
      }],
      "sessionId": "123",
      "transactionType": "update"

    }

    console.log("req of sbu", request);
    this.addb = true;
    this.hrms.updateSubbusinessUnit(request).subscribe((res: any) => {
      this.updateSubbusinessDetails = res;
      console.log(this.updateSubbusinessDetails);
      if (this.updateSubbusinessDetails.message == "Successfully record updated") {
        this.noedit = false;

        // swal(this.updateSubbusinessDetails.message, "", "success");
        this.toast.success(this.updateSubbusinessDetails.message);
        this.searchfield = false;

        this.getSubBusinessUnit();

      }
    },
      error => {
        this.noedit = false;
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed");
        this.getSubBusinessUnit();
      })
    }
      this.key = ""
  }

  onSelectCostId(event: any) {
    this.selectedBusinessunit = event;
    console.log(this.selectedBusinessunit);

  }
  employeeInfo: Object;
  sbuHeadData = [];
  getempdata() {
    var empinfo =
    {
      "employeeInfo": [{

      }],
      "transactionType": "getAllInfo"
    }

    this.hrms.getempinfo(empinfo).subscribe(res => {
      this.empbasic = res;
      this.empList = this.empbasic.employeeInfo
       console.log("empdata", this.empList);
      this.empId = Array.from(new Set(this.empList.map(x => x.employeeId)));
     /* console.log("empIds", this.empId)
      this.sbuids = Array.from(new Set(this.subBusinessUnitlist.map(x => x.sbuHead)));
      console.log("sbuHeadidslist", this.sbuids); */

      for (let n = 0; n <= this.empList.length; n++) {
        for (let m = 0; m < this.sbuHeads.length; m++) {
          if (this.empId[n] == this.sbuHeads[m]) {
            this.sbuHeadData.push(this.empList[n]);

          }
        }
      }
      console.log("empbaisdata", this.sbuHeadData);
    })
  }
  navigateTo(){
    this.hrms.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }


    getSBUHeads() {
      let req = {
        "transactionType":"getHeads"
      }
      this.hrms.getSbuHeads(req).subscribe(res => {
        this.sbuResp = res;
        console.log('SBU Heads: ', this.sbuResp);
        
        this.sbuHeads = this.sbuResp.sbuHeads;
      })
    }
  }
