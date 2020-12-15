import { Component, OnInit } from '@angular/core';


import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { FormControl, NgModel } from '@angular/forms';


import { ThrowStmt } from '@angular/compiler';
import { Router } from '@angular/router';
import * as moment from 'moment';

import { ExcelService } from '../../services/excel.service';
import { HrmsService } from '../../services/hrms.service';
import { DictionaryService } from '../../services/dictionary.service';
@Component({
  selector: 'app-empreports',
  templateUrl: './empreports.component.html',
  styleUrls: ['./empreports.component.scss']
})
export class EmpreportsComponent implements OnInit {
  selectService: boolean = false;
  uncheck: any;
  firstname: any;

  employeeInfo: (e: any) => void;
  fileName: any;


  constructor(private hrms: HrmsService, private excel: ExcelService, private route: Router, private dictionary: DictionaryService) { }


  ngOnInit() {
    this.getAllServices()
  }



  Details: any;
  fkey: any
  selected_fields: any;
  table_heading: any
  service: any;
  finalservices: any
  table_data: any
  status: any = false
  states: any
  gender: any
  genderList: any
  role: any
  roleList: any
  resource: any
  resourceType: any
  pstatus = false
  stateList: any
  ippr: any = 5;
  qualification: any
  qualificationList: any
  resignation: any
  resignationType: any
  p: any = 1
  selectedipp() {
    this.p = 1
  }
  getKye() {

    var reqObj =
    {
      "kye":
        [{


        }],
      "transactionType": "getAll"
    }

    this.hrms.getEmployeeKyeDetails(reqObj).subscribe(res => {

      console.log("kyeRes", res);
      this.Details = res;
      this.table_data = this.Details.kyeList
      var key = Object.keys(this.Details.kyeList[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "passport_status");
      this.fkey = this.fkey.filter(d => d != "pan_status");
      this.fkey = this.fkey.filter(d => d != "aadhar_status");
      this.fkey = this.fkey.filter(d => d != "created_by");
      this.fkey = this.fkey.filter(d => d != "updated_by");
      this.fkey = this.fkey.filter(d => d != "created_date");
      this.fkey = this.fkey.filter(d => d != "updated_date");
      this.fkey = this.fkey.filter(d => d != "passport_img");
      this.fkey = this.fkey.filter(d => d != "pan_img");
      this.fkey = this.fkey.filter(d => d != "aadhar_img");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }


  selected_fields_fun() {
    console.log(this.selected_fields)
    this.table_heading = this.selected_fields

    this.status = true


    console.log("table data", this.table_data)

    if (this.selected_fields.length == 0) { // to check array is empty or not
      this.pstatus = false
      this.status = false;

    }
    else {
      this.pstatus = true
      this.status = true;

    }
  }

  getExperience() {
    console.log("experience")

    var requestObj =
    {
      "employeeExperienceDetails": [{


      }],

      "transactionType": "getAll"
    }


    this.hrms.getEmployeeExperienceDetails(requestObj).subscribe(res => {
      console.log(res)
      this.Details = res;

      this.table_data = this.Details.employeeExperienceDetails

      for (let i in this.table_data) {
        if (this.table_data[i].is_current_company == 1) {
          this.table_data[i].is_current_company = "Yes"
        } else {
          this.table_data[i].is_current_company = "No"
        }
      }
      console.log("tabel_data", this.table_data)
      var key = Object.keys(this.Details.employeeExperienceDetails[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "status");
      this.fkey = this.fkey.filter(d => d != "documentsstatus");
      this.fkey = this.fkey.filter(d => d != "image");
      this.fkey = this.fkey.filter(d => d != "created_by");
      this.fkey = this.fkey.filter(d => d != "updated_by");
      this.fkey = this.fkey.filter(d => d != "created_date");
      this.fkey = this.fkey.filter(d => d != "updated_date");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }


  getEducation() {
    console.log("Education")
    var reqtitle =
    {
      "employeeEducationDetailsList": [
        {

        }
      ],

      "transactionType": "getAll"

    }

    this.hrms.getEmpEduDetails(reqtitle).subscribe(res => {
      console.log(res)
      this.Details = res;

      this.table_data = this.Details.employeeEducationDetailsList
      console.log("Education", this.table_data);

      var key = Object.keys(this.Details.employeeEducationDetailsList[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "status");
      this.fkey = this.fkey.filter(d => d != "image");
      this.fkey = this.fkey.filter(d => d != "createdBy");
      this.fkey = this.fkey.filter(d => d != "updatedBy");
      this.fkey = this.fkey.filter(d => d != "createdDate");
      this.fkey = this.fkey.filter(d => d != "updatedDate");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.fkey.filter(d => d != "comment");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }

  getBasicInfo() {
    console.log("basicInfo")

    var empinfo =
    {
      "employeeInfo": [{

      }],
      "transactionType": "getall"
    }

    this.hrms.getempinfo(empinfo).subscribe(res => {
      console.log(res)
      this.Details = res;
      console.log("basicInfo", this.Details);



      this.table_data = this.Details.employeeInfo
      var key = Object.keys(this.Details.employeeInfo[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "status");
      this.fkey = this.fkey.filter(d => d != "image");
      this.fkey = this.fkey.filter(d => d != "createdBy");
      this.fkey = this.fkey.filter(d => d != "updatedBy");
      this.fkey = this.fkey.filter(d => d != "createdOn");
      this.fkey = this.fkey.filter(d => d != "updatedOn");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.fkey.filter(d => d != "password");
      this.fkey = this.fkey.filter(d => d != "role");
      this.fkey = this.fkey.filter(d => d != "statusDate");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }



  getSkills() {
    console.log("skills")

    var Requestdata = {
      "listOfSkill": [{


      }],
      "transactionType": "getAll"
    }
    this.hrms.getSkill(Requestdata).subscribe(res => {
      console.log(res)
      this.Details = res;
      this.table_data = this.Details.getSkillInfoList
      var key = Object.keys(this.Details.getSkillInfoList[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "status");

      this.fkey = this.fkey.filter(d => d != "created_by");
      this.fkey = this.fkey.filter(d => d != "update_by");
      this.fkey = this.fkey.filter(d => d != "created_date");
      this.fkey = this.fkey.filter(d => d != "updated_date");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }

  getCertifications() {
    console.log("certifications")

    var request =

    {

      "certificationDetailsModel": [{


      }],

      "transactionType": "getall"
    }
    this.hrms.getCertification(request).subscribe(res => {
      console.log(res)
      this.Details = res;
      this.table_data = this.Details.certificationDetailsList
      var key = Object.keys(this.Details.certificationDetailsList[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "createdBy");
      this.fkey = this.fkey.filter(d => d != "updatedBy");
      this.fkey = this.fkey.filter(d => d != "createdDate");
      this.fkey = this.fkey.filter(d => d != "updatedDate");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }

  getContactInfo() {
    console.log("contacts")

    var request =
    {
      "empInfo": [
        {

        }
      ],
      "transactionType": "getAll"
    }

    this.hrms.getContactInfo(request).subscribe(res => {
      console.log(res)
      this.Details = res;
      this.table_data = this.Details.empContacts
      var key = Object.keys(this.Details.empContacts[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "createdBy");
      this.fkey = this.fkey.filter(d => d != "updatedBy");
      this.fkey = this.fkey.filter(d => d != "createdDate");
      this.fkey = this.fkey.filter(d => d != "updatedDate");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }
  getBankInfo() {
    console.log("BankInfo")

    var bankdetails =
    {
      "bankDetails": [{

      }],
      "transactionType": "getall"
    }


    this.hrms.getbankserverdetails(bankdetails).subscribe(res => {
      console.log(res)
      this.Details = res;
      this.table_data = this.Details.listBankDetails
      for (let i in this.table_data) {
        if (this.table_data[i].bank_account_status == 1) {
          this.table_data[i].bank_account_status = "Active"
        } else {
          this.table_data[i].bank_account_status = "InActive"
        }
      }
      var key = Object.keys(this.Details.listBankDetails[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "Is_active");
      this.fkey = this.fkey.filter(d => d != "created_by");
      this.fkey = this.fkey.filter(d => d != "updated_by");
      this.fkey = this.fkey.filter(d => d != "created_date");
      this.fkey = this.fkey.filter(d => d != "updated_date");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }
  getResignation() {
    console.log("Resignation")

    var req1 = {
      "resignation": [
        {

        }],
      "transactionType": "getall"
    }
    this.hrms.getResignation(req1).subscribe(res => {
      console.log(res)
      this.Details = res;
      console.log("Resignation", this.Details);



      this.table_data = this.Details.resignationList
      var key = Object.keys(this.Details.resignationList[0])
      this.fkey = key.filter(d => d != "id");
      this.fkey = key.filter(d => d != "resignationSubmittedOn")
      this.fkey = key.filter(d => d != "finalSettlementDate")
      this.fkey = key.filter(d => d != "leavingDate")
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }
  getProjectDetails() {
    console.log("ProjectDetails")

    var jsonData =
    {
      "projectDetailsList": [{

      }],
      "transactionType": "getAll"
    }

    this.hrms.getProjectDetails(jsonData).subscribe(res => {
      console.log(res)
      this.Details = res;
      this.table_data = this.Details.projectDetailsList
      var key = Object.keys(this.Details.projectDetailsList[0])
      this.fkey = key.filter(d => d != "billingId");
      this.fkey = this.fkey.filter(d => d != "rateId");
      this.fkey = this.fkey.filter(d => d != "isInternal");
      this.fkey = this.fkey.filter(d => d != "createdBy");
      this.fkey = this.fkey.filter(d => d != "updatedBy");
      this.fkey = this.fkey.filter(d => d != "createdDate");
      this.fkey = this.fkey.filter(d => d != "updatedDate");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.fkey.filter(d => d != "flag");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }

  getDependents() {
    console.log("Dependents")

    var requestData = {


      "dependentDetails": [{

      }],
      "transactionType": "getall",

    }

    this.hrms.getdependentdetails(requestData).subscribe(res => {
      console.log(res)
      this.Details = res;
      this.table_data = this.Details.getDependentDetailsList
      var key = Object.keys(this.Details.getDependentDetailsList[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "created_By");
      this.fkey = this.fkey.filter(d => d != "updated_By");
      this.fkey = this.fkey.filter(d => d != "created_Date");
      this.fkey = this.fkey.filter(d => d != "updated_Date");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }


  getOnboarding() {
    console.log("Onboarding")

    var employmentdetailss =
    {
      "employmentDetails": [{

      }],
      "transactionType": "getAll"
    }
    this.hrms.getonboardingdetails(employmentdetailss).subscribe(res => {
      console.log(res)
      this.Details = res;
      this.table_data = this.Details.employmentDetailsList
      var key = Object.keys(this.Details.employmentDetailsList[0])
      this.fkey = key.filter(d => d != "flag");
      this.fkey = this.fkey.filter(d => d != "createdBy");
      this.fkey = this.fkey.filter(d => d != "updatedBy");
      this.fkey = this.fkey.filter(d => d != "createdDate");
      this.fkey = this.fkey.filter(d => d != "updatedDate");
      this.fkey = this.fkey.filter(d => d != "bondStatus");
      this.fkey = this.fkey.filter(d => d != "sbuId");
      this.fkey = this.fkey.filter(d => d != "buId");
      this.fkey = this.fkey.filter(d => d != "costCenterId");
      this.fkey = this.fkey.filter(d => d != "id");
      this.fkey = this.changevalues(this.fkey)
      console.log("field names", this.fkey);
    })
  }



  getAllServices() {
    var ReportReqObj =

    {
      "services": [{


      }],
      "transactionType": "getAll"
    }

    this.hrms.getAllservices(ReportReqObj).subscribe(res => {
      console.log("response", res)
      this.service = res
      this.finalservices = this.service.servicesList
      console.log("final services", this.finalservices)
    })
  }

  selected_service(e) {
    this.fileName = e;
    this.pstatus = false
    this.uncheck = "";
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
      case "Kye":
        this.getKye();
        break;
      case "Experience":
        this.getExperience();
        break;
      case "Education":
        this.getEducation()
        break;
      case "Basic Info":
        this.getBasicInfo();
        break;
      case "Contact Info":
        this.getContactInfo();
        break;
      case "Certification":
        this.getCertifications();
        break;
      case "Dependents":
        this.getDependents();
        break;
      case "Project Details":
        this.getProjectDetails();
        break;
      case "Resignation":
        this.getResignation();
        break;
      case "Onboarding":
        this.getOnboarding();
        break;
      case "Skills":
        this.getSkills();
        break;
      case "Bank Info":
        this.getBankInfo();
        break;

    }

  }
  download_pdf() {
    var one = []
    var heading = []
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

    if (checkAll) {
      select.update.emit(values);
      this.selected_fields_fun();
    }
    else {
      select.update.emit([]);
      this.selected_fields_fun();
    }
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



