import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-documenttype',
  templateUrl: './documenttype.component.html',
  styleUrls: ['./documenttype.component.scss']
})
export class DocumenttypeComponent implements OnInit {


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

  documenttypelist: any;
  documenttype: any;
  setdocumetdata: any;

  documentArr: (arg0: string, documenttypelist: any) => any;

  getDocumentTypeInfo: any;
  documenttype_type: string;
  documenttypeId: any;
  dId: any;
  key: string;
  editname: any;

  constructor(private psa: PsaService,private toast:NotificationService) { }

  ngOnInit() {
    this.getdocumenttype()
  }




  getdocumenttype() {
    //console.log("servicetype info function called");

    var Requestdata = {


      "documenttypelist": [{


      }],
      "transactionType": "getall"
    }


    console.log("success", Requestdata)
    this.psa.getDocument_Type(Requestdata).subscribe(responce => {
      this.documenttypelist = responce;
      //this.servicetypelist=this.servicetypelist.servicetypeList;
      console.log("Get response", this.documenttypelist)
      console.log("Get response", this.documenttypelist.documenttypelist)
      this.table = this.documenttypelist.documenttypelist;

    //   for (let i in this.table)

    //     if (this.table[i].status == true) {
    //       this.table[i].status = "Active"
    //     } else if (this.table[i].status == false) {
    //       this.table[i].status = "InActive"
    //     }
    })
    console.log("documenttype function called");
  }
  //get all close






  //save
  setdocumenttype(s) {
    var count=0;
  var str = s.adddocument.replace(/[\. ,:-]+/g, "");

  this.table.map(d=>{
    var one=d.documenttype.replace(/[\. ,:-]+/g, "")
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
      "documenttypelist": [
        {
          "documenttype": s.adddocument,
          "status": true
        }
      ],
      "transactionType": "save"
    }

    console.log("success", reqData);

    this.psa.setDocument_Type(reqData).subscribe(response => {
      this.setdocumetdata = response;
      console.log("Save response", this.setdocumetdata, this.setdocumetdata.message);



      // swal(this.setdocumetdata.message, "", "success");
      this.toast.success(this.setdocumetdata.message);
      this.getdocumenttype()


    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed");

        this.documentArr = this.setdocumetdata.documenttypelist;
        this.getdocumenttype()

      });
    this.documenttype = "",
      this.value = false;
    }
  }
  // //save close






  updatedocument(documenttable) {
    console.log("documenttable", documenttable);
    this.searchfield = false;

var count = 0
var str = documenttable.documenttype_type.replace(/[\. ,:-]+/g, "");
this.table.map(d=>{
  var one=d.documenttype.replace(/[\. ,:-]+/g, "");
  if (one.toLowerCase() == str.toLowerCase() && d.documenttypeId != this.dId) {
    count = 1
  }
})
if (count == 1) {
  this.toast.error("Duplicates are not allowed")

  this.getdocumenttype()
  this.noedit = false;
  this.value=false;
  this.addb=true;
} else
    // this.searchfield=false;
{
    


    var updateRequestData =
    {
      "documenttypelist": [
        {
          "documenttype": documenttable.documenttype_type,
          "documenttypeId": this.dId,
         
          "status": this.status
        }
      ],
      "transactionType": "update"
    }
    console.log(" update success ", updateRequestData)
    
    this.psa.updateDocument_Type(updateRequestData).subscribe((res: any) => {
      this.masterList = res;
      console.log("update data",this.masterList);

      // swal(this.masterList.message, "", "success");
      this.toast.success(this.masterList.message)
      this.getDocumentTypeInfo;
      this.noedit = false;
      this.addb = true;
      this.getdocumenttype()
    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed");
        this.getdocumenttype()
        this.noedit = false;
      })
    }
    this.key=""
  }


  edit(id,name, status) {
    console.log("edit", id)
    this.dId = id;
    this.noedit = true;
    this.value = false;
    this.status = status;
    this.addb = false;
    this.searchfield = true;
    this.editname=name
  }




  cancelbulist() {
    this.value = false;
    this.addb = true;
    this.getdocumenttype()
  }
  clear() {
    this.documenttype_type = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key=""
    this.getdocumenttype()
  }



 
  navigateTo(){
    this.psa.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
}
