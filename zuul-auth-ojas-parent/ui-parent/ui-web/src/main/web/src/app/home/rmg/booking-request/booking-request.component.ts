import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormArray, Validators, FormBuilder } from '@angular/forms';
import { takeUntil } from 'rxjs/operators';
import { PsaService } from '../../services/psa.service';
import { HttpClient } from '@angular/common/http';
import { RmgService } from '../../services/rmg.service';
import { HrmsService } from '../../services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { AuthService } from 'src/app/services/auth.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-booking-request',
  templateUrl: './booking-request.component.html',
  styleUrls: ['./booking-request.component.scss']
})
export class BookingRequestComponent implements OnInit {

  //@ViewChild('someModal') someModal:ElementRef;
  projectmangersList:any=[]
  skilledEmployees:any;
  updategenericid: any
  bookcard = false;
  hidedtab = true;
  backhide = false;
  bookmoreid = false;
  addhide = true;
  rmgdata: any;
  rmgDataResponse: any;
  rmgProposal = [];
  dataOfResourceOF: any;
  dataToBook = [];
  appResources: any;
  encryptedRole: string;
  role1: string;
  specificShow: boolean;
  genericShow: boolean;
  mappingShow: boolean;
  arrayResourceCount: any;
  openBook: boolean;
  specificMoreInfo: boolean;
  specificToBook = [];
  specificDataSp: any;
  // mapresourcehide: boolean=true;
  firstNames: any;
  dataresponese: any;
  dataresponse: any;
  // mapreshide: boolean=true;
  bookingidre: any;
  specificbook: any;

  array: any;
  selectedActivitycount: number;
  empObj: any;
  showRequestbutton: boolean;
  skillList: any;
  skillBasedEmployees: any;
  proId = [];
  getallProjects: any;
  allProjectInfo: any;
  e: any;
  managersList:any
  
  back() {
    this.hidedtab = true;
    this.backhide = false;
    this.bookmoreid = false;
    this.bookcard = false;
    this.addhide = true;

  }

  private projectAll: Subject<any> = new Subject();
  specifiicData: any;
  projectId: any;
  createBtn: any = true;
  hidevalues: boolean = false;
  hidaddbtn: boolean = false;
  status1: boolean = false;
  loggedUserName: any;
  empbasicin: any;
  empObjTech: any;
  manager: unknown[];
  employee: unknown[];
  managerdata: any = [];
  dataTo: any;
  dataProject: any;
  projectNames: any;
  hidebt: boolean;
  modalremovecount: any;
  c: number;
  statusofbu: boolean = false;
  a: any;
  removestatus: boolean = false;
  removestatus1: boolean = false;
  removecount: any;
  remove: boolean;
  showmsg: boolean = true;
  hideoptions: boolean = false;
  mindate: Date;
  maxdate: Date;
  getIdData: any;
  role: any;
  empbasicinfo: any;
  eid: any;
  startdate: any;
  enddate: any;
  projectid1: any;
  resourcesData: any;
  contentid = true;
  mybookid = false;
  propid = false;

  bookidmore = false;
  propidmore = false;
  appridmore = false;
  rejectidmore = false;
  hideadd = true;
  disablename = true;
  disablein = false;
  hidethis = true;
  msg: any;
  messageTosend: any;
  pages: any = 5;
  proposalData: any;
  counter: any

  // filteredOptions:any;
  //   resourceId = new FormControl();


  constructor(private fb: FormBuilder, private psaService: PsaService, private http: HttpClient, private rmgService: RmgService, private hrms: HrmsService, private toastr: NotificationService, private authservice: AuthService) {
    this.getAllProject();
  }
  emplnames;
  ngOnInit() {
    
    this.encryptedRole = sessionStorage.getItem('Role');
    this.role = this.authservice.decryptData(this.encryptedRole);
    console.log("ROLE ", this.role);

    this.role1 = sessionStorage.getItem('UserName');
    this.loggedUserName = this.authservice.decryptData(this.role1);

    //     this.filteredOptions = this.resourceId.valueChanges
    //     .pipe(
    //       startWith(''),
    //       map(value => typeof value === 'string' ? value : value.name),
    //       map(firstname => firstname ? this._filter(firstname) : this.emplnames.slice())


    //     );
    // console.log(this.filteredOptions,"FILTEROPTIONS");

   
    this.getAllResources();
    this.getAllEmployees();
    this.getAllEmployeedetails()
    
    this.getSkillInfomaster();
    this.getI();
    this.getExpereince();

    // this.role = sessionStorage.getItem('Role');
    // console.log("form", this.resourceForm);
    console.log(this.genericformbook, "bookingform")
  }


