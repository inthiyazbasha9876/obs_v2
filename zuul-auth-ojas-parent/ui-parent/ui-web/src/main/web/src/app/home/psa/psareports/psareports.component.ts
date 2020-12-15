import { Component, OnInit } from '@angular/core';
import { ExcelService } from '../../services/excel.service';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { FormControl, NgModel } from '@angular/forms';
import { takeUntil } from 'rxjs/operators';


import * as moment from 'moment';
import { PsaService } from '../../services/psa.service';
import { HrmsService } from '../../services/hrms.service';
import { Router } from '@angular/router';
import { DictionaryService } from '../../services/dictionary.service';

@Component({
  selector: 'app-psareports',
  templateUrl: './psareports.component.html',
  styleUrls: ['./psareports.component.scss']
})
export class PsareportsComponent implements OnInit  {
  [x: string]: Object;
  selectService: boolean = false;
  customerDatares: any;
  updatecustomerarrayupdate: any;
  customerarray: any;
  customersdata: any;
  customersAllData: any;
  psa: any;
  data: any[];
  getAllcontractdata: any;
  contractAllDetails: any;
  projectData: any;
  dataTo: any;
  toData: any;
  status: boolean=false;
  fkey: any;
  f: any;
  table_heading: any;
  finalservices = ['Customer', 'Customer Contact', 'Customer Contract', 'Customer Project']
  response: any;
  table_data: any;
  customerList: any;
  selected_fields: any = null;
 
  pstatus = false;
  customercontractdetailslist: any;
  con: any;
  projectRatecard: unknown[];
  projectResourcefield: unknown[];
  customer: any;
  stateData: any;
  StateListassign: any;
  Servicename: any;
  ippr: any = 5;
  uncheck:any;
  fileName:any;
 p:any=1
  constructor(private service: PsaService, private excel: ExcelService, private hrms: HrmsService, private route:Router,private dictionary:DictionaryService) { }


  ngOnInit() {
    this.getCustomerData()
    this.getContractAll()
    this.getAllCustomerContactInfos()
    this.getAllProject()
    this.getStateListData()
    //this.getServiceTypeInfo()
  }

