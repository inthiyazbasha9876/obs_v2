import { Component, OnInit } from '@angular/core';
import { PsaService } from '../../services/psa.service';
import { DictionaryService } from '../../services/dictionary.service';
import { FormBuilder, Validators } from '@angular/forms';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
declare const $: any;
import { RmgService } from './../../services/rmg.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { HrmsService } from '../../services/hrms.service';
 interface Tasks {
  value: string;
  viewValue: string;
}
 @Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.scss']
})


export class ScheduleComponent implements OnInit {
  
  taskStatus: Tasks[] = [
    {value: 'Open', viewValue: 'Open'},
    {value: 'In-Progress', viewValue: 'In-Progress'},
    {value: 'Completed', viewValue: 'Completed'}
  ];
  selectedValue;
  allProjects: any
  todayDate: any
  dates: any = []
  dateType: any = "week"
  createModal: any
  projectView;
  resourceView;
  reviewTxt: any = true;
  selectedPro: any;
  resourceList: any = []
  totalAmount: any = 0
  empall: any
  projects: any
  alldata: any
  resourceData: any = [];
  resourceTask:any = [];
  projectResource :any =false;
  allResouceValues : any ;
  projectcount:any =false;
  private resourceBilling: Subject<any> = new Subject();
  task: any=[];
  currentday;
  constructor(private psa: PsaService, private dictinoary: DictionaryService, private hrms: HrmsService, private fromBuilder: FormBuilder, private toast: NotificationService, private rmgService: RmgService) {
    this.todayDate = new Date()
    this.getWeekDates(this.todayDate)
   // this.getAllProjects();
    this.getResource();
    this.allProject();
    this.getallEmp();
  }

  milestoneFrom = this.fromBuilder.group({
    projectId: [null],
    mileStoneID: [null],
    mileStoneName: [null, Validators.required],
    mileStoneStartDate: [null],
    mileStoneEndDate: [null]
  })
  TaskForm = this.fromBuilder.group({
    taskName: [null, Validators.required],
    taskStartDate: [null],
    taskEndDate: [null],
    hoursPerDay:[null],
    taskStatus:[null],
    bookingId:[null],
    resourceType:[null],
    specificId:[null],
    taskId:[null]

  })
  ngOnInit() {
    
      
 
  }
  ngOnDestroy(): any {
    this.resourceBilling.next();
    this.resourceBilling.complete();
  }
  getAllProjects() {
    this.projectView = true;
    this.resourceView = false;
    sessionStorage.removeItem('projectId');
    var response
    var reqObj = {
      "projectInfo": {
      },
      "transactionType": "getall"
    }
    this.psa.getAllProject(reqObj).subscribe(res => {
      response = res
      console.log("all Projects", response.projectList);
      this.allProjects = response.projectList;
    })
  }

  getResource() {
    this.resourceView = true;
    this.projectView = false;
    this.reviewTxt = true;
    this.getresources();
    this.selectedValue = null;
    this.projectResource =false;
  }
  //start get resource by project ID//
  getResourcesid(projectId) {
    let responses;
    this.reviewTxt = false;
    this.projectResource =true;
    this.projectcount =false;
    //console.log("projectId", projectId);
    sessionStorage.setItem('projectId',projectId);
    let objProject = {
      "rmgInfo":
        {
           "projectId":projectId
        },
     "transactiontype":"getbyprojectid"
  }

    this.rmgService.getEmpByProjectId(objProject).subscribe(res => {
      responses = res;
      this.allResouceValues = responses.rmgInfo
      console.log(this.allResouceValues);
      if (this.allResouceValues.length <=0){
        this.projectResource =false;
        this.projectcount=true;
      }

      
    });
  }


  getresources() {
    let response;
    this.reviewTxt = true;
    this.resourceData=[];
    this.currentday = new Date().toISOString().slice(0,10);
    sessionStorage.removeItem('projectId');
    let objProject = {
      "rmgInfo":
        {

        },
     "transactiontype":"getalltasks"
}

    this.rmgService.getEmpByProjectId(objProject).subscribe(res => {
      response = res;
      this.alldata = response.rmgSpecifictasks;
      console.log(this.alldata , 'project by resource');
      this.getallEmp();

    })
  }
  //end 
  allProject(){
  var response
    var reqObj = {
      "projectInfo": {
      },
      "transactionType": "getall"
    }
    this.psa.getAllProject(reqObj).subscribe(res => {
      response = res
      console.log("all Projects", response.projectList);
      this.allProjects = response.projectList;
    })
}

