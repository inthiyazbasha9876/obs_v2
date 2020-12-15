import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import * as moment from 'moment';
import { DataService } from 'src/app/home/services';

@Component({
  selector: 'app-resignation',
  templateUrl: './resignation.component.html',
  styleUrls: ['./resignation.component.scss']
})
export class ResignationComponent implements OnInit {
  ddddd: any;
  date: Date;
  date1: Date;
  d: number;
  // [x: string]: any;
  applystatus:any;
  leavingDatemodifies:any;
  finalSettlementDate1:any;
  saveres:any;
  output: any;
  eid: string;
  ippr: any = 5
  updateResignationList = [];
  updateEmpid;
  upemailId1;
  upemailId2;
  upemailId4
  upemailId3;
  upemployeeIsDeceased;
  upfinalSettlementDate;
  upid;
  upleavingDate;
  upleavingReason;
  upremarks;
  upresignationSubmittedOn;
  upresignationTyp;
  upstate;
  role: any;
  getbyidstatus: boolean;
  encryptedUsername: string;
  loggeduser: any;
  encryptedRole: string;

  constructor(private hrms: HrmsService, private fb: FormBuilder, private dataservice: DataService,private authService: AuthService,private toast:NotificationService ) {
    // , private toastr: ToastrManager
    this.eid = this.dataservice.paramId;
    console.log(this.eid, "    this is in construcrto ", this.dataservice.paramId)
    this.encryptedRole=sessionStorage.getItem('Role');
    this.role=this.authService.decryptData(this.encryptedRole);
  }

  leavingDate1: Date;
  resignationSubmittedOn1: any;
  finalSettlementDate1s: any;
  resignationmodifies: any;
  finalSettlementsmodifies: any;
  leaingreasoninfo;

  setUserRole;

  maxDate = moment().subtract(18, 'years').format('YYYY-MM-DD');
  minDate = moment().subtract(118, 'years').format('YYYY-MM-DD');
  formList: FormGroup;
  ngOnInit() {
    this.encryptedUsername=sessionStorage.getItem('UserName');
    this.loggeduser=this.authService.decryptData(this.encryptedUsername);
    console.log("loggeduser",this.loggeduser);
    
    this.setUserRole = sessionStorage.getItem("setUserRole");
    console.log('role to check user', this.setUserRole);
    if (this.setUserRole == "true") {
      this.roleUser = true;
    }
    // this.getResignationDetails();
    this.getSeperationType();
    this.resignationGetByEmpId();
    this.udateResignation(this.user);
    this.abc();
    this.fn();







    this.formList = new FormGroup({
      //employeeIsDeceased:new FormControl('',Validators.required),
      // dateOfDemise:new FormControl('',Validators.required),
      leavingDate: new FormControl('', Validators.required),
      resignationSubmittedOn: new FormControl('', Validators.required),
      finalSettlementDate: new FormControl('', Validators.required),
      resignationType: new FormControl('', Validators.required),
      Remarks: new FormControl('', Validators.required),
      state: new FormControl('', Validators.required)
    })
    this.leavingDate1 = new Date();
    // this.leavingDatemodifies = this.leavingDate1.toISOString().split("T")[0];
    this.leavingDatemodifies=this.formatDate(this.leavingDate1)


    this.resignationSubmittedOn1 = new Date(Date.now() + (30 * 24 * 60 * 60 * 1000));
    //this.resignationmodifies = this.resignationSubmittedOn1.toISOString().split("T")[0];
    this.resignationmodifies = this.formatDate(this.resignationSubmittedOn1);

    this.finalSettlementDate1 = new Date(Date.now() + (45 * 24 * 60 * 60 * 1000));
    //this.finalSettlementsmodifies = this.finalSettlementDate1.toISOString().split("T")[0];
    this.finalSettlementsmodifies =this.formatDate(this.finalSettlementDate1);
  }


  resignationform = this.fb.group({
    leavingreason: ['', Validators.required]
  })

  updateresignationform=this.fb.group({

    employeeId: [Validators.required],
    leavingDate: [Validators.required],
    ResignationSubmittedOn: [Validators.required],
    FinalSettlementDate: [Validators.required],
    State: [Validators.required],
    resignationType:[Validators.required]
    

  })


  hidetothank = true;
  a1;
  saveResignationDetailsadv(a) {

    var a1 = this.getbyidlogic()
    console.log(a1,
      " resignation")
    console.log("leaving reason", a);


    var req = {

      "resignation": [
        {
          "employeeId": this.loggeduser,
          "dateOfDemis": null,
          "emailId1": "narayana.jadapally@ojas-it.com",
          "emailId2": "shiva@gmail.com",
          "emailId3": "naveen@gmail.com",
          "emailId4": "sri@gmail.com",
          "employeeIsDeceased": " false",
          "finalSettlementDate": this.finalSettlementsmodifies,

          "leavingDate": this.leavingDatemodifies,
          "leavingReason": a.leavingreason,
          "remarks": "nice",
          "resignationSubmittedOn": this.resignationmodifies,
          "resignationType": "2",
          "state": "applied"
        }],
      "transactionType": "save"
    }
    console.log(req, "lkjhgfdsa")
    if (a1) {

    }
    else {


      this.hrms.saveResignation(req).subscribe(res => {
        console.log(res, "this is from resignarion of narayana")
        this.saveres = res;
        if(res){
          this.resignationform.reset();
          this.resignationGetByEmpId();
          if (this.saveres.message == "Successfully record added") {
         
            this.toast.success(this.saveres.message,"Success")
  
          }
        }
        
       

      })
    }

  }




