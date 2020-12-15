import { Component, OnInit, HostListener } from '@angular/core';
import { takeUntil } from 'rxjs/operators';
import { HrmsService } from '../../services/hrms.service';
import { Subject } from 'rxjs';
import { RmgService } from '../../services/rmg.service';

@Component({
  selector: 'app-bench-employees',
  templateUrl: './bench-employees.component.html',
  styleUrls: ['./bench-employees.component.scss']
})
export class BenchEmployeesComponent implements OnInit {

  employeeSubscription: Subject<any> = new Subject();
  private projectAll: Subject<any> = new Subject();
  empbasicinfo: any;
  pic1: any=[];
  empbasic: any;
  empbasicin: any;
  role:any;
  manager: unknown[];
  employee: unknown[];
  managerdata: any[];
  eid: any;
  empShortList: any;
  pages:any=5
  orderid:any
  ordername:any

  constructor(private hrms:HrmsService,private rmgService:RmgService) { 
    this.role=sessionStorage.getItem('Role');
    this.eid=sessionStorage.getItem('UserName');
   }

  ngOnInit() {
    this.getempdata();
  }

  ngOnDestroy(): any {
    this.employeeSubscription.next();
    this.employeeSubscription.complete();
    this.projectAll.next();
    this.projectAll.complete();
  }

  loading = true;


  getempdata() {
    //this.isupdate=false;
let empbasic
    var empinfo =

    {
      "rmgemployeelist":
  
      {

        },
  
  
     
     "transactiontype":"getempbytstatus"
}
    // {
    //   "employeeInfo": [{

    //   }],
    //   "transactionType": "getall"
    // }

    this.rmgService.getAllEmp(empinfo).pipe(takeUntil(this.projectAll)).subscribe(res => {
      empbasic = res;
      this.empbasicin = empbasic.rmgemployeelist;
      console.log(this.empbasicin,"Bench");
      
      this.loading = false;
      // if (this.role === 'ROLE_MANAGER') {
      //   this.empbasicinfo = this.empbasicin.filter(info => info.reportingManager == this.eid);
      //   //console.log("Report data : ", this.empbasicinfo);

      // } else {

      //   this.empbasicinfo = this.empbasicin;

      //   console.log("all info",this.empbasicinfo);
        
      // }
      this.empbasicinfo = this.empbasicin;

      console.log("all info",this.empbasicinfo);


       this.empShortList= this.empbasicinfo.filter(status=>status.employmentStatus=="Bench" || status.employmentStatus=="Lateral");
       




      //console.log(this.empbasicinfo, "ffffffffffffffffffirst");
      //console.log(this.empbasic.employeeInfo, "")
      //console.log(this.empbasicinfo[0].image, "ssssssssssssssssseconddddddddddddddddddd");
      // this.manager = Array.from(new Set(this.empbasicinfo.map(x => x.reportingManager)));

      // this.employee = Array.from(new Set(this.empbasicinfo.map(x => x.employeeId)));
      //console.log(this.employee, "only employessssssssss........")
      //console.log(this.manager, "only managers in employee info...................");
      // let mngrData = [];
      // for (let n = 0; n <= this.manager.length; n++) {
      //   for (let m = 0; m < this.employee.length; m++) {
      //     if (this.manager[n] == this.employee[m]) {
      //       mngrData.push(this.empbasicinfo[m]);
            
      //     }
      //   }
      // }
      // this.managerdata = mngrData;
      // console.log("reactshivaaaaaaaaaaaaaaaa", this.managerdata);
      // for (let i = 0; i < this.empbasicinfo.length; i++) {

      //   for (let j = 0; j < this.empgenderinfo.length; j++) {
      //     if (this.empbasicinfo[i].gender == this.empgenderinfo[j].id) {
      // console.log(this.empbasicinfo[i].gender, "this.empbasicinfo[i].gender");
      //console.log(this.empgenderinfo[j].id, "this.empgenderinfo[j].id");
      // this.getgenderdata = this.empgenderinfo[j].gender;
      // console.log("GEnder details");
      // console.log(this.getgenderdata);
      //     }
      //   }
      //   this.empbasicinfo[i].gender = this.getgenderdata;
      //  // console.log("Final Educational Details Array");
      // console.log(this.empbasicinfo);
      // }
      // for (let i = 0; i < this.empbasicinfo.length; i++) {

      //   for (let j = 0; j < this.employee_statuslist.length; j++) {
      //     if (this.empbasicinfo[i].status == this.employee_statuslist[j].id) {

      //       this.getstatusdata = this.employee_statuslist[j].status;
      //       //console.log("GEnder And Status details");
      //       //console.log(this.getstatusdata);
      //     }
      //   }


      // this.empbasicinfo[i].status = this.getstatusdata;
      // if(this.empbasicinfo[i].status=="active" ||  this.empbasicinfo[i].status=="notice period"){
      //   this.empinfo.push(this.empbasicinfo[i]);
      // }

      // console.log("Final Educational Details Array");
      //console.log(this.empbasicinfo);
      // }


      // this.fName = this.empbasicinfo.map(_ => _.firstname);
      // this.lName = this.empbasicinfo.map(_ => _.lastname);
      for (var j = 0; j < this.empbasicinfo.length; j++) {

        if (this.empbasicinfo[j].image != null) {
        this.pic1.push(true);
        } else {
          // for (var i = 0; i < this.empbasicinfo.length; i++) {

          // this.initials.push(this.fName[i].substring(0,1).toUpperCase() + this.lName[i].substring(0,1).toUpperCase());
         
          // }
          this.pic1.push(false);
        }
      }
      //  console.log("pic array",this.initials);
      


    })

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
  sortid(){
    if(this.orderid==null){
      this.empShortList.sort((a, b) => (a.employeeId > b.employeeId) ? 1 : -1)
      this.orderid=0
    }
    else if(this.orderid==0){
      this.empShortList.sort((a, b) => (a.employeeId < b.employeeId) ? 1 : -1)
      this.orderid=1
    }else{
      this.empShortList.sort((a, b) => (a.employeeId > b.employeeId) ? 1 : -1)
      this.orderid=0
    }
    
  }
  
  sortname(){
    if(this.ordername==null){
      this.empShortList.sort((a, b) => (a.firstname > b.firstname) ? 1 : -1)
      this.ordername=0
    }
    else if(this.ordername==0){
      this.empShortList.sort((a, b) => (a.firstname < b.firstname) ? 1 : -1)
      this.ordername=1
    }
    else{
      this.empShortList.sort((a, b) => (a.firstname > b.firstname) ? 1 : -1)
      this.ordername=0
    }
  }
}
