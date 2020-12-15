import { PsaService } from './../../services/psa.service';
import { Component, OnInit, HostListener } from '@angular/core';
import { takeUntil } from 'rxjs/operators';
import { HrmsService } from '../../services/hrms.service';
import { RmgService } from '../../services/rmg.service';
import { Subject } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-peopleinpsa',
  templateUrl: './peopleinpsa.component.html',
  styleUrls: ['./peopleinpsa.component.scss']
})
export class PeopleinpsaComponent implements OnInit {

  employeeSubscription: Subject<any> = new Subject();
  private projectAll: Subject<any> = new Subject();
  empbasicinfo: any;
  pic1: any = [];
  empbasic: any;
  empbasicin: any;
  role: any;
  manager: unknown[];
  employee: unknown[];
  managerdata: any[];
  eid: any;
  empShortList: any;
  pages: any = 5
  orderid: any
  ordername: any;

  resourceAllocation = this.formBuild.group({
    projectId: [null],
    resourceStartDate: [null],
    resourceEndDate: [null],
    billRate: [null],
    numberOfHours: [null]
  })
  availabilityArray: any = [];
  hoursCheck: any;
  dontAllowResource: boolean =true;
  dataTo: any;
  getIdData: any;
  resourceArray: any = [];
  projectStartDate: any;
  projectEndDate: any;
  showFileds: boolean;
  checkHours: number;
  empDetails: any;
  saveSuccess:any;

  constructor(private hrms: HrmsService, private rmgService: RmgService, private formBuild: FormBuilder, private psaService: PsaService, private toast: NotificationService) {
    this.role = sessionStorage.getItem('Role');
    this.eid = sessionStorage.getItem('UserName');
  }

  ngOnInit() {
    this.getempdata();
    this.getAllProject();
  }

  ngOnDestroy(): any {
    this.projectAll.next();
    this.projectAll.complete();
  }

  loading = true;