  flag: any;
  user: any;
  id: any;
  EmployeeId: any;
  Remarks: any;
  resignationType: any;
  leavingReason: any;
  emailId1: any;
  emailId2: any;
  emailId3: any;
  emailId4: any;
  employeeIsDeceased: any;
  dateOfDemise: any;
  leavingDate: any;
  resignationSubmittedOn: any;
  finalSettlementDate: any;
  access: boolean = false;
  use: any;
  list: any;
  n: any;
  useSep: any;
  sepList: any;
  isEditable: boolean;
  state: any;


  getbyidlogic() {
    if (this.empIdRes.message == "success") {
     
      this.toast.info('You have already applied for resignation, please wait for Manager approval',"Info")
      return true;
    }

    else {
      return false;
    }


  }

  fn() {
    this.user = this.fb.group({
      // EmployeeId:['',Validators.required],
      // resignationType:['',Validators.required],
      leavingReason: ['', Validators.required],
      // employeeIsDeceased:['',[Validators.required]],
      // dateOfDemise:['',[Validators.required]],
      // leavingDate:['',Validators.required],
      // resignationSubmittedOn:['',Validators.required],
      // finalSettlementDate:['',Validators.required],
      //   Remarks:['',Validators.required],
      //  state:['',Validators.required ]
    });
  }








  loc: string = this.loggeduser




  getSeperationType() {
    var reqSep = {
      "separationType": [

      ],
      "sessionId": "1234",
      "transactionType": "getall"
    }

    this.hrms.getSeperationType(reqSep).subscribe(res => {
      this.useSep = res;
      this.sepList = this.useSep.separationTypeList;

      console.log(this.sepList, "seperation type getall");
    })
  }
  getResignationDetails() {

    var req1 = {
      "resignation": [
        {

        }],
      "transactionType": "getall"
    }
    this.hrms.getResignation(req1).subscribe(res => {
      this.use = res;
      this.output = this.use.resignationList;
      console.log(this.output);
      console.log(this.use + " this  in resignation");
      this.list = this.use.resignationList;
      //  this.n = this.list.map(x=>x.emailId1);
      //  console.log(this.n, "  all ids")
      console.log(this.list, " this  in from narayana");



    })
  }
  savResiRes: any;
  saveResList: any;
  saveResignationDetails(us) {
    this.user.controls['resignationType'].markAsTouched();
    this.user.controls['leavingReason'].markAsTouched();
    this.user.controls['leavingDate'].markAsTouched();
    this.user.controls['resignationSubmittedOn'].markAsTouched();
    this.user.controls['finalSettlementDate'].markAsTouched();

    this.getResignationDetails();


    console.log(this.loc, " in save methis");
    var req = {

      "resignation": [
        {
          "employeeId": this.loc,

          "remarks": "good",
          "resignationType": us.resignationType,
          "leavingReason": us.leavingReason,

          // "employeeIsDeceased":us.employeeIsDeceased,
          //"dateOfDemise":us.dateOfDemise,
          "leavingDate": us.leavingDate,
          "resignationSubmittedOn": us.resignationSubmittedOn,
          "finalSettlementDate": us.finalSettlementDate

        }],
      "transactionType": "save"
    }

    console.log("request ", req);
    this.hrms.saveResignation(req).subscribe(res => {
      this.savResiRes = res;
      this.saveResList = this.savResiRes.resignationList;

      console.log(this.savResiRes, "save resignation details");


      if (this.saveResList.message == "Successfully record added") {
        // swal(this.saveResList.message, "", "success");
        this.toast.success(this.saveResList.message)


      }
    },
      error => {
        // swal("Duplicates are not allowed", "", "error");
        this.toast.error("Duplicates are not allowed")



        //this.getResignationDetails();
      })

  }

  upList: any;
  upResponse: any;
  udateResignation(usr) {

    console.log(this.eid, "")
    console.log(usr, " in HR update");


    // var resobj = {
    //   "resignation": [{
    //     "id":this.empList[0].id,
    //     "employeeId": usr.employeeId,
    //     "state": usr.State
    //   }],
    //   "transactionType": "updatestate"
    // }
    var resobj ={
      "resignation":
      [{
             "id":this.empList[0].id,
              "employeeId": usr.employeeId,
              "resignationType": usr.resignationType,
              "leavingDate": this.formatDate(new Date(usr.leavingDate)),
              "resignationSubmittedOn":this.formatDate(new Date(usr.ResignationSubmittedOn)),
              "finalSettlementDate": this.formatDate(new Date(usr.FinalSettlementDate)),
              "state": usr.State
      }],
      "transactionType":"update"
      }
      
    console.log("sssssssssssssssssssssssssssssss", resobj);
    this.hrms.updateResignation(resobj).subscribe(res => {
      console.log(res, "this is updated by id")
      this.upResponse = res;
      console.log("update list in updte", this.upResponse);
      this.upList = this.upResponse.resignationList;
      console.log(this.upList, " upated list ");


      if (res) {
        // swal(this.upResponse.message, "", "success");
        this.toast.success(this.upResponse.message)
        this.resignationGetByEmpId();
      }
    }
    )
    //this.resignationGetByEmpId();
  }

