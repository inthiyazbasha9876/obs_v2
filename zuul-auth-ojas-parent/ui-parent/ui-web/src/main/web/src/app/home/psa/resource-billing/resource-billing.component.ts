import { RmgService } from './../../services/rmg.service';
import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { PsaService } from '../../services/psa.service';
import { HrmsService } from '../../services/hrms.service';
import { takeUntil } from 'rxjs/operators';
import { TimesheetService } from '../../timesheetmanagement/tmsservices/timesheet.service';
import { DictionaryService } from '../../services/dictionary.service';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import html2canvas from 'html2canvas';
@Component({
  selector: 'app-resource-billing',
  templateUrl: './resource-billing.component.html',
  styleUrls: ['./resource-billing.component.scss']
})
export class ResourceBillingComponent implements OnInit {
  private resourceBilling: Subject<any> = new Subject();
  projects: any
  recordsList: any = []
  empall: any
  date:any
  firstday: any
  lastday: any
  days: any
  fromDate: any
  toDate: any
  selectedPro:any


  //list resource
  resourceList: any = []
  totalAmount: any = 0

  //bollean declarations
  resSelection: any = true
  reviewTxt: any = true
  resTable: any = false
  billTable: any = false
  recordsTable: any = false
  reports: any = false
  tabletext: any = false

  //resource details
  employeeName: any
  employeeId: any
  billRate: any
  resourceTotalAmount: any
  projectName: any
  projectId: any

  //modal values
  projectDetials: any
  customerDetials: any
  contractDetails: any


  constructor(private psaService: PsaService, private hrms: HrmsService, private tms: TimesheetService, private dictionary: DictionaryService, private rmgService : RmgService) {
    this.date = new Date();
    this.firstday = new Date(this.date.getFullYear(), this.date.getMonth(), 1);
    this.lastday = new Date(this.date.getFullYear(), this.date.getMonth() + 1, 0);
    this.days = this.getdays(this.date.getMonth() + 1, this.date.getFullYear())
    console.log("days", this.days, this.firstday, this.lastday);
    this.getAllProject()
    this.getallEmp()
  }

  ngOnInit() {
  }


  ngOnDestroy(): any {
    this.resourceBilling.next();
    this.resourceBilling.complete();
  }

  getAllProject() {
    let projectData;
    var getProject = {
      "projectInfo": {
      },
      "transactionType": "getall"
    }
    this.psaService.getAllProject(getProject).pipe(takeUntil(this.resourceBilling)).subscribe(response => {
      projectData = response;
      this.projects = projectData.projectList
      console.log("contract data in project", this.projects);
    });
  }

