import { Component, OnInit, HostListener } from '@angular/core';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { FormBuilder, FormGroup, FormControl, FormArray, Validators } from '@angular/forms';
import { HrmsService } from '../services/hrms.service';
import { TimesheetService } from './tmsservices/timesheet.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import swal from 'src/assets/sweetalert';
import { PsaService } from '../services/psa.service';
import { LeaveserviceService } from '../lms/lmsservices/leaveservice.service';

@Component({
  selector: 'app-timesheetmanagement',
  templateUrl: './timesheetmanagement.component.html',
  styleUrls: ['./timesheetmanagement.component.scss']
})
export class TimesheetmanagementComponent implements OnInit {

  private unsubscribe = new Subject();
  encryptedUsername: string;
  encryptedRole: string;
  role1: any;
  constructor(private authService: AuthService, private week_form: FormBuilder, private hrms: HrmsService, private tms: TimesheetService, private route: Router, private toast: NotificationService, private lms: LeaveserviceService, private psa: PsaService) {
    this.encryptedRole = sessionStorage.getItem('Role');
    this.role1 = this.authService.decryptData(this.encryptedRole);
    this.getHolidays();
  }

  ngOnInit() {
    this.role()
    this.maxdate = new Date();
  }

  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

  //Form for timesheets
  TimeSheets = new FormGroup({
    timesheetId: new FormControl,
    statusId: new FormControl,
    comments: new FormControl,
    fileName: new FormControl,
    attachment: new FormControl,
    week_ts: new FormArray([
      new FormGroup({
        activity: new FormControl([Validators.required]),
        total: new FormControl,
        monday: new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'), Validators.max(24)]),
          projectId: new FormControl
        }),
        tuesday: new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
        wednesday: new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
        thursday: new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
        friday: new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
        saturday: new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
        sunday: new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
      })
    ])
  })


  //ngIf status

  navbar: any = false
  card: any = false
  hr: any = false
  user: any = false
  //project: any = false
  team: any = false
  timeSheetsStatus: any = false
  closebtn: any = false
  tmstable: any = false
  saveBtn: any = true
  submitBtn: any = true
  p_week: any = true
  n_week: any = true
  addBtn: any = true
  userbtn: any = false
  pending: any = false
  approved: any = false
  rejected: any = false
  approveReject_status = true
  managerbtn: any = false
  read: any = false
  teamview: any = false
  loader: any = false
  hideBtn: any = false
  //declarations
  _MS_PER_DAY = 1000 * 60 * 60 * 24;
  login: any
  eid: any
  eName: any
  searchdata: any
  r_name: any
  r_manager: any
  empJoiningDate: any
  maxdate: any
  reqEmp: any
  teamdata: any
  dates: any = []
  len: any
  activities: any
  selectedActivitycount = 0
  calender_date: any
  data: any
  tsrecords: any
  d: any
  filename: any
  filedata: any
  holidays: any = []
  acitvitiesList: any
  appliedLeaves: any
  approveddates = []
  rejecteddates = []
  pendingdates = []
  projectName:any = []
  projectId :any= []
  projectStartdate:any=[]
  projectEnddate:any=[]
  p_manager = []
  viewCount: any = 0;
  roleuser: any = 0
  btnName = "Get TimeSheets List"
  managersList:any
  pages:any=5
  orderid:any
  ordername:any
  pd:any=1
  //days total calculation

  mon: any = 0;
  tue: any = 0;
  wed: any = 0;
  thu: any = 0;
  fri: any = 0;
  sat: any = 0;
  sun: any = 0;
  total: any = 0;

  //holiday color
  mh: any = null
  th: any = null
  wh: any = null
  thh: any = null
  fh: any = null
  //back to top

  isShow: boolean;
  topPosToStartShowing = 100;

  role() {
    let role = this.role1
    this.login = this.authService.decryptData(sessionStorage.getItem('UserName'));
    this.getAllEmployee()
    // if (role == "ROLE_USER") {
    //   this.userRole()
    // }
    // else if (role == "ROLE_MANAGER") {
    //   this.navbar = true
    //   this.mysheet()
    // } else {
    //   this.userRole()
    // }
  }

  userRole() {
    this.viewCount = 0
    this.roleuser = 1
    this.reqEmp = this.login
    this.card = true
    this.user = true
    //this.project = true
    this.hideBtn = true
    this.tmstable = true
    this.userbtn = true
    this.teamview = true
    this.managerbtn = false
    this.timeSheetsStatus = false
    this.getempinfo()
    this.getEmpOnBoarding()
    this.getproject()
    this.getempleaves()
    this.get_Timesheetdates()
    this.getTimesheetsdetails()
  }

  mysheet() {
    this.viewCount = 0
    this.roleuser = 0
    this.reqEmp = this.login
    this.getempinfo()
    this.getproject()
    this.getempleaves()
    this.card = true
    this.user = true
    this.team = false
    //this.project = false
    this.hideBtn = true
    this.timeSheetsStatus = false
    this.closebtn = false
    this.tmstable = true
    this.userbtn = true
    this.managerbtn = false
    this.teamview = true
    this.teamdata = null
    this.pendingdates = []
    this.get_Timesheetdates()
    this.getTimesheetsdetails()
    this.getEmpOnBoarding()

  }


  teamsheet() {
    this.viewCount = 0
    this.teamdata = null
    this.reqEmp = this.login
    this.getemployeeList(this.reqEmp)
    this.card = false
    this.team = true
    this.timeSheetsStatus = false
    this.tmstable = false
    this.teamview = false
    this.pendingdates = []
  }

  getteamsheet(e) {
    console.log("data", e)
    this.reqEmp = e
    this.getempinfo()
    this.getproject()
    this.getEmpOnBoarding()
    this.team = false
    this.card = true
    this.user = true
    this.hideBtn = false
    this.timeSheetsStatus = true
    this.closebtn = true
    this.viewCount = 0
    this.getemptmsDates()
  }

  gettms(e) {
    this.viewCount = 1
    this.timeSheetsStatus = false
    this.tmstable = true
    if (this.roleuser == 0) {
      this.managerbtn = true
    } else {
      this.managerbtn = false
    }
    this.userbtn = false
    this.teamview = false
    this.getdates(e)
  }

  close() {
    this.teamsheet()
    this.viewCount = 0
    this.pendingdates = []
    this.approveddates = []
    this.rejecteddates = []
  }

  closetms() {
    this.timeSheetsStatus = true
    this.viewCount = 0
    this.closebtn = true
    this.tmstable = false
    this.getemptmsDates()
  }

  getsheetsList() {
    if (this.btnName == "Get TimeSheets List") {
      this.btnName = "Upload TimeSheets"
      this.closetms()
    }
    else if (this.btnName == "Upload TimeSheets") {
      this.btnName = "Get TimeSheets List"
      this.userRole()
    }
  }

  selectedipp(){
    this.pd=1
  }

  getempinfo() {
    var empinfo =
    {

      "employeeInfo": [{
        "employeeId": this.reqEmp
      }],
      "transactionType": "getbyid"
    }
    let emp_info
    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      emp_info = res
      console.log(emp_info, "basic info");

      let emp_data = emp_info.employeeInfo
      this.eid = emp_data[0].employeeId
      this.eName = emp_data[0].firstname + " " + emp_data[0].lastname
      this.r_manager = emp_data[0].reportingManager
      this.getreportingmanager(this.r_manager)

    })
  }


  getreportingmanager(e) {
    var obj = {
      "employeeInfo": [{
        "employeeId": e
      }],
      "transactionType": "getbyid"
    }
    console.log(obj, "req");

    let data
    this.hrms.getempinfo(obj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      data = res
      console.log(data, "hello")
      this.r_name = data.employeeInfo[0].firstname + " " + data.employeeInfo[0].lastname
      console.log("one", this.r_name)
    })
  }


  getEmpOnBoarding() {
    var requestobj =
    {
      "employmentDetails": [{
        "employeeId": this.reqEmp
      }],
      "transactionType": "getAll"
    }
    let empOnboarding
    this.hrms.getonboardingdetails(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      console.log("onboarding details", res)
      empOnboarding = res
      this.empJoiningDate = empOnboarding.employmentDetailsList[0].joiningDate
      for (let i in this.dates) {
        if (this.empJoiningDate == this.formatDate(this.dates[i])) {
          this.p_week = false
        }
      }
      console.log("Employee Joining Date", this.empJoiningDate)
    })
  }

  getemployeeList(e) {
    this.loader = true
    console.log("in get", e)
    var requestobj = {
      "employeeInfo": [{
        "reportingManager": e
      }],
      "transactionType": "getreporties"
    }
    let data
    this.tms.getemployees(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      data = res
      this.teamdata = data.employeeInfo
      this.orderid=0
      this.loader = false
      console.log("teamdata", this.teamdata)
    })
  }

  get_Timesheetdates() {
    this.mh = null
    this.th = null
    this.wh = null
    this.thh = null
    this.fh = null
    this.dates = []
    var date = new Date();
    var n = 1;
    while (n <= 7) {
      var from = new Date(date.getFullYear(), date.getMonth(), date.getDate() - (date.getDay() - n))
      this.dates.push(from)
      n++;
    }
    console.log("dates", this.dates)
    this.len = this.dates.length
    this.n_week = false
    //this.timesheet_details_status = false;
  }

  //get calender date
  getcalender_dates() {
    this.mh = null
    this.th = null
    this.wh = null
    this.thh = null
    this.fh = null
    this.dates = []
    this.n_week = true
    this.p_week = true
    var currentDate = new Date();
    console.log("input date", this.calender_date);
    var date = new Date(this.calender_date)
    console.log("calender date", date);
    var n = 1;
    while (n <= 7) {
      var from = new Date(date.getFullYear(), date.getMonth(), date.getDate() - (date.getDay() - n))
      this.dates.push(from)

      n++;
    }
    console.log("dates", this.dates)
    this.len = this.dates.length
    for (let i in this.dates) {
      if (this.empJoiningDate == this.formatDate(this.dates[i])) {
        this.p_week = false
        this.n_week = true
      }
      if (this.formatDate(currentDate) == this.formatDate(this.dates[i])) {
        this.n_week = false
        this.p_week = true
      }
    }

    this.clearTimeSheets();
    this.getTimesheetsdetails();
  }

  // previous week_timesheets
  previous_week() {
    this.mh = null
    this.th = null
    this.wh = null
    this.thh = null
    this.fh = null
    this.calender_date = null
    this.n_week = true
    var today = this.dates[0].getDate() - 7
    var month = this.dates[0].getMonth();
    var year = this.dates[0].getFullYear();
    var day = this.dates[0].getDay()
    this.dates = []
    var n = 1;
    while (n <= 7) {
      var from = new Date(year, month, today - (day - n))
      this.dates.push(from)
      n++;
    }
    console.log("dates", this.dates)
    this.len = this.dates.length
    for (let i in this.dates) {
      if (this.empJoiningDate == this.formatDate(this.dates[i])) {
        this.p_week = false
      }
    }
    //this.timesheet_details_status = false;
    this.clearTimeSheets();
    this.getTimesheetsdetails();

  }

  next_week() {
    this.mh = null
    this.th = null
    this.wh = null
    this.thh = null
    this.fh = null
    this.calender_date = null
    this.p_week = true
    var currentDate = new Date();
    var today = this.dates[0].getDate() + 7
    var month = this.dates[0].getMonth();
    var year = this.dates[0].getFullYear();
    var day = this.dates[0].getDay()
    this.dates = []
    var n = 1;
    while (n <= 7) {
      var from = new Date(year, month, today - (day - n))
      this.dates.push(from)
      n++;
    }
    console.log("dates", this.dates)
    this.len = this.dates.length
    for (let i in this.dates) {
      if (this.formatDate(currentDate) == this.formatDate(this.dates[i])) {
        this.n_week = false
      }
    }
    this.clearTimeSheets();
    this.getTimesheetsdetails();
  }

  //date convertion 
  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }

  //adding new activity 
  add_activity() {
    (this.TimeSheets.get('week_ts') as FormArray).push(this.week_form.group({
      activity: ['', Validators.required],
      total: new FormControl,
      monday: new FormGroup({
        recordId: new FormControl,
        loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'), Validators.max(24)]),
        projectId: new FormControl
      }),
      tuesday:
        new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
      wednesday:
        new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
      thursday:
        new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
      friday:
        new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
      saturday:
        new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
      sunday:
        new FormGroup({
          recordId: new FormControl,
          loggedHours: new FormControl(null, [Validators.pattern('^(\[0-9]*\.)?[0-9]{1,2}$'),Validators.max(24)]),
          projectId: new FormControl
        }),
    }))
    console.log("Add activity : ", this.TimeSheets);
    this.saveBtn = false
    this.submitBtn = true
    this.TimeSheets.controls.fileName.disable()
    this.getvalue()
  }

  //funtion for delete activity
  delete_activity(e,d) {
    console.log(d)
    if (e >= this.activities && d.length > 1) {
      console.log(e)
      swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover this activity!",
        buttons: [
          'No, cancel it!',
          'Yes, I am sure!'
        ],
        dangerMode: true,
      })
        .then((willDelete) => {
          if (willDelete) {
            (this.TimeSheets.get('week_ts') as FormArray).removeAt(e);
            this.calculate(this.TimeSheets);
            this.toast.success('Activity successfully deleted', 'Success')
            this.selectedActivitycount=0
            this.saveBtn=false
          }
        });
    } else {
      this.toast.info('You can not delete this actviity', 'Info')
    }

  }

  //clear timesheets function

  clearTimeSheets() {
    this.TimeSheets.reset()
    let array = <FormArray>this.TimeSheets.get('week_ts')
    while (array.length) {
      array.removeAt(array.length - 1);
    }
    this.activities = 0
    this.add_activity();
  }

  getTimesheetsdetails() {
    this.mon = 0
    this.tue = 0
    this.wed = 0
    this.thu = 0
    this.fri = 0
    this.sat = 0
    this.sun = 0
    this.total = 0

    this.clearTimeSheets()

    this.addBtn = true
    this.saveBtn = true
    this.submitBtn = true
    this.pending = false
    this.approved = false
    this.rejected = false
    this.read = false

    let error
    this.approveReject_status = true
    this.TimeSheets.controls.fileName.enable()

    console.log("getting dates", this.dates)
    this.activities = 0

    var requestobj = {
      "sheet":
      {
        "employeeId": this.reqEmp,
        "timeSheetStartDate": this.formatDate(this.dates[0])
      },
      "transactionType": "getByEmpId"
    }

    console.log("get request object", requestobj)
    this.tms.getTimesheets(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      this.holidayset(this.dates)
      this.getvalue()
      this.data = res
      console.log("get timesheets", this.data)
      this.d = this.TimeSheets.get('week_ts')
      for (let i in this.data.timesheetList) {
        this.tsrecords = this.data.timesheetList[i].record
        console.log("records", this.tsrecords)
        let set = new Set();
        for (let j in this.tsrecords) {
          set.add(this.tsrecords[j].activityType)
        }
        console.log("common activities", set)
        var activityarr = Array.from(set);
        var len = activityarr.length
        this.activities = len;
        var c = 0;
        var activityTotal
        for (let b in activityarr) {

          if (c == 0 && c < len) {
            activityTotal = 0
            for (let l in this.tsrecords) {

              for (let a in this.dates) {

                if ((this.formatDate(new Date(this.tsrecords[l].date))) == (this.formatDate(this.dates[a])) && this.tsrecords[l].activityType == activityarr[b]) {
                  var day = this.dates[a].getDay()
                  if (day == 1) {
                    this.d.controls[c].get('monday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.mon = this.mon + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('monday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('monday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);
                  }
                  else if (day == 2) {
                    this.d.controls[c].get('tuesday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.tue = this.tue + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('tuesday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('tuesday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);

                  }
                  else if (day == 3) {
                    this.d.controls[c].get('wednesday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.wed = this.wed + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('wednesday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('wednesday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);

                  }
                  else if (day == 4) {
                    this.d.controls[c].get('thursday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.thu = this.thu + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('thursday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('thursday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);

                  }
                  else if (day == 5) {
                    this.d.controls[c].get('friday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.fri = this.fri + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('friday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('friday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);
                  }
                  else if (day == 6) {
                    this.d.controls[c].get('saturday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.sat = this.sat + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('saturday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('saturday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);
                  }
                  else if (day == 0) {
                    this.d.controls[c].get('sunday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.sun = this.sun + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('sunday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('sunday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);
                  }
                }
              }
              console.log('total', activityTotal)
              this.d.controls[c].get('total').setValue(activityTotal);
            }
            this.total = this.total + activityTotal
            c++;
          }
          else if (c > 0 && c < len) {
            activityTotal = 0
            this.add_activity();
            console.log("activity added in get method")
            for (let l in this.tsrecords) {
              for (let a in this.dates) {
                if ((this.formatDate(new Date(this.tsrecords[l].date))) == (this.formatDate(this.dates[a])) && this.tsrecords[l].activityType == activityarr[b]) {
                  var day = this.dates[a].getDay()
                  if (day == 1) {
                    this.d.controls[c].get('monday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.mon = this.mon + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('monday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('monday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);
                  }
                  else if (day == 2) {
                    this.d.controls[c].get('tuesday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.tue = this.tue + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('tuesday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('tuesday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);

                  }
                  else if (day == 3) {
                    this.d.controls[c].get('wednesday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.wed = this.wed + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('wednesday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('wednesday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);

                  }
                  else if (day == 4) {
                    this.d.controls[c].get('thursday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.thu = this.thu + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('thursday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('thursday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);

                  }
                  else if (day == 5) {
                    this.d.controls[c].get('friday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.fri = this.fri + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('friday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('friday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);
                  }
                  else if (day == 6) {
                    this.d.controls[c].get('saturday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.sat = this.sat + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('saturday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('saturday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);
                  }
                  else if (day == 0) {
                    this.d.controls[c].get('sunday').controls.loggedHours.setValue(this.tsrecords[l].hoursLogged);
                    this.sun = this.sun + this.tsrecords[l].hoursLogged
                    activityTotal = activityTotal + this.tsrecords[l].hoursLogged
                    this.d.controls[c].get('sunday').controls.recordId.setValue(this.tsrecords[l].recordId);
                    this.d.controls[c].get('sunday').controls.projectId.setValue(this.tsrecords[l].projectId);
                    this.d.controls[c].get('activity').setValue(this.tsrecords[l].activityType);
                  }
                }
              }

              console.log('total', activityTotal)
              this.d.controls[c].get('total').setValue(activityTotal);
            }
            this.total = this.total + activityTotal
            c++;
          }
        }
        this.TimeSheets.controls.timesheetId.setValue(this.data.timesheetList[i].timeSheetID)
        this.TimeSheets.controls.statusId.setValue(this.data.timesheetList[i].timesheetStatus.statusId)
        this.TimeSheets.controls.comments.setValue(this.data.timesheetList[i].timesheetStatus.comment)
        if (this.data.timesheetList[i].timesheetStatus.submissionState == "Pending") {
          this.addBtn = false
          this.saveBtn = true
          this.submitBtn = true
          this.approveReject_status = false
          this.read = true
          this.pending = true
          this.TimeSheets.controls.fileName.disable()
        }
        else if (this.data.timesheetList[i].timesheetStatus.submissionState == "Approved") {
          this.addBtn = false
          this.saveBtn = true
          this.submitBtn = true
          this.approveReject_status = true
          this.read = true
          this.approved = true
          this.TimeSheets.controls.fileName.disable()
        }
        else if (this.data.timesheetList[i].timesheetStatus.submissionState == "Rejected") {
          this.addBtn = true
          this.saveBtn = false
          this.submitBtn = true
          this.approveReject_status = true
          if (this.viewCount == 1) {
            this.read = true
          } else {
            this.read = false
          }
          this.rejected = true
          this.TimeSheets.controls.fileName.disable()
        } else {
          this.submitBtn = false
          this.saveBtn = true
          this.pending = false
          this.approved = false
          this.rejected = false
          this.TimeSheets.controls.fileName.enable()
        }
      }
      console.log("Final timesheets", this.TimeSheets)
    },
      err => {
        console.log(err)
        error = err
        this.TimeSheets.controls.fileName.disable()
        this.toast.info(error.error.message + " from " + this.formatDate(this.dates[0]) + " to " + this.formatDate(this.dates[this.len - 1]), 'Info')
        this.getvalue()
        this.holidayset(this.dates)
      })
  }

  save_timesheets(e) {
    console.log("Form object : ", e);
    //this.calculate(this.TimeSheets);
    var array = []
    var d = e.get('week_ts').value;
    console.log("dummy1",d);
    console.log("activitylist",this.acitvitiesList);
    
    
    for (let i in d) {
      for (let j in this.dates) {
        var dummy = this.acitvitiesList.find(e => d[i].activity.includes(e.task))
        console.log("dummy", dummy);
        
        var id = dummy.pId
        var day = new Date(this.dates[j]).getDay();

        var obj =
        {
          "date": this.formatDate(this.dates[j]),
          "hoursLogged": "",
          "activityType": d[i].activity,
          "projectId": id,
          "recordId": ""
        }
        if (day == 1 && d[i].monday.loggedHours !=null) {
          obj.hoursLogged = d[i].monday.loggedHours
          obj.recordId = d[i].monday.recordId
          array.push(obj)
        }
        if (day == 2 &&  d[i].tuesday.loggedHours !=null) {
          obj.hoursLogged = d[i].tuesday.loggedHours
          obj.recordId = d[i].tuesday.recordId
          array.push(obj)
        }
        if (day == 3 && d[i].wednesday.loggedHours !=null) {
          obj.hoursLogged = d[i].wednesday.loggedHours
          obj.recordId = d[i].wednesday.recordId
          array.push(obj)
        }
        if (day == 4 && d[i].thursday.loggedHours !=null) {
          obj.hoursLogged = d[i].thursday.loggedHours
          obj.recordId = d[i].thursday.recordId
          array.push(obj)
        }
        if (day == 5 && d[i].friday.loggedHours !=null) {
          obj.hoursLogged = d[i].friday.loggedHours
          obj.recordId = d[i].friday.recordId
          array.push(obj)
        }
        if (day == 6 && d[i].saturday.loggedHours !=null) {
          obj.hoursLogged = d[i].saturday.loggedHours
          obj.recordId = d[i].saturday.recordId
          array.push(obj)
        }
        if (day == 0 && d[i].sunday.loggedHours !=null) {
          obj.hoursLogged = d[i].sunday.loggedHours
          obj.recordId = d[i].sunday.recordId
          array.push(obj)
        }
      }
    }
    console.log("final object", array)
    console.log("total logged hours", this.total)
    if (array.length == 0) {
      this.toast.info('You can not save empty timesheet', 'Alert!')
    } else {
      if ((this.mon != 0 && (this.mon < 9 || this.mon > 24) && this.mh == null) || (this.tue != 0 && (this.tue < 9 || this.tue > 24) && this.th == null) || (this.wed != 0 && (this.wed < 9 || this.wed > 24) && this.wh == null) || (this.thu != 0 && (this.thu < 9 || this.thu > 24) && this.thh == null) || (this.fri != 0 && (this.fri < 9 || this.fri > 24) && this.fh == null)) {
        this.toast.info("logged hours should not less than 9 or greater than 24 per day", "Info")
      } else {
        var requestobj = {
          "recordsList": array,
          "sheet":
          {
            "employeeId": this.reqEmp,
            "timeSheetID": this.TimeSheets.value.timesheetId,
            "timeSheetStartDate": this.formatDate(this.dates[0]),
            "totalHoursLogged": this.total
          },
          "status": {
            "statusId": this.TimeSheets.value.statusId,
            "submissionState": "",
            "approverId": ""
          },
          "transactionType": "save"
        }
        console.log("request obj", requestobj)
        let data
        let msg
        this.tms.saveTimesheet(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
          console.log("response getted", res)
          data = res
          this.toast.success(data.message, 'Success')
          this.clearTimeSheets();
          this.getTimesheetsdetails();
        },
          err => {
            msg = err
            this.toast.info(msg.error.message, 'Info')
          })
      }

    }
  }

  submit_timesheet(e) {
    console.log("Form object : ", e);
    this.calculate(this.TimeSheets);
    var array = []
    var d = e.get('week_ts').value

    console.log(d)
    for (let i in d) {
      for (let j in this.dates) {
        var dummy = this.acitvitiesList.find(e => d[i].activity.includes(e.task))
        var id = dummy.pId
        var day = new Date(this.dates[j]).getDay();
        var obj =
        {
          "date": this.formatDate(this.dates[j]),
          "hoursLogged": "",
          "activityType": d[i].activity,
          "projectId": id,
          "recordId": ""
        }
        if (day == 1 && d[i].monday.loggedHours != null) {
          obj.hoursLogged = d[i].monday.loggedHours
          obj.recordId = d[i].monday.recordId
          array.push(obj)
        }
        if (day == 2 && d[i].tuesday.loggedHours != null) {
          obj.hoursLogged = d[i].tuesday.loggedHours
          obj.recordId = d[i].tuesday.recordId
          array.push(obj)
        }
        if (day == 3 && d[i].wednesday.loggedHours != null) {
          obj.hoursLogged = d[i].wednesday.loggedHours
          obj.recordId = d[i].wednesday.recordId
          array.push(obj)
        }
        if (day == 4 && d[i].thursday.loggedHours != null) {
          obj.hoursLogged = d[i].thursday.loggedHours
          obj.recordId = d[i].thursday.recordId
          array.push(obj)
        }
        if (day == 5 && d[i].friday.loggedHours != null) {
          obj.hoursLogged = d[i].friday.loggedHours
          obj.recordId = d[i].friday.recordId
          array.push(obj)
        }
        if (day == 6 && d[i].saturday.loggedHours != null) {
          obj.hoursLogged = d[i].saturday.loggedHours
          obj.recordId = d[i].saturday.recordId
          array.push(obj)
        }
        if (day == 0 && d[i].sunday.loggedHours !=  null) {
          obj.hoursLogged = d[i].sunday.loggedHours
          obj.recordId = d[i].sunday.recordId
          array.push(obj)
        }

      }
    }
    console.log("final object", array)
    var requestobj = {
      "recordsList": array,
      "sheet":
      {
        "employeeId": this.reqEmp,
        "timeSheetID": this.TimeSheets.value.timesheetId,
        "timeSheetStartDate": this.formatDate(this.dates[0]),
        "totalHoursLogged": this.total,
        "fileName": this.filename,
        "attachment": this.TimeSheets.value.attachment,
        "reportingMngr": this.r_manager

      },
      "status": {
        "statusId": this.TimeSheets.value.statusId,
        "submissionState": "Pending",
        "approverId": ""
      },
      "transactionType": "submit"
    }
    console.log("request obj", requestobj)
    let data
    swal({
      title: "Are you sure?",
      text: "Once submitted, you will not be able to edit this timesheet!",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((willsubmit) => {
        if (willsubmit) {
          this.tms.saveTimesheet(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
            console.log("submitted", res)
            data = res
            // swal("", data.message, "success")
            // this.toastr.successToastr(data.message, 'Success', {
            //   animate: 'slideFromRight',
            //   showCloseButton: true,
            // });
            this.toast.success(data.message, 'Success')
            this.clearTimeSheets();
            this.getTimesheetsdetails();
          })
        }
      });

  }

  number(e,d) {
    if(d.value.activity!=""){
      if(d.value.activity.toLowerCase()=="leave" || d.value.activity.toLowerCase()=="paid leave"){
        this.submitBtn = true
        var key = e.keyCode
        if (key == 52 || key == 57 || key == 46 || key == 48) {
          return true
        } else{
          this.toast.info("Leave hours should be 4 or 9")
          return false
        }
          
      }else{
        this.submitBtn = true
        var key = e.keyCode
        if (key >= 48 && key <= 57 || key == 46) {
          return true
        } else{
          this.toast.info("Please enter only numbers")
          return false
        }
          
      }
     
    }else{
      this.toast.info("Please select the actvity first")
      return false
    }
    

  }

  fileSelected(evt) {
    //this.submitBtn = false
    var files = evt.target.files;
    var file = files[0];
    console.log("filename", file.name)
    this.filename = file.name
    if (files && file) {
      var reader = new FileReader();
      reader.onload = this.handleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }

  }

  handleReaderLoaded(readerEvt) {
    console.log(readerEvt)
    var binaryString = readerEvt.target.result;
    this.filedata = btoa(binaryString);
    console.log(this.filedata);
    this.TimeSheets.controls.attachment.setValue(this.filedata)
    console.log("timesheets", this.TimeSheets)
  }

  downloadFile() {
    console.log("timesheetid", this.TimeSheets.value.timesheetId)
    var requestobj = {
      "sheet":
      {
        "timeSheetID": this.TimeSheets.value.timesheetId
      },
      "transactionType": "getFile"
    }
    let data
    this.tms.getfile(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      data = res
      console.log("file getting", data.timesheetList[0].attachment)
      var filepdf = 'data:image/jpeg;base64,' + data.timesheetList[0].attachment;
      let a = document.createElement('a');
      a.href = filepdf;
      a.download = 'timesheetfile';
      a.click();
    },
      err => {
        data = err
        console.log("error", data)
        // this.toastr.infoToastr(data.error.message + " for this timesheet", 'Error', {
        //   animate: 'slideFromRight',
        //   showCloseButton: true,
        // });
        this.toast.info(data.error.message + " for this timesheet", 'Error')
      })
  }


  approvetms(e) {
    console.log("approve tms", e);
    var requestobj = {
      "sheet":
      {
        "timeSheetID": e.value.timesheetId,
        "employeeId": this.reqEmp,
        "reportingMngr": this.r_manager

      },
      "status": {
        "statusId": e.value.statusId,
        "submissionState": "Approved",
        "approverId": this.r_manager,
        "comment": e.value.comments
      },
      "transactionType": "update"
    }
    console.log("approve ", requestobj)
    this.tms.updateTMSStatus(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      console.log("approved", res)
      this.toast.success('Timesheet approved', 'Success')
      this.clearTimeSheets();
      this.getTimesheetsdetails()
    },err=>{
      console.log("error",err);
      this.toast.error('Approving Timesheets has not done', 'error')
      this.clearTimeSheets();
      this.getTimesheetsdetails()
    })
  }

  rejecttms(e) {
    console.log("reject tms", e);
    var requestobj = {
      "sheet":
      {
        "timeSheetID": e.value.timesheetId,
        "employeeId": this.reqEmp,
        "reportingMngr": this.r_manager

      },
      "status": {
        "statusId": e.value.statusId,
        "submissionState": "Rejected",
        "approverId": this.r_manager,
        "comment": e.value.comments
      },
      "transactionType": "update"
    }
    console.log("reject ", requestobj)
    this.tms.updateTMSStatus(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {

      console.log("approved", res)
      this.toast.success('Timesheet has been rejected', 'Success')
      this.clearTimeSheets();
      this.getTimesheetsdetails()
    }, err => {
      console.log("error",err);
      
      this.toast.error('Time Sheets rejection has not done', 'error')
      this.clearTimeSheets();
      this.getTimesheetsdetails()
    })
  }
  //back to top functionality



  @HostListener('window:scroll')
  checkScroll() {
    const scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
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

  calculate(e) {
    var activitytotal = 0
    this.mon = 0
    this.tue = 0
    this.wed = 0
    this.thu = 0
    this.fri = 0
    this.sat = 0
    this.sun = 0
    this.total = 0
    this.TimeSheets.controls.fileName.disable()
    let val = e.value.week_ts
    for (let i in val) {
      activitytotal = 0
      this.mon = this.mon + Number(val[i].monday.loggedHours)
      this.tue = this.tue + Number(val[i].tuesday.loggedHours)
      this.wed = this.wed + Number(val[i].wednesday.loggedHours)
      this.thu = this.thu + Number(val[i].thursday.loggedHours)
      this.fri = this.fri + Number(val[i].friday.loggedHours)
      this.sat = this.sat + Number(val[i].saturday.loggedHours)
      this.sun = this.sun + Number(val[i].sunday.loggedHours)
      activitytotal = Number(val[i].monday.loggedHours) + Number(val[i].tuesday.loggedHours) + Number(val[i].wednesday.loggedHours) + Number(val[i].thursday.loggedHours) + Number(val[i].friday.loggedHours) + Number(val[i].saturday.loggedHours) + Number(val[i].sunday.loggedHours)
      this.total = this.total + activitytotal
      console.log("total", activitytotal);
      (this.TimeSheets.get('week_ts') as FormArray).controls[i].controls.total.setValue(activitytotal)
    }
    if (this.selectedActivitycount == 0) {
      this.saveBtn = false
    } else {
      this.saveBtn = true
    }
  }

  selected(e,d) {
    console.log(e,d);
    console.log((this.TimeSheets.controls.week_ts as FormArray).controls[d]);
    this.clearSelectRow(d)
    this.getvalue()
    var uniqueCount = e.map(d => d.value.activity)
    this.submitBtn = true
    this.selectedActivitycount = this.calculateDuplicate(uniqueCount)
    if (this.selectedActivitycount > 0) {
      this.toast.info("You had already selected this activities", "Info")
      this.saveBtn = true
    } else {
      this.saveBtn = false
    }
  }

  calculateDuplicate(e) {
    var duplicate = 0;
    var counts = {};
    e.forEach(function (i) { counts[i] = (counts[i] || 0) + 1; });
    for (var key in counts) {
      if (counts.hasOwnProperty(key)) {
        counts[key] > 1 ? duplicate++ : duplicate;
      }
    }
    return duplicate;
  }

  getdates(e) {
    this.mh = null
    this.th = null
    this.wh = null
    this.thh = null
    this.fh = null
    this.dates = []
    console.log("input date", e);
    var date = new Date(e)
    console.log("calender date", date);
    var today = date.getDate();
    var month = date.getMonth();
    var year = date.getFullYear();
    var day = date.getDay()
    var n = 1;
    while (n <= 7) {
      var from = new Date(year, month, today - (day - n))
      this.dates.push(from)
      n++;
    }
    console.log("dates", this.dates)
    this.len = this.dates.length
    for (let i in this.holidays) {
      for (let j in this.dates) {
        if (this.holidays[i] == this.formatDate(this.dates[j])) {
          console.log("holiday", this.holidays[i])
          if (this.dates[j].getDay() == 1)
            this.mh = "#F5DEB3"
          if (this.dates[j].getDay() == 2)
            this.th = "#F5DEB3"
          if (this.dates[j].getDay() == 3)
            this.wh = "#F5DEB3"
          if (this.dates[j].getDay() == 4)
            this.thh = "#F5DEB3"
          if (this.dates[j].getDay() == 5)
            this.fh = "#F5DEB3"
        }
      }
    }
    this.clearTimeSheets();
    this.getTimesheetsdetails();
    //this.read = true
  }

  getemptmsDates() {
    this.pendingdates = []
    this.approveddates = []
    this.rejecteddates = []
    var requestobj = {
      "sheet":
      {
        "employeeId": this.reqEmp
      },
      "transactionType": "getDates"
    }
    var obj
    this.tms.getempSPRDates(requestobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      console.log("submitted dates", res)
      obj = res
      for (let i in obj.timesheetList) {
        if (obj.timesheetList[i].timesheetStatus.submissionState == "Pending") {
          this.pendingdates.push(obj.timesheetList[i].timeSheetStartDate)
        } else if (obj.timesheetList[i].timesheetStatus.submissionState == "Approved") {
          this.approveddates.push(obj.timesheetList[i].timeSheetStartDate)
        } else if (obj.timesheetList[i].timesheetStatus.submissionState == "Rejected") {
          this.rejecteddates.push(obj.timesheetList[i].timeSheetStartDate)
        }
      }
    })
  }

  getempleaves() {
    var response
    var reqObj = {
      "leaveInfo": {
        "empId": this.reqEmp
      },

      "transationType": "getallleaveinfo"

    }
    this.lms.lmsget(reqObj).subscribe(res => {
      response = res
      this.appliedLeaves = response.leaveInfo
      console.log("res", this.appliedLeaves);

    })
  }


  getvalue() {
    console.log("get value", this.appliedLeaves)
    var obj
    for (let i in this.dates) {
      for (let j in this.appliedLeaves) {
        if ((this.appliedLeaves[j].status == "approved" && this.appliedLeaves[j].applyType == "leaveapply") && (this.appliedLeaves[j].fromDate == this.formatDate(this.dates[i]))) {
          obj = this.dateDiffInDays(new Date(this.appliedLeaves[j].fromDate), new Date(this.appliedLeaves[j].toDate))
          for (let k in obj) {
            if (obj[k].getDay() == 1) {
              this.mh = "#FBEEFF"
              this.setvalue('monday')
              this.calculate(this.TimeSheets)
            }
            if (obj[k].getDay() == 2) {
              this.th = "#FBEEFF"
              this.setvalue('tuesday')
              this.calculate(this.TimeSheets)
            }
            if (obj[k].getDay() == 3) {
              this.wh = "#FBEEFF"
              this.setvalue('wednesday')
              this.calculate(this.TimeSheets)
            }
            if (obj[k].getDay() == 4) {
              this.thh = "#FBEEFF"
              this.setvalue('thursday')
              this.calculate(this.TimeSheets)
            }
            if (obj[k].getDay() == 5) {
              this.fh = "#FBEEFF"
              this.setvalue('friday')
              this.calculate(this.TimeSheets)
            }
            if (obj[k].getDay() == 6) {
              this.setvalue('saturday')
              this.calculate(this.TimeSheets)
            }
            if (obj[k].getDay() == 0) {
              this.setvalue('sunday')
              this.calculate(this.TimeSheets)
            }
          }
        }
      }
    }
  }

  setvalue(e) {
    var d = (this.TimeSheets.get('week_ts') as FormArray).controls
    console.log("week_ts", d, e);
    for (let i in d) {
      d[i].get(e).get('loggedHours').setValue("0")
    }

  }

  dateDiffInDays(a, b) {
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
    var ndays = Math.floor((utc2 - utc1) / this._MS_PER_DAY) + 1
    var n = 0;
    var dates = []
    while (n < ndays) {
      var from = new Date(a.getFullYear(), a.getMonth(), a.getDate() + n)
      dates.push(from)
      n++;
    }
    return dates
  }

  getproject() {
    var response
    var reqObj =
    {
      "projectlist":
      {
        "empId": this.reqEmp

      },
      "transactiontype": "getprojects"
    }
    this.tms.getProjectDetails(reqObj).subscribe(res => {
      console.log("project id data", res);
      response = res
      this.getPorjectByID(response.projectlist)
    })
  }

  getPorjectByID(e) {
    console.log("ids", e);
    var response;
    this.acitvitiesList = []
    this.projectId = []
    this.projectName = []
    this.projectStartdate=[]
    this.projectEnddate=[]
    for (let i in e) {
      var reqObj = {
        "projectInfo": {
          "projectId": e[i].projectId
        },
        "transactionType": "getByProId"
      }

      this.psa.getIdProject(reqObj).subscribe(res => {
        response = res
        console.log("all projects task", response.projectList[0]);

        if(response.projectList[0].buStatus=="Approved" && response.projectList[0].financeStatus=="Approved"){

          var task = [...response.projectList[0].tasks,...response.projectList[0].additionalTasks]
          this.projectId.push(response.projectList[0].projectId)
          this.projectName.push(response.projectList[0].projectName)
          this.projectStartdate.push(response.projectList[0].startDate)
          this.projectEnddate.push(response.projectList[0].endDate)
          this.getprojectmanger(response.projectList[0].projectResourceMapping.projectManager)
          task.map(d => {
            var one = new Object({ task: d, pId: response.projectList[0].projectId, pName: response.projectList[0].projectName })
            this.acitvitiesList.push(one)
          })
        }
       
        console.log("final", this.acitvitiesList);
      })
    }
  }

  getprojectmanger(e) {
    this.p_manager = []
    var obj = {
      "employeeInfo": [{
        "employeeId": e
      }],
      "transactionType": "getbyid"
    }
    console.log(obj, "req");

    let data
    this.hrms.getempinfo(obj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      data = res
      var fs = data.employeeInfo[0].firstname
      var ls = data.employeeInfo[0].lastname
      var fullName = fs[0].toUpperCase() + fs.slice(1) + " " + ls[0].toUpperCase() + ls.slice(1)
      this.p_manager.push(fullName)
    })
  }


  getHolidays() {
    var response
    var reqObj = {
      "holidayslist": [
        {

        }
      ],
      "transactionType": "getall"
    }
    this.lms.lmsholidayget(reqObj).subscribe(res => {
      console.log(res);
      response = res
      this.holidays = response.holidayslist.map(d => d.holidayDate)
      console.log("calender", this.holidays);

    })
  }


  holidayset(e) {
    for (let i in this.holidays) {
      for (let j in e)
        if (this.holidays[i] == this.formatDate(e[j])) {
          console.log("holidayss", this.holidays[i])
          if (e[j].getDay() == 1)
            this.mh = "#F5DEB3"
          if (e[j].getDay() == 2)
            this.th = "#F5DEB3"
          if (e[j].getDay() == 3)
            this.wh = "#F5DEB3"
          if (e[j].getDay() == 4)
            this.thh = "#F5DEB3"
          if (e[j].getDay() == 5)
            this.fh = "#F5DEB3"
        }
    }
  }

  //route to dashboard
  navigateTo() {
    this.route.navigate(['home/dashboard']);
  }


  getAllEmployee(){
    var count=0
    var response
    var list =new Set()
    var reqObj= {
        "employeeInfo": [{
        }],
        "transactionType": "getAllInfo"
      }
      this.hrms.getempinfo(reqObj).subscribe(res=>{
        console.log("res",res);  
        response=res
        response.employeeInfo.map(d=>
          list.add(d.reportingManager))
          console.log(list,"list");
          
          this.managersList=Array.from(list)
          for(let i in this.managersList){
            if(this.login==this.managersList[i]){
              count=1
            }
          }
          if(count==1){
            this.navbar = true
            this.mysheet()
          }
          else{
            this.userRole()
          }
      })
  }


  sortid(){
    if(this.orderid==null){
      this.teamdata.sort((a, b) => (a.employeeId > b.employeeId) ? 1 : -1)
      this.orderid=0
    }
    else if(this.orderid==0){
      this.teamdata.sort((a, b) => (a.employeeId < b.employeeId) ? 1 : -1)
      this.orderid=1
    }else{
      this.teamdata.sort((a, b) => (a.employeeId > b.employeeId) ? 1 : -1)
      this.orderid=0
    }
    
  }
  
  sortname(){
    if(this.ordername==null){
      this.teamdata.sort((a, b) => (a.firstname > b.firstname) ? 1 : -1)
      this.ordername=0
    }
    else if(this.ordername==0){
      this.teamdata.sort((a, b) => (a.firstname < b.firstname) ? 1 : -1)
      this.ordername=1
    }
    else{
      this.teamdata.sort((a, b) => (a.firstname > b.firstname) ? 1 : -1)
      this.ordername=0
    }
  }

  clearSelectRow(e){
    console.log(e);
    var one=(this.TimeSheets.get('week_ts') as FormArray).controls[e] as FormGroup
    one.controls.monday.reset()
    one.controls.tuesday.reset()
    one.controls.wednesday.reset()
    one.controls.thursday.reset()
    one.controls.friday.reset()
    one.controls.saturday.reset()
    one.controls.sunday.reset()
    this.calculate(this.TimeSheets);
  }

}

