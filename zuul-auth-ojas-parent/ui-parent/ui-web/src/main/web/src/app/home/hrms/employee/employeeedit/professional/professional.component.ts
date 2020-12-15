import { Component, OnInit } from '@angular/core';
import { Subscription, Subject } from 'rxjs';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { DataService } from 'src/app/home/services';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { takeUntil } from 'rxjs/operators';
import * as moment from 'moment';

@Component({
  selector: 'app-professional',
  templateUrl: './professional.component.html',
  styleUrls: ['./professional.component.scss']
})
export class ProfessionalComponent implements OnInit {
  today;
  subscription: Subscription;
  message: any;
  public eid: any;
  loggeduser: string;
  emp: any;
  empbasic: any;
  empbasicinfo: any;
  filedata: string;
  pass: boolean;
  role;
  hide: boolean = false;
  emphide: boolean = false;
  pdfs: boolean;
  expereince: any;
  StateList: any;
  statename: any;
  ipppd: any = 5;
  sd: any;
  ed: any;
  msg: any;
  Accepted: any;
  Access: boolean;
  employeeExpdetails: any;
  view: boolean;
  reason: any;
  private unsubscribe = new Subject();
  encryptedUsername: string;
  encryptedRole: string;
  buttonHide: boolean;
  iconHide: boolean;
  constructor(private hrms: HrmsService, private dataservice: DataService, private paramroute: ActivatedRoute,private authService: AuthService,private toast:NotificationService) {
    // , private toastr: ToastrManager
    this.eid = this.dataservice.paramId;
    // this.loggeduser = sessionStorage.getItem('UserName');
    this.encryptedUsername=sessionStorage.getItem('UserName');
    this.loggeduser=this.authService.decryptData(this.encryptedUsername);
    this.encryptedRole=sessionStorage.getItem('Role');
    this.role=this.authService.decryptData(this.encryptedRole);
    this.today = moment().format('YYYY-MM-DD');
    console.log(this.today, "today date expirence");
    this.getStateListDetails();
  }
  // getempId(){

  // }
  rolemanagerflag: boolean = true;
  addexp:any=true
  edit:any=true
  setUserRole;

