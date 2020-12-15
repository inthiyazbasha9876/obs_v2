import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-passport-centers',
  templateUrl: './passport-centers.component.html',
  styleUrls: ['./passport-centers.component.scss']
})
export class PassportCentersComponent implements OnInit {
  centerName: any;
  deletepassportRes: any;
  updatedpassportRes: any;
  passportCenterList: any;
  passportCenterDetails: any;
  passportCenterReq: any;
  value: boolean;
  private pageSize: number = 5;
  pid: any;
  key: string;
  editname: any;
  //   public data1=[];
  //   public show:boolean = false;
  //   public buttonName:any = 'Show';


  //   //new code starts here

  //   data; 
  //   pass:any;
  //   passPort;
  //   isEditable:boolean = false;
  //   reverse: boolean = false;

  //   order: string = 'info.name';



  constructor(private hrms: HrmsService, private toast: NotificationService) {
    // Ng2SearchPipeModule: Ng2SearchPipeModule,
  }
  //    passportlist:Passport[]=[{

  //     "passPort": "Thirupathi"
  //    },
  //  {
  //    "passPort": "Rajahmundry"
  //  },
  //  {
  //    "passPort": "Vishakapatnam"
  //  },
  //  {
  //    "passPort": "Hyderabad"
  //  },
  //  {
  //    "passPort": "Amaravathi"
  //  },
  // ]




  //  saveBu(){
  //   debugger;
  //   this.value = false;
  //   this.data = {
  //     "passPort":this.pass,

  //   }
  //   this.passportlist.unshift(this.data);

  //   this.passPort='';


  // }
  // editData(blist){
  //   console.log(blist);
  //   //this.listDetails = blist;
  //   this.pass = blist.passPort;

  // }
  // saveData(){
  //   var editD = {
  //     "passPort":this.pass,

  //   }
  //   console.log(editD);
  // }
  // deleterow(index){
  //   debugger;
  //   if(index !== -1){
  //     this.passportlist.splice(0,1);
  //   }
  //   else {
  //   this.passportlist.splice(index,1);
  //   }
  // }

  // cancelbulist(){
  //   this.value=false;

  // }
  ngOnInit() {
    this.getPassportCenter();
  }
  //   handleClick(event: Event) {
  //     console.log('Button clicked', event)
  //   }
  //   toggle() {
  //     this.show = !this.show;

  //     // CHANGE THE NAME OF THE BUTTON.
  //     if(this.show)  
  //       this.buttonName = "Hide";
  //     else
  //       this.buttonName = "Show";
  //   }

  //   setOrder(value: string) {
  //     if (this.order === value) {
  //       this.reverse = !this.reverse;
  //     }

  //     this.order = value;

  // }
  //  if(this.empDesignationRes.statusMessage == "Successfully record added"){
  //       this.value = false;
  //       swal(this.empDesignationRes.statusMessage , "","success");
  //       this.getEmpDesignation();
  //     }

  passportrequset: any;
  setPassportCenter() {

    var count = 0;
    var str = this.centerName.replace(/[\. ,:-]+/g, "");

    this.passportCenterList.map(d => {
      var one = d.centerName.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })

    if (count == 1) {

      this.toast.error("Duplicates are not allowed")

    }
    else {



      var requestData =
      {
        "passportList": [{

          "centerName": this.centerName

        }
        ],

        "sessionId": "323",
        "transaactionType": "save"
      }
      this.hrms.setPassportCeneter(requestData).subscribe(response => {
        this.passportCenterReq = response;
        this.passportrequset = this.passportCenterReq;

        console.log(this.passportCenterReq);

        if (this.passportrequset.message == "PassportCenter has been saved successfully") {
          // swal(this.passportrequset.message, "", "success");
          this.toast.success(this.passportrequset.message)

          this.getPassportCenter();
        }
      },

        error => {
          // swal("Duplicates are not allowed", "", "error");
          this.toast.error("Duplicates are not allowed")

          this.getPassportCenter();
        })
    }
    this.centerName = "",
      this.value = false;
  }

  getPassportCenter() {
    var request = {
      "passportList": [
        {
          "centerName": "Hyd",
          "id": 8
        }
      ],

      "sessionId": "323",
      "transaactionType": "getall"
    }
    this.hrms.getPassportCeneter(request).subscribe(res => {
      this.passportCenterDetails = res;
      this.passportCenterList = this.passportCenterDetails.passportList;
      console.log("list of centers", this.passportCenterList);
    })
  }

  updatedpassportResData: any;
  noedit: boolean;
  searchfield = false;
  addb = true;
  edit(id,name) {
    this.pid = id;
    this.noedit = true;
    this.value = false;
    this.addb = false;
    this.searchfield = true;
    this.editname=name
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key=""
    this.getPassportCenter();
  }

  saveUpdatedValues(bulist) {
    this.searchfield = false;
    console.log(bulist);
    var count = 0
    var str = bulist.centerName.replace(/[\. ,:-]+/g, "");
    this.passportCenterList.map(d => {
      var one = d.centerName.replace(/[\. ,:-]+/g, "");
      if (one.toLowerCase() == str.toLowerCase() && d.id != this.pid) {
        count = 1
      }
    })
    if (count == 1) {
      this.toast.error("Duplicates are not allowed")
      this.getPassportCenter();
      this.noedit = false;
    } else {


      var updateRequestData =
      {
        "passportList": [{

          "centerName": bulist.centerName,
          "id": this.pid
        }
        ],

        "sessionId": "323",
        "transaactionType": "update"
      }
      this.addb = true;
      this.hrms.updatePassportCenter(updateRequestData).subscribe((res: any) => {
        this.updatedpassportRes = res;
        this.updatedpassportResData = this.updatedpassportRes;
        console.log(this.updatedpassportRes);
        if (this.updatedpassportResData.message == "PassportCenter has been updated successfully") {
          this.noedit = false;
          // swal(this.updatedpassportResData.message, "", "success");
          this.toast.success(this.updatedpassportResData.message)
          this.getPassportCenter();
        }
      },
        error => {
          this.noedit = false;
          // swal("Duplicates are not allowed", "", "error");
          this.toast.error("Duplicates are not allowed")
          this.getPassportCenter();
        })
    }
    this.value = false
    this.key=""
  }
  deletePassport(bulist) {
    var deleteRequest = {
      "passport": {
        "id": bulist.id,
        "centerName": bulist.centerName
      },
      "transactionType": "delete",
      "sessionId": "any String"
    }
    this.hrms.deletepassportCenter(deleteRequest).subscribe(data => {
      this.deletepassportRes = data;
      console.log(this.deletepassportRes);
      if (this.deletepassportRes.statusMessage === "PassportCenter has  deleted successfully") {
        // swal(this.deletepassportRes.statusMessage, "", "success");
        this.toast.success(this.deletepassportRes.statusMessage)
        this.getPassportCenter();
      }
    })
  }
  cancelbulist() {
    this.value = false;

  }
  clear() {
    this.centerName = "";
  }


  navigateTo(){
    this.hrms.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
  }
