import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-employee-education',
  templateUrl: './employee-education.component.html',
  styleUrls: ['./employee-education.component.scss']
})
export class EmployeeEducationComponent implements OnInit {
  value: boolean;
  deletedEduDetails: any;
  EmpEduUpdateDetails: any;
  EmpEduSaveList: any;
  EmpEduSaveDetails: any;
  EmpEducationDetails: any;
  pg_stream: any;
  degree_stream: any;
  pg: any;
  degree: any;
  educationType: any;
  edu_master: any;
  private pageSize: number = 5;
  eid: any;
  key: string;
  editname: any;
  //   public data1=[];
  //   public show:boolean = false;
  //   public buttonName:any = 'Show';


  //   //new code starts here
  //   value: boolean;
  //   data; 
  //   employeeeducation:any;
  //   employeeEducation;
  //   serialno:any;
  //   educationType:any;
  //   empEducation:any;
  //   isEditable:boolean = false;
  //   reverse: boolean = false;
  //   order: string = 'name';

  //   employeeEducationlist:EmployeeEducation[]=[{

  //     "sno": "1",
  //     "educationType":"Post Graduation",
  //     "education":"MTech"
  //    },
  //  {
  //    "sno": "2",
  //     "educationType":"Graduation",
  //     "education":"BTech"
  //  },
  //  {
  //    "sno": "3",
  //     "educationType":"Post Graduation",
  //     "education":"MTech"
  //  },
  //  {
  //   "sno": "4",
  //    "educationType":"Post Graduation",
  //    "education":"MTech"
  // },
  //  {
  //   "sno": "5",
  //    "educationType":"Graduation",
  //    "education":"BTech"
  // }]





  constructor(private hrms: HrmsService, private toast: NotificationService) {
  }

  ngOnInit() {
    this.getEmployeeQualification();
    //this.getEmpEducation();
  }


  //   handleClick(event: Event) {
  //     console.log('Button clicked', event)
  //   }
  //   toggle() {
  //     this.show = !this.show;

  //     // CHANGE THE NAME OF THE BUTTON.
  //     if(this.show)  
  //       this.buttonName = "Hide";
  //     else
  //       this.buttonName = "Show";
  //   }
  //   // deleteFieldValue(index) {
  //   //   debugger; 
  //   //   this.data.splice(index, 1);
  //   //   }


  //   saveBu(){
  //     debugger;
  //     this.value = false;
  //     this.data = {
  //       "sno":this.serialno,
  //       "educationType":this.educationType,
  //       "education":this.empEducation,
  //     }
  //     this.employeeEducationlist.unshift(this.data);

  //     this.employeeEducation='';


  //   }
  //   editData(blist){
  //     console.log(blist);
  //     //this.listDetails = blist;
  //     this.serialno = blist.serialno;
  //     this.educationType = blist.educationType; 
  //     this.empEducation = blist.empEducation;

  //   }
  //   saveData(){
  //     var editD = {
  //       "sno":this.serialno,
  //       "educationType":this.educationType,
  //       "education":this.empEducation,

  //     }
  //     console.log(editD);
  //   }
  //   deleterow(index){
  //     debugger;
  //     if(index !== -1){
  //       this.employeeEducationlist.splice(0,1);
  //     }
  //     else {
  //     this.employeeEducationlist.splice(index,1);
  //     }
  // }

  // setOrder(value: string) {
  //   if (this.order === value) {
  //     this.reverse = !this.reverse;
  //   }

  //   this.order = value;

  // }
  UpdateEmpEduQual: any;
  noedit: boolean;
  searchfield = false;
  addb = true;
  edit(id,name) {
    this.eid = id;
    this.value = false
    this.noedit = true;
    this.searchfield = true;
    this.addb = false;
    this.editname=name
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.key=""
    this.getEmpEducation();
    this.getEmployeeQualification();

  }