  getempdata() {

    let empbasic
    let empinfo =
    {
      "employeeInfo": [{

      }],
      "transactionType": "getall"
    }

    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.projectAll)).subscribe(res => {
      empbasic = res;
      this.empbasicin = empbasic.employeeInfo;
      console.log(this.empbasicin, "Bench");

      this.loading = false;
      this.empbasicinfo = this.empbasicin;
      console.log("all info", this.empbasicinfo);
      this.empShortList = this.empbasicinfo
      //  this.empShortList= this.empbasicinfo.filter(status=>status.employmentStatus=="Bench" || status.employmentStatus=="Lateral");

      for (var j = 0; j < this.empbasicinfo.length; j++) {

        if (this.empbasicinfo[j].image != null) {
          this.pic1.push(true);
        } else {
          this.pic1.push(false);
        }
      }

    })

  }

  getNames(name) {
    
    let empinfodata = this.empbasicin.filter(names => names.employeeId == name);
    let empData = empinfodata[0].firstname + ' ' + empinfodata[0].lastname
    return empData
  }

  getprojectName(id) {


    let data = this.dataTo.filter(projectid => projectid.projectId == id)

    return data[0].projectName;
  }


  showEmpDetails(empId) {
    this.availabilityArray = [];
    this.resourceAllocation.reset();
    this.resourceArray = [];
    this.showFileds = false;
    this.empDetails = empId;

    console.log("selected", empId.employeeId);
    let response;
    let hoursToCheck = 0;
    let toCheckObj = {
      "projectlist":
      {
        "empId": empId.employeeId
      },
      "transactiontype": "getprojects"
    }

    this.rmgService.getEmpAvailability(toCheckObj).subscribe(res => {
      response = res;
      this.availabilityArray = response.projectlist;

      // if(this.availabilityArray.length == 0){

      // }else{
        console.log("response of availability", response);
        this.hoursCheck = this.availabilityArray;
        this.hoursCheck.forEach(element => {
          if (element.hoursOfAllocation != null) {
            hoursToCheck += element.hoursOfAllocation
  
          }
  
        });
        console.log("element", hoursToCheck);
        this.checkHours = hoursToCheck
  
        if (hoursToCheck > 16 || this.availabilityArray == null) {
          this.dontAllowResource = false;
        }
        else {
          this.dontAllowResource = true;
  
        }
      // }
     

    })


  }





  getAllProject() {
    let projectData;
    let getProject = {
      "projectInfo": {
      },
      "transactionType": "getall"
    }
    this.psaService.getAllProject(getProject).pipe(takeUntil(this.projectAll)).subscribe(response => {
      projectData = response;
      this.dataTo = projectData.projectList;
      console.log("data in project", this.dataTo);
    })
  }

  selectedProject(id) {

    let getId;
    let getID = {
      "projectInfo": {
        "projectId": id
      },
      "transactionType": "getByProId"
    }

    this.psaService.getIdProject(getID).pipe(takeUntil(this.projectAll)).subscribe(response => {
      getId = response;
      this.getIdData = getId.projectList[0];
      this.projectStartDate = this.getIdData.startDate;
      this.projectEndDate = this.getIdData.endDate;
      console.log("Id", this.getIdData);

      this.showFileds = true;

    })
  }

  resourceMapping() {
    let resourceObj = {
      "projectId": this.resourceAllocation.value.projectId,
      "startDate": this.resourceAllocation.value.resourceStartDate,
      "endDate": this.resourceAllocation.value.resourceEndDate,
      "billRate": this.resourceAllocation.value.billRate,
      "numberOfHours": this.resourceAllocation.value.numberOfHours

    }

    this.resourceArray.push(resourceObj);
    console.log("obj resource", this.resourceArray);

    this.resourceAllocation.get('projectId').reset();
    this.resourceAllocation.get('resourceStartDate').reset();
    this.resourceAllocation.get('resourceEndDate').reset();
    this.resourceAllocation.get('billRate').reset();
    this.resourceAllocation.get('numberOfHours').reset();
    this.showFileds = false;
  }

  cancelResource(index) {
    this.resourceArray.splice(index, 1);
  }

  formatDate(date) {

    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }

  allocateToProject(){
    console.log("resource array", this.resourceArray);


    let array = []
      let status;
      if (this.getIdData.projectType == "Enablement" || this.getIdData.projectType == "Internal Product") {
        status = 'Deployed-IDC';
      }
      else if (this.getIdData.projectType == "Customer" && this.getIdData.locationType == "EDC") {
        status = 'Deployed-EDC';
      }
      else if (this.getIdData.projectType == "Customer" && this.getIdData.locationType == "CDC") {
        status = 'Deployed-CDC';
      }
      else {
        status = 'Deployed-' + this.getIdData.locationType;
      }
     
      for (let i in this.resourceArray) {
        let startDate
        let endDate
        if (this.resourceArray[i].startDate != null) {
          startDate = this.formatDate(new Date(this.resourceArray[i].startDate))
        } else {
          startDate = null
        }
        if (this.resourceArray[i].endDate != null) {
          endDate = this.formatDate(new Date(this.resourceArray[i].endDate))
        } else {
          endDate = null
        }
        var obj = {
          "specificEmployeeStatus": status,

          "reason": "",
          "startDate": startDate,
          "endDate": endDate,
          "billRate": Number(this.resourceArray[i].billRate),
          "employeeId": this.empDetails.employeeId,
          "hoursOfAllocation": this.resourceArray[i].numberOfHours,
          "alternateemployeeId": "",
          "flag": 1

        }

        array.push(obj)
      }
      console.log("array obj", array);
      if (array.length != 0) {
        let reqObj = {
          "rmgInfo": {


            "resourceType": "Specific",
            "projectId": this.getIdData.projectId,
            "status": "pending",
            "flag": 1,
            "message": "booked"
          },

          "rmgSpecificList": array,


          "transactiontype": "save"
        }
        console.log("resource", reqObj);
        this.rmgService.saveresources(reqObj).subscribe(res => {
          console.log("my response", res);
          this.getAllProject();
          this.saveSuccess = Response;
          this.toast.success(this.saveSuccess.message, 'Success')
          this.resourceAllocation.reset();

        }, err => {
          console.log(err);
          var err = err;
          this.getAllProject();
          this.toast.error(err.error.message)
        })
      }












    
  }








  //scroll up


  isShow: boolean;
  topPosToStartShowing = 300;

  @HostListener('window:scroll')
  checkScroll() {

    // window의 scroll top
    // Both window.pageYOffset and document.documentElement.scrollTop returns the same result in all the cases. window.pageYOffset is not supported below IE 9.

    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;

    console.log('[scroll]', scrollPosition);

    if (scrollPosition >= this.topPosToStartShowing) {
      this.isShow = true;
    } else {
      this.isShow = false;
    }
  }

  // TODO: Cross browsing
  gotoTop() {
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth'
    });
  }
  sortid() {
    if (this.orderid == null) {
      this.empShortList.sort((a, b) => (a.employeeId > b.employeeId) ? 1 : -1)
      this.orderid = 0
    }
    else if (this.orderid == 0) {
      this.empShortList.sort((a, b) => (a.employeeId < b.employeeId) ? 1 : -1)
      this.orderid = 1
    } else {
      this.empShortList.sort((a, b) => (a.employeeId > b.employeeId) ? 1 : -1)
      this.orderid = 0
    }

  }

  sortname() {
    if (this.ordername == null) {
      this.empShortList.sort((a, b) => (a.firstname > b.firstname) ? 1 : -1)
      this.ordername = 0
    }
    else if (this.ordername == 0) {
      this.empShortList.sort((a, b) => (a.firstname < b.firstname) ? 1 : -1)
      this.ordername = 1
    }
    else {
      this.empShortList.sort((a, b) => (a.firstname > b.firstname) ? 1 : -1)
      this.ordername = 0
    }
  }
}
