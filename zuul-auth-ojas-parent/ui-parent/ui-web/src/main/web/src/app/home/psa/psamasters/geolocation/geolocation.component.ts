import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-geolocation',
  templateUrl: './geolocation.component.html',
  styleUrls: ['./geolocation.component.scss']
})
export class GeolocationComponent implements OnInit {

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

  projecttype: any;
  locationTypeList: string;
  locationtypearr: any;
  location: any;
  locationtype: any;
  locationtypeTable: any
  geo: string;
  key: string;
  editname: any;


  constructor(private psa: PsaService, private toast: NotificationService) { }

  ngOnInit() {
    this.GetGeo();
  }

  //getall
  GetGeo() {
    console.log("getLocation ");


    var Requestdata = {
      "geoList": [
        {

        }
      ],
      "transactionType": "getall"
    }
    console.log("success", Requestdata)
    this.psa.GetGeo(Requestdata).subscribe(responce => {
      this.locationtype = responce;

      console.log("Get response", this.locationtype)
      console.log("Get response", this.locationtype.geoList)
      this.table = this.locationtype.geoList;

      for (let i in this.table) {
        if (this.table[i].status == true) {
          this.table[i].status = "Active";
        } else if (this.table[i].status == false) {
          this.table[i].status = "InActive";
        }
      }
    })

  }
  //get all close

  //save
  SetGeo() {

    var count = 0;
    var str = this.geo.replace(/[\. ,:-]+/g, "");

    this.table.map(d => {
      var one = d.geoname.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })

    if (count == 1) {

      this.toast.error("invalid")

    }
    else {
      var reqData =
      {
        "geoList": [{
          "geoname": this.geo,
          "status": true



        }],
        "transactionType": "save"
      }





      console.log("reqdata", reqData);

      this.psa.SetGeo(reqData).subscribe(response => {
        this.locationtype = response;
        console.log("Save response", this.locationtype);

        if (this.locationtype.message == "Geo type details saved successfully") {

          // swal("Geo Location has saved successfully", "", "success");
          this.toast.success("Geo Location has saved successfully");
          this.GetGeo();

        }

      },
        error => {
          // swal("Duplicates are not allowed", "", "error");
          this.toast.error("Duplicates are not allowed");



        });
    }
    this.id = "",
      this.location = "",
      this.value = false;

  }
  //save close

  UpdateGeo(locationtypeTable) {


    this.searchfield = false;

    console.log("reee", locationtypeTable)

    var count = 0
    var str = locationtypeTable.geo.replace(/[\. ,:-]+/g, "");
    this.table.map(d => {
      var one = d.geoname.replace(/[\. ,:-]+/g, "");
      if (one.toLowerCase() == str.toLowerCase() && d.geoId != this.sid) {
        count = 1
      }
    })
    if (count == 1) {
      this.toast.error("Duplicates are not allowed")

      this.GetGeo();
      this.noedit = false;
      this.value = false;
      this.addb = true;
    } else {
      console.log("ratetypeTable", locationtypeTable);
      var updateRequestData =



      {
        "geoList": [{
          "geoId": this.sid,
          "geoname": locationtypeTable.geo,
          "status": true



        }],
        "transactionType": "update"
      }
      //   {
      //     "geoList":[{
      //     "geoId":this.sid,
      //     "geoname":locationtypeTable.geo,
      //     "status":this.status



      //     }],    
      //     "transactionType":"update"
      // }


      console.log("success", updateRequestData)
      this.addb = true;
      this.psa.UpdateGeo(updateRequestData).subscribe((res: any) => {
        this.masterList = res;
        console.log(this.masterList);
        if (this.masterList.message == "GeoType details updated successfully") {
          // swal("Geo location has updated successfully", "", "success");
          this.toast.success("Geo location has updated successfully");
          this.GetGeo();
          this.noedit = false;
        }
        this.GetGeo();
      },
        error => {
          // swal("Duplicates are not allowed", "", "error");
          this.toast.error("Duplicates are not allowed");
          this.GetGeo();
          this.noedit = false;
        })
    }
    this.key = ""
  }


  edit(id, name, status) {
    console.log("edit", id)
    this.sid = id;
    this.noedit = true;
    this.value = false;
    this.status = status
    this.addb = false;
    this.searchfield = true;
    this.editname = name
  }




  cancelbulist() {
    this.value = false;
    this.addb = true;
    this.GetGeo();
  }
  clear() {
    this.geo = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key=""
    this.GetGeo();
  }

 
  navigateTo(){
    this.psa.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
}