  setEmpEducation(bulist) {
    
    this.edu_master = bulist;
    this.searchfield = false;
    console.log("update object",bulist);
    var count=0
    var str=bulist.educationType.replace(/[\. ,:-]+/g, "");
    console.log("get all",this.EmpQualArray);
    
    this.EmpQualArray.map(d=>{
      var one=d.educationType.replace(/[\. ,:-]+/g, "")
      if(one.toLowerCase()==str.toLowerCase() && d.id != this.eid){
        count=1
      }
    })
    if(count==1){
      this.toast.error("invalid")
      this.getEmployeeQualification();
      this.noedit = false;
      this.value=false;
      this.addb=true;
    }else{
      var request = {
        "listEmployeeEducations": [{
          "id": this.eid,
          "educationType": bulist.educationType
          // "pg":bulist.pg,
          // "degree_stream":bulist.degree_stream,
          // "pg_stream":bulist.pg_stream
  
        }],
  
        "transactionType": "update"
      }
      console.log("Update Request", request);
      this.addb = true;
      this.hrms.updateEmpEducationalQualification(request).subscribe((data: any) => {
        this.UpdateEmpEduQual = data,
          console.log(this.UpdateEmpEduQual);
        if (this.UpdateEmpEduQual.message == "Employeee education record updated Successfully") {
          this.noedit = false;
          this.value = false;
          // swal(this.UpdateEmpEduQual.message, "","success");
          this.toast.success(this.UpdateEmpEduQual.message)
          this.getEmployeeQualification();
        }
      },
        error => {
         
          // swal("Duplicates are not allowed","","error");
          this.toast.error("Duplicates are not allowed")
          this.getEmployeeQualification()
          this.noedit = false;
        })
    }
   this.key=""
  }

  getEmpEducation() {
    var responseData =
    {
      "employeeEducation": {
      },
      "sessionID": "12321",
      //"transactionType": "getall"
    }
    this.hrms.getEmployeeEducation(responseData).subscribe(res => {
      this.EmpEduSaveDetails = res,
        this.EmpEduSaveList = this.EmpEduSaveDetails.listCourse;
      console.log(this.EmpEduSaveDetails);
    })
  }

  // Employee Qualification Details 

  //master data for Employee Educational Qualification

  EmpQualObject: any;
  EmpQualArray: any[];
  EmpQualification: any[];
  getQualification: any[];

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
      //this.getQualification=this.EmpQualification;
      console.log("Employee Education details", this.EmpQualObject);
      console.log(this.EmpQualArray,"in");
    })
    console.log(this.EmpQualArray,"out");
  }

  //  saveUpdatedValues(bulist){
  //    var udatedvalue = {

  //     "employeeEducation":{
  //             "id":bulist.id,
  //             "degree"                :bulist.degree,
  //             "pg"                        :bulist.pg,
  //             "degree_stream"        :bulist.degree_stream,
  //             "pg_stream"                :bulist.pg_stream
  //     },
  //     "sessionID":"12321",
  //     "transactionType":"update"

  // }
  // this.hrms.updatedEmployeeEducation(udatedvalue).subscribe(res =>{
  //   this.EmpEduUpdateDetails = res,
  //   console.log(this.EmpEduUpdateDetails);
  //   if(this.EmpEduUpdateDetails.statusMessage == "Success fully Education record updated"){
  //     swal(this.EmpEduUpdateDetails.statusMessage, "","success");
  //     //this.getEmpEducation();
  //   }
  // })
  //  }

  EmployeeQualification: any;

  saveEmpEducationalQual() {

    var count = 0
    var str = this.educationType.replace(/[\. ,:-]+/g, "");
    this.EmpQualArray.map(d => {
      var one = d.educationType.replace(/[\. ,:-]+/g, "")
      if (one.toLowerCase() == str.toLowerCase()) {
        count = 1
      }
    })
    if (count == 1) {
      this.toast.error("Duplicates are not allowed")

    } else {
      var saveEmpEduQualRequest = {
        "listEmployeeEducations": [{
          "educationType": this.educationType

        }],

        "transactionType": "save"
      }
      this.hrms.saveEmpEducationalQualification(saveEmpEduQualRequest).subscribe((response: any) => {
        this.EmployeeQualification = response;
        console.log("kkkkk", this.EmployeeQualification.message);
        if (this.EmployeeQualification.message == "Employeee education record saved Successfully") {
          // swal(this.EmployeeQualification.message, "","success");
          this.toast.success(this.EmployeeQualification.message)
          this.getEmployeeQualification();
        }
      },
        error => {
          // swal("Duplicates are not allowed","","error");
          this.toast.error("Duplicates are not allowed")

          this.getEmployeeQualification();
        })
    }

    this.value = false;
    this.educationType = ""


  }


  cancelbulist() {
    this.value = false;
  }

  deleteEmpEdu(bulist) {
    var deletevalue = {
      "listEmployeeEducations": [{
        "id": bulist.id
      }],

      "transactionType": "delete"
    }
    this.hrms.deleteEmpEducationalQualification(deletevalue).subscribe(res => {
      this.deletedEduDetails = res,
        console.log(this.deletedEduDetails);
      if (this.deletedEduDetails.statusMessage == "Successfully employeee education deleted") {
        // swal(this.deletedEduDetails.statusMessage, "","success");
        this.toast.success(this.deletedEduDetails.statusMessage)
        this.getEmployeeQualification();
      }
    })
  }
  navigateTo(){
    this.hrms.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
  }