  getResource(e) {
    this.selectedPro=e
    this.totalAmount = 0
    this.resourceList = []
    console.log(e);
    var response
    var reqObj = {
      "employeeprojects":
      {
        "projectId": e.value
      },
      "transactiontype": "getresourcebyproject"
    }
    this.rmgService.getEmpByProjectId(reqObj).pipe(takeUntil(this.resourceBilling)).subscribe(res => {
      console.log(res)
      response = res
      let data = response.employeeprojects;
      console.log("in employees",response);

      for (let i in data) {
        
        let pName = this.projects.find(p => p.projectId == data[i].projectId);
        let resourseSpecific = this.empall.filter(data1 => data1.employeeId == data[i].employeeId)
        console.log("array of resources", resourseSpecific);

        let resourceObj = {
          'employeeId': data[i].employeeId,
          'employeeName': resourseSpecific[0].firstname + ' ' + resourseSpecific[0].lastname,
          'projectId': data[i].projectId,
          'projectName': pName.projectName,
          'hours': data[i].hoursOfAllocation,
          'billRate': data[i].billRate,
        }

        

        this.resourceList.push(resourceObj);
        // var sepResource = data[i].rmgspecific
        // var genResource = data[i].rmggeneric
        // if (sepResource.length > 0) {
        //   for (let j in sepResource) {
        //     var resourceObj = {
        //       'employeeId': '',
        //       'employeeName': '',
        //       'projectId': '',
        //       'projectName': '',
        //       'hours': '',
        //       'billRate': '',
        //     }
        //     console.log("hello", sepResource[j]);
        //     var eName = this.empall.find(e => e.empId == sepResource[j].employeeId)
        //     resourceObj.projectId = data[i].projectId
        //     resourceObj.projectName = pName.projectName
        //     resourceObj.employeeName = eName.name
        //     resourceObj.employeeId = sepResource[j].employeeId
        //     resourceObj.billRate = sepResource[j].billRate
        //     this.resourceList.push(resourceObj)
        //   }
        // }
        // if (genResource.length > 0) {
        //   for (let j in genResource) {
        //     console.log("gen", genResource[j]);
        //     var resource = genResource[j].rmggenericresourcemap
        //     for (let k in resource) {
        //       var resourceObj = {
        //         'employeeId': '',
        //         'employeeName': '',
        //         'projectId': '',
        //         'projectName': '',
        //         'hours': '',
        //         'billRate': '',
        //       }
        //       console.log("one", resource[k]);
        //       var eName = this.empall.find(e => e.empId == resource[k].empId)
        //       resourceObj.projectId = data[i].projectId
        //       resourceObj.projectName = pName.projectName
        //       resourceObj.billRate = genResource[j].billRate
        //       resourceObj.employeeName = eName.name
        //       resourceObj.employeeId = resource[k].empId
        //       this.resourceList.push(resourceObj)
        //     }
        //   }
        // }
      }
      console.log(this.resourceList, "all resources");

      var tmsReqObj = {
        "sheet": {
          "projectId": e.value,
          "startDate": this.dictionary.formatDate(this.firstday),
          "endDate": this.dictionary.formatDate(this.lastday)
        },
        "transactionType": "getHours"
      }
      console.log("tms req obj ", tmsReqObj);
      var tmsResponse
      this.tms.getTmsData(tmsReqObj).pipe(takeUntil(this.resourceBilling)).subscribe(res => {
        console.log("tms data ", res);
        tmsResponse = res
        var list = tmsResponse.recordList
        console.log("final list", list, this.resourceList);
        if (list == null) {
          this.resourceList.map(r => {
            r.hours = 0
          })
        } else {
          for (let i in list) {
              if (this.resourceList[i].employeeId == list[i].employeeId && this.resourceList[i].projectId == list[i].projectId) {  
                this.resourceList[i].hours = list[i].hours
              } else {
                this.resourceList[i].hours = 0
              }
          }
        }
        console.log("resourceList", this.resourceList);
        
        if (this.resourceList.length != 0) {
          this.reviewTxt = false
          this.resTable = true
          this.reports = true
          this.tabletext = false
        } else {
          this.reviewTxt = false
          this.resTable = false
          this.reports = false
          this.tabletext = true
        }
        for (let i in this.resourceList) {
          this.totalAmount = this.totalAmount + (this.resourceList[i].billRate) * this.resourceList[i].hours
        }

        var projectDt = this.projects.find(c => c.projectId == e.value)
        this.projectDetials = projectDt
        console.log("cid", projectDt.customerId);
        this.getCustomer(projectDt.customerId)
        this.getContract(projectDt.contractId)
      })
    })
  }

  viewResource(e) {
    console.log("paticular resource", e);
    this.resTable = false
    this.reviewTxt = false
    this.resSelection = false
    this.billTable = true
    this.employeeId = e.employeeId
    this.employeeName = e.employeeName
    this.billRate = e.billRate
    this.projectName = e.projectName
    this.projectId = e.projectId
    this.getRecord()
  }

  getDatesAmount() {
    console.log("from and to", this.fromDate, this.toDate);
    this.firstday = this.fromDate
    this.lastday = this.toDate
    this.getRecord()
  }

  getRecord() {
    this.recordsList = []
    this.resourceTotalAmount = 0
    var response
    var reqObj = {
      "sheet": {
        "projectId": this.projectId,
        "employeeId": this.employeeId,
        "startDate": this.dictionary.formatDate(this.firstday),
        "endDate": this.dictionary.formatDate(this.lastday)
      },
      "transactionType": "getByProject"
    }
    console.log("records req obj", reqObj);

    this.tms.getTmsData(reqObj).pipe(takeUntil(this.resourceBilling)).subscribe(res => {
      console.log("records list", res);
      response = res
      this.recordsList = response.recordList
      if (this.recordsList != null) {
        this.recordsTable = true
      } else {
        this.recordsTable = false
      }
      for (let i in this.recordsList) {
        this.resourceTotalAmount = this.resourceTotalAmount + (this.billRate) * this.recordsList[i].hoursLogged
      }
    })
  }

