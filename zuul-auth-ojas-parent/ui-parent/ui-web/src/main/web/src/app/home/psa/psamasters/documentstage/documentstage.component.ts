import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-documentstage',
  templateUrl: './documentstage.component.html',
  styleUrls: ['./documentstage.component.scss']
})
export class DocumentstageComponent implements OnInit {



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


  documentTypeList: string;
  documenttypearr: any;

  documenttypeTable: any;
  documenttype: any
  adddocumenttype: string;
  documentstage: string;
  key: string;
  editname: any;

  constructor(private psa: PsaService,private toast:NotificationService) { }

  ngOnInit() {
    this.getDocument();
  }
  //getall
  getDocument() {
    console.log("getDocument called");


    var Requestdata = {
      "doucumentStageList": [{




      }],
      "transactionType": "getall"
    }
    console.log("success", Requestdata)
    this.psa.getDocument(Requestdata).subscribe(responce => {
      this.documenttype = responce;

      console.log("Get response", this.documenttype)
      console.log("Get response", this.documenttype.doucumentStageList)
      this.table = this.documenttype.doucumentStageList;
      for (let i in this.table)

        if (this.table[i].status == true) {
          this.table[i].status = "Active"
        } else if (this.table[i].status == false) {
          this.table[i].status = "InActive"
        }

    })

  }
  //get all close

  //save
  setDocument(e) {
    var count=0;
    var str = e.adddocumenttype.replace(/[\. ,:-]+/g, "");
  
    this.table.map(d=>{
      var one=d.documentstage.replace(/[\. ,:-]+/g, "")
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
      "doucumentStageList": [{

        "documentstage": e.adddocumenttype,
        "status": true

      }],
      "transactionType": "save"
    }




    console.log("reqdata", reqData);

    this.psa.setDocument(reqData).subscribe((response: any) => {
      this.documenttype = response;
      console.log("Save response", this.documenttype);

      if (this.documenttype.message == "document stage details has saved successfully") {

        // swal("document stage details has saved successfully ", "", "success");
        this.toast.success("document stage details has saved successfully")
        this.getDocument();
      }

    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed");

    
        this.getDocument();

      });
    }
    this.id = "",
      this.documentstage = "",
      this.value = false;
    }
  //save close

  UpdateDocument(documenttypeTable) {

    this.searchfield = false;

    console.log("updating value",documenttypeTable)
 
    var count = 0
    var str = documenttypeTable.documentstage.replace(/[\. ,:-]+/g, "");
    
   
    this.table.map(d=>{
      var one=d.documentstage.replace(/[\. ,:-]+/g, "");
      console.log("oppp",this.sid)
      console.log("doc",d.documentstageId)
      if (one.toLowerCase() == str.toLowerCase() && d.documentstageId != this.sid) {
        count = 1
      }
    })
    
    if (count == 1) {
      this.toast.error("Duplicates are not allowed")
     
      this.getDocument();
      this.noedit = false;
      this.value=false;
      this.addb=true;
    } else
    
   {
  
    var updateRequestData =
    {
      "doucumentStageList": [{
        "documentstageId": this.sid,

        "documentstage": documenttypeTable.documentstage,
        "status": false

      }],
      "transactionType": "update"
    }

    console.log("success", updateRequestData)
    this.addb = true;
    this.psa.UpdateDocument(updateRequestData).subscribe((res: any) => {
      this.masterList = res;
      console.log(this.masterList);
    
      if (this.masterList.message == "document stage details has updated successfully") {
        // swal("Project tech stack has updated successfully", "", "success");
        this.noedit = false;
        this.value=false;
        this.toast.success(this.masterList.message);
        this.getDocument();
    
      }
      this.getDocument();
    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed");
        this.getDocument();
        this.noedit = false;
       
      })
    
    }
    this.key=""
  }


  edit(id,name, status) {
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
    this.getDocument();
  }
  clear() {
    this.documenttype = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key=""
    this.getDocument();
  }
 
  navigateTo(){
    this.psa.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
}
