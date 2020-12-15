import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-c2h',
  templateUrl: './c2h.component.html',
  styleUrls: ['./c2h.component.scss']
})
export class C2hComponent implements OnInit {



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
  c2hstatus: any;
  c2harr: any;


  c2hres: any;
  editname: any;
  key: string;


  constructor(private psa: PsaService, private toast: NotificationService) { }
  
  ngOnInit() {
    this.getC2H();
  }
  //getall
  getC2H() {
    
    console.log("getc2H called");


    var Requestdata = {
      "c2hstatuslist": [
        {
        }
      ],
      "transactionType": "getall"
    }



    console.log("req data", Requestdata)
    this.psa.getC2H(Requestdata).subscribe(responce => {
      this.c2hres = responce;

      console.log("Get response", this.c2hres)
      console.log("Get response", this.c2hres.c2hstatuslist)
      this.table = this.c2hres.c2hstatuslist;
    })

  }
  //get all close

  //save
  setC2H() {
    
    
  var count=0;
  var str = this.c2hstatus.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.c2hstatus.replace(/[\. ,:-]+/g, "")
    if (one.toLowerCase() == str.toLowerCase()) {
      count = 1
    }
  })

  if (count == 1) {
  
    this.toast.error("invalid")
    this.id = "",
    this.c2hstatus = ""
    this.value = true;
  }
  
  else{
    var reqData =
    {
      "c2hstatuslist": [{
        "c2hstatusId": this.id,
        "c2hstatus": this.c2hstatus,
        "status": true

      }],
      "transactionType": "save"

    }

    console.log("reqdata", reqData);

    this.psa.setC2H(reqData).subscribe((response: any) => {
      this.c2hres = response;
      console.log("Save response", this.c2hres);

      if (this.c2hres.message == "C2HStatus saved successfully") {

        // swal("C2H Status saved successfully", "","success");
        this.toast.success("C2H status saved successfully")
        this.getC2H();
       
    
      }
      // this.id = "",
      // this.c2hstatus = ""
      this.value = true;

    },
      error => {
        // swal("Duplicates are not allowed","","error");
        this.toast.error("Duplicates are not allowed")

        this.c2harr = this.c2hres.c2hstatuslist;
        console.log(this.c2harr, "array")
        this.getC2H();

      });
    }

    
  }
  //save close

  updateC2H(c2hStatusname) {

    // this.searchfield=false;
    this.searchfield = false;

    console.log("reee",c2hStatusname)
    
    var count = 0
    var str = c2hStatusname.c2hstatus.replace(/[\. ,:-]+/g, "");
    this.table.map(d=>{
      var one=d.c2hstatus.replace(/[\. ,:-]+/g, "");
      if (one.toLowerCase() == str.toLowerCase() && d.c2hstatusId != this.sid) {
        count = 1
      }
    })
    if (count == 1) {
      this.toast.error("Duplicates are not allowed")
    
      this.getC2H();
      this.noedit = false;
      this.value=false;
      this.addb=true;
    } else
   {
    var updateRequestData =
    {
      "c2hstatuslist": [{
        "c2hstatusId": this.sid,
        "c2hstatus": c2hStatusname.c2hstatus,
        "status": this.status

      }],
      "transactionType": "update"

    }




    console.log("success update", updateRequestData)
    this.addb = true;
    this.psa.updateC2H(updateRequestData).subscribe((res: any) => {
      this.masterList = res;
      console.log("response msg",this.masterList);
      if (this.masterList.message == "C2HStatus updated successfully") {
        //  swal("C2H status has updated successfully", "","success");
        this.toast.success("C2H status has updated successfully")
        this.getC2H;
        this.noedit = false;
      }
      this.getC2H();
    },
      error => {
        // swal("Duplicates are not allowed","","error");
        this.toast.error("Duplicates are not allowed")
        this.getC2H();
        this.noedit = false;
      })
    this.searchfield = false;
    this.key=""
   }
  }


  edit(id,name, status) {
    console.log("edit", id)
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
    this.getC2H();
  }
  clear() {
    this.c2hstatus = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key=""
    this.getC2H();
  }
 
  navigateTo(){
    this.psa.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
}