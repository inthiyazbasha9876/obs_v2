import { RmgService } from './../../services/rmg.service';
// Murali A

import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl, AbstractControl } from '@angular/forms';
import { throwMatDialogContentAlreadyAttachedError, throwToolbarMixedModesError } from '@angular/material';
import { HrmsService } from '../../services/hrms.service';
import { BehaviorSubject, Observable, Subscription, Subject } from 'rxjs';
import swal from 'src/assets/sweetalert';
import { inputs } from '@syncfusion/ej2-angular-navigations/src/accordion/accordion.component';
import { takeUntil, filter } from 'rxjs/operators';
import { PsaService } from '../../services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { AuthService } from 'src/app/services/auth.service';



@Component({
  selector: 'app-project-psa',
  templateUrl: './project-psa.component.html',
  styleUrls: ['./project-psa.component.scss']
})
export class ProjectPSAComponent implements OnInit {

  private projectAll: Subject<any> = new Subject();


  projectData;
  dataProject;
  getId: any;
  getIdData: any;
  hideToEdit = true;
  // tblupdate = true;
  editTo = true;
  updateResponse: any;
  customer: any;
  customerData: any;
  customerId: any;
  contract: any;
  contractData: any;


  contractFilter: any;
  locTypes: any;
  loc;
  delivery: any;
  deliveryLoc: any;
  serviceCat: any;
  serviceCategoryList: any;
  serv: any;
  serviceTypes: any;
  billingTypeList: any;
  billingTypes;
  rateTypes: any;
  rateTypeList: any;
  techStackResp: any;
  techStacks: any;
  task: any;
  taskList: any;
  saveSuccess: any;
  contractDisable = true;
  contractDisableToTable = true;
  cus: any;
  contractIdGet: any;
  contractId: any;

  contractTableID: any;
  minStartDate: Date;
  maxStartDate: Date;
  minEndDate: Date;
  maxEndDate: Date;
  contr: any;
  contractDataTo: any;
  disableDate = true;
  procon: any;
  contractPro: any;
  countValue: any;
  contractValue: any;
  valueToShow;
  showBalance = false;
  greenShow = false;
  redShow = false;
  valueContract: any;
  show = true;
  onShow = false;
  business: any;
  businessUnit: any;
  subBusiness: any;
  subBusinessUnit: any;
  empbasic: any;
  empbasicin: any;
  loggedUserName;
  msgApprove: any;
  msg: any;
  msgdelete: any;
  manager: any[];
  employee: any[];
  managerdata = [];
  empDesignationDetails: any;
  empDesignationlist: any;
  ipppd = 5;
  empObjTech;
  errorContract = false;
  reason: any;
  reasonForBU = false;
  custId: any;
  contId: any;
  role: boolean;

  roleEdit: boolean;
  roleStatus: boolean;
  roleFinance: boolean;
  cusconeditHide = false;
  dataTo: any;
  customerDataToShow: any;
  customerserviceName: any;
  encryptedUsername: string;
  encryptedRole: string;
  role1: any;
  errorToEnter: boolean = false;
  editprojectvalue: any;
  empsorteddata: any;
  mapresourceData: any = [];
  internalNgModel: any;
  isCustomer: boolean = false;
  isNotCustomer: boolean = false;
  editIsNotCustomer: boolean;
  editIsCustomer: boolean;
  projectTypedata: any;
  techlead: any;
  managers: any[];
  buHead: any;
  sbuHead: any;
  contractbuHead: any;
  contractsbuHead: any;
  originalData: any[];
  rmgdata: any[];
  mappedMessage: boolean;
  rmgresource: any = [];
  empall: any;
  skillinfolist: any;
  skillInfoList: any;
  resourceId: any;
  resourceArray: any = [];
  allEmps: any = false
  noRecords: any = false
  otherTask: any = false
  taskvalues: any = []
  otherTaskValue: any = []
  editTaskValues: any = [];
  mileStonesArray: any = [];
  resourcesInProject: any;
  availabilityArray: any = [];
  projectNameGet: any;
  hoursCheck: any;
  dontAllowResource: boolean = true;
  editResource: boolean;
  checkHours: number;
  showErrorHours: boolean;
  releaseResourceArray: any = [];
  allowToRelease: boolean;
  allowupdateResource: boolean;
  showErrorBilling: boolean;
  constructor(private authService: AuthService, private formBuild: FormBuilder, private psaService: PsaService, private hrms: HrmsService, private toast: NotificationService, private rmgService: RmgService) {
    //this.getIdcontract();
    // console.log("projectstatus",this.project);

  }

  ngOnInit() {
    this.encryptedRole = sessionStorage.getItem('Role');
    this.role1 = this.authService.decryptData(this.encryptedRole);
    this.getAllCustomer();
    this.getAllContract();

    this.getHide();
    this.user();

    // this.getresources();
    // this.loggedUserName = sessionStorage.getItem('UserName');

    this.encryptedUsername = sessionStorage.getItem('UserName');
    this.loggedUserName = this.authService.decryptData(this.encryptedUsername);

    // this.getIdData = JSON.parse(sessionStorage.getItem('moreInfo'));
    if (this.getIdData != null) {
      this.disableDate = true;
      //  this.getContractId(this.getIdData.contractId);
    }
  }

  ngOnDestroy(): any {
    this.projectAll.next();
    this.projectAll.complete();
  }

  user() {
    if (this.role1 == "ROLE_DM" || this.role1 == "ROLE_BDM" || this.role1 == "ROLE_BDMHEAD" || this.role1 == "ROLE_ADMIN" || this.role1 == "ROLE_STAFFAUGHEAD" || this.role1 == "ROLE_SALES" || this.role1 == "ROLE_SBUHEAD") {
      this.role = true;
      this.roleEdit = true;
      this.getLocationType();
      this.getDelivaryLoc();
      this.getServiceCategory();
      this.getServiceType();
      this.getBillingType();
      this.getRateType();
      this.getTechStack();
      this.getTasks();
      this.getBusinessUnit();
      this.getSubBusinessUnit();
      this.getEmpDesignation();
      this.getProjectTypes();
      this.getSkillInfo();

      this.getAllContract();
      this.getAllCustomer();

      this.getAllProject();
      this.getAllEmployees();
    }
    if (this.role1 == "ROLE_BUHEAD") {
      this.roleStatus = true;
      this.getAllContract();
      this.getAllCustomer();
      this.getAllProject();
      this.getAllEmployees();

    }
    if (this.role1 == "ROLE_FINANCE") {
      this.roleFinance = true;
      this.getAllContract();
      this.getAllCustomer();
      this.getAllProject();
      this.getAllEmployees();

    }
    else {
      this.getAllContract();
      this.getAllCustomer();
      this.getAllProject();
      this.getAllEmployees();

    }
  }


  project = this.formBuild.group({
    customerChange: [null, Validators.required],
    contractChange: [null, Validators.required],
    projectName: [null, Validators.required],
    projectDescription: [null],
    projectStartDate: [null],
    projectEndDate: [null],
    projectManager: [null],
    projectTechnicalLead: [null],
    projectValue: ['', [(control: AbstractControl) => Validators.max(this.valueToShow)(control), Validators.min(0)]],
    serviceType: [null],
    billingType: [null],
    rateType: [null],
    projectTechStack: [null],
    serviceCategory: [null],
    projectType: [null],
    projectDelivaryLocation: [null],
    locationType: [null],
    // numberOfResources: [null, [Validators.required, (control: AbstractControl) => Validators.min(0)(control)]],
    tasks: [null],
    otherTask: [null],
    resourceId: [null],
    billRate: [null],
    resourceStartDate: [null],
    resourceEndDate: [null],
    numberOfHours: [null],
    // resources: [null, Validators.required],
    mileName: [null],
    mileStartDate: [null],
    mileEndDate: [null],
    projectColor: [null],
    mileBillingAmount: [null]

  })