  ipps(){
    this.p=1
  }
  getCustomerData() {
    this.customerarray = [];
    var request =
    {
      "customerList":
      {


      },



      "transactionType": "getall"
    }

    this.service.getallListiofCustomer(request).subscribe(res => {
      console.log(res, "get all")
      this.customerDatares = res;
      console.log("resp", this.customerDatares)
      this.customer = this.customerDatares.customerList;
    
      console.log(this.customer, "original")

      for (let i in this.customer) {

  

        let billingaddLine1 = this.customer.find(s => s.billingaddressLine1 = this.customer[i].billingaddress[0].billingaddressLine1)
        this.customer[i].billingaddressLine1 = billingaddLine1.billingaddressLine1

     

        let customercitylocation = this.customer.find(s => s.citylocation = this.customer[i].billingaddress[0].citylocation)
        this.customer[i].citylocation = customercitylocation.citylocation


        let customerpincode = this.customer.find(s => s.pincode = this.customer[i].billingaddress[0].pincode)
        this.customer[i].pincode = customerpincode.pincode

        let stateslocation = this.customer.find(s => s.stateslocation = this.customer[i].billingaddress[0].stateslocation)
        this.customer[i].stateslocation = stateslocation.stateslocation
        console.log(this.customer[i].stateslocation, "states")
        //let customerstatesetloc = this.StateListassign.find(s => s.id == this.customer[i].stateslocation)
        // this.customer[i].stateslocation = customerstatesetloc.stateName
        // console.log(this.customer[i].stateslocation, "this is customerstate")

        let customerStatus = this.customer.find(s => s.status = this.customer[i].billingaddress[0].status)
        this.customer[i].status = customerStatus.status


        this.customer[i].createddate = moment(this.customer[i].createddate).format('DD-MM-YYYY')
        if (this.customer[i].updateddate != null) {
          this.customer[i].updateddate = moment(this.customer[i].updateddate).format('DD-MM-YYYY')
        }


        let customerEmail = this.customer.find(s => s.amEmail = this.customer[i].billinginfo.amEmail)
        this.customer[i].amEmail = customerEmail.amEmail

        let amContact = this.customer.find(s => s.amconatctName = this.customer[i].billinginfo.amconatctName)
        this.customer[i].amconatctName = amContact.amconatctName

        let apcontactEmail = this.customer.find(s => s.apEmail = this.customer[i].billinginfo.apEmail)
        this.customer[i].apEmail = apcontactEmail.apEmail

        let apContact = this.customer.find(s => s.apcontactName = this.customer[i].billinginfo.apcontactName)
        this.customer[i].apcontactName = apContact.apcontactName

        let bdmemail = this.customer.find(s => s.bdmEmail = this.customer[i].billinginfo.bdmEmail)
        this.customer[i].bdmEmail = bdmemail.bdmEmail

   

        let phoneNum = this.customer.find(s => s.phoneNumber = this.customer[i].billinginfo.phoneNumber)
        this.customer[i].phoneNumber = phoneNum.phoneNumber

      
        let contactinfoname = this.customer.find(s => s.contactName = this.customer[i].contactinfo.contactName)
        this.customer[i].contactName = contactinfoname.contactName

   

        let contactcountries = this.customer.find(s => s.countries = this.customer[i].countries)
        this.customer[i].countries = contactcountries.countries


        // let customergst = this.customer.find(s => s.gst = this.customer[i].customergst.gst)
        // this.customer[i].gst = customergst.gst

        // let customergstlocation = this.customer.find(s => s.location = this.customer[i].customergst.location)
        // this.customer[i].location = customergstlocation.location


        let registeredaddresscitylocation = this.customer.find(s => s.citylocation = this.customer[i].registeredaddress.citylocation)
        this.customer[i].citylocation = registeredaddresscitylocation.citylocation

        let registeredaddressregisteredpincode = this.customer.find(s => s.registeredpincode = this.customer[i].registeredaddress.registeredpincode)
        this.customer[i].registeredpincode = registeredaddressregisteredpincode.registeredpincode

    

        let registerestateslocation = this.customer.find(s => s.stateslocation = this.customer[i].registeredaddress.stateslocation)
        this.customer[i].stateslocation = registerestateslocation.stateslocation
        // let stateset = this.StateListassign.find(s => s.id == this.customer[i].stateslocation)
        // this.customer[i].stateslocation = stateset.stateName
        
        let servicetypeID = this.customer.find(s => s.servicetypeid = this.customer[i].servicetype[0].servicetypeid)
        this.customer[i].servicetypeid = servicetypeID.servicetypeid
   
        let shippingaddressCityLoc = this.customer.find(s => s.citylocation = this.customer[i].shippingaddress[0].citylocation)
        this.customer[i].citylocation = shippingaddressCityLoc.citylocation

      
      }
      console.log("Inside Objects", this.customer);

      this.table_data = this.customerDatares.customerList;
      console.log("tabledata from customer ", this.table_data);


      this.fkey = Object.keys(this.customerDatares.customerList[0])
      this.fkey = this.fkey.filter(d => d != "billingaddress");
      this.fkey = this.fkey.filter(d => d != "billinginfo");
      this.fkey = this.fkey.filter(d => d != "contactinfo");
      this.fkey = this.fkey.filter(d => d != "customergst");
      this.fkey = this.fkey.filter(d => d != "registeredaddress");
      this.fkey = this.fkey.filter(d => d != "servicetype");
      this.fkey = this.fkey.filter(d => d != "shippingaddress");

      this.fkey = this.fkey.filter(d => d != "mailstatus")
      this.fkey = this.fkey.filter(d => d != "billingaddressId");
     
      this.fkey = this.fkey.filter(d => d != "billingaddressLine2");
    
      this.fkey = this.fkey.filter(d => d != "status");
      
      this.fkey = this.fkey.filter(d => d != "billingId");
      this.fkey = this.fkey.filter(d => d != "billinginfostatus");
   
      this.fkey = this.fkey.filter(d => d != "contactId");
      
      this.fkey = this.fkey.filter(d => d != "contactinfostatus");
     
      this.fkey = this.fkey.filter(d => d != "customergststatus");
     
      this.fkey = this.fkey.filter(d => d != "id");
      
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.fkey.filter(d => d != "status");
     
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.fkey.filter(d => d != "servicestatus");
    
      this.fkey = this.fkey.filter(d => d != "status");
      this.fkey = this.changevalues(this.fkey)
      console.log("customer detailes", this.fkey);

    })
  }