  // displayFn(edata?:EmployeeData): any | undefined {
  //   console.log(edata)
  //   return edata ? edata.firstname : undefined;
  // }
  // private _filter(firstname: string) {
  //   const filterValue = firstname.toLowerCase();

  //   return this.emplnames.filter(option => option.firstname.toLowerCase().indexOf(filterValue) === 0);
  // }
  // private _filter(value: string): string[] {
  //   const filterValue = value.toLowerCase();

  //   return this.firstNames.filter(option => option.toLowerCase().includes(filterValue));
  // }


  ngOnDestroy(): any {
    this.projectAll.next();
    this.projectAll.complete();
  }
  changeffilter(e) {
    console.log(e.value, "datarrrrrrrrrrrr")
  }

  resourcetype: boolean;
  foods = [
    { value: 'steak-0', viewValue: 'Steak' },
    { value: 'pizza-1', viewValue: 'Pizza' },
    { value: 'tacos-2', viewValue: 'Tacos' }
  ];

  add() {
    this.hidebt = false;
  }

  editenable() {
    this.disablein = true;
    this.hidethis = false;
  }
  resourceForm = this.fb.group({
    projectname: ['', Validators.required],
    resourceType: ['', Validators.required],
    projectid: [],
    startdate2: [],
    enddate2: [],

    AddResourcespecific: this.fb.array([

      this.fb.group({

        resourceId: ['', [Validators.required]],

        startDate: ['', Validators.required],
        endDate: ['', Validators.required],
        resourceCost: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],

        // resourceCurrentAssignment:[],
        // resourcePreviousAssignment:[],
        // availability: ['', Validators.required]

      })
    ])

    // AddResouce1:this.fb.array([

    //   this.fb.group({

    //    resourceId1: ['', Validators.required],
    //    resourceName1: ['', Validators.required],
    //    startDate1:['', [Validators.required]],
    //    endDate1: ['', [Validators.required]],
    //    resourceCost1: ['', Validators.required],
    //    availability1: ['', Validators.required]

    //   })
    //  ])


  })

  skillForm = this.fb.group({
    resourceType: [],
    projectname1: [],
    // skillName: ['', Validators.required],
    // experience: ['', Validators.required],
    AddResourcegeneric: this.fb.array([

      this.fb.group({
        skillName: ['', [Validators.required]],
        experience: ['', [Validators.required]],
        startDate1: ['', [Validators.required]],
        endDate1: ['', [Validators.required]],
        billrate: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
        resourceCount: ['', [Validators.required, Validators.pattern('^[1-9]*$')]],
        // resourceCurrentAssignment1:[],
        // resourcePreviousAssignment1:[]
        // availability1: ['', Validators.required]


      }),

    ])

  })
  genericformbook = this.fb.group({
    // resourceType: ['', Validators.required],
    // projectname1: [],
    // skillName: ['', Validators.required],
    // experience: ['', Validators.required],
    // AddResourcegeneric: this.fb.array([

    //   this.fb.group({
    //     skillName: ['', Validators.required],
    //     experience: ['', Validators.required],
    //     startDate1: ['', [Validators.required]],
    //     endDate1: ['', [Validators.required]],
    //     billrate: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
    //     resourceCount: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],



    //   }),


    // ]),
    mapresouce: this.fb.array([

      this.fb.group({

        empId: ['', [Validators.required]],
        startDate: ['', [Validators.required]],
        endDate: ['', [Validators.required]],
        // availability:[]

      })
    ]),



  })

  propForm = this.fb.group({
    resourceId1: ['', [Validators.required]],
    reason: ['', [Validators.required]]
  })


  // mapping(count){


  //   this.arrayResourceCount=count;
  // }
  addingmapresouces(count, index, projectIds) {

    this.removingarray();
    var id = index;


    this.mappingShow = true;
    // this.mapresourcehide=false;
    console.log('data consoled', count);
    var i = 0;
    this.genericformbook.controls.mapresouce.clearValidators();
    for (var j = 1; j <= count; j++) {
      (this.genericformbook.get('mapresouce') as FormArray).push(this.fb.group({

        empId: ['', [Validators.required]],
        startDate: ['', [Validators.required]],
        endDate: ['', [Validators.required]],
        genericStatus: ['approved'],
        genericEmployeeStatus: [],
        flag:[1]

      })
      )
      this.getProjectDetails(projectIds)
      console.log(count, "value")
      console.log("index", id);
      this.updategenericid = id;
      // this.getIdProject(id)

    }



  }

  removingarray() {

    this.genericformbook.reset();

    var a = (this.genericformbook.get('mapresouce') as FormArray);

    for (var i = a.length; i >= 0; i--) {
      a.removeAt(i);
    }
    a.reset();
    console.log("length of array", a);

  }
  getI() {


    this.removecount = this.resourceForm.controls.AddResourcespecific.value.length
    console.log(this.removecount, "Remove Ubhsbfdjfbjbf");


    if (this.removecount <= 1) {
      this.removestatus = false;
    }
    else
      this.removestatus = true;

  }
  getII() {
    this.removecount = this.skillForm.controls.AddResourcegeneric.value.length
    console.log(this.removecount, "Remove Ubhsbfdjfbjbf");


    if (this.removecount <= 1) {
      this.removestatus1 = false;
    }
    else
      this.removestatus1 = true;
  }

  addresouces() {
    this.hidebt = true;
    this.hidevalues = true;
    this.hidaddbtn = false;
    (this.resourceForm.get('AddResourcespecific') as FormArray).push(this.fb.group({
      resourceId: ['', Validators.required],

      startDate: ['', [Validators.required]],
      endDate: ['', [Validators.required]],
      resourceCost: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],

      // resourceCurrentAssignment:[],
      // resourcePreviousAssignment:[],
      // availability: ['', Validators.required],

    }))
    this.getI();

  }


  addresouces1() {
    this.hidebt = true;
    (this.skillForm.get('AddResourcegeneric') as FormArray).push(this.fb.group({
      skillName: ['', Validators.required],
      experience: ['', Validators.required],
      startDate1: ['', [Validators.required]],
      endDate1: ['', [Validators.required]],
      billrate: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      resourceCount: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      // resourceCurrentAssignment1:[],
      // resourcePreviousAssignment1:[]
      // availability1: ['', Validators.required],

    }))
    this.getII();
  }

  selectedProject(e) {

    this.hidevalues = true;
    this.status1 = false;
    this.showmsg = false;
    this.hidaddbtn = true;
    this.resourceForm.clearValidators();
  }
  selected_projects(id) {
    this.hideoptions = true;
    this.getIdProject(id);


  }
  getIdProject(id) {
    var getId;
    var getID = {
      "projectInfo": {
        "projectId": id
      },
      "transactionType": "getByProId"
    }

    this.psaService.getIdProject(getID).pipe(takeUntil(this.projectAll)).subscribe(response => {
      getId = response;


      this.getIdData = getId.projectList[0];
      this.mindate = this.getIdData.startDate;
      this.startdate = this.getIdData.startDate;
      this.enddate = this.getIdData.endDate;
      this.projectid1 = this.getIdData.projectId
      this.maxdate = this.getIdData.endDate;
      console.log(this.mindate, "startdate");
      console.log(this.maxdate, "endDate");
      this.resourceForm.get("projectid").setValue(this.projectid1)
      this.resourceForm.get("startdate2").setValue(this.startdate)
      this.resourceForm.get("enddate2").setValue(this.enddate);
    }
    )
  }


  resetforms() {
    this.clearcreateform();
    this.clearskillsform();
    this.clearproposform()
  }

  option() {

    this.status1 = true;
    this.hidevalues = false;
    this.showmsg = false;
    this.skillForm.reset();
  }
  clear() {
    this.resourceForm.reset();
    this.skillForm.reset();
    this.resourcetype = false;
    this.hidevalues = false;
    this.status1 = false;
    // this.clearcreateform();
    // this.clearskillsform();
  }
  clearpropForm() {
    this.propForm.reset
  }
  create() {
    this.resourceForm.reset();
    this.hidevalues = false;
    this.status1 = false;
  }
  formhide() {
    this.hidevalues = false;
    this.status1 = false;
    this.showmsg = true;
    this.hideoptions = false;
  }
  // book() {
  //   this.mybookid = true;
  //   this.contentid = false;
  //   this.backhide = true;
  //   this.hideadd = false;
  // }
  prop() {
    this.propid = true;
    this.contentid = false;
    this.backhide = true;
    this.hideadd = false;
  }
  backClicked() {
    this.backhide = false;
    this.mybookid = false;
    this.propid = false;
    this.contentid = true;
    this.bookidmore = false;
    this.hideadd = true;
    this.propidmore = false;
    this.appridmore = false;
    this.rejectidmore = false;
  }
  // bookmore() {
  //   this.bookidmore = true;
  //   this.backhide = true;
  //   this.contentid = false;
  //   this.hideadd = false;
  // }
  propmore() {
    this.propidmore = true;
    this.backhide = true;
    this.contentid = false;
  }
  apprmore() {
    this.appridmore = true;
    this.backhide = true;
    this.contentid = false;
    this.hideadd = false;
  }

  rejectmore() {
    this.rejectidmore = true;
    this.backhide = true;
    this.contentid = false;
    this.hideadd = false;
  }

  namedisplay(e, i) {
    console.log(e, i)
    var empdata
    var empinfo =
    {
      "employeeInfo": [{
        "employeeId": e
      }],
      "transactionType": "getById"
    }
    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.projectAll)).subscribe(res => {
      console.log(res)
      empdata = res
      var name = empdata.employeeInfo[0].firstname + " " + empdata.employeeInfo[0].lastname
      console.log(name)
      var d = (this.resourceForm.get("AddResourcespecific") as FormArray).controls
      d[i].get('resourceName').setValue(name)
    })
  }
  disablerem() {

    if (this.c <= 1) {
      this.statusofbu = false;
    }
    else {
      this.statusofbu = true;
    }
  }

  modalremovingresource(a) {

    this.hidevalues = true;

    (this.resourceForm.get('AddResourcespecific') as FormArray).removeAt(a)



    this.getI();

  }
  modalremovinggenresource(b) {

    this.status1 = true;
    this.c = b;
    (this.skillForm.get('AddResourcegeneric') as FormArray).removeAt(b)

    this.modalremovecount = this.skillForm.controls.AddResourcegeneric.value.length
    this.getII();
  }

  getAllProject() {
    let projectData;
    var getProject = {
      "projectInfo": {
      },
      "transactionType": "getall"
    }


    this.psaService.getAllProject(getProject).pipe(takeUntil(this.projectAll)).subscribe(response => {
      projectData = response;
      this.dataTo = projectData.projectList
      console.log("contract data in project", this.dataTo);
      this.projectmangersList=this.dataTo.map(x => x.projectResourceMapping.projectManager)
      console.log("list of managers",this.projectmangersList);
       
      this.mindate = this.dataTo.map(x => x.startDate)
      this.maxdate = this.dataTo.map(y => y.endDate);
      console.log(this.mindate, "startdate");
      console.log(this.maxdate, "endDate");



      // if (this.role == "ROLE_MANAGER") {
      this.getAllEmployees();
      //this.bookingEnable();
      //console.log("projectDATA Manager", this.dataProject);



      this.dataProject = this.dataTo.filter(info => info.projectResourceMapping.projectManager == this.loggedUserName);



      console.log("projectDATA Manager", this.dataProject);


      // }
      // else {
      this.dataProject = this.dataTo;
      // }
    });

  }



  getAllEmployees() {
    let empbasic;
    var empinfo =
    {
      "rmgemployeelist":

      {

      },



      "transactiontype": "getempbytstatus"
    }
    // {
    //   "employeeInfo": [{
    //   }],
    //   "transactionType": "getAllInfo"
    // }

    this.rmgService.getAllEmp(empinfo).pipe(takeUntil(this.projectAll)).subscribe(res => {
      empbasic = res;
      this.empbasicin = empbasic.rmgemployeelist;
      console.log(this.empbasicin, "ALLEMPLOYEESDATA");


      this.manager = Array.from(new Set(this.empbasicin.map(x => x.reportingManager)));
      this.employee = Array.from(new Set(this.empbasicin.map(x => x.employeeId)));

       console.log("managers",this.manager);
      //  this.showRequestbutton = this.manager.includes(this.loggedUserName);

      //  console.log('show manager', this.showRequestbutton);

      this.empbasicinfo = this.empbasicin.filter(info => info.employmentStatus == "Bench" || info.employmentStatus == "Lateral");
      console.log(this.empbasicinfo, "ALLEMPLOYEEDATA");

    });




  }
  //masters starts
  skillmasterlist: any;

  skillmasterArray: any;


  getSkillInfomaster() {

    var Requestdata = {
      "listOfSkill": [{


      }],
      "transactionType": "getAll"
    }
    this.hrms.getSkillmaster(Requestdata).subscribe(responce => {
      this.skillmasterlist = responce;
      this.skillmasterArray = this.skillmasterlist.listOfSkill;
      // this.skillName=this.skillMasterName
      console.log(this.skillmasterArray, "Skill DETAILS")
    })

  }
  experiences: any;
  expList: any;
  getExpereince() {

    var request = {

      "empExperienceList": [
        {}
      ],
      "transactionType": "getAll"
    }
    this.rmgService.getempExperience(request).subscribe(res => {
      this.experiences = res;
      this.expList = this.experiences.empExperienceList;
      console.log(this.expList, "experience data")
    })

    // this.http.post("http://192.168.2.100:9974/get", request).subscribe(res => {


    //   this.experiences = res;
    //   this.expList = this.experiences.empExperienceList;
    //   console.log(this.expList, "experience data")



    // })
  }

  clearproposform() {
    this.propForm.reset();
  }



  clearcreateform() {
    this.resourceForm.controls.AddResourcespecific.clearValidators();
    this.resourceForm.reset();
    let array = this.resourceForm.get('AddResourcespecific') as FormArray

    while (array.length) {
      array.removeAt(array.length - 1);
    }

    this.addresouces();

  }

  clearskillsform() {
    this.skillForm.controls.AddResourcegeneric.clearValidators();
    this.skillForm.reset();
    let array = this.skillForm.get('AddResourcegeneric') as FormArray

    while (array.length) {
      array.removeAt(array.length - 1);
    }

    this.addresouces1();

  }




  getAllResources() {
    var resourcedata = {
      "rmgInfo": {

      },
      "transactiontype": "getall"
    }

    this.rmgService.getAllResource(resourcedata).subscribe(res => {
      this.rmgdata = res;
      this.rmgDataResponse = this.rmgdata.rmgInfo;
      console.log("resorcesss data", this.rmgDataResponse)

      this.rmgProposal = this.rmgDataResponse.filter(x => x.status == 'pending')
      console.log(this.dataTo,"projects");
      for (let i in this.rmgDataResponse) {
           
  
        var proName = this.dataTo.find(s => s.projectId == this.rmgDataResponse[i].projectId)
         this.rmgDataResponse[i].projectName = proName.projectName
        console.log(this.rmgDataResponse[i].projectName,"pdddddddddddddd");
        

        if (this.rmgDataResponse[i].resourceType == "Generic" && (this.rmgDataResponse[i].status == 'approved')) {


          for (let j in this.rmgDataResponse[i].rmggeneric) {

            if (this.rmgDataResponse[i].rmggeneric[j].rmggenericresourcemap.length == 0) {
              this.rmgDataResponse[i].status = 'pending'
              this.rmgProposal = [];
              this.rmgProposal.push(this.rmgDataResponse[i]);
              break;
            }

          }


        }

        this.rmgProposal = this.rmgDataResponse.filter(x => x.status == 'pending')
        
        
        if ((this.rmgDataResponse[i].status == 'approved') || (this.rmgDataResponse[i].status == 'rejected') || (this.rmgDataResponse[i].status == 'proposed') && this.rmgDataResponse[i].resourceType == 'Specific') {


          // var array=[];
          for (let j in this.rmgDataResponse[i].rmgspecific) {

            if (this.rmgDataResponse[i].rmgspecific[j].specificStatus == null) {
              this.rmgDataResponse[i].status = 'pending'


              this.rmgProposal.push(this.rmgDataResponse[i])
              
              break;
            }
            console.log(this.rmgProposal,"rmg proposal data");

          }


          //  this.rmgProposal=this.rmgProposal.concat(array)


        }

      }

      console.log(this.rmgProposal, "responseaftersetupprojectname")

    })


  }


  statusMethod(a, projectdetails) {
    console.log(a, "data")
    let status;
    console.log(this.allProjectInfo, "RESPONSE DATA");
    if (this.allProjectInfo[0].projectType == "Enablement" || this.allProjectInfo[0].projectType == "Internal Product") {
      status = 'Deployed-IDC';
    }
    else if (this.allProjectInfo[0].projectType == "Customer" && this.allProjectInfo[0].locationType == "EDC") {
      status = 'Deployed-EDC';
    }
    else if (this.allProjectInfo[0].projectType == "Customer" && this.allProjectInfo[0].locationType == "CDC") {
      status = 'Deployed-CDC';
    }
    else {
      status = 'Deployed-' + this.allProjectInfo[0].locationType;
    }
    var appResources;
    var req =
    {
      "rmgInfo":
      {

        "bookingId": this.dataOfResourceOF.bookingId,
        "resourceType": this.dataOfResourceOF.resourceType,
        "projectId": this.dataOfResourceOF.projectId,
        "status": "approved"



      },


      "rmgSpecificList": [
        {
          "specificId": a.specificId,
          "employeeId": a.employeeId,
          "billRate": a.billRate,
          "startDate": a.startDate,
          "endDate": a.endDate,
          "reason": "available",
          "specificStatus": "approved",
          "specificEmployeeStatus": status,
          "flag":1

        }],


      "transactiontype": "update"
    }


    console.log("app data", req);

    this.rmgService.updateresources(req).subscribe(res => {
      appResources = res;
      console.log("approved", appResources);
      // this.openBook = false;
      // this.hidedtab = true;
      this.getAllResources();
      var message = appResources.message;
      if (res) {
        
        this.getAllResources();
        this.resourceForm.reset();
      }
      this.toastr.success(message)


    }
      , err => {
        this.toastr.info('Resource Not Added')

      })
  }


  statusMethod1(a) {

    var appResources;
    var req = {
      "rmgInfo":
      {

        "bookingId": this.dataOfResourceOF.bookingId,
        "resourceType": this.dataOfResourceOF.resourceType,
        "projectId": this.dataOfResourceOF.projectId,
        "status": "rejected"



      },


      "rmgSpecificList": [
        {
          "specificId": a.specificId,
          "employeeId": a.employeeId,
          "billRate": a.billRate,
          "startDate": a.startDate,
          "endDate": a.endDate,
          "reason": "not available",
          "specificStatus": "rejected",
          "flag":0

        }],


      "transactiontype": "update"
    }


    console.log("app data", req);

    this.rmgService.updateresources(req).subscribe(res => {
      appResources = res;
      console.log("rejected", appResources);
      // this.openBook = false;
      // this.hidedtab = true;
      this.getAllResources();
      var message = appResources.message;
      if (res) {
        this.resourceForm.reset();
        this.getAllResources();
      }
      this.toastr.success(message)


    }
      , err => {
        this.toastr.info('Resource Not Added')

      })
  }
  statusMethod2(a) {
    console.log(a, "formdata")
    console.log(this.bookingidre, "bookingid")
    console.log(this.dataOfResourceOF, "dataform")
    var appResources;
    var request = {
      "rmgInfo":
      {

        "bookingId": this.dataOfResourceOF.bookingId,
        "resourceType": this.dataOfResourceOF.resourceType,
        "projectId": this.dataOfResourceOF.projectId,
        "status": "proposed"



      },


      "rmgSpecificList": [
        {
          "specificId": this.proposalData.specificId,
          "employeeId": this.proposalData.employeeId,
          "billRate": this.proposalData.billRate,
          "startDate": this.proposalData.startDate,
          "endDate": this.proposalData.endDate,
          "alternateemployeeId": a.resourceId1,
          "reason": a.reason,
          "specificStatus": "proposed",
          "flag":1

        }],


      "transactiontype": "update"
    }



    console.log("app data", request);

    this.rmgService.updateresources(request).subscribe(res => {
      appResources = res;
      console.log("proposed", appResources);
      // this.openBook = false;
      // this.hidedtab = true;
      this.getAllResources();
      var message = appResources.message;
      if (res) {
        this.getAllResources();
        this.resourceForm.reset();
       
      }
      this.toastr.success(message)


    }
      , err => {
        this.toastr.info('Resource Not Added')

      })
  }

  saveresourceSpecific(a) {
    console.log(a, "values")
    var saveres;
    var alist = a.AddResourcespecific
    console.log(alist, "datavalues")
    var resouceList = [];
    for (let i in alist) {

      console.log(alist[i].resourceId, "emp")
      var obj = {
        "employeeId": alist[i].resourceId,

        "startDate": this.formatDate(alist[i].startDate),
        "endDate": this.formatDate(alist[i].endDate),
        "billRate": alist[i].resourceCost,
        "alternateemployeeId": "",
        "flag":1

      }

      resouceList.push(obj)
    }
    console.log(resouceList, "datavalues")

    var req =
    {
      "rmgInfo":
      {


        "resourceType": a.resourceType,
        "projectId": a.projectname,
        "message": "Request for resources to SAHEAD",
        "status": "pending",
        "flag": 1

      },


      "rmgSpecificList": resouceList

      ,
      "transactiontype": "save"
    }
    console.log("request to save", req);

    this.rmgService.saveresources(req).subscribe(res => {
      saveres = res;
      console.log("specific response save", saveres);
      var message = saveres.message;
      this.getAllResources();
      if (res) {
        this.resourceForm.reset();
      }
      this.toastr.success(message)


    }
      , err => {
        this.toastr.info('Resource Not Added')

      })
  }



  genres: any;
  saveresourceGeneric(resoucespecific, genric) {
    var specificdata = resoucespecific;

    var genericlist = genric.AddResourcegeneric

    var generresouceList = [];
    for (let i in genericlist) {

      //   var skill=[]
      //  for(let j in genericlist[i].skillName){   
      //   skill.push(genericlist[i].skillName[j] ) 
      // }
      var obj = {

        "resourceExperience": String(genericlist[i].experience),
        "resourceSkills": genericlist[i].skillName,
        "billRate": Number(genericlist[i].billrate),
        "startDate": this.formatDate(genericlist[i].startDate1),
        "endDate": this.formatDate(genericlist[i].endDate1),
        "resourceCount": Number(genericlist[i].resourceCount),

         
      }

      generresouceList.push(obj)
    }


    var request =
    {
      "rmgInfo":
      {


        "resourceType": resoucespecific.resourceType,
        "projectId": String(resoucespecific.projectname),
        "status": "pending",
        "message": "Request for resources to SAHEAD",
        "flag": 1
      },


      "rmgGenericList": generresouceList,



      "transactiontype": "save"
    }

    console.log(request, "this is data")
    this.rmgService.saveresources(request).subscribe(res => {
      this.genres = res;
      this.getAllResources();
      console.log(this.genres, "GENERIC DATA");
      var message = this.genres.message;
      if (res) {
        this.skillForm.reset();
      }
      this.toastr.success(message)


    }
      , err => {
        console.log(err)
        this.toastr.info('Resource Not Added')


      })
  }

  book(id, projectid) {
    console.log(projectid, "Id OF THE PROJECT");

    this.openBook = true;
    this.hidedtab = false;
    // this.mapresourcehide=true;
    this.genericformbook.reset();
    let resoucedata;
    console.log(id, "updateddid")
    var bookingid = {

      "rmgInfo":
      {
        "bookingId": id
      },
      "transactiontype": "getbyid"

    }
    console.log(bookingid, "reqobj")
    this.rmgService.getResourcesbyId(bookingid).subscribe(res => {

      resoucedata = res;
      this.dataOfResourceOF = resoucedata.rmg;
      console.log(this.dataOfResourceOF, "response")





      this.dataresponse = this.dataOfResourceOF.rmggeneric;


      if (res) {
        if (this.dataOfResourceOF.resourceType == 'Generic') {
          this.dataToBook = [];
          for (let i in this.dataresponse) {

            console.log(this.dataresponse[i].rmggenericresourcemap.length, "length attributes")
            if (this.dataresponse[i].rmggenericresourcemap.length == 0) {
              console.log("entry", this.dataresponse[i])
              this.dataToBook.push(this.dataresponse[i])
            }
            else {
              this.dataToBook = [];
            }

          }


        }
        else if (this.dataOfResourceOF.resourceType == 'Specific') {
          this.proId.push(this.dataOfResourceOF.projectId)
          console.log("proId", this.dataOfResourceOF.projectId);
          this.specificbook = this.dataOfResourceOF.rmgspecific
          this.specificToBook = [];
          for (let i in this.specificbook) {


            console.log(this.specificbook[i].specificStatus, "statusspecific")
            if (this.specificbook[i].specificStatus == null) {
              console.log("exit1")

              this.specificToBook.push(this.specificbook[i])

            }

          }
          console.log(this.specificToBook, "specific data");


        }

      }


      // console.log(this.dataToBook,"RESPONSE OF DATARESOURCE");

      // this.specificToBook=this.dataOfResourceOF.rmgspecific;
      console.log("resource dataaahfghfgjfgj", this.dataOfResourceOF);


      if (this.dataOfResourceOF.resourceType.toLowerCase() == "specific") {

        this.specificShow = true;
      }
      else {

        this.specificShow = false;
      }


      this.getProjectDetails(projectid)

    })
  }



  dataOfResource: any;
  bookmore(id) {
    this.hidedtab = false;
    this.bookmoreid = true;
    this.backhide = true;
    this.addhide = false;



    let resoucedata;
    var getenpid = {

      "rmgInfo":
      {
        "bookingId": id
      },
      "transactiontype": "getbyid"



    }
    this.rmgService.getResourcesbyId(getenpid).subscribe(res => {
      resoucedata = res;
      this.dataOfResource = resoucedata.rmg;
      this.specificDataSp = this.dataOfResource.rmgspecific
      console.log("resource dataaa", this.dataOfResource);

      if (this.dataOfResource.resourceType.toLowerCase() == "specific") {

        this.specificMoreInfo = true;
      }
      else {

        this.specificMoreInfo = false;
      }

    })

  }



  cancelSA() {
    this.mappingShow = false;
    this.genericformbook.reset();
    this.openBook = false;
    this.hidedtab = true;
  }

  allocateResourseArray: any = [];

  allocate(value, dataArray, data) {
    console.log(value, "value");
    console.log(dataArray, "dateArray")
    console.log(data, "data")
    // this.mapreshide=false;

    let status;
    console.log(this.allProjectInfo, "RESPONSE DATA");
    if (this.allProjectInfo[0].projectType == "Enablement" || this.allProjectInfo[0].projectType == "Internal Product") {
      status = 'Deployed-IDC';
    }
    else if (this.allProjectInfo[0].projectType == "Customer" && this.allProjectInfo[0].locationType == "EDC") {
      status = 'Deployed-EDC';
    }
    else if (this.allProjectInfo[0].projectType == "Customer" && this.allProjectInfo[0].locationType == "CDC") {
      status = 'Deployed-CDC';
    } else {
      status = 'Deployed-' + this.allProjectInfo[0].locationType;
    }

    console.log("geneic value", value, dataArray, data);
    for (var i = 0; i < value.mapresouce.length; i++) {
      value.mapresouce[i].startDate = this.formatDate(value.mapresouce[i].startDate);
      value.mapresouce[i].endDate = this.formatDate(value.mapresouce[i].endDate);
      // value.mapresource[i].genericStatus="approved"
      value.mapresouce[i].genericEmployeeStatus = status;
      this.allocateResourseArray.push(value.mapresouce[i]);
      console.log(this.allocateResourseArray, "MAPPING DAATA");

      //console.log("anusha",this.allocateResourseArray.push(value.mapresouce[i].resourcegenericId))

    }


    let genresp;

    this.bookingidre = data.bookingId
    var request =
    {
      "rmgInfo":
      {

        "bookingId": data.bookingId,
        "resourceType": data.resourceType,
        "status": "approved"


      },




      "rmgGenericList": [
        {
          "genericId": this.updategenericid,


          "rmggenericresourcemap": this.allocateResourseArray,



        }],



      "transactiontype": "update"
    }


    console.log("log of request", request);

    this.rmgService.updateresources(request).subscribe(res => {
      console.log(res, "response")
      genresp = res;

      console.log(genresp, "GENERIC DATA");

      this.getAllResources();
      var message = "Resources allocated successfully"
      if (res) {
        this.book(this.bookingidre, this.e);

        this.getAllResources();
        this.genericformbook.reset();
      }
      this.toastr.success(message)


    }
      , err => {
        console.log(err)
        this.toastr.info('Resources not allocated')


      })

  }

  getAllSkill(a) {
    this.skilledEmployees=[]
    var request =
    {

      "skilllist":

      {

        "resourceSkills": a.resourceSkills,
        "resourceExperience": a.resourceExperience

      },



      "transactiontype": "getskills"
    }
    console.log("skills data", request);

    this.rmgService.getAllSkills(request).subscribe(res => {
      this.skillList = res;
      this.skillBasedEmployees = this.skillList.empIdList

      console.log("All Emp :", this.empbasicinfo);

      for (let i = 0; i < this.skillBasedEmployees.length; i++) {
        var obj=this.empbasicinfo.find(d=>d.employeeId==this.skillBasedEmployees[i])
        console.log("employess",obj);
        this.skilledEmployees.push(obj);
        
      }
      console.log("Emp Name : ", this.skillBasedEmployees[0].fullName);
      
      console.log(this.skillBasedEmployees, "skill based employees");

    })

  }

  propose(a) {

    console.log(a, "alternate data")
    this.proposalData = a;


  }





  cancelAllocation() {
    this.allocateResourseArray = [];

    this.genericformbook.controls.mapresouce.clearValidators();
    this.genericformbook.reset();


  }


  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }
  saveBtn: boolean;
  resourerepeat(e) {
    console.log(e.value.AddResourcespecific, "vaule")

    var uniqueCount = e.value.AddResourcespecific.map(d => d.resourceId)


    this.selectedActivitycount = this.calculateDuplicate(uniqueCount)
    if (this.selectedActivitycount > 0) {
      this.toastr.info('Please employees should not be duplicated')
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


  resourerepeated(e) {
    console.log(e, "formvalue")
    var uniqueCount = e.value.mapresouce.map(d => d.empId)


    this.selectedActivitycount = this.calculateDuplicated(uniqueCount)
    if (this.selectedActivitycount > 0) {
      this.toastr.info('Please employees should not be duplicated')
      this.saveBtn = true
    } else {
      this.saveBtn = false
    }
  }
  calculateDuplicated(e) {
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

  // resourerepeatedproposal(e){
  //   console.log(e,"formvalue")
  //   var uniqueCount=e.value.map(d=>d.resourceId1)


  //   this.selectedActivitycount = this.calculateDuplicatedproposal(uniqueCount)
  //   if (this.selectedActivitycount > 0) {
  //     this.toastr.info( 'Please employees should not be duplicated')
  //     this.saveBtn = true
  //   } else {
  //     this.saveBtn = false
  //   }
  // }
  // calculateDuplicatedproposal(e) {
  //   var duplicate = 0;
  //   var counts = {};
  //   e.forEach(function (i) { counts[i] = (counts[i] || 0) + 1; });
  //   for (var key in counts) {
  //     if (counts.hasOwnProperty(key)) {
  //       counts[key] > 1 ? duplicate++ : duplicate;
  //     }
  //   }
  //   return duplicate;
  // }





  getProjectDetails(e) {


    var getID = {
      "projectInfo": {
        "projectId": e
      },
      "transactionType": "getByProId"
    }
    this.psaService.getIdProject(getID).pipe(takeUntil(this.projectAll)).subscribe(response => {
      console.log(response, "GETALL PROJECTS");

      this.getallProjects = response;

      console.log(this.getallProjects, "ALLLL")
      this.allProjectInfo = this.getallProjects.projectList;
      console.log(this.allProjectInfo[0].projectName, "console data");

    })
  }

  getAllEmployeedetails(){
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
            if(this.loggedUserName==this.managersList[i]){
              this.showRequestbutton=true
            }
          }
      })
  }
}
