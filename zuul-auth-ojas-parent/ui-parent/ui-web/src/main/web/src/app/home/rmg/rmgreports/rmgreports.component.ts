import { Component, OnInit } from '@angular/core';
import { NgModel } from '@angular/forms';
import { RmgService } from '../../services/rmg.service';
import { ExcelService } from '../../services/excel.service';
import { Router } from '@angular/router';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { DictionaryService } from '../../services/dictionary.service';
@Component({
  selector: 'app-rmgreports',
  templateUrl: './rmgreports.component.html',
  styleUrls: ['./rmgreports.component.scss']
})
export class RmgreportsComponent implements OnInit {

  selectService: boolean;
  rmgSpecific: any;
  specific: any;
  generic: any;
  test: any;
  genericIdData: any;
  specificarr:[];
  genericarr:[];
  obj:any;
  p:any=1
  // excel: any;

  constructor(private rmgService: RmgService, private excel: ExcelService, private route: Router,private dictionary:DictionaryService) { }

  ngOnInit() {
    this.getresources();

  }
  ipps(){
    this.p=1
  }
  hidedtab = true;
  backhide = false;
  rejectmoreid = false;
  addhide = true;
  rmgdata: any;
  finalservices = ['Specific','Generic'];
  rmgDataResponse: any;
  // rmgSpecificResponse: any;
  dataOfResource: any;
  pages: any = 5;
  //

  response: any;
  table_data: any;
  rmgDataResponseList: any;
  selected_fields: any = null;

  pstatus = false;
  rmgDataResponsecontractdetailslist: any;
  con: any;
  projectRatecard: unknown[];
  projectResourcefield: unknown[];
  //rmgDataResponse: any;
  stateData: any;
  StateListassign: any;
  Servicename: any;
  ippr: any = 5;
  uncheck: any;
  fileName: any;
  status: boolean = false;
  fkey: any;
  f: any;
  table_heading: any;
  resourceType:any;
  specificdata=[];
  genericdata=[];
  genericassign=[];
  specificassign=[];
  getresources() {
    var resourcedata = {
      "rmgInfo": {


      },
      "transactiontype": "getall"
    }

    this.rmgService.getAllResource(resourcedata).subscribe(res => {
      this.rmgdata = res;
      console.log("rmg", this.rmgdata);

      this.rmgDataResponse = this.rmgdata.rmgInfo;
    
      console.log("resorcesss data", this.rmgDataResponse)

      this.specificdata=this.rmgDataResponse.filter(s=>s.resourceType.toLowerCase()== 'specific')
     
      var finalarray=[]; 
      for (let i in this.specificdata) {

        console.log(this.specificdata[i],"thisdata");
        console.log(this.specificdata[i].rmgspecific.length,"length");
        
        
        
          var array=[]
         
          for(let j in this.specificdata[i].rmgspecific){

     var obj = {
           "bookingId": this.specificdata[i].bookingId,
           "resourceType": this.specificdata[i].resourceType,
           "projectId": this.specificdata[i].projectId,
           "status": this.specificdata[i].status,
           "flag": this.specificdata[i].flag,
           "message": this.specificdata[i].message,       
           "specificId": this.specificdata[i].rmgspecific[j].specificId,
           "startDate": this.specificdata[i].rmgspecific[j].startDate,
           "endDate": this.specificdata[i].rmgspecific[j].endDate,
           "employeeId": this.specificdata[i].rmgspecific[j].employeeId,
           " billRate": this.specificdata[i].rmgspecific[j].billRate,
           "reason": this.specificdata[i].rmgspecific[j].reason,
           "specificStatus": this.specificdata[i].rmgspecific[j].specificStatus,
           "alternateemployeeId": this.specificdata[i].rmgspecific[j].alternateemployeeId,
         };
     
         console.log(obj,"pushingdata");
         
         finalarray.push(obj)
       
     
          }
        
         
          console.log(finalarray,"pusheddata");
          this.specificassign=finalarray;
 
      }



     

     

    
    
            console.log(finalarray,"finaldata");

          
    
         this.genericdata=this.rmgDataResponse.filter(s=>s.resourceType.toLowerCase()== 'generic')
      console.log(this.genericdata,"genericdata");
      var arraygeneric=[];
      for(let i in this.genericdata){

        for(let j in this.genericdata[i].rmggeneric){

              
          for(let l in this.genericdata[i].rmggeneric[j].rmggenericresourcemap){
            
            let obj={
              "bookingId": this.genericdata[i].bookingId,
              "resourceType": this.genericdata[i].resourceType,
              "projectId": this.genericdata[i].projectId,
              "status": this.genericdata[i].status,
              "flag": this.genericdata[i].flag,
              "message": this.genericdata[i].message,
              "genericId": this.genericdata[i].rmggeneric[j].genericId,
              "resourceExperience": this.genericdata[i].rmggeneric[j].resourceExperience,
              "resourceSkills":this.genericdata[i].rmggeneric[j].resourceSkills ,
              "startDate":this.genericdata[i].rmggeneric[j].startDate,
              "endDate":this.genericdata[i].rmggeneric[j].endDate,
              "billRate": this.genericdata[i].rmggeneric[j].billRate,
              "resourceCount":this.genericdata[i].rmggeneric[j].resourceCount,
              "resourcegenericId":this.genericdata[i].rmggeneric[j] ,
              "empId":this.genericdata[i].rmggeneric[j].rmggenericresourcemap[l].empId,
              "startDateresource": this.genericdata[i].rmggeneric[j].rmggenericresourcemap[l].startDateresource,
              "endDateResource":this.genericdata[i].rmggeneric[j].rmggenericresourcemap[l].endDateResource,
              "genericStatus": this.genericdata[i].rmggeneric[j].rmggenericresourcemap[l].genericStatus
  
            }

           arraygeneric.push(obj)
          }
          

        }


      }
      
        console.log(arraygeneric,"genericdat123a");
        this.genericassign=arraygeneric
        
     
        
      
      


    })
  }
  setupData(e){
    if(this.fileName=='Specific'){
      
      this.fkey = Object.keys(this.specificassign[0])
      this.fkey = this.changevalues(this.fkey)
      this.table_data =this.specificassign;
    }
    else if(this.fileName=='Generic'){
    
      this.fkey = Object.keys(this.genericassign[0])
      this.fkey = this.fkey.filter(d => d != "resourcegenericId");
      this.fkey = this.changevalues(this.fkey)
      this.table_data =this.genericassign;
    }
  }