  getContractAll() {
    this.data = []
    var contractdetails =
    {
      "customercontractdetailslist": [{

      }],
      "transactiontype": "getall"
    }

    this.service.getAllContractdDetails(contractdetails).subscribe(res => {
      this.getAllcontractdata = res;
      console.log("contract Get All", this.getAllcontractdata);

      this.contractAllDetails = this.getAllcontractdata.customercontractdetailslist;
      this.table_data = this.contractAllDetails;
      console.log("contract get deatils ", this.contractAllDetails);
      for (let i in this.contractAllDetails) {
      this.contractAllDetails[i].startdate = moment(this.contractAllDetails[i].startdate).format('DD-MM-YYYY')
      if (this.contractAllDetails[i].enddate != null) {
        this.contractAllDetails[i].enddate = moment(this.contractAllDetails[i].enddate).format('DD-MM-YYYY')
      }
    }
    console.log("contractDetailssss", this.contractAllDetails);
    
      this.fkey = Object.keys(this.contractAllDetails[0])
      this.fkey = this.fkey.filter(d => d != "financeStatus");
      this.fkey = this.fkey.filter(d => d != "buStatus");
      this.fkey = this.fkey.filter(d => d != "updatedBy");
      this.fkey = this.fkey.filter(d => d != "status");
      this.fkey = this.fkey.filter(d => d != "comment");
      this.fkey = this.fkey.filter(d => d != "description");
      this.fkey = this.fkey.filter(d => d != "document");
      this.fkey = this.changevalues(this.fkey)
      console.log("fields contract details", this.fkey);

    })
  }

  getAllCustomerContactInfos() {
    this.data = []
    var getallReq = {
      "customerContactInfo": [{

      }],
      "transactionType": "getall"
    }
    // let response:any
    let con
    this.service.getCustomerContactInfo(getallReq).subscribe(res => {
      console.log("getAll", res)
      this.response = res
      this.con = this.response.customerContactInfoList
      console.log("contactDetails", this.con);
      this.table_data = this.con;
      console.log("con", con)
      this.fkey = Object.keys(this.response.customerContactInfoList[0])
      this.fkey = this.changevalues(this.fkey)
      console.log("fields contact", this.fkey);
    })
  }

