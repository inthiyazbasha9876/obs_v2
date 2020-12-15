import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import * as moment from 'moment';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { DataService } from 'src/app/home/services';


@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.scss']
})
export class EducationComponent implements OnInit {
  today;

  // @ViewChild('pdf') pdf = ElementRef
  public eid: any;
  loggeduser: string;
  filedata: any;
  _handleReaderLoaded: any;
  binaryString: any;
  cerres: any;
  pdfs;
  edu_image: any;
  pass: boolean;
  role;
  hide: boolean = false;
  emphide: boolean = false;
  ipps: any = 5
  ippc: any = 5
  updateEmpEduDetails: any;
  msg: any;
  Accepted: any;
  a = false;
  view: boolean;
  reason: any;
  notAllow: boolean;
  reasonToDecline: boolean;
  updateSkilldetails;
  encryptedUsername: string;
  encryptedRole: any;
  acceptdata: any;
  declinedata: any;
  buttonHide: boolean;
  iconHide: boolean;
  addHide: boolean;
  constructor(private hrms: HrmsService, private dataservice: DataService, private authService: AuthService, private toast: NotificationService) {
    this.eid = this.dataservice.paramId;
    // this.loggeduser = sessionStorage.getItem('UserName');
    this.encryptedUsername = sessionStorage.getItem('UserName');
    this.loggeduser = this.authService.decryptData(this.encryptedUsername);
    this.encryptedRole = sessionStorage.getItem('Role');
    this.role = this.authService.decryptData(this.encryptedRole);
    this.today = moment().format('YYYY-MM-DD');
    console.log(this.today, "today date ");
    this.a = false
  }
  username: any;
  rolemanagerflag: boolean = true;
  setUserRole;

  ngOnInit() {
    this.getSkillInfo();
    this.openFileBrowser;
    this.getEmpEdu();
    //this.username=sessionStorage.getItem("UserName");
    this.getCertificationDetails();
    this.getEmployeeQualification();
    this.getSkillInfomaster();
    this.getSkillInfo();
    this.getempdata();
    this.getEmpRole();
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
    }