  close() {
    this.resTable = false
    this.reviewTxt = true
    this.resSelection = true
    this.billTable = false
    this.fromDate = null
    this.toDate = null
    this.reports = false
    this.tabletext = false
  }

  getallEmp() {
    var response
    var empinfo =
    {
      "employeeInfo": [{

      }],
      "transactionType": "getall"
    }

    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.resourceBilling)).subscribe(res => {
      response = res
      this.empall = response.employeeInfo
      // this.empall = data.map(d => new Object({ name: d.firstname + " " + d.lastname, empId: d.employeeId }))
      // console.log("all employees", data);
    })
  }

  amountCal(i, e) {
    console.log((i / 30) / 9, e, "logs");

  }

  getdays(month, year) {
    return new Date(year, month, 0).getDate();
  }

  getCustomer(e) {
    this.customerDetials = null
    var response
    var reqObj = {
      "customerList":
      {
        "customerId": e
      },

      "transactionType": "getbyid"
    }
    this.psaService.getIdCustomer(reqObj).subscribe(res => {
      console.log("customer", res);
      response = res
      this.customerDetials = response.customerList[0]
    })
  }

  getContract(e) {
    this.contractDetails = null
    var response
    var reqObj = {
      "customercontractdetailslist": [
        {

          "contractid": e

        }],
      "transactiontype": "getbyid"
    }
    this.psaService.getIdContract(reqObj).subscribe(res => {
      console.log("contract", res);
      response = res
      this.contractDetails = response.customercontractdetailslist[0]

    })
  }

  downloadPdf() {
    const doc = new jsPDF();
    var heading = ['Activity', 'Date', 'Day', 'Hours Logged', 'Amount']
    var res = doc.autoTableHtmlToJson(document.getElementById('tabelData'));
    doc.autoTable(heading, res.data);
    //doc.autoTable({ html: '#tabelData' });
    doc.save('billedAmount.pdf');
  }

  downloadReport() {
    var data = document.getElementById('reports');
    html2canvas(data).then(canvas => {
      var imgWidth = 208;
      var imgHeight = canvas.height * imgWidth / canvas.width;
      const contentDataURL = canvas.toDataURL('image/png')
      let pdf = new jsPDF('p', 'mm', 'a4');
      var position = 0;
      pdf.addImage(contentDataURL, 'PNG', 0, position, imgWidth, imgHeight)
      pdf.save('report.pdf');
    });
  }

  nextMonth(){
    console.log(this.date.getMonth());
    if(this.firstday.getMonth()>10){
      this.firstday = new Date(this.firstday.getFullYear()+1, 0, 1);
      this.lastday = new Date(this.firstday.getFullYear()+1, 0 + 1, 0);
      this.days = this.getdays(0 + 1, this.firstday.getFullYear()+1)
      this.getResource(this.selectedPro)
       console.log("new",this.firstday,this.lastday,this.days);   
    }else{
      this.firstday = new Date(this.firstday.getFullYear(), this.firstday.getMonth() + 1, 1);
      this.lastday = new Date(this.firstday.getFullYear(), this.firstday.getMonth() + 2, 0);
      this.days = this.getdays(this.firstday.getMonth() + 1, this.firstday.getFullYear())
      this.getResource(this.selectedPro)
      console.log("new",this.firstday,this.lastday,this.days);      
    }
  }

  previousMonth(){
    console.log(this.date.getMonth());
    if(this.firstday.getMonth()==0){
      this.firstday = new Date(this.firstday.getFullYear()-1, 11, 1);
      this.lastday = new Date(this.firstday.getFullYear()-1, 11 + 1, 0);
      this.days = this.getdays(11 + 1, this.firstday.getFullYear()-1)
      this.getResource(this.selectedPro)
       console.log("new",this.firstday,this.lastday,this.days);   
    }else{
      this.firstday = new Date(this.firstday.getFullYear(), this.firstday.getMonth() - 1, 1);
      this.lastday = new Date(this.firstday.getFullYear(), this.firstday.getMonth() - 2, 0);
      this.days = this.getdays(this.firstday.getMonth() - 1, this.firstday.getFullYear())
      this.getResource(this.selectedPro)
      console.log("new",this.firstday,this.lastday,this.days);      
    }
  }
}