  getHide() {
    // if (sessionStorage.getItem('hideToEdit') == "false") {
    //   this.hideToEdit = false;
    // }
    // else {
    //   this.hideToEdit = true;
    // }
  }
  dataManager: any = [];
  getAllProject() {

    var getProject = {
      "projectInfo": {
      },
      "transactionType": "getall"
    }


    this.psaService.getAllProject(getProject).pipe(takeUntil(this.projectAll)).subscribe(response => {
      // if(response){
      //   this.getAllCustomer();
      //   this.getAllContract();
      // }
      this.projectData = response;
      this.dataTo = this.projectData.projectList;
      console.log("data in project", this.dataTo);


      // for (let i = 0; i < this.dataTo.length; i++) {

      //   this.dataTo[i].customerId = this.getCustomerName(this.dataTo[i].customerId)
      //   this.dataTo[i].contractId = this.getContractName(this.dataTo[i].contractId)

      // }
      if (this.role1 == "ROLE_MANAGER") {

        this.getAllEmployees();

        this.dataProject = this.dataTo.filter(info => info.projectResourceMapping.projectManager == this.loggedUserName);
        // console.log("projectDATA Manager", this.dataProject);

      }

      else {

        this.dataProject = this.dataTo;
        // console.log("projectDATA", this.dataProject);

      }

      for (let i = 0; i < this.dataTo.length; i++) {
        this.dataTo[i].contractId = this.getContractName(this.dataTo[i].contractId)

        this.dataTo[i].customerId = this.getCustomerName(this.dataTo[i].customerId)
      }


    })
  }

  getCustomerName(idCustomer) {

    let custName = this.customerData.find(data => data.customerId == idCustomer)
    return custName.customerName
  }

  getContractName(idContract) {

    let contractName = this.contractData.find(data => data.contractid == idContract)
    return contractName.contractname
  }


  moreinfo(id) {


    this.mileStonesArray = [];
    var getID = {
      "projectInfo": {
        "projectId": id
      },
      "transactionType": "getByProId"
    }

    this.psaService.getIdProject(getID).pipe(takeUntil(this.projectAll)).subscribe(response => {
      this.getId = response;

      // sessionStorage.setItem('hideToEdit', "false");
      // this.getHide();
      this.hideToEdit = false;
      this.getIdData = this.getId.projectList[0];


      this.getNameBUSBU(this.getIdData.projectResourceMapping.projectManager, this.getIdData.projectResourceMapping.techLead, this.getIdData.buHead, this.getIdData.sbuHead)

      this.editprojectvalue = this.getIdData.projectRatecard.projectValue;

      // sessionStorage.setItem('moreInfo', JSON.stringify(this.getIdData));
      console.log("selected data of project", this.getIdData);

      this.mileStonesArray = [];
      for (let n of this.getIdData.milestones) {
        console.log("dtta", n);

        this.mileStonesArray.push(n);
      }
      console.log("mile stones", this.mileStonesArray);

      console.log("Location types : ", this.locTypes);

      this.getContractId(this.getIdData.contractId);

      this.disableDate = false;

      this.getProjectContractID(this.getIdData.contractId);

      this.show = true;
      this.onShow = false;

      if (this.getIdData.projectType == "Customer" || this.getIdData.projectType == "customer") {
        this.editIsCustomer = true;
      }
      else {
        this.editIsNotCustomer = true;
      }

      if (this.getIdData.buStatus == "Approved" || this.getIdData.buStatus == "Pending") {
        this.reasonForBU = false;
      }
      if (this.getIdData.buStatus == "Approved" || this.getIdData.buStatus == "Rejected" && this.role1 == "ROLE_BUHEAD") {
        this.roleStatus = false;
      }
      if (this.getIdData.buStatus == "Pending" && this.role1 == "ROLE_BUHEAD") {
        this.roleStatus = true;
      }
      if (this.getIdData.buStatus == "Rejected" || this.getIdData.financeStatus == "Rejected") {

        this.reason = this.getIdData.comment;
        this.reasonForBU = true;
        console.log("Reason", this.reason);
      }
      if (this.getIdData.buStatus == "Rejected" || this.getIdData.buStatus == "Pending" && this.role1 == "ROLE_FINANCE") {
        this.roleFinance = false;
      }
      if (this.getIdData.buStatus == "Approved" && this.getIdData.financeStatus == "Pending" && this.role1 == "ROLE_FINANCE") {
        this.roleFinance = true;
      }

      if (this.getIdData.finaceStatus == "Approved" || this.getIdData.financeStatus == "Pending") {
        this.reasonForBU = false;
      }
      if (this.getIdData.financeStatus == "Approved" || this.getIdData.financeStatus == "Rejected" && this.role1 == "ROLE_FINANCE") {
        this.roleFinance = false;
      }


      this.rmgresource = [];
      this.getresources(this.getIdData.projectId);
      // this.rmgdata = this.originalData.filter(d => d.projectId == id)


      // console.log("rmgData", this.rmgdata);

      // for (let k = 0; k < this.rmgdata.length; k++) {

      //   if (this.rmgdata[k].resourceType.toLowerCase() == "specific") {
      //     let resourseSpecific = this.empbasicin.filter(data => data.employeeId == this.rmgdata[k].resource.employeeId)
      //     let resourceObj = {
      //       "employeeId": resourseSpecific[0].employeeId,
      //       "fistname": resourseSpecific[0].firstname,
      //       "lastname": resourseSpecific[0].lastname,
      //       "title": resourseSpecific[0].title,
      //       "startDate": this.rmgdata[k].resource.startDate,
      //       "endDate": this.rmgdata[k].resource.endDate,
      //       "billRate": this.rmgdata[k].resource.billRate

      //     }
      //     console.log("resourceObj", resourceObj);
      //     if (resourseSpecific.length != 0) {
      //       this.rmgresource.push(resourceObj);

      //     }
      //   }
      //   else {
      //     let resourseGeneric = this.empbasicin.filter(data => data.employeeId == this.rmgdata[k].resource.empId)
      //     let resourceObj = {
      //       "employeeId": resourseGeneric.employeeId,
      //       "fistname": resourseGeneric.firstname,
      //       "lastname": resourseGeneric.lastname,
      //       "title": resourseGeneric.title,
      //       "startDate": this.rmgdata[k].resource.startDate,
      //       "endDate": this.rmgdata[k].resource.endDate,
      //       "billRate": this.rmgdata[k].resource.billRate

      //     }
      //     console.log("resourceObj", resourceObj);
      //     if (resourseGeneric.length != 0) {
      //       this.rmgresource.push(resourceObj);

      //     }
      //   }




      // }
      // if (this.rmgdata.length == 0) {
      //   console.log("nulllll");
      //   this.mappedMessage = true

      // } else {
      //   this.mappedMessage = false
      // }

      // console.log("resources of this project", this.rmgresource);

      for (var i = 0; i < this.customerData.length; i++) {
        if (this.getIdData.customerId == this.customerData[i].customerId) {
          this.custId = this.customerData[i].customerName;
        }
      }
      for (var i = 0; i < this.contractIdGet.length; i++) {
        if (this.getIdData.contractId == this.contractIdGet[i].contractid) {
          this.contId = this.contractIdGet[i].contractname;
        }
      }







    })


  }