  empIdRes: any;
  empIdResponseList: any;
  empList: any;
  finaldata: any
  resignationId: any
  tabledata:any;
  resignationGetByEmpId() {
    console.log("Eid : ", this.eid);

    var req2 = {

      "resignation": [{

        "employeeId": this.eid

      }],
      "transactionType": "getById"

    }
    console.log(req2, "request")
    this.hrms.getByEmpIdResignation(req2).subscribe(re => {
      console.log(re, "this is getbyid")
      this.empIdRes = re;
      // console.log(this.empIdRes.message,"suemessage")
     

      if (this.empIdRes.message == "success") {
        this.applystatus = true;
         this.empList = this.empIdRes.resignationList;
    console.log(this.empList,"datagetbyid")
        this.tabledata=this.empList
         console.log(this.tabledata,"ressponsefortable")
         this.resignationId = this.empList[0].resignationType
      
        // this.upstate = this.empList[0].state
         var rdate = this.formatDate(new Date(this.empList[0].resignationSubmittedOn))
         var ldate = this.formatDate(new Date(this.empList[0].leavingDate))
         var fsdate = this.formatDate(new Date(this.empList[0].finalSettlementDate))
        // this.finaldata[0].resignationSubmittedOn = rdate
        // this.finaldata[0].finalSettlementDate = fsdate
        // this.finaldata[0].leavingDate = ldate

        // var sp = this.sepList.find(s => s.separationTypeId == this.finaldata[0].resignationType)
        // this.finaldata[0].resignationType = sp.separationType
        // this.upstate = this.finaldata[0].state
        // this.updateResignationList = this.empList;
        console.log(this.empList[0].resignationType,"resignationtype")
       var a= this.updateresignationform.controls
        a.employeeId.setValue(this.empList[0].employeeId)
        a.leavingDate.setValue(ldate)
        a.ResignationSubmittedOn.setValue(rdate)
        a.FinalSettlementDate.setValue(fsdate)
        a.State.setValue(this.empList[0].state)
        a.resignationType.setValue(this.empList[0].resignationType)
      }
      if ((this.empIdRes.message == 'no records found') && ((this.role == 'ROLE_MANAGER') || (this.role == 'ROLE_ADMIN') || (this.role == 'ROLE_HR')) && (sessionStorage.getItem('setUserRole') != 'true')) {
        // this.toastr.infoToastr('Resignation not yet applied ', 'Info', {
        //   showCloseButton: true,
        //   animate: 'slideFromRight'
        // })
        this.toast.info('Resignation not yet applied ', 'Info')
      }
      









    })
  }
  roleUser: boolean = false;
  abc(): boolean {
    if ((this.role == "ROLE_USER") || (this.role == "ROLE_BDM")||(this.role == "ROLE_FINANCE")
      || (this.role == "ROLE_SALES")||(this.role == "ROLE_BUHEAD")||(this.role == "ROLE_SBUHEAD")||(this.role == "ROLE_DM")||(this.role == "ROLE_BDMHEAD") )   {
      return this.roleUser = true;
    }
    else if (this.role == 'ROLE_BDM') {

      return this.roleUser = true;
    }
    console.log(this.loc, " user ema");
    console.log("deddddddddddddddddddddd", this.roleUser);
  }

  da(f) {

    this.date = new Date(f);
    this.d = this.date.setDate(this.date.getDate() + 90);
    this.date1 = new Date(this.d);
    console.log(this.date1);
  }

  is_edit: boolean = true;







  foo: any;
  fo: any;


  edit(h) {
    console.log("jjjjjj", h);
    //this.foo = false;
    //this.fo=true;
    // this.h.setValue({
    //   employeeIsDeceased:this.employeeIsDeceased,
    //   dateOfDemise:this.dateOfDemise,
    //   leavingDate:this.leavingDate,
    //   resignationSubmittedOn:this.resignationSubmittedOn,
    //   finalSettlementDate:this.finalSettlementDate,
    //   resignationType:this.resignationType
    // })


    this.EmployeeId = h.employeeId;
    this.resignationType = h.resignationType;
    console.log("resi", this.resignationType);
    this.employeeIsDeceased = h.employeeIsDeceased;
    //this.dateOfDemise = h.dateOfDemise;
    this.leavingDate = h.leavingDate;
    this.resignationSubmittedOn = h.resignationSubmittedOn;
    this.finalSettlementDate = h.finalSettlementDate;
    this.state = h.state;
    console.log("this for stateeee", this.state)
    console.log("hhhhhhhhhhhhhhhhh", h);
  }


  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }
}