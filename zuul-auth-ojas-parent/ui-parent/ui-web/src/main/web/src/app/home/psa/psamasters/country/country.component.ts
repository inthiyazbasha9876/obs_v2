import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.scss']
})
export class CountryComponent implements OnInit {

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

  // projecttype: any;

  countrytypearr: any;
  country: string;
  countrylist: any;
  countrytypeTable: any;
  countrytype: any
  key: string;
  tablecopy: any;
  editname: any;
  p: number;

  constructor(private psa: PsaService,private toast:NotificationService) { }
 
  ngOnInit() {
    this.GetCountry();
  }
  //getall
  GetCountry() {
    console.log("getproject called");


    var Requestdata = {
      "countrylist": [{

      }],
      "transactionType": "getall"
    }
    console.log("success", Requestdata)
    this.psa.GetCountry(Requestdata).subscribe(responce => {
      this.countrytype = responce;

      console.log("Get response", this.countrytype)
      console.log("Get response", this.countrytype.body.countrylist)
      this.table = this.countrytype.body.countrylist;
      this.tablecopy=this.countrytype.body.countrylist;
    })

  }
  //get all close

  //save
  SetCountry() {

    var count=0;
    var str = this.country.replace(/[\. ,:-]+/g, "");
  
    this.table.map(d=>{
      var one=d.countryname.replace(/[\. ,:-]+/g, "")
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
      "countrylist": [{
        "countryname": this.country,
        "status": true
      }],
      "transactionType": "save"
    }






    console.log("reqdata", reqData);

    this.psa.SetCountry(reqData).subscribe((response: any) => {
      this.countrytype = response;
      console.log("Save response", this.countrytype);

      if (this.countrytype.message == "record saved successfully") {

        // swal("country has saved successfully", "", "success");
        this.toast.success("country has saved successfully");
        this.GetCountry();
      }

    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed");

        if (this.countrytype.message == "record saved successfully")
          this.countrytypearr = this.countrytype.countrylist;
        this.GetCountry();

      });
    }
    this.id = "sid",
      this.country = "",
      this.value = false;

  }
  //save close

  UpdateCountry(countrytypeTable) {
    this.key="";
    this.searchfield = false;

    console.log("reee",countrytypeTable)
    
    var count = 0
    var str = countrytypeTable.country.replace(/[\. ,:-]+/g, "");
    this.table.map(d=>{
      var one=d.countryname.replace(/[\. ,:-]+/g, "");
      if (one.toLowerCase() == str.toLowerCase() && d.id != this.sid) {
        count = 1
      }
    })
    if (count == 1) {
      this.toast.error("Duplicates are not allowed")
    
      this.GetCountry();
      this.noedit = false;
      this.value=false;
      this.addb=true;
    } else
{
    console.log("ratetypeTable", countrytypeTable);
    var updateRequestData =
    {
      "countrylist": [{
        "id": this.sid,
        "countryname": countrytypeTable.country,
        "status": true
      }],
      "transactionType": "update"
    }

    console.log("success", updateRequestData)
    this.addb = true;
    this.psa.UpdateCountry(updateRequestData).subscribe((res: any) => {
      this.masterList = res;
      console.log(this.masterList);
      if (this.masterList.message == "updated successfully") {
        // swal("country has updated successfully", "", "success");
        this.toast.success("Country has updated successfully");
        this.GetCountry();
        this.noedit = false;
      }
      this.GetCountry();
    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed");
        this.GetCountry();
        this.noedit = false;
      })
    }
this.editname="";
  }


  edit(id,name, status) {
    console.log("edit", id,name)
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
    this.GetCountry();
  }
  clear() {
    this.country = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.GetCountry();
    this.key=""
    this.editname=""
  }
 
  navigateTo(){
    this.psa.navigateToDashboard();
  }
  searchPage(){
    this.p=1;
    }
  }
