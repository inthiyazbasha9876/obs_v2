import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-billingtype',
  templateUrl: './billingtype.component.html',
  styleUrls: ['./billingtype.component.scss']
})
export class BillingtypeComponent implements OnInit {
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
  billingTypearr: any;
  billingTypelist: any;
  billingname: string;
  name: any;
  billingType: any;
  editname: any;
  key: string;


  constructor(private psa: PsaService, private toast: NotificationService) { }

  ngOnInit() {
    this.getbillingtype();
  }
  //getall
  getbillingtype() {


    var Requestdata = {
      "billingList": [{

      }],
      "transactionType": "getall"
    }

    console.log("success", Requestdata)
    this.psa.getbillingtype(Requestdata).subscribe(responce => {
      this.billingTypelist = responce;

      console.log("Get response", this.billingTypelist)
      console.log("Get response", this.billingTypelist.billingList)
      // for(let i in this.billingTypelist.billingList){


      //   if(this.billingTypelist.billingList[i].status==true){
      //     this.billingTypelist.billingList[i].status="Active"
      //   }
      //   }
      this.table = this.billingTypelist.billingList;
    })

  }
  //get all close 

  //save
  setbillingtype() {
    var count = 0;
    var str = this.name.replace(/[\. ,:-]+/g, "");

    this.table.map(d => {
      var one = d.name.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })

    if (count == 1) {

      this.toast.error("Duplicates are not allowed")

    }
    else {
      var reqData =
      {
        "billingList": [{
          // "id":this.id,
          "name": this.name,
          "status": true

        }],
        "transactionType": "save"

      }
      console.log("success", reqData);

      this.psa.setbillingtype(reqData).subscribe((response: any) => {
        this.billingTypelist = response;
        console.log("Save response", this.billingTypelist, this.billingTypelist.message);
        console.log("saveresult", this.name);

        if (this.billingTypelist.message == "record saved successfully") {

          // swal("record saved successfully", "","success");
          this.toast.success("record saved successfully");
          this.getbillingtype();
        }
        this.billingType = this.billingTypelist.billingList;


      },
        error => {
          // swal("Duplicates are not allowed","","error");
          this.toast.error("Duplicates are not allowed");
          console.log(error);

          this.billingTypearr = this.billingTypelist.deliverylocationList;
          this.getbillingtype();

        });
    }
    this.id = "",
      this.name = "",
      this.value = false;

  }
  //save close

  updatebillingtype(billingname) {
    console.log("reee", this.table)

    var count = 0
    var str = billingname.name.replace(/[\. ,:-]+/g, "");
    this.table.map(d => {
      var one = d.name.replace(/[\. ,:-]+/g, "");
      console.log('STR', str);
      console.log('ONE', one);
      if (one.toLowerCase() == str.toLowerCase() && d.id != this.sid) {
        console.log(one, 'Matched with ', str);
        count = 1
      }
    })

    if (count == 1) {
      this.toast.error("Duplicates are not allowed")

      this.getbillingtype();
      this.noedit = false;
      this.value = false;
      this.addb = true;

    } else {

      console.log("updating value is", billingname);
      var updateRequestData =
      {
        "billingList": [{
          "id": this.sid,
          "name": billingname.name,
          "status": this.status

        }],
        "transactionType": "update"

      }
      console.log("request sent", updateRequestData)
      this.addb = true;
      this.psa.updatebillingtype(updateRequestData).subscribe((res: any) => {
        this.masterList = res;
        console.log(this.masterList, res);
        if (this.masterList.message == "updated successfully") {
          this.noedit = false;
          this.value = false;
          // swal("Billing Type has updated successfully", "","success");
          this.toast.success("Billing Type has updated successfully");
          this.getbillingtype();

        }
        console.log("success update")

      },
        error => {
          console.log("error")
          // swal("Duplicates are not allowed","","error");
          this.toast.error("Duplicates are not allowed");
          this.getbillingtype();
          this.noedit = false;
          this.value = false;
        })
    }
    this.key="";
    this.searchfield=false;
  }


  edit(id,name, status) {
    console.log("edit", id, this.table)
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
    this.getbillingtype();
  }
  clear() {
    this.name = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.getbillingtype();
    this.key="";
  }
 
  navigateTo(){
    this.psa.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
}