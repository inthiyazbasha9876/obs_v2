import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';


@Component({
  selector: 'app-businessunit',
  templateUrl: './businessunit.component.html',
  styleUrls: ['./businessunit.component.scss']
})
export class BusinessunitComponent  implements OnInit {
  BudeleteDetails: any;
  buupdateDetails: any;
  id: any;
  value: boolean;
  coscentergetlist: any;
  costCenterList: any;
  costCenterId: any;
  businessUnitName: any;
  businessUnitList: any;
  businessUnitlist: any;
  businessunitDetails: any;
  businessunitRes: any;
  addbb = true;
  private pageSize: number = 5;
  buId: any;
  buHead: any;
  empbasic: any;
  empbasicin: any;
  empList: any;
  empId: {}[];
  buids: {}[];
  encryptedRole: string;
  key: string;
  editname: any;
  buHeadResp: any;

  constructor(private hrms: HrmsService, private authService: AuthService, private toast: NotificationService) {
    this.getCostCenter();
  }

  role;
  hide: boolean;
  ngOnInit() {
    this.getBusinessunit();
    this.getempdata();

    // this.role = sessionStorage.getItem("Role");

    this.encryptedRole = sessionStorage.getItem('Role');
    this.role = this.authService.decryptData(this.encryptedRole);
    if (this.role == "ROLE_ADMIN") {
      this.hide = true;
    }
  }


  cancelbulist() {
    this.value = false;

  }
  clear() {
    this.businessUnitName = "";
    this.costCenterId = "";
    this.buHead = "";
  }

  setBusinessunit(bu) {
    var count = 0
    var str = bu.businessUnitName.replace(/[\. ,:-]+/g, "");
    this.businessUnitList.map(d => {
      var one = d.businessUnitName.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })
    if (count == 1) {
      this.toast.error("Record Already Exists")
    } else {
      var requestData = {
        "businessUnit": [{

          "businessUnitName": this.businessUnitName,
          "costCenterId": this.costCenterId,
          "buHead": bu.firstname
        }],


        "transactionType": "save"
      }


      this.hrms.setBusinessunit(requestData).subscribe((response: any) => {
        this.businessunitRes = response;
        console.log(this.businessunitRes);
          this.toast.success(this.businessunitRes.message)
          // swal(this.businessunitRes.message, "", "success");
          this.getBusinessunit();
      },
        error => {
          this.toast.error("Record Already Exists")
          // swal("Record Already Exists", "", "error");
          this.getBusinessunit();
        })
    }

    this.businessUnitName = ""
    this.costCenterId = ""
    this.buHead = ""
    this.value = false;
  }

  getBusinessunit() {
    var request = {
      "businessUnit": [{

      }],
      "transactionType": "getAll"
    }
this.getBUHead();
    this.hrms.getBusinesinfo(request).subscribe(res => {
      this.businessunitDetails = res;
      this.businessUnitList = this.businessunitDetails.businessUnitList;
      console.log("BU Details : ", this.businessUnitList);

      // console.log("Cost center list : ", this.coscentergetlist);
      // for (let i in this.businessUnitList) {

      //   let cost = this.coscentergetlist.find(cst => cst.id == this.businessUnitList[i].costCenterId);
      //   console.log("costCenterId",cost);
      //   this.businessUnitList[i].costCenterId = cost.costCenterCode;
      // }
    })
  }

  getBUHead() {
    let resp;
    var data = {
      "businessUnit": [{
 
      }
      ],
      "transactionType": "getallbuid"
    }
    
    this.hrms.getBusinesinfo(data).subscribe(res => {
      resp=res;
      
      this.buHeadResp=resp.buHeads;
      console.log("bu Heads data",this.buHeadResp);
    })
  }

  getCostCenter() {
    var request = {


      "costCenter": [{


      }],

      "sessionId": "124",


      "transactionType": "get"

    }

    this.hrms.getCostcenter(request).subscribe(res => {
      this.costCenterList = res;
      this.coscentergetlist = this.costCenterList.listOfCostCenter;
      console.log(this.costCenterList);
    })
  }
  noedit: boolean;
  searchfield = false;
  edit(d) {
    this.buId = d.id;
    this.noedit = true;
    this.value = false;
    this.addbb = false;
    this.searchfield = true;
    this.editname=d.businessUnitName
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addbb = true;
    this.getBusinessunit();
  }

  saveUpdatedBuData(bulist) {
    this.searchfield = false;
    console.log("reee",bulist)
var count = 0
var str = bulist.businessUnitName.replace(/[\. ,:-]+/g, "");
this.businessUnitList.map(d=>{
  var one=d.businessUnitName.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.id != this.buId) {
    count = 1
  }
})
if (count == 1) {
  this.toast.error("Business unit name already exists")

  // this.getBusinessunit();
  // this.noedit = false;
  // this.value=false;

} else {
    var burequest = {
      "businessUnit": [{
        "id": this.buId,
        "businessUnitName": bulist.businessUnitName,
        "costCenterId": bulist.costCenterId,
        "buHead": bulist.firstname
      }],
      "transactionType": "update",
      "sessionId": "132"
    }
    console.log("BU update Request : ", burequest);
    this.hrms.updateBusinessunit(burequest).subscribe((res: any) => {
      this.buupdateDetails = res;
      console.log(this.buupdateDetails);
      if (this.buupdateDetails.message == "Successfully record updated") {
        // swal(this.buupdateDetails.message, "", "success");
        this.toast.success(this.buupdateDetails.message)
        
        this.getBusinessunit();
        this.addbb = true;
        this.noedit = false;
      }

    },
      error => {
        // this.noedit = false;
        // swal("Record Already Exists", "", "error");
        this.toast.error("Record Already Exists")
        // this.getBusinessunit();
      })
    }
this.key=""
  }


  deleteBuData(bulist) {
    var budeletereq = {
      "businessUnit": {
        "id": bulist.id,
        "businessUnitName": bulist.businessUnitName,
        "costCenterId": bulist.costCenterId,
        "buHead": bulist.buHead

      },
      "transactionType": "delete",
      "sessionId": "132"
    }
    this.hrms.deleteBusiness(budeletereq).subscribe(res => {
      this.BudeleteDetails = res;
      console.log(this.BudeleteDetails);
      if (this.BudeleteDetails.statusMessage == "Success fully record deleted") {

        this.getBusinessunit();
      }
    })
  }
  employeeInfo: Object;
  buHeadData = [];
  getempdata() {

    var empinfo =
    {
      "employeeInfo": [{

      }],
      "transactionType": "getall"
    }

    this.hrms.getempinfo(empinfo).subscribe(res => {
      this.empbasic = res;
      this.empList = this.empbasic.employeeInfo
      console.log("empdata", this.empList[0]);
      this.empId = Array.from(new Set(this.empList.map(x => x.employeeId)));
      console.log("empIds", this.empId)
      //this.buids = Array.from(new Set(this.businessUnitList.map(x => x.buHead)));
   

      for (let n = 0; n <= this.empList.length; n++) {
        for (let m = 0; m <this.buHeadResp.length; m++) {
          if (this.empId[n]== this.buHeadResp[m]) {
            this.buHeadData.push(this.empList[n]);

          }
        }
      }
      // this.buHeadData=this.empList.filter(dta==a);
      console.log("empbasicdata", this.buHeadData);
    })
  }



  navigateTo(){
    this.hrms.navigateToDashboard();
  }
  
  p: number;
  searchPage(){
    this.p=1;
    }
  
}