  getWeekDates(date) {
    this.dates = []
    var n = 1;
    while (n <= 7) {
      var from = new Date(date.getFullYear(), date.getMonth(), date.getDate() - (date.getDay() - n))
      this.dates.push(from)
      n++;
    }
    console.log("dates", this.dates)
  }

  getMonthDates(date) {
    var days = this.getdays(date.getMonth() + 1, date.getFullYear())
    this.dates = []
    var n = 1;
    while (n <= days) {
      var from = new Date(date.getFullYear(), date.getMonth(), n)
      this.dates.push(from)
      n++;
    }
    console.log("dates", this.dates)
  }

  getdays(month, year) {
    return new Date(year, month, 0).getDate();
  }

  selectedDisplayType() {
    console.log(this.dateType);
    if (this.dateType == 'week') {
      this.getWeekDates(this.todayDate)
    } else if (this.dateType == 'month') {
      this.getMonthDates(this.todayDate)
    }
  }


  //all employees

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
      this.empall  = response.employeeInfo;
      // this.empall = data.map(d => new Object({ name: d.firstname + " " + d.lastname, empId: d.employeeId }))
      // console.log("all employees", this.empall);
    })
  }

  getEmpName(empId){
    // if(empId==undefined){
      
    //   return empId
    // }
    // else{
    //   let name = this.empall.find(data => data.employeeId == empId);
    //   return name.firstname + ' ' + name.lastname
    // }
     
  }

  previous() {
    var oldDate = this.dates[0]
    if (this.dateType == 'week') {
      this.todayDate = new Date(oldDate.getFullYear(), oldDate.getMonth(), oldDate.getDate() - 7)
      this.getWeekDates(this.todayDate)
    } else if (this.dateType == 'month') {
      if (oldDate.getMonth() == 0) {
        this.todayDate = new Date(oldDate.getFullYear() - 1, 11, 1)
        this.getMonthDates(this.todayDate)
      } else { 
        this.todayDate = new Date(oldDate.getFullYear(), oldDate.getMonth() - 1, 1)
        this.getMonthDates(this.todayDate)
      }
    }

  }

  next() {
    var oldDate = this.dates[this.dates.length - 1]
    if (this.dateType == 'week') {
      this.todayDate = new Date(oldDate.getFullYear(), oldDate.getMonth(), oldDate.getDate() + 1)
      this.getWeekDates(this.todayDate)
    } else if (this.dateType == 'month') {
      if (oldDate.getMonth() == 0) {
        this.todayDate = new Date(oldDate.getFullYear() + 1, 0, 1)
        this.getMonthDates(this.todayDate)
      } else {
        this.todayDate = new Date(oldDate.getFullYear(), oldDate.getMonth() + 1, 1)
        this.getMonthDates(this.todayDate)
      }
    }
  }

  display(p, d) {
    var arrayDates = this.getBetweenDates(new Date(p.startDate), new Date(p.endDate))
    for (let k in arrayDates) {
      if (this.dictinoary.formatDate(d) == this.dictinoary.formatDate(arrayDates[k])) {
        return true
      }
    }
  }
  displaytask(p, d) {
    var arrayDates = this.getBetweenDates(new Date(p.taskStartDate), new Date(p.taskEndDate))
    for (let k in arrayDates) {
      if (this.dictinoary.formatDate(d) == this.dictinoary.formatDate(arrayDates[k])) {
        return true
      }
    }
  }

  getBetweenDates(a, b) {
    let _MS_PER_DAY = 1000 * 60 * 60 * 24;
    let datesArray = []
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
    var ndays = Math.floor((utc2 - utc1) / _MS_PER_DAY) + 1
    var n = 0;
    while (n < ndays) {
      var from = new Date(a.getFullYear(), a.getMonth(), a.getDate() + n)
      datesArray.push(from)
      n++;
    }
    return datesArray
  }

  create(e, p) {
    this.createModal = true
    this.milestoneFrom.reset()
    this.milestoneFrom.controls.projectId.setValue(p.projectId)
    this.milestoneFrom.controls.mileStoneStartDate.setValue(e)
    this.milestoneFrom.controls.mileStoneEndDate.setValue(e)
  }

  edit(e, d, p) {
    this.createModal = false
    this.milestoneFrom.controls.projectId.setValue(p.projectId)
    this.milestoneFrom.controls.mileStoneID.setValue(e.milestoneId)
    this.milestoneFrom.controls.mileStoneName.setValue(e.milestoneName)
    this.milestoneFrom.controls.mileStoneStartDate.setValue(e.startDate)
    this.milestoneFrom.controls.mileStoneEndDate.setValue(e.endDate)
    $('#milestonemodal').modal('show');
    d.stopPropagation()
  }

  createMileStone(milestoneFrom) {
    var response
    var milestonearray = this.allProjects.find(d => d.projectId == milestoneFrom.projectId)
    var newObj = {
      "milestoneName": milestoneFrom.mileStoneName,
      "startDate": this.dictinoary.formatDate(new Date(milestoneFrom.mileStoneStartDate)),
      "endDate": this.dictinoary.formatDate(new Date(milestoneFrom.mileStoneEndDate))
    }
    var finalArray = [...milestonearray.milestones, newObj]
    let reqObj = {
      "projectInfo": {
        "projectId": milestoneFrom.projectId
      },
      "milestones": finalArray,
      "transactionType": "addMilestone"
    }
    this.psa.saveProject(reqObj).subscribe(res => {
      response = res
      this.toast.success(response.message)
      this.getAllProjects()
      $('#milestonemodal').modal('hide');
    }, err => {
      response = err
      this.toast.error(response.error.message)
      this.getAllProjects()
    })

  }

  updateMileStone(milestoneFrom) {
    var response
    var reqObj = {
      "projectInfo": {
        "projectId": milestoneFrom.projectId
      },
      "milestones": [
        {
          "milestoneId": milestoneFrom.mileStoneID,
          "milestoneName": milestoneFrom.mileStoneName,
          "startDate": this.dictinoary.formatDate(new Date(milestoneFrom.mileStoneStartDate)),
          "endDate": this.dictinoary.formatDate(new Date(milestoneFrom.mileStoneEndDate))
        }],
      "transactionType": "updateMilestone"
    }
    this.psa.saveProject(reqObj).subscribe(res => {
      response = res
      this.toast.success(response.message)
      this.getAllProjects()
      $('#milestonemodal').modal('hide');
    }, err => {
      response = err
      this.toast.error(response.error.message)
      this.getAllProjects()
      $('#milestonemodal').modal('hide');
    })
  }

  //new task//

  createtask(e, p) {
    this.createModal = true
    this.TaskForm.reset();
     this.TaskForm.controls.bookingId.setValue(p.rmg[0].bookingId)
     this.TaskForm.controls.resourceType.setValue(p.rmg[0].resourceType)
     this.TaskForm.controls.specificId.setValue(p.specificId)
    this.TaskForm.controls.taskStartDate.setValue(e)
    this.TaskForm.controls.taskEndDate.setValue(e)

  }

  projectcreatetask(e, p) {
    this.createModal = true
    this.TaskForm.reset();
     this.TaskForm.controls.bookingId.setValue(p.bookingId)
     this.TaskForm.controls.resourceType.setValue(p.resourceType)
     this.TaskForm.controls.specificId.setValue(p.rmgspecific[0].specificId)
    this.TaskForm.controls.taskStartDate.setValue(e)
    this.TaskForm.controls.taskEndDate.setValue(e)

  }

  createNewTask(TaskForm) {
    var response
    var newObj = [{
      "taskName": TaskForm.taskName,
      "taskStartDate": this.dictinoary.formatDate(new Date(TaskForm.taskStartDate)),
      "taskEndDate": this.dictinoary.formatDate(new Date(TaskForm.taskEndDate)),
      "hoursPerDay": TaskForm.hoursPerDay,
      "taskStatus" :TaskForm.taskStatus
    }]
    let reqObj = {
      "rmgInfo" :{
        "bookingId":TaskForm.bookingId,
        "resourceType": TaskForm.resourceType
    },
    "rmgSpecificList": [{
      "specificId":TaskForm.specificId,
 
      "projectTasks":newObj
    }],
    "transactiontype": "savetasks"
  }

    this.rmgService.saveresources(reqObj).subscribe(res => {
      response = res
      this.toast.success(response.message)
      let projectId = sessionStorage.getItem('projectId')
      if(Number(projectId)>=1){
        this.getResourcesid(projectId);
      }else{
        this.getresources();
       }
      //this.getresources()
      $('#taskmodal').modal('hide');
    }, err => {
      response = err
      this.toast.error(response.error.message)
      let projectId =sessionStorage.getItem('projectId')
      if(Number(projectId)>=1){
        this.getResourcesid(projectId);
      }else{
        this.getresources();
       }
    })

  }

  //end new task//



  //update Task

  //new task//

  updatetask(e, p,d) {
    this.createModal = false
     this.TaskForm.controls.bookingId.setValue(d.rmg[0].bookingId)
     this.TaskForm.controls.resourceType.setValue(d.rmg[0].resourceType)
     this.TaskForm.controls.specificId.setValue(d.specificId)
    this.TaskForm.controls.taskStartDate.setValue(e.taskStartDate)
    this.TaskForm.controls.taskEndDate.setValue(e.taskEndDate)
    this.TaskForm.controls.taskId.setValue(e.taskId)
    this.TaskForm.controls.taskName.setValue(e.taskName)
    this.TaskForm.controls.hoursPerDay.setValue(e.hoursPerDay)
    this.TaskForm.controls.taskStatus.setValue(e.taskStatus)
    $('#taskmodal').modal('show');
    p.stopPropagation()

  }

  projectupdatetask(e, p,d) {
    this.createModal = false
     this.TaskForm.controls.bookingId.setValue(d.bookingId)
     this.TaskForm.controls.resourceType.setValue(d.resourceType)
     this.TaskForm.controls.specificId.setValue(d.rmgspecific[0].specificId)
    this.TaskForm.controls.taskStartDate.setValue(e.taskStartDate)
    this.TaskForm.controls.taskEndDate.setValue(e.taskEndDate)
    this.TaskForm.controls.taskId.setValue(e.taskId)
    this.TaskForm.controls.taskName.setValue(e.taskName)
    this.TaskForm.controls.hoursPerDay.setValue(e.hoursPerDay)
    this.TaskForm.controls.taskStatus.setValue(e.taskStatus)
    $('#taskmodal').modal('show');
    p.stopPropagation()

  }

  UpdateNewTask(TaskForm) {
    var response
    var newObj = [{
      "taskId": TaskForm.taskId,
      "taskName": TaskForm.taskName,
      "taskStartDate": this.dictinoary.formatDate(new Date(TaskForm.taskStartDate)),
      "taskEndDate": this.dictinoary.formatDate(new Date(TaskForm.taskEndDate)),
      "hoursPerDay": TaskForm.hoursPerDay,
      "taskStatus" :TaskForm.taskStatus
    }]
    let reqObj = {
      "rmgInfo" :{
        "bookingId":TaskForm.bookingId,
        "resourceType": TaskForm.resourceType
    },
    "rmgSpecificList": [{
      "specificId":TaskForm.specificId,
 
      "projectTasks":newObj
    }],
    "transactiontype": "updatetasks"
  }

    this.rmgService.saveresources(reqObj).subscribe(res => {
      response = res
      this.toast.success(response.message)
      let projectId =sessionStorage.getItem('projectId')
      if(Number(projectId)>=1){
        this.getResourcesid(projectId);
      }else{
        this.getresources();
       }
      $('#taskmodal').modal('hide');
    }, err => {
      response = err
      this.toast.error(response.error.message)
      let projectId =sessionStorage.getItem('projectId')
      if(Number(projectId)>=1){
        this.getResourcesid(projectId);
      }else{
        this.getresources();
      }
    })

  }


  //end update Task
}