  selected_fields_fun() {
    console.log("selecter", this.selected_fields)
    this.table_heading = this.selected_fields
    console.log(this.table_heading,"datatatata");
    
    this.status = true
    console.log("table data", this.table_data)
   console.log(this.selected_fields.length,"lengthofdata");
   
    if (this.selected_fields.length == 0) { // to check array is empty or not
     
      
      this.pstatus = false
      this.status = false;
    }
    else {
  
      this.pstatus = true
      this.status = true;
    }
  }

  selected_service(e) {
    this.fileName = e;
  this.setupData(e)
   

    this.pstatus = false
    this.uncheck = "";
    console.log("service name",this.fileName)
    this.selected_fields = "";
   

    this.selectService = true;
    this.table_heading = null;
  
    if (this.status == false)
      this.status = true
    else
      this.status = false;



    var choice = e;
    switch (choice) {
      case "Resorces":
        console.log("Resources Choice")
        this.getresources();
    

    }
    

  }

  download_pdf() {
    var one = []
    var heading=[]
    for (let i in this.table_data) {
      var obj = []
      for (let j in this.table_heading) {
        obj.push(this.table_data[i][this.table_heading[j].value])
      }
      one.push(obj)
    }
    for (let i in this.table_heading) {
      heading.push(this.table_heading[i].display)
    }
    console.log("final data", one)
    var doc = new jsPDF({
      orientation: 'landscape',
    });
    doc.autoTable(heading, one);
    var val = this.fileName + ".pdf";
    doc.save(val);
  }

  download_excel() {
    var xlFile = this.fileName;
    this.excel.exportAsExcelFile(this.table_data, xlFile);
  }

  selectAll(checkAll, select: NgModel, values) {
    console.log("select all", checkAll, select, values);

    if (checkAll) {
      select.update.emit(values);
      this.selected_fields_fun();
      //console.log(select.update.emit(values),'yyyyyyyyyyyyy')
    }
    else {
      select.update.emit([]);
      this.selected_fields_fun();
    }
    checkAll = false;
  }
  navigateTo() {
    this.route.navigate(['home/dashboard']);
  }

  changevalues(e) {
    var array = []
    for (let i in e) {
      var count = 0
      var one = e[i].split(/(?=[A-Z][a-z]+|[0-9])/).join("_");
      if (one.includes("_")) {
        var obj = new Object({ value: e[i], display: one })
        array.push(obj)
      } else {
        for (let j in this.dictionary.words) {
          if (this.dictionary.words[j].replace(/[_]+/g, "").toLowerCase() == e[i].toLowerCase()) {
            var obj = new Object({ value: e[i], display: this.dictionary.words[j] })
            array.push(obj)
            count = 1
          }
        }
        if (count == 0) {
          var obj = new Object({ value: e[i], display: e[i] })
          array.push(obj)
        }
      }
    }
    return array
  }
}