  ngOnInit() {

    this.getEmpExp();
    this.getProject();
    this.getempdata();


    this.pdfs = false;
    // this.role = sessionStorage.getItem("Role");

    if (this.role == "ROLE_HR") {
      this.hide = true;
    }
    if (this.role == "ROLE_USER") {
      this.emphide = true;
    }
    if (this.role == "ROLE_MANAGER") {
      this.rolemanagerflag = false;
      this.edit=false
      this.addexp=false
    }
    this.setUserRole = sessionStorage.getItem("setUserRole");
    console.log('role to check user', this.setUserRole);
    if (this.setUserRole == "true") {
      this.rolemanagerflag = true;
      this.emphide = true;
      this.hide = false;
    }

  }
  ngOnDestroy() {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
  user: boolean = false;


  getempdata() {

    var empinfo =
    {

      "employeeInfo": [{
        "employeeId": this.eid

      }],
      "transactionType": "getbyid"

    }
    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      this.empbasic = res;
      this.empbasicinfo = this.empbasic.employeeInfo;
      console.log(this.empbasicinfo);
    })
  }
  // ----Employee Experience Details Starts---------------------------------------------
  empexp: any;
  empexpdetails: any;
  saveRes: any;

  //---getting employee details---------------------------------------------------
  getEmpExp() {
    var requestObj =
    {
      "employeeExperienceDetails": [{

        "employee_id": this.eid
      }],

      "transactionType": "getById"
    }

      console.log("ep request",requestObj)
    this.hrms.getEmployeeExperienceDetails(requestObj).pipe(takeUntil(this.unsubscribe)).subscribe(response => {
      this.empexp = response;
      this.empexpdetails = this.empexp.employeeExperienceDetails;
      console.log("Employee Experience data ", this.empexpdetails[0]);

      this.Accepted = this.empexpdetails[0].documentsstatus;
      console.log("Document status value ", this.Accepted,this.hide);
      if(this.empexpdetails[0].documentsstatus=="Pending"){
        console.log('sdfsdfsdfsdfsdfsdfsdfsdfsdfsdf');
        
        this.buttonHide=true;
        this.iconHide=false;
      }
      if (this.Accepted == "Accepted") {
        this.Access = true;
        this.edit=false
        this.buttonHide=false;
        this.iconHide=true;


      }  if (this.Accepted == "Decline"){
        this.edit=true
        this.Access = false;
        this.buttonHide=false;
        this.iconHide=true;


      }
      this.addexp=false
      console.log("Document status value data", this.buttonHide,this.hide);

    },err=>{
      console.log(err)
      this.addexp=true
      this.edit=true
    })
  }

  //---adding employee experience----------------------------------------------------

  company_name: any;
  joining_date: any;
  exit_date: any;
  salary: any;
  //location:any;
  is_current_company: any;
  //employee_Id:any;
  first_Reference_Name: any;
  first_Reference_Contact: any;
  second_Reference_Name: any;
  second_Reference_Contact: any;
  sessionId: any;
  created_date: any;
  updated_date: any;
  perfectfile:any=false
  booleanValue = true;
  EmpExpArr: any;
  status: any;
  documentstatus: any;


  saveEmpExp() {
    var today=new Date()
    var savReqObj =

    {
      "employeeExperienceDetails":

        [
          {
            "company_name": this.editemparrlist.company_name,
            "joining_date": this.editemparrlist.joining_date,
            "exit_date": this.editemparrlist.exit_date,
            "salary": this.editemparrlist.salary,
            "location": this.editemparrlist.location,
            "is_current_company": this.editemparrlist.is_current_company,
            "employee_id": this.eid,
            "reference_1_name": this.editemparrlist.reference_1_name,
            "reference_1_contact": this.editemparrlist.reference_1_contact,
            "reference_2_name": this.editemparrlist.reference_2_name,
            "reference_2_contact": this.editemparrlist.reference_2_contact,
            "flag": false,
            "created_by": this.loggeduser,
            "created_date": "",
            "image": this.filedata,
            "experience": 2.7,
            "status": false,
            "documentsstatus": this.msg

          }
        ],

      "transactionType": "save"
    }

    console.log("Employee Experience save data is", savReqObj);
    if (this.editemparrlist.exit_date > this.editemparrlist.joining_date) {
      this.hrms.saveEmployeeExperienceDetails(savReqObj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
        this.saveRes = res;
        this.expereince = this.saveRes.employeeExperienceDetails;
        // this.toastr.successToastr(this.saveRes.message,"Success",{
        //   showCloseButton: true,
        //   animate: 'slideFromRight'
        // })
        this.toast.success(this.saveRes.message,"Success")
        this.getEmpExp();
      },err=>{
        // this.toastr.infoToastr("Experience details not saved","Info",{
        //   showCloseButton: true,
        //   animate: 'slideFromRight'
        // })
        this.toast.info("Experience details not saved","Info")
        this.getEmpExp();
      })
    } else {
      // this.toastr.infoToastr("Enter valid relieving date!","Info",{
      //   showCloseButton: true,
      //   animate: 'slideFromRight'
      // })
      this.toast.info("Enter valid relieving date!","Info")
      this.getEmpExp();
    }
  }


  exfileSelected(evt) {
    console.log("files",evt.target.files[0].size)

    if(evt.target.files[0].size < 1000000){
      var files = evt.target.files;
      var file = files[0];
  
      if (files && file) {
        var reader = new FileReader();
        reader.onload = this.handleReaderLoaded.bind(this);
        reader.readAsBinaryString(file);
        this.perfectfile=false
      }
    }else{
      // this.toastr.infoToastr("File should nnot greater than 1 MB","Info",{
      //   showCloseButton: true,
      //   animate: 'slideFromRight'
      // })
      this.toast.info("File should not greater than 1 MB","Info")
      this.perfectfile=true
    }
  }

  handleReaderLoaded(readerEvt) {
    console.log(readerEvt)
    var binaryString = readerEvt.target.result;
    this.filedata = btoa(binaryString);
    //this.filedata.push({i:this.base64textString});
    //this.filedata=this.base64textString;
    console.log(this.filedata);

  }
  //---deleting employee experience---------------------------
  deleteres: any;
  details;
  deleteEmpExpArr: any;


  deleteEmpExp(emp) {
    var deleteReqObj =
    {
      "employeeExperienceDetails":
        [{
          "id": emp.id,
          "updated_by": emp.updated_by
        }],
      "transactionType": "delete",
      "sessionId": "any String"
    }
    this.hrms.deleteEmployeeExperienceDetails(deleteReqObj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      this.deleteres = res;

      this.deleteEmpExpArr = this.deleteres.employeeExperienceDetails;
      console.log(this.deleteres);

      if (this.deleteres.message == "EmployeeExperienceDetails deleted sucesfully") {
        // swal(this.deleteres.message, "", "success");
        this.toast.success(this.deleteres.message)
        this.getEmpExp();
      }
      //this.getEmpExp();
    })
  }

  //---editing employee experience------------------------------------------
  editemp: any;
  editemparr: any;

  public editemparrlist =
    {
      "id": "",
      "company_name": "",
      "salary": "",
      "location": "",
      "joining_date": "",
      "exit_date": "",
      "is_current_company": "",
      "employee_id": "",
      "reference_1_name": "",
      "reference_1_contact": "",
      "reference_2_name": "",
      "reference_2_contact": "",
      "flag": "",
      "created_by": "",
      "updated_by": "",
      "created_date": "",
      "updated_date": "",
      "image": "",
      "experience": "",
      "status": "",
      "documentsstatus": ""
    }

  //--Add Button----------------
  isUpdate: boolean
  isCreated: boolean = false;
  addempexp(newUserFormEmpExp) {
    newUserFormEmpExp.reset();
    this.isUpdate = false;
    this.isCreated = true;

  }


  editempexpbyid(emp) {
    console.log("ssssssssssssss", emp);


    // this.editemparrlist.employee_Id=emp.employee_Id;
    // this.editemparrlist.company_name=emp.company_name;
    // this.editemparrlist.salary=emp.salary;
    // this.editemparrlist.location=emp.location;
    // this.editemparrlist.joining_date=emp.joining_date;
    // this.editemparrlist.exit_date=emp.exit_date;
    // this.editemparrlist.is_current_company=emp.is_current_company;

    // this.editemparrlist.first_Reference_Name=emp.first_Reference_Name;
    // this.editemparrlist.second_Reference_Name=emp.second_Reference_Name;
    // this.editemparrlist.second_Reference_Contact=emp.second_Reference_Contact;
    // this.editemparrlist.updated_by=emp.updated_by;
    // this.editemparrlist.created_by=emp.created_by;
    // this.editemparrlist.created_date=emp.created_date;
    // this.editemparrlist.updated_date=emp.updated_date;

    this.isUpdate = true;
    this.isCreated = false;
    var empid = emp.id;
    var editempexpobj =
    {
      "employeeExperienceDetails": [{
        "id": empid

      }],
      "transactionType": "getById"

    }

    this.hrms.editEmpExpDetails(editempexpobj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      this.editemp = res;
      this.editemparr = this.editemp.employeeExperienceDetails;
      this.editemparrlist = this.editemparr[0];
      console.log("this.editemparrlist", this.editemparrlist);

    })
  }



  //---updating employee experience------------------------

  updateres: any;
  updateEmpExpArr: any;
  newUserFormExp: any;



  updateEmpExp() {
    var today=new Date()
    var updateReqObj =
    {
      "employeeExperienceDetails":
        [{

          "id": this.empexpdetails[0].id,
          "employee_id":this.empexpdetails[0].employee_id,
          "updated_by": this.loggeduser,
          "updated_date": this.formatDate(today),
          "image": this.filedata,
          "documentsstatus": this.msg,

        }],
      "transactionType": "update"

    }
    console.log("Emp Exp update request object is", updateReqObj);

    this.hrms.updateEmployeeExperienceDetails(updateReqObj).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      this.updateres = res;
      // this.employeeExpdetails = this.updateres.employeeExperienceDetails;
      // this.toastr.successToastr("Your experience details updated successfully", "Success", {
      //   showCloseButton: true,
      //   animate: 'slideFromRight'
      // })
      this.toast.success("Your experience details updated successfully", "Success")
      this.getEmpExp();
    }, err => {
      // this.toastr.infoToastr("Your experience details not updated", "Info", {
      //   showCloseButton: true,
      //   animate: 'slideFromRight'
      // })
      this.toast.info("Your experience details not updated", "Info")
      this.getEmpExp();
    })

  }


  download() {
   console.log("hello",this.empexpdetails)
    // for (let i in this.empexpdetails) {
    //   let data = this.empexpdetails[i].image
    //   var filepdf = 'data:application/pdf;base64,' + data;
    // }
    var filepdf = 'data:application/pdf;base64,' + this.empexpdetails[0].image;
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'experince certificates';
    a.click();

  }


  //Access=false;

  accept() {
    var message;
    var updateReqObj1 =
    {
      "employeeExperienceDetails":
        [{
          "employee_id": this.empexpdetails[0].employee_id,
          "documentsstatus": this.msg
        }],
      "transactionType": "fileuploadstatus"

    }
    console.log("accept request object is", updateReqObj1);
    if (this.empexpdetails[0].status != null) {
      this.hrms.updateEmployeeExperienceDetails(updateReqObj1).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
        this.updateres = res;
        console.log("accept data is", res);
        message = res;
        // this.toastr.successToastr(message.message, "Success", {
        //   showCloseButton: true,
        //   animate: 'slideFromRight'
        // })
        this.toast.success(message.message, "Success")
        this.getEmpExp();
      },
        err => {
          message = err;
          // this.toastr.infoToastr(message.error.message, "Info", {
          //   showCloseButton: true,
          //   animate: 'slideFromRight'
          // })
          this.toast.info(message.error.message, "Info")
          this.getEmpExp();
        })

    } else {
      this.getEmpExp();
    }


  }
  decline(e) {
    var message
    console.log("dtaa is ", updateReqObj2);
    var updateReqObj2 =
    {

      "employeeExperienceDetails":
        [{

          "employee_id": this.empexpdetails[0].employee_id,
          "documentsstatus": this.msg
        }],
      "transactionType": "fileuploadstatus"

    }
    console.log("update request object is", updateReqObj2);
    if (this.empexpdetails[0].status != null) {
      this.hrms.updateEmployeeExperienceDetails(updateReqObj2).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
        this.updateres = res;
        console.log("updated data is", this.updateres);
        this.save(e)
        this.getEmpExp();
      },err=>{
        message=err
        // this.toastr.infoToastr(message.error.message, "Info", {
        //   showCloseButton: true,
        //   animate: 'slideFromRight'
        // })
        this.toast.info(message.error.message, "Info")
        this.getEmpExp();
      })
    } else {
      this.getEmpExp();
    }

  }

  projectDetailsLi: any;
  projectDetails: any;
  projectDetailsList: any;
  projectsave: any;

  valueAdd(newUserFormProject) {
    newUserFormProject.reset();
    this.isUpdate = false;
  }

  getProject() {
    var jsonData =
    {
      "projectDetailsList": [{
        "employeeId": this.eid

      }],
      "transactionType": "getById"
    }
    //    {

    //     "transactionType":"getAll"
    // }
    this.hrms.getProjectDetails(jsonData).pipe(takeUntil(this.unsubscribe)).subscribe(response => {
      this.projectDetails = response;
      this.projectDetailsLi = this.projectDetails.projectDetailsList;
      console.log("Project Details List", this.projectDetailsLi);
      for (let proj in this.projectDetailsLi) {
        // let loc = this.StateList.find(l => l.id == this.projectDetailsLi[proj].location);
        // this.projectDetailsLi[proj].location = loc.stateName;
        if (this.projectDetailsLi[proj].internal) {
          this.projectDetailsLi[proj].internal = "Yes"
        } else {
          this.projectDetailsLi[proj].internal = "No"
        }
      }
    })

  }
  setProject() {

    var requestObj = {
      "projectDetailsList": [{
        "projectName": this.projectDetailss.projectName,
        "contractId": this.projectDetailss.contractId,
        "rateId": this.projectDetailss.rateId,
        "employeeId": this.eid,
        "startDate": this.projectDetailss.startDate,
        "endDate": this.projectDetailss.endDate,
        "billingId": this.projectDetailss.billingId,
        "projectTechStack": this.projectDetailss.projectTechStack,
        "customer": this.projectDetailss.customer,
        "location": this.projectDetailss.location,
        "gstApplicable": this.projectDetailss.gstApplicable,
        "projectType": this.projectDetailss.projectType,
        "projectStatus": this.projectDetailss.projectStatus,
        "bdmContact": this.projectDetailss.bdmContact,
        "internal": this.projectDetailss.internal,
        "createdBy": this.loggeduser


      }],
      "transactionType": "save"
    }
    if (this.projectDetailss.startDate < this.projectDetailss.endDate) {


      this.hrms.setProjectDetails(requestObj).pipe(takeUntil(this.unsubscribe)).subscribe(response => {
        this.projectsave = response;
        this.projectDetailsList = this.projectsave.projectDetailsList;
        console.log(this.projectsave);
        if (this.projectsave.statusMessage == "ProjectDetails has saved successfully") {
          // swal(this.projectsave.statusMessage, "", "success");
          this.toast.success(this.projectsave.statusMessage)
          this.getProject();
        }

      })
    } else {
      // swal("project end date should be greater than start date");
      this.toast.error("project end date should be greater than start date")
    }

  }

  // isUpdate = false;
  public projectDetailss = {
    "id": "",
    "projectName": "",
    "contractId": "",
    "rateId": "",
    "employeeId": "",
    "startDate": "",
    "endDate": "",
    "billingId": "",
    "projectTechStack": "",
    "customer": "",
    "location": "",
    "gstApplicable": "",
    "projectType": "",
    "projectStatus": "",
    "bdmContact": "",
    "internal": "",
    "createdBy": "",
    "updatedBy": ""
  };

  projectGetById: any;
  projectdetailsupdate: any;
  projectdelete: any;

  startDate: any
  endDate: any
  getdatabyId1(user) {
    this.isUpdate = true;
    // this.sd=user.startDate;
    // this.ed=user.endDate;
    console.log("Edit Project : ", user.id);


    var pid = user.id;
    var getupdatedata = {
      "projectDetailsList": [{
        "id": pid

      }],
      "transactionType": "getById",

    }
    console.log("GetById Request : ", getupdatedata);

    this.hrms.getprojectbyId(getupdatedata).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      this.projectGetById = res;
      this.projectDetails = this.projectGetById.projectDetailsList;
      this.projectDetailss = this.projectDetails[0];
      console.log("this.projectDetailss", this.projectDetailss);
      this.startDate = new Date(this.projectDetailss.startDate).getFullYear() + '-' + (new Date(this.projectDetailss.startDate).getMonth() < 10 ? '0' : '') + (new Date(this.projectDetailss.startDate).getMonth() + 1) + '-' + (new Date(this.projectDetailss.startDate).getDate() < 10 ? '0' : '') + new Date(this.projectDetailss.startDate).getDate()
      this.projectDetailss.startDate = this.startDate
      this.endDate = new Date(this.projectDetailss.endDate).getFullYear() + '-' + (new Date(this.projectDetailss.endDate).getMonth() < 10 ? '0' : '') + (new Date(this.projectDetailss.endDate).getMonth() + 1) + '-' + (new Date(this.projectDetailss.endDate).getDate() < 10 ? '0' : '') + new Date(this.projectDetailss.endDate).getDate()
      this.projectDetailss.endDate = this.endDate
    })
  }
  updateprojdata() {

    var updatereq = {
      "projectDetailsList": [{
        "id": this.projectDetailss.id,
        "projectName": this.projectDetailss.projectName,
        "contractId": this.projectDetailss.contractId,
        "rateId": this.projectDetailss.rateId,
        "employeeId": this.projectDetailss.employeeId,
        "startDate": this.projectDetailss.startDate,
        "endDate": this.projectDetailss.endDate,
        "billingId": this.projectDetailss.billingId,
        "projectTechStack": this.projectDetailss.projectTechStack,
        "customer": this.projectDetailss.customer,
        "location": this.projectDetailss.location,
        "gstApplicable": this.projectDetailss.gstApplicable,
        "projectType": this.projectDetailss.projectType,
        "projectStatus": this.projectDetailss.projectStatus,
        "bdmContact": this.projectDetailss.bdmContact,
        "internal": this.projectDetailss.internal,
        "updatedBy": this.loggeduser
      }],
      "transactionType": "update",
      "sessionId": "any String"
    }
    console.log("Project update req : ", updatereq);

    this.hrms.updateproject(updatereq).pipe(takeUntil(this.unsubscribe)).subscribe(response => {
      this.projectdetailsupdate = response;
      console.log(this.projectdetailsupdate);
      if (this.projectdetailsupdate.statusMessage == "ProjectDetails has updated successfully")
        // swal(this.projectdetailsupdate.statusMessage, "", "success")
        this.toast.success(this.projectdetailsupdate.statusMessage)
      this.getProject();
    })
  }

  deleteproj(user) {
    // alert(user.id);
    var deletep =
    {
      "projectDetailsList": [{
        "id": user.id



      }],
      "transactionType": "delete"
    }
    this.hrms.deleteproject(deletep).pipe(takeUntil(this.unsubscribe)).subscribe(response => {
      this.projectdelete = response;
      console.log(this.projectdelete);
      // if(this.projectdelete.statusMessage == "ProjectDetails has deactivated successfully"){
      //  swal(this.projectdelete.statusMessage, "","success");
      //   this.getProject();
      // }
      this.getProject();

    })
  }


  // states Master
  StateDetails: any;
  getStateListDetails() {
    var request = {

      "states":
        [],

      "sessionId": "1234",
      "transactionType": "getAll"

    }
    this.hrms.getStateListMaster(request).pipe(takeUntil(this.unsubscribe)).subscribe(res => {
      this.StateDetails = res;
      this.StateList = this.StateDetails.statesList;
      console.log("StateListgetAll", this.StateList);
    })

  }


  show() {
    this.view = true;
  }
  save(modalform) {
    this.reason = modalform.value.uname;
    console.log("reason :", this.reason)
    // this.toastr.successToastr("Experience Certificates are Declined" + `Due to : ${this.reason}`, "Success", {
    //   showCloseButton: true,
    //   animate: 'slideFromRight'
    // })
    this.toast.success("Experience Certificates are Declined" + `Due to : ${this.reason}`)
  }
  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
}
}