  selected_fields_fun() {
    console.log(this.selected_fields)
    this.table_heading = this.selected_fields
    this.status = true
  
    console.log("table data", this.table_data)

    if (this.selected_fields.length == 0) { // to check array is empty or not
      this.pstatus = false
      this.status=false;    
    }
    else {
      this.pstatus = true
      this.status=true;    
    }
  }
  getAllProject() {
    var getProject = {
      "projectInfo": {
      },
      "transactionType": "getall"
    }


    this.service.getAllProject(getProject).subscribe(response => {
      this.projectData = response;
      console.log("parent", this.projectData)
      this.dataTo = this.projectData.projectList;
     
      console.log("total", this.dataTo)
      for (let i in this.dataTo) {

        let ratetypeassign = this.dataTo.find(s => s.rateType = this.dataTo[i].projectRatecard.rateType)
        this.dataTo[i].ratetype = ratetypeassign.rateType

        let billingtypeassing = this.dataTo.find(s => s.billingType = this.dataTo[i].projectRatecard.billingType)
        this.dataTo[i].billingtype = billingtypeassing.billingType

    
        let rateTypeassing = this.dataTo.find(s => s.rateType = this.dataTo[i].projectRatecard.rateType)
        this.dataTo[i].ratetype = rateTypeassing.rateType

        // let ratecardIdassing = this.dataTo.find(s => s.ratecardId = this.dataTo[i].projectRatecard.ratecardId)
        // this.dataTo[i].ratecardid = ratecardIdassing.ratecardId

        let serviceTypeassing = this.dataTo.find(s => s.serviceType = this.dataTo[i].projectRatecard.serviceType)
        this.dataTo[i].servicetype = serviceTypeassing.serviceType

        let projectManagerassing = this.dataTo.find(s => s.projectManager = this.dataTo[i].projectResourceMapping.projectManager)
        this.dataTo[i].projectmanager = projectManagerassing.projectManager

        let techLeadassing = this.dataTo.find(s => s.techLead = this.dataTo[i].projectResourceMapping.techLead)
        this.dataTo[i].TechLead = techLeadassing.techLead

        // let resourceCountassing = this.dataTo.find(s => s.resourceCount = this.dataTo[i].projectResourceMapping.resourceCount)
        // this.dataTo[i].resourcecount = resourceCountassing.resourceCount

      
        // let resourcesassing = this.dataTo.find(s => s.resources = this.dataTo[i].projectResourceMapping.resources)
        // this.dataTo[i].Resources = resourcesassing.resources

        let techStackassing = this.dataTo.find(s => s.techStack = this.dataTo[i].projectResourceMapping.techStack)
        this.dataTo[i].TechStack = techStackassing.techStack

      }
      console.log(this.dataTo, "llkhdsxcmb")



      this.table_data = this.dataTo
      console.log(this.table_data, "lol1")




      console.log("contract data in project", this.dataTo);

      this.fkey = Object.keys(this.projectData.projectList[0])


    

      this.fkey = this.fkey.filter(d => d != "projectRatecard");
      this.fkey = this.fkey.filter(d => d != "projectResourceMapping");
      this.fkey = this.fkey.filter(d => d != "billingType");
     
      this.fkey = this.fkey.filter(d => d != "rateType");
      this.fkey = this.fkey.filter(d => d != "ratecardId");
      this.fkey = this.fkey.filter(d => d != "serviceType");
      this.fkey = this.fkey.filter(d => d != "projectManager");
     
      // this.fkey = this.fkey.filter(d => d != "resourceMappingId");
      // this.fkey = this.fkey.filter(d => d != "resources");
      this.fkey = this.fkey.filter(d => d != "techStack");
      this.fkey = this.fkey.filter(d => d != "techLead");
      this.fkey = this.fkey.filter(d => d != "comment");
      this.fkey = this.fkey.filter(d => d != "flag");
      // this.fkey = this.fkey.filter(d => d != "isInternal");
      this.fkey = this.fkey.filter(d => d != "projectDescription");

      this.fkey = this.changevalues(this.fkey)
      console.log("customer detailes", this.fkey);
    })
  }

 
 

  selected_service(e) {
    this.fileName=e;
    
    
    this.pstatus=false    
  this.uncheck="";
    console.log("service name", e)
    this.selected_fields = "";
    

    this.selectService = true;
    this.table_heading = null;
    this.fkey = null;
    if (this.status == false)
      this.status = true    
    else
      this.status = false;
     
   
   
    var choice = e;
    switch (choice) {
      case "Customer":
        console.log("customer Choice")
        this.getCustomerData();
        break;
      case "Customer Contract":
        this.getContractAll();
        break;
      case "Customer Contact":
        this.getAllCustomerContactInfos();
        break;
      case "Customer Project":
        this.getAllProject();
        break;
    }
    console.log("serviced", choice);
    
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
    console.log("select all",checkAll,select,values);
    
    
    if (checkAll) {
      select.update.emit(values);
      this.selected_fields_fun();
      
    }
    else {
      select.update.emit([]);
      this.selected_fields_fun();
    }
    checkAll=false;
  }

  getStateListData() {
    var request = {

      "states":
        [],

      "sessionId": "1234",
      "transactionType": "getAll"

    }
    this.hrms.getStateListMaster(request).subscribe(res => {
      this.stateData = res;
      this.StateListassign = this.stateData.statesList;
      console.log("ststelistgetAll", this.StateListassign);
    })
  }
  navigateTo(){
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