  getNameBUSBU(managerGet, techLead, bu, sbu) {
    // this.managerdata=[]
    this.getAllEmployees();

    this.techlead = this.empObjTech.filter(lead => lead.employeeId == techLead);
    console.log("techlead", this.techlead);

    this.managers = this.managerdata.filter(manager => manager.employeeId == managerGet);

    console.log("managers of get", this.managers);

    this.buHead = this.empbasicin.filter(emp => emp.employeeId == bu);
    console.log("bu name", this.buHead);
    this.sbuHead = this.empbasicin.filter(emp => emp.employeeId == sbu);
    console.log("sbu name", this.sbuHead);
  }
  getNames(name) {
    // console.log('ids of resources',name);


    let empinfodata = this.empbasicin.filter(names => names.employeeId == name);

    // console.log("resourse daataa", empinfodata);
    let empData = empinfodata[0].firstname + ' ' + empinfodata[0].lastname
    return empData
  }

  back() {
    this.rmgresource = [];
    this.mileStonesArray = [];
    this.hideToEdit = true;
    // this.tblupdate = true;
    this.editTo = true;
    this.getAllProject();
    // sessionStorage.removeItem('hideToEdit');
    // sessionStorage.removeItem('moreInfo');
    if (this.getIdData.status == "Approved" && this.role1 == "ROLE_BUHEAD") {
      this.roleStatus = true;
    }
    if (this.getIdData.status == "Rejected" && this.role1 == "ROLE_BUHEAD") {
      this.roleStatus = true;
    }
    this.mapresourceData = [];
  }

  formatDate(date) {

    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }

  createProject(proDataSave) {
    let response
    this.isNotCustomer = false;
    this.isCustomer = false;
    for (let i in this.project.value.tasks) {
      if (this.project.value.tasks[i] == 'other') {
        continue
      } else {
        this.taskvalues.push(this.project.value.tasks[i])
      }
    }
    let startDate
    let endDate
    if (proDataSave.projectStartDate != null) {
      startDate = this.formatDate(new Date(proDataSave.projectStartDate))
    } else {
      startDate = null
    }
    if (proDataSave.projectEndDate != null) {
      endDate = this.formatDate(new Date(proDataSave.projectEndDate))
    } else {
      endDate = null
    }

    let mileArray = [];
    for (let i in this.mileStonesArray) {
      let startDate
      let endDate
      if (this.mileStonesArray[i].startDate != null) {
        startDate = this.formatDate(new Date(this.mileStonesArray[i].startDate))
      } else {
        startDate = null
      }
      if (this.mileStonesArray[i].endDate != null) {
        endDate = this.formatDate(new Date(this.mileStonesArray[i].endDate))
      } else {
        endDate = null
      }
      let obj = {
        "milestoneName": this.mileStonesArray[i].milestoneName,
        "startDate": startDate,
        "endDate": endDate,
        "billingAmount": this.mileStonesArray[i].billingAmount

      }

      mileArray.push(obj)
    }




    // var finaltask = [...this.taskvalues, ...this.otherTaskValue]
    var data = {
      "projectInfo": {
        "projectName": proDataSave.projectName,
        "projectDescription": proDataSave.projectDescription,
        "startDate": startDate,
        "endDate": endDate,
        "customerId": proDataSave.customerChange,
        "contractId": proDataSave.contractChange,
        "servicecategory": proDataSave.serviceCategory,
        "projectType": proDataSave.projectType,
        "deliveryLocation": proDataSave.projectDelivaryLocation,
        "locationType": proDataSave.locationType,
        "tasks": this.taskvalues,
        "additionalTasks": this.otherTaskValue,
        "createdBy": this.loggedUserName,
        "buHead": this.contractDataTo.buHead,
        "sbuHead": this.contractDataTo.sbuHead,
        "buStatus": "Pending",
        "financeStatus": "Pending",
        "projectColor": proDataSave.projectColor

      },
      "resourceMap":
      {

        // "resourceCount": proDataSave.numberOfResources,
        "projectManager": proDataSave.projectManager,
        "techLead": proDataSave.projectTechnicalLead,
        "techStack": proDataSave.projectTechStack,
        //  "resources": proDataSave.resources
      },
      "rateCard": {

        "projectValue": proDataSave.projectValue,
        "serviceType": proDataSave.serviceType,
        "billingType": proDataSave.billingType,
        "rateType": proDataSave.rateType
      },
      "milestones": mileArray,
      "transactionType": "save"
    }

    console.log("data", data);

    this.contractDisable = true;
    this.show = true;
    this.onShow = false;
    this.psaService.saveProject(data).pipe(takeUntil(this.projectAll)).subscribe(res => {
      response = res
      this.toast.success(response.message)
      this.getAllProject()
      console.log("save data", res);
      this.project.reset();
      this.mileStonesArray = [];
      let projectId = response.projectList[0].projectId
      let array = []
      let status;
      if (response.projectList[0].projectType == "Enablement" || response.projectList[0].projectType == "Internal Product") {
        status = 'Deployed-IDC';
      }
      else if (response.projectList[0].projectType == "Customer" && response.projectList[0].locationType == "EDC") {
        status = 'Deployed-EDC';
      }
      else if (response.projectList[0].projectType == "Customer" && response.projectList[0].locationType == "CDC") {
        status = 'Deployed-CDC';
      }
      else {
        status = 'Deployed-' + response.projectList[0].locationType;
      }
      this.mileStonesArray = [];
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
          "employeeId": this.resourceArray[i].empId,
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
            "projectId": projectId,
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
          this.project.reset();

        }, err => {
          console.log(err);
          var err = err;
          this.getAllProject();
          this.toast.error(err.error.message)
        })
      }

    },
      err => {
        console.log(err);
        this.getAllProject();
        this.project.reset();
        this.toast.info(err.error.message, 'Info')
      }
    )

  }


  //edit

  edit() {
    // this.tblupdate = false;
    this.editTo = false;
    this.isCustomer = false;
    this.isNotCustomer = false;
    this.otherTask = false
    this.resourceArray = []
    this.otherTaskValue = []
    this.availabilityArray = []
    this.allEmps = false
    console.log("getIdData", this.getIdData);

    this.project.controls.projectName.setValue(this.getIdData.projectName);
    this.project.controls.customerChange.setValue(this.getIdData.customerId);
    this.selected_customer(this.getIdData.customerId);
    this.project.controls.contractChange.setValue(this.getIdData.contractId);
    this.getContractId(this.getIdData.contractId);
    this.project.controls.projectType.setValue(this.getIdData.projectType);

    this.project.controls.projectStartDate.setValue(this.getIdData.startDate);

    this.project.controls.projectEndDate.setValue(this.getIdData.endDate);

    this.project.controls.projectDescription.setValue(this.getIdData.projectDescription);
    this.project.controls.serviceCategory.setValue(this.getIdData.servicecategory);

    this.project.controls.projectDelivaryLocation.setValue(this.getIdData.deliveryLocation);

    this.project.controls.locationType.setValue(this.getIdData.locationType);

    this.project.controls.tasks.setValue(this.getIdData.tasks);
    this.otherTaskValue = this.getIdData.additionalTasks

    this.project.controls.projectManager.setValue(this.getIdData.projectResourceMapping.projectManager);

    this.project.controls.projectTechnicalLead.setValue(this.getIdData.projectResourceMapping.techLead);
    this.project.controls.projectTechStack.setValue(this.getIdData.projectResourceMapping.techStack);
    console.log("projectDetails", this.project);

    this.project.controls.projectValue.setValue(this.getIdData.projectRatecard.projectValue);
    this.project.controls.serviceType.setValue(this.getIdData.projectRatecard.serviceType);
    this.project.controls.billingType.setValue(this.getIdData.projectRatecard.billingType);
    this.project.controls.rateType.setValue(this.getIdData.projectRatecard.rateType);
    this.project.controls.projectColor.setValue(this.getIdData.projectColor);

    console.log("projectDetails", this.project);





  }

  cancel() {
    this.editTo = true;
    // this.tblupdate = true;
    this.mapresourceData = [];
    this.mileStonesArray = [];
    this.moreinfo(this.getIdData.projectId);
    this.contractDisable = true;
    this.disableDate = true;
    this.showBalance = false;
    this.show = true;
    this.onShow = false;
    this.isCustomer = false;
    this.isNotCustomer = false;
  }

  updateproject() {
    this.mapresourceData = [];
    var proRes
    var RmgRes
    var array = []
    // var finaltask = [...this.editTaskValues, ...this.otherTaskValue]
    for (let i in this.project.value.tasks) {
      if (this.project.value.tasks[i] == 'other') {
        continue
      } else {
        this.taskvalues.push(this.project.value.tasks[i])
      }
    }
    console.log(this.otherTaskValue, this.taskvalues);

    let startDate
    let endDate
    if (this.project.controls.projectStartDate.value != null) {
      startDate = this.formatDate(new Date(this.project.controls.projectStartDate.value))
    } else {
      startDate = null
    }
    if (this.project.controls.projectEndDate.value != null) {
      endDate = this.formatDate(new Date(this.project.controls.projectEndDate.value))
    } else {
      endDate = null
    }
    let mileArray = [];
    for (let i in this.mileStonesArray) {
      let startDate
      let endDate
      if (this.mileStonesArray[i].startDate != null) {
        startDate = this.formatDate(new Date(this.mileStonesArray[i].startDate))
      } else {
        startDate = null
      }
      if (this.mileStonesArray[i].endDate != null) {
        endDate = this.formatDate(new Date(this.mileStonesArray[i].endDate))
      } else {
        endDate = null
      }
      let obj = {
        "milestoneId": this.mileStonesArray[i].milestoneId,
        "milestoneName": this.mileStonesArray[i].milestoneName,
        "startDate": startDate,
        "endDate": endDate,
        "billingAmount": this.mileStonesArray[i].billingAmount

      }

      mileArray.push(obj)
    }
    var data = {
      "projectInfo": {
        "projectId": this.getIdData.projectId,
        "projectName": this.project.controls.projectName.value,
        "projectDescription": this.project.controls.projectDescription.value,
        "startDate": startDate,
        "endDate": endDate,
        "customerId": this.project.controls.customerChange.value,
        "contractId": this.project.controls.contractChange.value,
        "servicecategory": this.project.controls.serviceCategory.value,
        "projectType": this.project.controls.projectType.value,
        "deliveryLocation": this.project.controls.projectDelivaryLocation.value,
        "locationType": this.project.controls.locationType.value,
        "tasks": this.taskvalues,
        "additionalTasks": this.otherTaskValue,
        "buHead": this.getIdData.buHead,
        "sbuHead": this.getIdData.sbuHead,
        "buStatus": "Pending",
        "financeStatus": "Pending",
        "createdBy": this.loggedUserName,
        "updatedBy": this.loggedUserName,
        "projectColor": this.project.controls.projectColor.value
      },
      "resourceMap":
      {
        "resourceMappingId": this.getIdData.projectResourceMapping.resourceMappingId,
        // "resourceCount": dataValue.resourcecount,
        "projectManager": this.project.controls.projectManager.value,
        "techLead": this.project.controls.projectTechnicalLead.value,
        "techStack": this.project.controls.projectTechStack.value,
        // "resources": dataValue.resources
      },
      "rateCard": {
        "ratecardId": this.getIdData.projectRatecard.ratecardId,
        "projectValue": this.project.controls.projectValue.value,
        "serviceType": this.project.controls.serviceType.value,
        "billingType": this.project.controls.billingType.value,
        "rateType": this.project.controls.rateType.value
      },
      "milestones": mileArray,


      "transactionType": "update"
    }

    console.log("update", data);
    this.editTo = true;
    // this.tblupdate = true;

    this.psaService.saveProject(data).pipe(takeUntil(this.projectAll)).subscribe(response => {
      proRes = response
      this.getAllProject();
      this.mileStonesArray = [];
      this.moreinfo(this.getIdData.projectId);
      this.toast.success(proRes.message)
      console.log(response)
      this.updateResponse = response;
      if (this.resourceArray.length != 0) {
        let status;
        if (data.projectInfo.projectType == "Enablement" || data.projectInfo.projectType == "Internal Product") {
          status = 'Deployed-IDC';
        }
        else if (data.projectInfo.projectType == "Customer" && data.projectInfo.locationType == "EDC") {
          status = 'Deployed-EDC';
        }
        else if (data.projectInfo.projectType == "Customer" && data.projectInfo.locationType == "CDC") {
          status = 'Deployed-CDC';
        }
        else {
          status = 'Deployed-' + data.projectInfo.locationType;
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
            "employeeId": this.resourceArray[i].empId,
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
              "projectId": data.projectInfo.projectId,
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
            RmgRes = res
            this.toast.success(RmgRes.message)
            this.getAllProject();
            this.resourceArray = [];
            this.rmgresource = [];
            this.mileStonesArray = [];
            this.moreinfo(this.getIdData.projectId);
          }, err => {
            RmgRes = err
            this.toast.error(RmgRes.error.message)
            this.resourceArray = [];
            this.rmgresource = [];
          })
        }
      }

      if (this.rmgresource.length != 0 && this.allowupdateResource == true) {
        console.log("came to update");

        let status;
        if (data.projectInfo.projectType == "Enablement" || data.projectInfo.projectType == "Internal Product") {
          status = 'Deployed-IDC';
        }
        else if (data.projectInfo.projectType == "Customer" && data.projectInfo.locationType == "EDC") {
          status = 'Deployed-EDC';
        }
        else if (data.projectInfo.projectType == "Customer" && data.projectInfo.locationType == "CDC") {
          status = 'Deployed-CDC';
        }
        else {
          status = 'Deployed-' + data.projectInfo.locationType;
        }

        for (let i in this.rmgresource) {
          let startDate
          let endDate
          if (this.rmgresource[i].startDate != null) {
            startDate = this.formatDate(new Date(this.rmgresource[i].startDate))
          } else {
            startDate = null
          }
          if (this.rmgresource[i].endDate != null) {
            endDate = this.formatDate(new Date(this.rmgresource[i].endDate))
          } else {
            endDate = null
          }
          let obj = {
            "specificId": this.rmgresource[i].specificId,
            "specificEmployeeStatus": status,
            "specificStatus": "booked",
            "reason": "",
            "startDate": startDate,
            "endDate": endDate,
            "billRate": Number(this.rmgresource[i].billRate),
            "employeeId": this.rmgresource[i].employeeId,
            "hoursOfAllocation": this.rmgresource[i].hoursOfAllocation,
            "alternateemployeeId": "",
            "flag": 1

          }

          //   array.push(obj)
          // }
          // console.log("array obj", array);
          // if (array.length != 0) {
          let reqObj = {
            "rmgInfo": {

              "bookingId": this.rmgresource[i].bookingId,
              "resourceType": "Specific",
              "projectId": data.projectInfo.projectId,
              "status": "pending",
              "flag": 1,
              "message": "booked"
            },

            "rmgSpecificList": [obj],


            "transactiontype": "updatebooking"
          }
          console.log("resource", reqObj);
          this.rmgService.saveresources(reqObj).subscribe(res => {
            console.log("my response", res);
            RmgRes = res
            this.toast.success(RmgRes.message)
            this.resourceArray = [];
            this.rmgresource = [];
            this.getresources(data.projectInfo.projectId)


          }, err => {
            RmgRes = err
            this.toast.error(RmgRes.error.message)
            this.resourceArray = [];
            this.rmgresource = [];
          })
        }
        this.getAllProject();
        this.mileStonesArray = [];
        this.moreinfo(this.getIdData.projectId);
      }

      if (this.allowToRelease == true && this.releaseResourceArray.length != 0) {
        console.log('resource release', this.releaseResourceArray);

        for (let release in this.releaseResourceArray) {
          let response;
          let obj =
          {
            "rmgInfo":
            {

              "bookingId": this.releaseResourceArray[release].bookingId,
              "resourceType": 'specific'

            },

            "rmgSpecificList": [{
              "specificId": this.releaseResourceArray[release].specificId,
              "specificEmployeeStatus": status,
              "employeeId": this.releaseResourceArray[release].employeeId,
              "flag": 0

            }],

            "transactiontype": "release"
          }

          this.rmgService.releaseRes(obj).subscribe(res=>{
            response = res;
            console.log("response on release resource", response);
            this.rmgresource = [];
            this.getresources(data.projectInfo.projectId)
          })
        }

      }

      this.rmgresource = [];
      // this.project.reset();
      this.getresources(data.projectInfo.projectId)
    }, err => {
      if (err) {
        console.log(err);

        this.toast.info('Not Updated', 'Error')
      }
    });
  }

  //get Customer

  getAllCustomer() {
    var data = {
      "customerList":
      {


      },
      "transactionType": "getall"
    }
    this.psaService.getAllCustomer(data).pipe(takeUntil(this.projectAll)).subscribe(response => {
      // console.log("all customer", response)
      this.customer = response;
      this.customerData = this.customer.customerList;

    });

  }

  getIDCustomer(id) {
    var resCustomer;
    var data = {
      "customerList":
      {
        "customerId": id

      },

      "transactionType": "getbyid"
    }
    this.psaService.getByIDListofCustomer(data).pipe(takeUntil(this.projectAll)).subscribe(res => {
      resCustomer = res;
      this.customerDataToShow = resCustomer.customerList[0];
      console.log("getByidcustomer", this.customerDataToShow);
      // for (var i = 0; i < this.serviceTypes.length; i++) {
      //   if (this.customerDataToShow.servicetype[0].servicetypeid == this.serviceTypes[i].id) {
      //     this.customerserviceName = this.serviceTypes[i].serviceTypeName
      //   }
      // }
    })
  }

  // get contract
  getAllContract() {
    var data = {
      "customercontractdetailslist": [
        {

        }],
      "transactiontype": "getAll"
    }

    this.psaService.getAllContractdDetails(data).pipe(takeUntil(this.projectAll)).subscribe(response => {


      this.contract = response;
      this.contractData = this.contract.customercontractdetailslist;
      this.getAllProject();
      // console.log("contract DATA", this.contractData)

    })
  }

  getIdcontract(id) {
    var data = {
      "customercontractdetailslist": [
        {
          "customerid": id
        }],
      "transactiontype": "getbycustid"
    }
    console.log("data customer select to contract", data);


    this.psaService.getIdContract(data).pipe(takeUntil(this.projectAll)).subscribe(response => {
      console.log("get id contract", response)
      this.cus = response;

      this.contractIdGet = this.cus.customercontractdetailslist.filter(res => res.financeStatus == "Approved");


    })
  }


  getContractId(id) {
    var data = {
      "customercontractdetailslist": [
        {

          "contractid": id



        }],
      "transactiontype": "getbyid"
    }

    this.onShow = true;

    this.cusconeditHide = true;

    this.show = false;

    this.psaService.getIdContract(data).pipe(takeUntil(this.projectAll)).subscribe(response => {
      console.log("contract selected", response)
      this.contr = response;
      this.contractDataTo = this.contr.customercontractdetailslist[0];
      this.contractbuHead = this.empbasicin.filter(data => data.employeeId == this.contractDataTo.buHead)
      this.contractsbuHead = this.empbasicin.filter(data => data.employeeId == this.contractDataTo.sbuHead)

      this.contractValue = this.contractDataTo.contractvalue;

      this.minStartDate = new Date(this.contractDataTo.startdate);
      this.maxStartDate = new Date(this.contractDataTo.enddate);
      console.log("dates ", this.minStartDate, this.maxStartDate);
      this.disableDate = false;
      this.getProjectContractID(id);
      this.valueContract = this.contractValue - this.valueToShow;
      console.log("contract value", this.valueContract);
      if (this.contractDataTo.contractType == "Customer") {
        this.isCustomer = true;
        this.project.get('projectType').setValue(this.contractDataTo.contractType);

      }
      else if (this.contractDataTo.contractType == "Internal") {
        this.isNotCustomer = true;
      }

    });
  }



  getProjectContractID(id) {
    var data = {
      "projectInfo": {
        "contractId": id
      },
      "transactionType": "getByContId"
    }
    this.contractDisable = true;
    this.psaService.getProjectContractId(data).pipe(takeUntil(this.projectAll)).subscribe(response => {
      console.log("con pro ID", response);

      this.procon = response;
      this.contractPro = this.procon.projectList;
      this.countValue = 0;

      if (this.procon.message == "No projects found!") {
        this.valueToShow = this.contractValue;
        this.showBalance = true;
        this.greenShow = true;
      }
      else {
        for (var i = 0; i < this.contractPro.length; i++) {
          this.countValue = this.countValue + this.contractPro[i].projectRatecard.projectValue;
        }
        this.valueToShow = this.contractValue - this.countValue;
        if (this.valueToShow >= 0) {

          this.greenShow = true;
          this.redShow = false;

        }
        else {
          this.redShow = true;
          this.greenShow = false;
        }
        this.showBalance = true;

      }




    })

  }

  //edit customer and contract
  editHere() {
    if (this.cusconeditHide == true) {
      this.cusconeditHide = false;
    }
    this.allEmps = false
  }

  cusConEdit() {

    this.contractDisable = true;
    this.onShow = false;
    this.showBalance = false;
    this.greenShow = false;
    this.redShow = false;
    this.show = true;
    this.cusconeditHide = false;
    this.isNotCustomer = false;
    this.isCustomer = false;

  }


  //get ALL Masters
  getLocationType() {
    var request = {
      "locationTypeList": [
        {

        }
      ],
      "transactionType": "getall"
    }
    this.psaService.getlocationType(request).pipe(takeUntil(this.projectAll)).subscribe(response => {
      this.loc = response;
      this.locTypes = this.loc.locationTypeList;
      console.log("location Type", this.locTypes)
    }
    );
  }

  getDelivaryLoc() {
    var data = {
      "deliverylocationList": [{

      }], "transactionType": "getall"
    }
    this.psaService.getdeliverylocation(data).pipe(takeUntil(this.projectAll)).subscribe(response => {

      this.delivery = response;
      this.deliveryLoc = this.delivery.deliverylocationList;
      console.log("delivary loc", this.deliveryLoc)
    });
  }

  getServiceType() {
    var data = { "servicetypeList": [{}], "transactionType": "getall" }
    this.psaService.getservicetype(data).pipe(takeUntil(this.projectAll)).subscribe(res => {

      this.serv = res;
      this.serviceTypes = this.serv.servicetypeList;
      console.log("service type", this.serviceTypes)
    });
  }
  getServiceCategory() {
    var data = { "servicecategoryList": [{}], "transactionType": "getall" }
    this.psaService.getServiceCategory(data).pipe(takeUntil(this.projectAll)).subscribe(Response => {
      this.serviceCat = Response;
      this.serviceCategoryList = this.serviceCat.servicecategoryList;
      console.log("service catogory", this.serviceCategoryList);
    });
  }

  getBillingType() {
    var data = {
      "transactionType": "getall"
    }
    this.psaService.getbillingtype(data).pipe(takeUntil(this.projectAll)).subscribe(response => {


      this.billingTypeList = response;
      this.billingTypes = this.billingTypeList.billingList;
      console.log("Billing Type", this.billingTypeList);

    })
  }

  getRateType() {
    var data = {
      "rateType": [{

      }],
      "transactionType": "getAll"
    }
    this.psaService.getRatetype(data).pipe(takeUntil(this.projectAll)).subscribe(response => {

      this.rateTypeList = response;
      this.rateTypes = this.rateTypeList.rateTypeList;
      console.log("rate type", this.rateTypes);

    }
    )
  }

  getTechStack() {
    var data = {
      "projectTechStackList": [
        {
        }
      ],
      "transactionType": "getall"
    }
    this.psaService.getProjectTechStack(data).pipe(takeUntil(this.projectAll)).subscribe(response => {
      this.techStackResp = response;
      this.techStacks = this.techStackResp.projectTechStackList;
      console.log("Tech Stack", this.techStacks);

    })
  }

  getTasks() {
    let req = {
      "projecttasklist": [
        {}
      ],
      "transactionType": "getall"
    }
    this.psaService.getProjectTask(req).pipe(takeUntil(this.projectAll)).subscribe(resp => {
      this.task = resp;
      this.taskList = this.task.projecttasklist;
      console.log('Task List : ', this.taskList);

    })
  }

  getBusinessUnit() {
    var data = {
      "businessUnit": [{
      }],
      "transactionType": "GetAll"
    }
    this.psaService.getBusinessUnit(data).pipe(takeUntil(this.projectAll)).subscribe(Response => {
      this.business = Response;
      this.businessUnit = this.business.businessUnitList;

    })
  }

  getSubBusinessUnit() {
    var data = {

      "subBusinessUnitModel": [
        {
        }],
      "transactionType": "getAll"
    }

    this.psaService.getSubBusinessUnit(data).pipe(takeUntil(this.projectAll)).subscribe(Response => {
      this.subBusiness = Response;
      this.subBusinessUnit = this.subBusiness.subBusinessUnitList;

      console.log("subbusiness Unit", this.subBusinessUnit);

    })
  }
  getEmpDesignation() {
    var request = {
      "designation": [

      ],
      "sessionId": "3121",
      "transactionType": "getall"
    }
    this.hrms.getEmployeeDesignation(request).pipe(takeUntil(this.projectAll)).subscribe(res => {
      this.empDesignationDetails = res;
      this.empDesignationlist = this.empDesignationDetails.listDesignation;
      console.log("emp designatoin", this.empDesignationlist);
    })
  }


  getProjectTypes() {
    let dta;
    var data = {
      "projectTypeList": [{


      }],
      "transactionType": "getall"
    }
    this.psaService.getProjectType(data).subscribe(resp => {
      dta = resp;
      this.projectTypedata = dta.body.projectTypeList;
      console.log("projectType", this.projectTypedata);

    });

  }
  //masters end





  buListArr = new Array();
  sbuListArr = new Array();



  getAllEmployees() {
    this.empall = null
    var empinfo =
    {
      "transactionType": "getAllInfo"
    }

    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.projectAll)).subscribe(res => {
      this.empbasic = res;
      this.empbasicin = this.empbasic.employeeInfo;
      this.empall = this.empbasicin.map(d => new Object({ name: d.firstname + " " + d.lastname, empId: d.employeeId }))
      console.log("All Employees", this.empbasicin);
      // console.log("Business Unit", this.businessUnit);
      // for (var i = 0; i < this.businessUnit.length; i++) {
      //   let buObj = {
      //     'buHead': "",
      //     "buHeadName": "",
      //     "buName": ""
      //   }
      //   let empObj = this.empbasicin.find(type => type.employeeId == this.businessUnit[i].buHead)
      //   //console.log("Find employee : ", empObj);
      //   buObj.buHeadName = empObj.firstname + empObj.lastname;
      //   buObj.buHead = empObj.employeeId;
      //   buObj.buName = this.businessUnit[i].businessUnitName;
      //   this.buListArr.push(buObj);
      // }
      // for (var i = 0; i < this.subBusinessUnit.length; i++) {
      //   let sbuObj = {
      //     'sbuHead': "",
      //     "sbuHeadName": "",
      //     "name": ""
      //   }
      //   let empObj = this.empbasicin.find(type => type.employeeId == this.subBusinessUnit[i].sbuHead)
      //  // console.log("Find employee : ", empObj);
      //   sbuObj.sbuHeadName = empObj.firstname + empObj.lastname;
      //   sbuObj.sbuHead = empObj.employeeId;
      //   sbuObj.name = this.subBusinessUnit[i].name;
      //   this.sbuListArr.push(sbuObj);
      // }

      this.empsorteddata = this.empbasicin.sort((a, b) => {
        if (a.employeeId > b.employeeId) {
          return 1;
        }
        else {
          return -1;
        }
        return 0;
      })



      this.empObjTech = this.empbasicin.filter(type => {

        if (type.title.includes("Lead") || type.title.includes("lead"))
          return true;
      })
      console.log("Find employee of tech lead: ", this.empObjTech);


      this.manager = Array.from(new Set(this.empbasicin.map(x => x.reportingManager)));
      this.employee = Array.from(new Set(this.empbasicin.map(x => x.employeeId)));
      console.log(this.employee, "only employessssssssss........")
      console.log(this.manager, "only managers in employee info...................");
      for (let n = 0; n <= this.manager.length; n++) {
        for (let m = 0; m < this.employee.length; m++) {

          if (this.manager[n] == this.employee[m]) {
            this.managerdata.push(this.empbasicin[m]);

          }
        }
      }
      console.log("managers", this.managerdata)




    });
    console.log("bu obj", this.buListArr);



  }



  selected_customer(id) {
    this.resourceArray = []
    this.contractDisable = false;
    this.customerId = id;
    this.contractIdGet = null;
    console.log("customer selected", this.customerId);
    this.getIDCustomer(id);
    this.getIdcontract(this.customerId);
  }





  cancelSave(value) {
    this.availabilityArray = [];
    this.disableDate = true;
    this.showBalance = false;
    value.reset();
    this.show = true;
    this.contractDisable = true;
    this.onShow = false;
    this.isCustomer = false;
    this.isNotCustomer = false;
  }


  selected_customerToTable(value) {
    this.contractDisable = false;
    this.contractTableID = value;
  }

  approveBU() {
    var approve = {
      "projectInfo": {
        "projectId": this.getIdData.projectId,
        "buStatus": "Approved",
        "updatedBy": this.loggedUserName
      },
      "transactionType": "statusUpdate"
    }
    // console.log("approved",approve);

    swal({
      title: "Are you sure?",
      text: "Want to approve the project",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {

          this.psaService.saveProject(approve).pipe(takeUntil(this.projectAll)).subscribe(response => {
            console.log("approved response", response);
            this.msgApprove = response;
            // this.toastr.successToastr(this.msgApprove.message, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(this.msgApprove.message, 'Success')
            this.moreinfo(this.getIdData.projectId);
            this.getAllProject();
          }, err => {
            console.log(err)
            this.msg = err
            //   this.toastr.infoToastr(this.msg.error.message, 'info', {
            //     showCloseButton: true,
            //     animate: 'slideFromRight'
            //   })
            this.toast.info(this.msg.error.message, 'Info')
          });


        }
        else {
          // this.toastr.infoToastr('You did not approve project', 'Error', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.info('You did not approve project', 'Error')
        }
      });







  }

  rejectBU(comment) {
    var reject = {
      "projectInfo": {
        "projectId": this.getIdData.projectId,
        "buStatus": "Rejected",
        "comment": comment.comments,
        "updatedBy": this.loggedUserName
      },
      "transactionType": "statusUpdate"
    }
    console.log("rejected", reject);

    swal({
      title: "Are you sure?",
      text: "Want to reject the project",

      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {

          this.psaService.saveProject(reject).pipe(takeUntil(this.projectAll)).subscribe(response => {
            console.log("Reject response", response);
            this.msgApprove = response;
            // this.toastr.successToastr(this.msgApprove.message, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(this.msgApprove.message, 'Success')
            this.moreinfo(this.getIdData.projectId);
            this.getAllProject();

          }, err => {
            console.log(err)
            this.msg = err
            // this.toastr.infoToastr(this.msg.error.message, 'Error', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.info(this.msg.error.message, 'Error')
          });

        }
        else {
          // this.toastr.infoToastr('You did not reject project', 'info', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.info('You did not reject project', 'Info')
        }
      });
  }

  approveFinance() {
    var approve = {
      "projectInfo": {
        "projectId": this.getIdData.projectId,
        "financeStatus": "Approved",
        "updatedBy": this.loggedUserName
      },
      "transactionType": "statusUpdate"
    }
    console.log("approved", approve);

    swal({
      title: "Are you sure?",
      text: "Want to approve the project",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {

          this.psaService.saveProject(approve).pipe(takeUntil(this.projectAll)).subscribe(response => {
            console.log("approved response", response);
            this.msgApprove = response;
            // this.toastr.successToastr(this.msgApprove.message, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(this.msgApprove.message, 'Success')
            this.moreinfo(this.getIdData.projectId);
            this.getAllProject();
          }, err => {
            console.log(err)
            this.msg = err
            // this.toastr.infoToastr(this.msg.error.message, 'info', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.info(this.msg.error.message, 'info')
          });

        }
        else {
          // this.toastr.infoToastr('You did not approve project', 'Error', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.info('You did not approve project', 'Error')
        }
      });







  }

  rejectFinance(comment) {
    var reject = {
      "projectInfo": {
        "projectId": this.getIdData.projectId,

        "financeStatus": "Rejected",
        "comment": comment.comments,
        "updatedBy": this.loggedUserName
      },
      "transactionType": "statusUpdate"
    }
    console.log("rejected", reject);

    swal({
      title: "Are you sure?",
      text: "Want to reject the project",

      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {

          this.psaService.saveProject(reject).pipe(takeUntil(this.projectAll)).subscribe(response => {
            console.log("Reject response", response);
            this.msgApprove = response;
            // this.toastr.successToastr(this.msgApprove.message, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(this.msgApprove.message, 'Success')
            this.moreinfo(this.getIdData.projectId);
            this.getAllProject();

          }, err => {
            console.log(err)
            this.msg = err
            // this.toastr.infoToastr(this.msg.error.message, 'Error', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.info(this.msg.error.message, 'Error')
          });

        }
        else {
          // this.toastr.infoToastr('You did not reject project', 'info', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.info('You did not reject project', 'Info')
        }
      });
  }




  deleteProject(id) {
    var data = {
      "projectInfo": {
        "projectId": id,
        "updatedBy": this.loggedUserName
      },
      "transactionType": "delete"
    }

    swal({
      title: "Are you sure?",
      text: "Once deleted, you will not be able to recover this activity!",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {

          this.psaService.saveProject(data).pipe(takeUntil(this.projectAll)).subscribe(response => {
            console.log("delete response", response);
            this.msgdelete = response;
            // this.toastr.successToastr(this.msgdelete.message, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(this.msgdelete.message, 'Success')

            this.getAllProject();
          }, err => {
            console.log(err)
            this.msg = err
            // this.toastr.infoToastr(this.msg.error.message, 'error', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.info(this.msg.error.message, 'Error')
          });

        }
        else {
          // this.toastr.infoToastr('Delete activity not completed', 'info', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.info('Delete activity not completed', 'info')
        }
      });


  }


  valueChange(valUpdate) {
    console.log("value change", valUpdate.target.value, this.valueToShow);
    console.log("value of addition change", this.valueToShow + this.editprojectvalue);

    if ((Number(valUpdate.target.value)) > this.valueToShow + this.editprojectvalue) {
      this.errorToEnter = true;
    }
    else {
      this.errorToEnter = false;
    }
  }


  //get resources

  getresources(projectId) {
    let response;
    console.log("projectId", projectId);



    let objProject = {
      "employeeprojects":
      {
        "projectId": projectId
      },
      "transactiontype": "getresourcebyproject"
    }

    this.rmgService.getEmpByProjectId(objProject).subscribe(res => {
      response = res;
      console.log("response resource obj", response);
      this.rmgresource = [];
      this.resourcesInProject = response.employeeprojects;


      for (let k = 0; k < this.resourcesInProject.length; k++) {


        let resourseSpecific = this.empbasicin.filter(data => data.employeeId == this.resourcesInProject[k].employeeId)
        let resourceObj = {
          "employeeId": resourseSpecific[0].employeeId,
          "firstname": resourseSpecific[0].firstname,
          "lastname": resourseSpecific[0].lastname,
          "title": resourseSpecific[0].title,
          "startDate": this.resourcesInProject[k].startDate,
          "endDate": this.resourcesInProject[k].endDate,
          "billRate": this.resourcesInProject[k].billRate,
          "hoursOfAllocation": this.resourcesInProject[k].hoursOfAllocation,
          "bookingId": this.resourcesInProject[k].bookingId,
          "specificId": this.resourcesInProject[k].specificId

        }
        this.rmgresource.push(resourceObj);


      }
      console.log("resource array project", this.rmgresource);

    })









    // var response
    // var data
    // var obj = []
    // var resourcedata = {
    //   "rmgInfo": {


    //   },
    //   "transactiontype": "getall"
    // }

    // this.rmgService.getAllResource(resourcedata).pipe(takeUntil(this.projectAll)).subscribe(res => {
    //   response = res
    //   data = response.rmgInfo
    //   console.log("resorcesss data", data)
    //   data.map(d => {
    //     if (d.resourceType == "Specific") {
    //       console.log(d,"length");

    //       for (let i in d.rmgspecific) {
    //         if (d.rmgspecific[i].flag == true) {

    //           var project = this.dataTo.find(e => e.projectId == d.projectId)

    //           var final = new Object({ bookingId: d.bookingId, resourceType: d.resourceType, projectId: d.projectId, projectName: project.projectName, resource: d.rmgspecific[i] })
    //           obj.push(final)
    //         }
    //       }
    //     }
    //     else {
    //       for (let i in d.rmggeneric) {
    //         for (let j in d.rmggeneric[i].rmggenericresourcemap) {
    //           if (d.rmggeneric[i].rmggenericresourcemap[j].flag == true) {
    //             var project = this.dataTo.find(e => e.projectId == d.projectId)
    //             var final = new Object({ bookingId: d.bookingId, resourceType: d.resourceType, projectId: d.projectId, projectName: project.projectName, resource: d.rmggeneric[i].rmggenericresourcemap[j], genericId: d.rmggeneric[i].genericId })
    //             obj.push(final)
    //           }
    //         }

    //       }
    //     }
    //   })

    //   this.originalData = obj

    //   this.rmgdata = obj
    //   console.log(this.rmgdata, "final object");

    // })
  }



  cancelResourceData(index, resourceData) {

    console.log("index resource", index, resourceData);

    swal({
      title: "Are you sure?",
      text: "The resource will no more in this project",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.releaseResourceArray.push(this.rmgresource[index]);
          this.rmgresource.splice(index, 1);
          this.allowToRelease = true;
        }
      });





    // this.rmgresource.splice(index, 1);
  }

  addtask() {
    console.log(this.project.value.tasks);
    let count = 0
    for (let i in this.project.value.tasks) {
      if (this.project.value.tasks[i] == 'other') {
        this.otherTask = true
        count = 1
      }
    }
    if (count == 0) {
      this.otherTask = false
      // this.taskValues = this.project.value.tasks
    }
  }

  removeTask(removeData) {
    this.project.value.tasks.splice(this.project.value.tasks.indexOf(removeData), 1);
    console.log(this.project.value.tasks);
    this.project.controls.tasks.setValue(this.project.value.tasks)

    //this.project.get('tasks').reset();
  }

  removeother(taskData) {
    this.otherTaskValue.splice(this.project.value.tasks.indexOf(taskData), 1);
    console.log(this.otherTaskValue);
  }

  removeeditTask(taskData) {
    this.editTaskValues.splice(this.project.value.tasks.indexOf(taskData), 1);
    console.log(this.editTaskValues);
  }

  addTask() {
    console.log("hello", this.project.value.otherTask);

    this.otherTaskValue.push(this.project.value.otherTask)
    console.log(this.otherTaskValue);
    this.project.controls.otherTask.reset()


  }

  getSkillInfo() {

    var Requestdata = {
      "listOfSkill": [{


      }],
      "transactionType": "getAll"
    }
    this.hrms.getSkillmaster(Requestdata).subscribe(responce => {
      this.skillinfolist = responce;
      this.skillInfoList = this.skillinfolist.listOfSkill;
      // console.log(this.skillInfoList)
    })

  }

  skillBasedEmployess(skills) {

    this.empall = null
    console.log("skills", skills);
    var count = 0
    for (let i in skills) {
      if (skills[i] == 'all') {
        this.allEmps = true
        count = 1
        this.getAllEmployees();

      }
    }
    if (skills.length == 0) {
      this.noRecords = false
      this.allEmps = false
      this.getAllEmployees()
      count = 1
    }
    if (count == 0) {
      this.allEmps = false
      let response
      let skillObj = {
        "employeeskills":
        {

          "skills": skills
        },

        "transactionType": "getskillsbyempinfo"
      }
      this.psaService.getSkillBased(skillObj).subscribe(resp => {
        this.noRecords = false
        console.log("skill", resp);
        response = resp
        this.empall = response.emplist.map(d => new Object({ name: d.empName, empId: d.employeeId }))
      },
        err => {
          this.noRecords = true
          // this.getAllEmployees()
        })
    }
  }


  resourceMapping() {
    console.log("resource mapped", this.project);
    let resourceObj = {
      "empId": this.project.value.resourceId,
      "startDate": this.project.value.resourceStartDate,
      "endDate": this.project.value.resourceEndDate,
      "billRate": this.project.value.billRate,
      "numberOfHours": this.project.value.numberOfHours

    }
    this.resourceArray.push(resourceObj);
    console.log("obj resource", this.resourceArray);
    this.project.get('resourceId').reset();
    this.project.get('resourceStartDate').reset();
    this.project.get('resourceEndDate').reset();
    this.project.get('billRate').reset();
    this.project.get('numberOfHours').reset();

    this.availabilityArray = [];


  }

  cancelResource(index) {
    this.resourceArray.splice(index, 1);


  }


  addMileStone() {
    let projectMileStone = {
      "milestoneName": this.project.value.mileName,
      "startDate": this.project.value.mileStartDate,
      "endDate": this.project.value.mileEndDate,
      "billingAmount": this.project.value.mileBillingAmount
    }

    this.mileStonesArray.push(projectMileStone);

    this.project.get('mileName').reset();
    this.project.get('mileStartDate').reset();
    this.project.get('mileEndDate').reset();


  }
  removeMileStone(index) {
    this.mileStonesArray.splice(index, 1);
  }

  toCheckAvailability(empId) {
    this.availabilityArray = [];

    console.log("selected", empId.target.value);
    let response;
    let hoursToCheck = 0;
    let toCheckObj = {
      "projectlist":
      {
        "empId": empId.target.value
      },
      "transactiontype": "getprojects"
    }

    this.rmgService.getEmpAvailability(toCheckObj).subscribe(res => {
      response = res;
      this.availabilityArray = response.projectlist;
      console.log("response of availability", response);
      this.hoursCheck = this.availabilityArray;
      this.hoursCheck.forEach(element => {
        if (element.hoursOfAllocation != null) {
          hoursToCheck += element.hoursOfAllocation

        }

      });
      this.checkHours = hoursToCheck
      console.log("element", this.checkHours);

      if (hoursToCheck > 16 || this.availabilityArray == null) {
        this.dontAllowResource = false;
      }
      else {
        this.dontAllowResource = true;

      }

    })


  }
  getprojectName(id) {


    let data = this.dataTo.filter(projectid => projectid.projectId == id)

    return data[0].projectName;
  }

  updateResourceEdit(i, resourcedata) {
    this.editResource = false;


    let obj =

    {
      "employeeId": resourcedata.employeeId,
      "firstname": resourcedata.firstname,
      "lastname": resourcedata.lastname,
      "title": resourcedata.title,
      "startDate": this.project.value.resourceStartDate,
      "endDate": this.project.value.resourceEndDate,
      "billRate": resourcedata.billRate,
      "hoursOfAllocation": this.project.value.numberOfHours,
      "bookingId": resourcedata.bookingId,
      "specificId": resourcedata.specificId

    }

    this.rmgresource.splice(i, 1, obj);

    this.project.get('resourceStartDate').reset();
    this.project.get('resourceEndDate').reset();

    this.project.get('numberOfHours').reset();

    this.allowupdateResource = true;
    console.log("rmg resource", this.rmgresource);

  }
  changeEditResource(resdata) {
    // this.editResource = true;
    console.log("res data", resdata);

    this.project.controls.resourceStartDate.setValue(resdata.startDate);
    this.project.controls.resourceEndDate.setValue(resdata.endDate);
    this.project.controls.numberOfHours.setValue(resdata.hoursOfAllocation);
    this.editResource = true;

  }
  cancelUpdateEdit() {
    this.editResource = false;


    this.project.get('resourceStartDate').reset();
    this.project.get('resourceEndDate').reset();

    this.project.get('numberOfHours').reset();

  }

  hourschange(data) {
    console.log("hours change", this.checkHours + Number(data));
    if (this.checkHours + Number(data) > 16) {
      this.showErrorHours = true

    } else {
      console.log("hours change", this.checkHours + data);

      this.showErrorHours = false

    }
  }

  
  milebillingValidation(billValue){
    if(billValue>this.project.value.projectValue){
      this.showErrorBilling = true;
    }else{
      this.showErrorBilling = false
    }
  }
}