    this.setUserRole = sessionStorage.getItem("setUserRole");
    console.log('role to check user', this.setUserRole);
    if (this.setUserRole == "true") {
      this.rolemanagerflag = true;
      this.emphide = true;
      this.hide = false;

    }

  }



  user: boolean = false;
  getEmpRole() {
    if (this.role == "ROLE_USER")
      this.user = true;
    console.log("Role", this.user);
  }
  hidebutton: boolean = false;
  showbutton: boolean = true;
  teamInfo() {
    this.hidebutton;
  }
  myInfo() {
    this.showbutton;
  }

  empbasic: any;
  empbasicinfo: any;
  getempdata() {

    var empinfo =
    {

      "employeeInfo": [{
        "employeeId": this.eid

      }],
      "transactionType": "getbyid"

    }
    this.hrms.getempinfo(empinfo).subscribe(res => {
      this.empbasic = res;
      this.empbasicinfo = this.empbasic.employeeInfo;
      console.log(this.empbasicinfo);
    })
  }



  // //----Employee Education details Ends--------------------

  //----Getting Employee Education Details--------------------
  getempedu: any;
  getempeduarr: any;
  Qualification_value: any;

  //src = "https://vadimdez.github.io/ng2-pdf-viewer/assets/pdf-test.pdf";

  getEmpEdu() {
    var reqtitle =
    {
      "employeeEducationDetailsList": [
        {
          "employeeId": this.eid
        }
      ],

      "transactionType": "getById"

    }

    this.hrms.getEmpEduDetails(reqtitle).subscribe(res => {
      this.getempedu = res;
      this.getempeduarr = this.getempedu.employeeEducationDetailsList;
      event.preventDefault();
      console.log("Employee Education get data ", this.getempeduarr);
      console.log("my status", this.getempeduarr[0].status)

      // //status check
      // this.Accepted = this.getempeduarr[0].status
      // console.log("Status Value :", this.Accepted);
      if (this.getempeduarr[0].status == "Pending") {
        this.buttonHide = true;
        this.iconHide = false;
        this.addHide = true;
      }
      if (this.getempeduarr[0].status == "Accepted") {
        this.Access = true;
        this.buttonHide = false;
        this.iconHide = true;
        this.addHide = true;

      }
      // else if (this.Accepted == "Decline")
      //   this.Access = false;
      //status end

      if (this.getempeduarr[0].status == "Decline") {
        this.reasonToDecline = true;
        this.buttonHide = false;
        this.iconHide = true;
        this.addHide = true;

      }
      for (let i = 0; i <= this.getempeduarr.length; i++) {
        console.log("array length is", this.getempeduarr);
        this.cerres = this.getempeduarr[i].image;
        console.log("images data is", this.cerres);
      }

      console.log("sssssssssssss", this.cerres)

      for (let i = 0; i <= this.getempeduarr.length; i++) {

        for (let j = 0; j < this.EmpQualArray.length; j++) {
          if (this.getempeduarr[i].qualification == this.EmpQualArray[j].id) {
            this.Qualification_value = this.EmpQualArray[j].educationType;
            console.log("Qualification details");
            console.log(this.Qualification_value);

          }
        }
        this.getempeduarr[i].qualification = this.Qualification_value;
        console.log("Final Educational Details Array");
        console.log("final array is", this.getempeduarr);

      }

    })
  }

  //--Saving Employee Education  Details----------------

  isCreatedEduby: boolean = false;
  isUpdatedEduby: boolean = false;
  saveEmpEduObj: any;
  saveEmpEduarr: any;

  employeeIdEdu: any;
  qualification: any;
  year_of_passing: any;
  percentage_marks: any;
  institution_name: any;
  flagEdu: boolean;
  createdByEdu: any;
  updatedByEdu: any;
  createdDate: any;
  updatedDate: any;
  fileName: any;
  image: any;



  clickaddEdu(EmpEducationForm) {
    EmpEducationForm.reset();
    this.isCreatedEduby = true;
    this.isUpdatedEduby = false;
  }

  saveEmpEdu() {
    //var user="user";
    var saveempeduobj =
    {
      "employeeEducationDetailsList": [
        {

          "employeeId": this.eid,
          "qualification": this.editEduEmp.qualification,
          "year_of_passing": this.editEduEmp.year_of_passing,
          "percentage_marks": this.editEduEmp.percentage_marks,
          "institution_name": this.editEduEmp.institution_name,
          "flag": 1,
          "createdBy": this.loggeduser,
          "image": this.filedata,
          "status": this.msg,
          //"id":this.editEduEmp.id
        }
      ],

      "transactionType": "save"

    }

    console.log("Emp Edu save request object : ", saveempeduobj);
    this.hrms.saveEmpEduDetails(saveempeduobj).subscribe(res => {
      this.saveEmpEduObj = res;
      // swal(this.saveEmpEduObj.message, "", "success");
      this.toast.success(this.saveEmpEduObj.message);
      console.log("console request object : ", this.saveEmpEduObj);
      this.getEmpEdu();

    })


  }


  fileSelected(evt) {
    console.log(evt)
    var files = evt.target.files;
    var file = files[0];

    if (files && file) {
      var reader = new FileReader();
      reader.onload = this.handleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }

  }

  handleReaderLoaded(readerEvt) {
    console.log('handle reader', readerEvt)
    if (readerEvt.loaded > 1000000) {
      this.notAllow = true;
    }
    else {
      var binaryString = readerEvt.target.result;
      this.filedata = btoa(binaryString);
      console.log(this.filedata);
      this.notAllow = false;
    }
  }

  //--Deleting Employee Education Details ----------------

  deleteEmpObj: any;
  deleteEmpEduarr: any;
  deleteEmpEdu(edu) {
    var deleteEmpEduObj =
    {
      "employeeEducationDetailsList": [
        {
          "id": edu.id

        }],

      "transactionType": "delete"
    }

    this.hrms.deleteEmpEduDetails(deleteEmpEduObj).subscribe(res => {
      this.deleteEmpObj = res;
      console.log(this.deleteEmpObj);
      this.deleteEmpEduarr = this.deleteEmpObj.employeeEducationDetailsList;
      if (this.deleteEmpObj.message == "Employee Education Details have been deleted") {
        // swal(this.deleteEmpObj.message, "", "success");
        this.toast.success(this.deleteEmpObj.message);

      }
      this.getEmpEdu();
    })
  }

  // Employee Qualification Details 

  EmpQualObject: any;
  EmpQualArray;
  EmpQualification: [];
  Qualification: [];

  // Qualification_n: [];

  getEmployeeQualification() {
    var QualificationRequest =
    {
      "listEmployeeEducations": [{
      }],

      "transactionType": "getall"
    }
    this.hrms.getEmpEduQualification(QualificationRequest).subscribe(response => {
      this.EmpQualObject = response;

      this.EmpQualArray = this.EmpQualObject.listCourse;
      // for(let i=0; i< this.EmpQualArray.length; i++){
      //   //console.log(this.EmpQualArray[i].degree); //use i instead of 0
      //   this.EmpQualification = this.EmpQualArray[i];
      //  // this.Qualification_n.push(this.EmpQualArray[i]);
      //   //console.log("List of EmpQualification");
      //   console.log(this.EmpQualification['id']);
      //   console.log(this.EmpQualification['degree']);
      // }
      this.Qualification = this.EmpQualification;
      //console.log(this.EmpQualObject);
      console.log(this.EmpQualArray);
    })
  }


  // //----Editing Employee Education  Details-----------------------

  public editEduEmp =
    {
      "id": "",
      "employeeId": "",
      "qualification": "",
      "year_of_passing": "",
      "percentage_marks": "",
      "institution_name": "",
      "flagEdu": "",
      "updatedbyEdu": "",
      "createdbyEdu": "",
      "createdDate": "",
      "updatedDate": "",
      "image": ""
    }

  editEmpEdu: any;
  editEmpEduarr: any;
  empEmpEdudetails: any;
  updatedEduby: any;

  editEmpEduId(edu) {
    this.isUpdatedEduby = true;
    this.isCreatedEduby = false;

    var eduid = edu.id;
    var editEmpEduobj =
    {
      "employeeEducationDetailsList": [
        {
          "id": eduid

        }
      ],

      "transactionType": "getAll"
    }

    this.hrms.editEmpEduDetails(editEmpEduobj).subscribe(res => {
      this.editEmpEdu = res;
      this.editEmpEduarr = this.editEmpEdu.employeeEducationDetailsList;
      this.editEduEmp = this.editEmpEduarr[0];

      console.log("getbyid data is:", this.editEmpEdu);
      console.log("this.empEmpEdudetails isss", this.editEduEmp);

    })

    this.getEmpEdu();
  }

  //----Updating Employee Education details-----------------------
  updateedures: any;
  updateEduarr: any;
  updateEmpEduDetails1: any;

  updateEdu() {
    var user = "user";
    var updateEduObj =
    {
      "employeeEducationDetailsList": [
        {
          "id": this.getempeduarr[0].id,
          "employeeId": this.getempeduarr[0].employeeId,
          // "qualification": this.getempeduarr[0].qualification,
          // "year_of_passing": this.getempeduarr[0].year_of_passing,
          // "percentage_marks": this.getempeduarr[0].percentage_marks,
          // "institution_name": this.getempeduarr[0].institution_name,
          // "flag": true,
          "updatedBy": this.loggeduser,
          "image": this.filedata,
          "status": this.msg,
          //"rejectreason":this.reason
        }],

      "transactionType": "update"
    }


    console.log("Update req object :", updateEduObj);
    if (updateEduObj.employeeEducationDetailsList[0].image != null) {
      this.hrms.updateEmpEduDetails(updateEduObj).subscribe(res => {
        console.log("response is:", res);
        this.updateEmpEduDetails1 = res;
        // swal(this.updateEmpEduDetails1.message, "", "success");
        this.toast.success(this.updateEmpEduDetails1.message);
        // this.updateEdu();
        this.getEmpEdu();
      },

        err => { console.log(err) }
      )
    }
  }

  // console.log("Update req object :", updateEduObj);
  // this.hrms.updateEmpEduDetails(updateEduObj).subscribe( res =>{
  //   this.updateEmpEduDetails1 = res;
  //   console.log("Update condition check req object", this.updateEmpEduDetails);
  //   swal( this.updateEmpEduDetails1.message, "", "success");
  //   this.getEmpEdu();
  //  },
  // err => { console.log(err) } 
  //   )

  // }


  // if(updateEduObj.employeeEducationDetailsList[0].image!=null){
  //   this.hrms.updateEmpEduDetails(updateEduObj).subscribe(updateEdu => {
  //     //debugger;
  //     if (updateEdu) {
  //       this.getEmpEdu();
  //       console.log("pass img",updateEdu);

  //     console.log("updated successfully")
  //     this.getempeduarr[0] = updateEdu;

  //     //console.log("pppppppp"+this.updateEdu);
  //    this.getempeduarr = this.getempeduarr[0];
  //    this.edu_image=updateEduObj.employeeEducationDetailsList;
  //       swal("Employee Education Details Updated Succesfully", "", "success");
  //     }


  //    if (this.getempeduarr.message == "record updated successfully") {


  //     this.pass=false;
  //  this.getEmpEdu();

  //     }

  //   },
  //   err => { console.log(err) } 
  //   )
  // }
  // else {
  //   swal("Enter the valid Date of expiry", "", "error")
  //  this.getEmpEdu();
  // }


  // }

  //----Employee Education details Ends--------------------



  download() {

    //  for(let i in this.getempeduarr){
    let data = this.getempeduarr[0].image
    var filepdf = 'data:application/pdf;base64,' + data;
    //  }
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'education certificates';
    a.click();

  }
  Access = false;

  acceptEdu() {
    // //this.fileSelected(this.getempeduarr[0].image)
    // var user = "user";
    // console.log("hiiiiiii", this.getempeduarr[0].image)
    // var updateEduObj1 =
    // {
    //   "employeeEducationDetailsList": [
    //     {
    //       "id": this.getempeduarr[0].id,
    //       "employeeId": this.getempeduarr[0].employeeId,
    //       // "qualification": this.getempeduarr[0].qualification,
    //       // "year_of_passing": this.getempeduarr[0].year_of_passing,
    //       // "percentage_marks": this.getempeduarr[0].percentage_marks,
    //       // "institution_name": this.getempeduarr[0].institution_name,
    //       // "flag": true,
    //       "updatedBy": this.loggeduser,
    //       // "image":this.getempeduarr[0].image,
    //       "status": this.msg,
    //       "comment": "Certificates have been Accepted"
    //     }],

    //   "transactionType": "statusupdate"
    // }


    var sttausupdateEduObj1 = {
      "employeeEducationDetailsList": [{
        "id": this.getempeduarr[0].id,
        "employeeId": this.getempeduarr[0].employeeId,
        "flag": true,
        "updatedBy": this.loggeduser,
        "comment": "Certificates have been Accepted",
        "status": "Accepted"
      }],

      "transactionType": "statusupdate"
    }

    console.log("accept req ", sttausupdateEduObj1)
    if (sttausupdateEduObj1.employeeEducationDetailsList[0].status != null) {
      this.hrms.statusupdateEmpEduDetails(sttausupdateEduObj1).subscribe(res => {
        this.acceptdata = res;
        // swal(this.acceptdata.message, '', 'success')
        this.toast.success(this.acceptdata.message)
        this.getEmpEdu();
        this.Access = true

      },
        err => {
          err => { console.log(err) }
          //this.toast.error(err.error.error.message)
        })

    }
    else {

      this.getEmpEdu();
    }

  }

  declineEdu(e) {
    //this.fileSelected(this.getempeduarr.image)

    var user = "user";
    // var updateEduObj2 =
    // {
    //   "employeeEducationDetailsList": [
    //     {
    //       "id": this.getempeduarr[0].id,
    //       "employeeId": this.getempeduarr[0].employeeId,
    //       // "qualification": this.getempeduarr[0].qualification,
    //       // "year_of_passing": this.getempeduarr[0].year_of_passing,
    //       // "percentage_marks": this.getempeduarr[0].percentage_marks,
    //       // "institution_name": this.getempeduarr[0].institution_name,
    //       // "flag": true,
    //       "updatedBy": this.loggeduser,
    //       // "image":this.getempeduarr[0].image,
    //       "status": this.msg,
    //       "comment": e.uname
    //     }],

    //   "transactionType": "statusupdate"
    // }

    var sttausupdateEduObj2 = {
      "employeeEducationDetailsList": [{
        "id": this.getempeduarr[0].id,
        "employeeId": this.getempeduarr[0].employeeId,
        "flag": true,
        "updatedBy": this.loggeduser,
        "comment": e.uname,
        "status": "Decline"
      }],

      "transactionType": "statusupdate"
    }

    console.log("Decline req ", sttausupdateEduObj2);


    if (sttausupdateEduObj2.employeeEducationDetailsList[0].status != null) {

      this.hrms.statusupdateEmpEduDetails(sttausupdateEduObj2).subscribe(res => {
        this.declinedata = res;
        // swal(this.declinedata.message, '', 'success')
        this.toast.success(this.declinedata.message)
        this.getEmpEdu();
        this.Access = false
      },
        err => { console.log(err) }
      )
    }
    else {

      this.getEmpEdu();
    }

  }
  //skill starts
  updateRes: any;
  skill_id: any;
  level_id: any;
  skillid: any;
  employee_id: any;
  created_by: any;
  skillReq: any;
  SkillArr: any;
  skillinfolist: any;
  skillInfoList: any;
  deleteSkilldetails: any;
  deleteSkillarr: any;
  skillbyid: any;
  SkillDetails: any;
  update_by: any;
  updateArr: any;

  skillmasterlist: any;
  skillmasterArray;

  //method save skillInfo
  setSkillInfo() {

    var count = 0;
    var str = this.SKILLinfo.skill_id;

    this.updateSkilldetails.map(d => {
      var one = d.skill_id
      if (one == str) {
        count = 1
      }
    })

    if (count == 1) {

      this.toast.error("Duplicates are not allowed!")

    }

    else {

      var reqData =
      {

        "skillInfoModel":
          [{



            "skill_id": this.SKILLinfo.skill_id,

            "level_id": this.SKILLinfo.level_id,

            "employee_id": this.eid,

            "created_by": this.loggeduser

          }],



        "sessionId": "14",

        "transactionType": "save"


      }
      console.log("skills", reqData);

      this.hrms.setSkill(reqData).subscribe(responce => {
        this.skillReq = responce;
        console.log(this.skillReq);
        if (this.skillReq) {

          // swal(this.skillReq.statusMessage, "", "success");
          this.toast.success(this.skillReq.statusMessage);

          this.getSkillInfo();

        }

      });
    }

  }

  //method get skillInfo

  getSkillName: any;
  getSkillInfo() {

    var Requestdata =
    {
      "skillInfoModel": [{
        "employee_id": this.eid
      }],
      // "sessionId": "1234",

      "transactionType": "getByEmpId"

    }

    this.hrms.getSkill(Requestdata).subscribe(responce => {
      this.skillinfolist = responce;
      this.updateSkilldetails = this.skillinfolist.getSkillInfoList;
      console.log("skills dy id", this.updateSkilldetails);

    })

  }

  //masterskill get method 
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
      console.log(this.skillmasterArray)
    })

  }

  addSkillvalue(newUserForm1) {
    newUserForm1.reset()
    this.isUPDATEDBY = false;
    this.CREATEDBY = true;
  }
  isUPDATEDBY = false;
  CREATEDBY = true;

  public SKILLinfo = {
    "skill_id": "",

    "level_id": "",

    "employee_id": "",

    "created_by": "",

    "update_by": "",
    "flag": "",
    "id": ""
  }

  //method edit skillInfo
  getdatabyID(tableData) {

    this.isUPDATEDBY = true;
    this.CREATEDBY = false;

    console.log(tableData);

    var skillid = tableData.id;

    var GetUpdateData = {

      "skillInfoModel": [{

        "id": skillid

      }],
      "sessionId": "1234",

      "transactionType": "getbyid"

    }

    this.hrms.getSkilbyId(GetUpdateData).subscribe(res => {
      this.skillbyid = res;
      this.SkillDetails = this.skillbyid.getSkillInfoList;
      this.SKILLinfo = this.SkillDetails[0];
      console.log("this.SKILLinfo", this.SKILLinfo)
    })
  }

  //method update skillInfo
  updateSkilldetail(SKILLinfo) {

    var updaterequestData = {

      "skillInfoModel": [{


        "skill_id": SKILLinfo.skill_id,

        "level_id": SKILLinfo.level_id,

        "employee_id": this.SKILLinfo.employee_id,
        "created_by": this.loggeduser,
        "update_by": this.loggeduser,
        "id": this.SKILLinfo.id

      }],


      "sessionId": "14",

      "transactionType": "update"


    }


    console.log("skill data is", updaterequestData);
    this.hrms.updateSkill(updaterequestData).subscribe(res => {
      this.updateRes = res;

      console.log("skill data is", this.updateRes);

      if (this.updateRes) {

        // swal(this.updateRes.statusMessage, "", "success");
        this.toast.success(this.updateRes.statusMessage);
        this.getSkillInfo();

      }

    })


  }
  //skill Info ends

  //certtification starts

  certificationName: any;
  issuedBy: any;
  dateOfIssue: any;
  // employeeId:any;
  // updatedBy:any;
  // flag:boolean;
  // createdBy:any;
  certificationReq: any;
  certificationArr: any;
  certificationDetailslist: any;
  cerDetailsList: any;
  deletedcertificationDetails: any;
  certificationDetailsListarr: any;
  CertificationDetails: any;
  certificationbyid: any;
  //  id:any;
  updateRequest: any;


  //method for save certification details
  setCertificationDetails() {

    var requestData = {


      "certificationDetailsModel": [
        {

          "certificationName": this.Certification.certificationName,
          "issuedBy": this.Certification.issuedBy,
          "dateOfIssue": this.Certification.dateOfIssue,
          "createdBy": this.Certification.createdBy,
          "employeeId": this.eid,
          "flag": this.Certification.flag
        }
      ],

      "sessionId": "fk21",
      "transactionType": "save"
    }


    this.hrms.setCertification(requestData).subscribe(responce => {
      this.certificationReq = responce;
      console.log(this.certificationReq);

      if (responce) {
        // swal(this.certificationReq.message, "", "success");
        this.toast.success(this.certificationReq.message);
        this.getCertificationDetails();
      }

      this.certificationArr = this.certificationReq.certificationDetailsModel;
      //this.getCertificationDetails();

    });
  }


  //method for get cerfification details
  getCertificationDetails() {

    var request =
    {

      "certificationDetailsModel": [{

        "employeeId": this.eid

      }],

      "transactionType": "getbyid"
    }

  
    this.hrms.getCertification(request).subscribe(res => {
      this.certificationDetailslist = res;
      this.cerDetailsList = this.certificationDetailslist.certificationDetailsList;
      console.log(this.cerDetailsList);

    })
  }

  //method for delete certification
  deleteCertificationDetails(certifications) {
    var Deletereq = {

      "certificationDetailsModel": [
        {
          "id": certifications.id,
          "updatedBy": certifications.updatedBy

        }],

      "sessionId": "fk21",
      "transactionType": "delete"
    }
    this.hrms.deleteCertification(Deletereq).subscribe(res => {
      this.deletedcertificationDetails = res;
      this.certificationDetailsListarr = this.deletedcertificationDetails.certificationDetailsList;
      console.log(this.deletedcertificationDetails);

      if (this.deletedcertificationDetails.message == "Employee Certification detail deleted successfuly") {
        // swal(this.deletedcertificationDetails.message, "", "success");
        this.toast.success(this.deletedcertificationDetails.message);

        this.getCertificationDetails();
      }
      this.getCertificationDetails();
    });
  }


  Addvalue(newUserFormCer) {
    newUserFormCer.reset()
    this.isUpdateBy = false;
    this.createdBY = true;
  }
  isUpdateBy = false;
  createdBY = true;

  public Certification = {
    "certificationName": "",
    "issuedBy": "",
    "dateOfIssue": "",
    "id": "",
    "createdBy": "",
    "updatedBy": "",
    "employeeId": "",
    "flag": ""

  }

  //edit certification details
  getDatabyId(certifications) {
    this.isUpdateBy = true;
    this.createdBY = false;
    console.log(certifications);
    var Cerid = certifications.id;

    var getUpdatedata = {

      "certificationDetailsModel": [
        {
          "id": Cerid
        }
      ],

      "sessionId": "fk21",
      "transactionType": "getbyid"
    }


    this.hrms.getCertificationbyId(getUpdatedata).subscribe(res => {
      this.certificationbyid = res;
      this.CertificationDetails = this.certificationbyid.certificationDetailsList;
      this.Certification = this.CertificationDetails[0];
      console.log("this.Certification", this.Certification)
    })
  }

  //method for update certification details
  updateCertificationdetails() {

    var updaterequestData = {

      "certificationDetailsModel": [
        {

          "certificationName": this.Certification.certificationName,
          "issuedBy": this.Certification.issuedBy,
          "dateOfIssue": this.Certification.dateOfIssue,
          "id": this.Certification.id,
          "employeeId": this.Certification.employeeId,
          "updatedBy": this.loggeduser,
          "createdBy": this.loggeduser,
          "flag": this.Certification.flag
        }],

      "sessionId": "fk21",
      "transactionType": "update"
    }

    this.hrms.updateCertification(updaterequestData).subscribe(res => {
      this.updateRequest = res;

      console.log(this.updateRequest);
      if (this.updateRequest.message == "Employee Certification detail updated successfuly") {
        // swal(this.updateRequest.message, "", "success");
        this.toast.success(this.updateRequest.message);
        this.getCertificationDetails();
      }
      //  this.getCertificationDetails();
    })


  }
  //certificatiion details ends

  openFileBrowser(event: any) {



  }
  show() {
    this.view = true;
  }
  save(modalform) {
    this.reason = modalform.value.uname;
    console.log("reason :", this.reason)
    // swal("Education Certificates are Declined", `Due to : ${this.reason}`, "success");
    this.toast.success("Education Certificates are Declined", `Due to : ${this.reason}`);
  }
}//class close
