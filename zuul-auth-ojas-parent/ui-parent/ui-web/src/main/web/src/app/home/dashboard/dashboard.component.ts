import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { HrmsService } from '../services/hrms.service';
import { Router } from '@angular/router';
import { PsaService } from '../services/psa.service';
import { takeUntil } from 'rxjs/operators';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Color } from 'ng2-charts/ng2-charts';
import * as moment from 'moment';
import swal from 'src/assets/sweetalert';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { RmgService } from '../services/rmg.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  dashboardSubscription: Subject<any> = new Subject();

  public userData: object

  loggeduser;
  role: any;


  constructor(private rmg: RmgService, private toast: NotificationService, private authService: AuthService, private hrms: HrmsService, private routerNavigate: Router, private psaService: PsaService) {

    this.loggeduser = this.authService.decryptData(sessionStorage.getItem('UserName'));
    this.role = this.authService.decryptData(sessionStorage.getItem('Role'));

  }
  ngOnInit() {

    this.authService.getUserdata().then(data => {
      this.userData = data;
    })
    if (this.role == "ROLE_ADMIN" || this.role == "ROLE_HR") {

      // hrms charts starts

      this.getEmployeeStatus()
      this.getReportsChart()
      this.getOnboard()
      this.getResData()
      this.getEmpData()
      this.getEmpMonthlyCount()
      this.getGenderDistribution()
      this.getYearsInDistService()
      // hrms charts ends

    }
    if (this.role == "ROLE_ADMIN" || this.role == "ROLE_STAFFAUGHEAD" || this.role == "ROLE_BDM" || this.role == 'ROLE_SALES' || this.role == 'ROLE_BUHEAD' || this.role == "ROLE_SBUHEAD") {

      // psa charts starts

      this.getAllProject();
      this.getcustomercontract()
      this.revenuedata()
      this.getBdmCustomerContactChart()
      this.getCustomercount()
      this.getBdmCustomerCountChart()
      this.getBdmWiseCustContract()
      // psa charts ends

    }

  }



  ngOnDestroy(): any {
    this.dashboardSubscription.next();
    this.dashboardSubscription.complete();
  }

  // project revenue ,project resource count,  project status starts
  public ProjectRevenue_BarChartLabels;
  public ProjectStatus_BarChartLabels;

  projectData: any;
  dataProject: any;
  revenueCountProject: any = new Array();
  projectToShow: any = new Array();
  resourceCount: any = new Array();
  remainingDays = [];
  pvalue = [];

  getAllProject() {
    var getProject = {
      "projectInfo": {
      },
      "transactionType": "getall"
    }
    this.psaService.getAllProject(getProject).pipe(takeUntil(this.dashboardSubscription)).subscribe(response => {
      this.projectData = response;

      this.dataProject = this.projectData.projectList;


      for (var i = 0; i < this.dataProject.length; i++) {


        this.revenueCountProject.push(this.dataProject[i].projectRatecard.projectValue);
        this.projectToShow.push(this.dataProject[i].projectName);
        this.resourceCount.push(this.dataProject[i].projectResourceMapping.resourceCount);
        let days = moment(this.dataProject[i].endDate).diff(moment().format('YYYY-MM-DD'), 'days')
        this.remainingDays.push(days);
        this.pvalue.push(this.dataProject[i].projectName)

      }

      console.log("count project", this.projectToShow, this.revenueCountProject, this.resourceCount);

      this.ProjectRevenue_BarChartLabels = this.projectToShow;
      this.ProjectResourceCount_BarChartLabels = this.projectToShow;
      this.ProjectStatus_BarChartLabels = this.pvalue;

    })

  }
  public ProjectRevenue_BarChartData: ChartDataSets[] = [
    { data: this.revenueCountProject, label: 'Revenue' },

  ];
  public ProjectRevenue_BarChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          }
        }
      ]
    }

  };
  public ProjectRevenue_BarChartColors: Color[] = [
    {
      borderColor: 'rgb(144,238,144)',
      backgroundColor: 'rgb(144,238,144)',
    },
  ];
  public ProjectRevenue_BarChartLegend = true;
  public ProjectRevenue_BarChartType: ChartType = 'bar';


  public ProjectResourceCount_BarChartData: ChartDataSets[] = [
    { data: this.resourceCount, label: 'Resource' },

  ];
  public ProjectResourceCount_BarChartLabels;
  public ProjectResourceCount_BarChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          }
        }
      ]
    }
  };

  public ProjectResourceCount_BarChartColors: Color[] = [
    {
      borderColor: 'rgba(30, 169, 224, 0.8)',
      backgroundColor: 'rgba(30, 169, 224, 0.8)',
    },
  ];
  public ProjectResourceCount_BarChartLegend = true;
  public ProjectResourceCount_BarChartType: ChartType = 'bar';
  public ProjectStatus_BarChartData: ChartDataSets[] = [
    { data: this.remainingDays, label: 'Remaining Days' },

  ];
  public ProjectStatus_BarChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          }
        }
      ]
    }

  };
  public ProjectStatus_BarChartColors: Color[] = [
    {
      borderColor: 'rgb(255,226,154)',
      backgroundColor: 'rgb(255,226,154)',
    },
  ];
  public ProjectStatus_BarChartLegend = true;
  public ProjectStatus_BarChartType: ChartType = 'bar';

  // project revenue ,project resource count,  project status ends

  //barchart for customer contract month wise
  cucount: number[] = new Array();
  cucount1: number[] = new Array();
  public CustomerContract_BarChartOptions: ChartOptions = {
    responsive: true,
  };
  totalCount1 = 0;
  public CustomerContract_BarChartLabels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  public CustomerContract_BarChartType: ChartType = 'bar';
  public CustomerContract_BarChartLegend = true;
  getcont: any;
  customercount88: any;
  cuarray: any[];
  cuarray1: any[];
  jan: any = 0;
  feb: any = 0;
  mar: any = 0;
  april: any = 0;
  may: any = 0;
  june: any = 0;
  july: any = 0;
  aug: any = 0;
  sep: any = 0;
  oct: any = 0;
  nov: any = 0;
  dec: any = 0;

  public CustomerContract_BarChartData: ChartDataSets[] = [
    { data: this.cucount, label: 'Monthwise Count' },


  ];

  getcustomercontract() {

    var req = {

      "customercontractdetailslist": [{}],
      "transactiontype": "getall"

    }

    this.psaService.getAllContractdDetails(req).pipe(takeUntil(this.dashboardSubscription)).subscribe(res => {
      this.getcont = res;
      console.log(this.getcont, "customer contact");

      this.customercount88 = this.getcont.customercontractdetailslist;
      console.log(this.customercount88, "fnfjihfjsndifns");

      this.cuarray = this.customercount88.map(c => c.startdate);
      this.cuarray1 = this.customercount88.map(c => c.createdby);

      console.log(this.cuarray1, "chadagdagfafygayufga");


      console.log(this.cuarray, "monthwise");


      for (let i in this.customercount88) {
        if (new Date(this.customercount88[i].startdate).getMonth() == 0) {
          this.jan++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 1) {
          this.feb++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 2) {
          this.mar++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 3) {
          this.april++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 4) {
          this.may++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 5) {
          this.june++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 6) {
          this.july++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 7) {
          this.aug++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 8) {
          this.sep++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 9) {
          this.oct++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 10) {
          this.nov++;
        }
        if (new Date(this.customercount88[i].startdate).getMonth() == 11) {
          this.dec++;
        }

      }

      this.cucount.push(this.jan);
      this.cucount.push(this.feb);
      this.cucount.push(this.mar);
      this.cucount.push(this.april);
      this.cucount.push(this.may);
      this.cucount.push(this.june);
      this.cucount.push(this.july);
      this.cucount.push(this.aug);
      this.cucount.push(this.sep);
      this.cucount.push(this.oct);
      this.cucount.push(this.nov);
      this.cucount.push(this.dec);

      console.log("basha", this.cucount);


    })

  }
  public CustomerContract_BarChartColors: Color[] = [
    {
      borderColor: 'rgb(25,246,190)',
      backgroundColor: 'rgb(25,246,190)',
    },
  ];
  //barchart for customer contract monthwise ends


  //project revenue chart starts


  public ContractWiseRevenue_BarChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          }
        }
      ]
    }
  };

  public ContractWiseRevenue_BarChartLabels;
  contractstatus2: any;
  contractstatus: any;
  rev: any;
  nameArray: any;
  contractarray: any;
  countrevenue = [];
  contractnames = [];
  pending = 0;
  approved = 0;
  draft = 0;
  rejected = 0;

  revenuedata() {

    var countstatus = [];

    var revenuedata1 = {

      "customercontractdetailslist": [
        {}
      ],
      "transactiontype": "getall"
    }

    this.psaService.getAllContractdDetails(revenuedata1).pipe(takeUntil(this.dashboardSubscription)).subscribe((res) => {
      this.rev = res;
      this.contractarray = this.rev.customercontractdetailslist
      console.log("contract array", this.contractarray);

      this.contractstatus = this.contractarray.map(x => x.financeStatus)
      console.log("contarct status", this.contractstatus)
      this.contractstatus2 = this.contractstatus.map(a => a.toUpperCase())
      console.log("contract status2", this.contractstatus2)
      for (let i in this.contractarray) {
        // console.log(this.contractarray[i].contractvalue);
        this.countrevenue.push(this.contractarray[i].contractvalue);

      }
      console.log("count", this.countrevenue);

      // this.rev_barChartData=countrevenue;

      for (let i in this.contractarray) {

        this.contractnames.push(this.contractarray[i].contractname + '-' + this.contractarray[i].customerid);


      }
      for (let i in this.contractstatus2) {
        console.log(this.contractstatus2[i]);

        if (this.contractstatus2[i] == "PENDING") {
          this.pending++;
        }
        if (this.contractstatus2[i] == "APPROVED") {
          this.approved++;
        }
        if (this.contractstatus2[i] == "DRAFT") {
          this.draft++;
        }
        if (this.contractstatus2[i] == "REJECTED") {
          this.rejected++;
        }
      }


      countstatus.push(this.approved);
      countstatus.push(this.rejected);
      countstatus.push(this.draft);
      countstatus.push(this.pending);
      console.log("count", countstatus);
      this.ContractWiseRevenue_BarChartLabels = this.contractnames;
      console.log("contract names in loop", this.ContractWiseRevenue_BarChartLabels);


    })

    this.ContractStatus_PieChartData = countstatus;
  }

  public ContractWiseRevenue_BarChartType: ChartType = 'bar';
  public ContractWiseRevenue_BarChartLegend = true;
  public ContractWiseRevenue_BarChartData: ChartDataSets[] = [
    { data: this.countrevenue, label: 'Revenue Contract Wise' },

  ];
  public ContractWiseRevenue_BarChartColors: Color[] = [
    {
      borderColor: 'rgba(30, 169, 224, 0.8)',
      backgroundColor: 'rgba(30, 169, 224, 0.8)',
    },
  ];


  // project revenue charts ends

  // contract status starts
  public ContractStatus_PieChartLegend = true;
  public ContractStatus_PieChartData;
  public ContractStatus_PieChartType: string = 'pie';
  public ContractStatus_PieChartColors: {}[] = [{
    backgroundColor: ['lightgreen', 'orange', 'lightpink', 'lightblue']
  }];
  public ContractStatus_PieChartLabel = ['Approved', 'Rejected', 'Draft', 'Pending'];
  public ContractStatus_PieChartOptions: ChartOptions = {

    responsive: true
  };
  // contract status ends


  //bdm wise customer contacty count starts

  bdmrole: number[] = [];
  contactdatatype2: any = [];
  bdm1;
  data: any
  custcontactresponse: any;
  custcntctdata: any;
  emp;
  designation: any;
  BDMId: any;

  getBdmCustomerContactChart() {
    //this.getname();
    this.data = []
    var bdmcount = []

    var bdm2 = 0;
    var getallReq = {
      "customerContactInfo": [{

      }],
      "transactionType": "getall"
    }
    this.psaService.getCustomerContactInfo(getallReq).pipe(takeUntil(this.dashboardSubscription)).subscribe(res => {
      console.log("customerContactInfo res", res)
      this.custcontactresponse = res;

      this.custcntctdata = this.custcontactresponse.customerContactInfoList;
      console.log("length", this.custcntctdata.length);
      // this.contactchartdata1 = this.custcntctdata.length;

      //another function data
      var empinfo =
      {

        "employeeInfo": [{


        }],
        "transactionType": "getAllInfo"
      }
      this.hrms.getempinfo(empinfo).pipe(takeUntil(this.dashboardSubscription)).subscribe(res => {
        console.log("abc emp info data", res);
        this.emp = res;
        //get BDM designation id
        var request = {
          "designation": [

          ],
          "sessionId": "3121",
          "transactionType": "getall"
        }

        this.hrms.getEmployeeDesignation(request).subscribe(res => {
          this.designation = res;

          for (let i in this.designation.listDesignation) {

            // console.log("desig list abc",this.designation.listDesignation[i]);

            if (this.designation.listDesignation[i].designation == "BDM") {
              console.log("abc BDM desig id", this.designation.listDesignation[i].id);
              this.BDMId = this.designation.listDesignation[i].id;
            }
          }
          //get BDM design id ends
          for (let i in this.emp.employeeInfo) {
            if (this.emp.employeeInfo[i].title == "BDM") {
              console.log(this.emp.employeeInfo[i].firstname);
              this.bdmrole.push(this.emp.employeeInfo[i].firstname)
            }
          }

          console.log("bdm names", this.bdmrole);

          for (var j = 0; j < this.bdmrole.length; j++) {
            this.bdm1 = 0;
            for (var i = 0; i < this.custcntctdata.length; i++) {


              if (this.custcntctdata[i].bdm == this.bdmrole[j]) {
                console.log("data loop abc cust cntct count", this.custcntctdata[i].bdm, this.bdmrole[j]);
                this.bdm1++;
              }

            }
            // if (this.bdm1 != 0) {
            //   this.contactdatatype2.push(this.bdm1);

            // }
            this.contactdatatype2.push(this.bdm1);
          }
          console.log("array abc", this.contactdatatype2);


          this.BdmWiseCustContact_BarChartLabels = this.bdmrole
          console.log(this.BdmWiseCustContact_BarChartLabels, "labels");

        });

      });


      //another func data ends


      console.log(this.bdmrole, "bdmrole from");


    });

  }
  public BdmWiseCustContact_BarChartLabels = [];
  public BdmWiseCustContact_BarChartType: ChartType = 'bar';
  public BdmWiseCustContact_BarChartLegend = true;

  public BdmWiseCustContact_BarChartData: ChartDataSets[] = [
    { data: this.contactdatatype2, label: 'Contacts Count' },

  ];
  public BdmWiseCustContact_BarChartOptions = {
    responsive: true,
    fill: false,
    scales: { yAxes: [{ ticks: { min: 0, stepSize: 5 } }] }
  };
  //bdm  wise customer contacty count ends

  // Customer Count By Monthly starts
  count = []
  customercountinfo: any;
  customercount: any;
  public CustCountMonthly_BarChartData: ChartDataSets[] = [
    { data: this.count, label: 'Customer Count' }

  ];
  public CustCountMonthly_BarChartLabels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  public CustCountMonthly_BarChartOptions: ChartOptions = {
    responsive: true,
  };
  public CustCountMonthly_BarChartLegend = true;
  public CustCountMonthly_BarChartType: ChartType = 'bar';

  public CustCountMonthly_BarChartColors: Array<any> = [
    {
      backgroundColor: 'rgb(147,217,217)'

    }
  ];
  getCustomercount() {
    var jan = 0;
    var feb = 0;
    var mar = 0;
    var apr = 0;
    var may = 0;
    var jun = 0;
    var jul = 0;
    var aug = 0;
    var sep = 0;
    var oct = 0;
    var nov = 0;
    var dec = 0;

    var customercount =
    {
      "customerList":
      {


      },



      "transactionType": "getall"
    }
    console.log("data", customercount)
    this.psaService.getAllCustomer(customercount).pipe(takeUntil(this.dashboardSubscription)).subscribe(res => {
      this.customercount = res;

      this.customercountinfo = this.customercount.customerList
      console.log("data", res)
      console.log("data1", this.customercountinfo)

      for (let i in this.customercountinfo) {
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 0) {
          jan++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 1) {
          feb++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 2) {
          mar++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 3) {
          apr++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 4) {
          may++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 5) {
          jun++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 6) {
          jul++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 7) {
          aug++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 8) {
          sep++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 9) {
          oct++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 10) {
          nov++;
        }
        if (new Date(this.customercountinfo[i].createddate).getMonth() == 11) {
          dec++;
        }

      }
      this.count.push(jan)
      this.count.push(feb)
      this.count.push(mar)
      this.count.push(apr)
      this.count.push(may)
      this.count.push(jun)
      this.count.push(jul)
      this.count.push(aug)
      this.count.push(sep)
      this.count.push(oct)
      this.count.push(nov)
      this.count.push(dec)

    })
  }
  // Customer Count By Monthly ends

  //bdm wise customer count chart

  customerResp;
  bdmincustomer: any[] = [];
  empinfo: any;
  bdmnames: number[] = [];
  bdmcount: any[] = [];

  getBdmCustomerCountChart() {
    var request = {
      "customerList":
      {


      },



      "transactionType": "getall"
    }


    this.psaService.getallListiofCustomer(request).subscribe(res => {
      this.customerResp = res;

      for (let i in this.customerResp.customerList) {
        this.bdmincustomer.push(this.customerResp.customerList[i].billinginfo.bdmconatctName);

      }
      console.log("abc bdms in customer", this.bdmincustomer);

      //get by id employee info data
      var empinfo =
      {

        "employeeInfo": [{


        }],
        "transactionType": "getAllInfo"
      }


      this.hrms.getempinfo(empinfo).subscribe(res => {
        this.empinfo = res


        for (let i in this.empinfo.employeeInfo) {

          if (this.empinfo.employeeInfo[i].title == "BDM") {
            this.bdmnames.push(this.empinfo.employeeInfo[i].firstname)
          }

        }
        console.log("abc bdm names in data", this.bdmnames);
        //bdm count
        for (var j = 0; j < this.bdmnames.length; j++) {
          this.bdm1 = 0;
          for (var i = 0; i < this.bdmincustomer.length; i++) {


            if (this.bdmincustomer[i] == this.bdmnames[j]) {
              console.log("data loop cust count", this.bdmincustomer[i], this.bdmnames[j]);
              this.bdm1++;
            }

          }
          if (this.bdm1 != 0) {
            this.bdmcount.push(this.bdm1);
          }
        }
        //bdm count ends
        console.log("abc this.bdmcount", this.bdmcount);

      }) //empinfo hrms http close

    }) //customer psa http close

  }

  public BdmWiseCustCount_BarChartOptions: any = {
    responsive: true,
    fill: false,
    scales: { yAxes: [{ ticks: { min: 0, stepSize: 10 } }] }
  };
  public BdmWiseCustCount_BarChartLabels: number[] = this.bdmnames;
  public BdmWiseCustCount_BarChartType: string = 'bar';
  public BdmWiseCustCount_BarChartLegend: boolean = true;

  public BdmWiseCustCount_BarChartColors: Array<any> = [
    {
      backgroundColor: '#90EE90',
      borderColor: 'rgba(105,159,177,1)'
    }
  ];
  public BdmWiseCustCount_BarChartData: any[] = [
    { data: this.bdmcount, label: 'Customers Count' }
  ];

  //bdm wise customer count chart ends


  //barchart for customer contract bdm wise count starts
  contractdata = [];
  nameemp = [];
  public BdmWiseCustContract_BarChartOptions: ChartOptions = {
    responsive: true,
  };
  public BdmWiseCustContract_BarChartLabels;
  public BdmWiseCustContract_BarChartType: ChartType = 'bar';
  public BdmWiseCustContract_BarChartLegend = true;
  public BdmWiseCustContract_BarChartData: ChartDataSets[] = [
    { data: this.contractdata, label: 'Contracts Count' },
  ];
  public BdmWiseCustContract_BarChartColors: Color[] = [
    {
      borderColor: 'rgb(71,185,231)',
      backgroundColor: 'rgb(71,185,231)',
    },

  ];

  bdmcount1: any;
  getallemloyees: any;
  allemployees: any;
  getbdmlist: any;
  names = new Set();
  getids = new Set();


  getBdmWiseCustContract() {
    var finalids
    var c
    var empreq = {

      "customercontractdetailslist": [{}],
      "transactiontype": "getall"
    }
    this.psaService.getAllContractdDetails(empreq).subscribe(res => {
      this.getallemloyees = res;
      console.log(this.getallemloyees, "all ENmnkasnfsjkanfjknf");


      this.allemployees = this.getallemloyees.customercontractdetailslist;
      this.nameemp.push(this.allemployees.map(c => c.createdby))
      console.log(this.nameemp, "EMPLOYEES NAME");

      console.log(this.allemployees, "ALL DATA OF EMPLOYEES");

      var rolreq = {

        "employeeInfo": [{

        }],

        "transactionType": "getall"
      }
      this.hrms.getempinfo(rolreq).subscribe(res => {
        this.getbdmlist = res;
        console.log(this.getbdmlist, "GET ALL EMLOYEES DATA");
        this.bdmcount1 = this.getbdmlist.employeeInfo;
        console.log(this.bdmcount1, "ARRAY OF TITLES");
        for (let i in this.allemployees) {
          for (let j in this.bdmcount1) {
            if (this.allemployees[i].createdby == this.bdmcount1[j].employeeId) {
              this.names.add(this.bdmcount1[j].firstname)
              this.getids.add(this.bdmcount1[j].employeeId)
            }
          }
        }
        var name = Array.from(this.names)
        console.log("narayan names", name)
        console.log("bdm ids", this.getids);

        this.BdmWiseCustContract_BarChartLabels = name;

        finalids = Array.from(this.getids)
        for (let i in finalids) {
          c = 0;
          for (let j in this.allemployees) {
            if (finalids[i] == this.allemployees[j].createdby) {
              c++;
            }
          }
          this.contractdata.push(c);
        }
        console.log("final count of contract", this.contractdata)
      })
    })
  }
  //barchart for customer contract bdm wise count ends

  // barchart data for Employee status starts 
  count1: any = 0;
  count2: any = 0;
  // count3: any = 0;
  // count4: any = 0;
  // count5: any = 0;
  // count6: any = 0

  jancount0: any = 0;
  febcount0: any = 0;
  marcount0: any = 0;
  aprcount0: any = 0;
  maycount0: any = 0;
  juncount0: any = 0;
  julcount0: any = 0;
  augcount0: any = 0;
  sepcount0: any = 0;
  octcount0: any = 0;
  novcount0: any = 0;
  deccount0: any = 0;

  jancount1: any = 0;
  febcount1: any = 0;
  marcount1: any = 0;
  aprcount1: any = 0;
  maycount1: any = 0;
  juncount1: any = 0;
  julcount1: any = 0;
  augcount1: any = 0;
  sepcount1: any = 0;
  octcount1: any = 0;
  novcount1: any = 0;
  deccount1: any = 0;



  jancount2: any = 0;
  febcount2: any = 0;
  marcount2: any = 0;
  aprcount2: any = 0;
  maycount2: any = 0;
  juncount2: any = 0;
  julcount2: any = 0;
  augcount2: any = 0;
  sepcount2: any = 0;
  octcount2: any = 0;
  novcount2: any = 0;
  deccount2: any = 0;


  jancount3: any = 0;
  febcount3: any = 0;
  marcount3: any = 0;
  aprcount3: any = 0;
  maycount3: any = 0;
  juncount3: any = 0;
  julcount3: any = 0;
  augcount3: any = 0;
  sepcount3: any = 0;
  octcount3: any = 0;
  novcount3: any = 0;
  deccount3: any = 0;


  jancount4: any = 0;
  febcount4: any = 0;
  marcount4: any = 0;
  aprcount4: any = 0;
  maycount4: any = 0;
  juncount4: any = 0;
  julcount4: any = 0;
  augcount4: any = 0;
  sepcount4: any = 0;
  octcount4: any = 0;
  novcount4: any = 0;
  deccount4: any = 0;


  jancount5: any = 0;
  febcount5: any = 0;
  marcount5: any = 0;
  aprcount5: any = 0;
  maycount5: any = 0;
  juncount5: any = 0;
  julcount5: any = 0;
  augcount5: any = 0;
  sepcount5: any = 0;
  octcount5: any = 0;
  novcount5: any = 0;
  deccount5: any = 0;

  jancount6: any = 0;
  febcount6: any = 0;
  marcount6: any = 0;
  aprcount6: any = 0;
  maycount6: any = 0;
  juncount6: any = 0;
  julcount6: any = 0;
  augcount6: any = 0;
  sepcount6: any = 0;
  octcount6: any = 0;
  novcount6: any = 0;
  deccount6: any = 0;

  jancount7: any = 0;
  febcount7: any = 0;
  marcount7: any = 0;
  aprcount7: any = 0;
  maycount7: any = 0;
  juncount7: any = 0;
  julcount7: any = 0;
  augcount7: any = 0;
  sepcount7: any = 0;
  octcount7: any = 0;
  novcount7: any = 0;
  deccount7: any = 0;

  users;
  empInfo: any;
  getStatusActive: any;
  loop1: any;
  // getDates: any;
  // a: any;

  // public EmployeeStatus_BarChartData: ChartDataSets[] = [
  //   { data: [this.count1, this.count1, this.count1, this.count1, this.count1, this.count1, this.count1, this.count1, this.count1, this.count1, this.count1, this.count1], label: 'Bench' },
  //   { data: [this.count2, this.count2, this.count2, this.count2, this.count2, this.count2, this.count2, this.count2, this.count2, this.count2, this.count2, this.count2], label: 'Training' },
  //   { data: [this.count3, this.count3, this.count3, this.count3, this.count3, this.count3, this.count3, this.count3, this.count3, this.count3, this.count3, this.count3], label: 'Internal_Project' },
  //   { data: [this.count4, this.count4, this.count4, this.count4, this.count4, this.count4, this.count4, this.count4, this.count4, this.count4, this.count4, this.count4], label: 'Client_Project' },
  //   { data: [this.count5, this.count5, this.count5, this.count5, this.count5, this.count5, this.count5, this.count5, this.count5, this.count5, this.count5, this.count5], label: 'Deployed' },
  //   { data: [this.count6, this.count6, this.count6, this.count6, this.count6, this.count6, this.count6, this.count6, this.count6, this.count6, this.count6, this.count6], label: 'Non-Technical' }

  // ];

  public EmployeeStatus_BarChartLabels = ['Jan', 'Feb', 'March', 'April', 'May', 'June', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  public EmployeeStatus_BarChartOptions: ChartOptions = {
    responsive: true,
    scales: { yAxes: [{ ticks: { min: 0, stepSize: 1 } }] }

  };
  public EmployeeStatus_BarChartLegend = true;
  public EmployeeStatus_BarChartType: ChartType = 'bar';
  public EmployeeStatus_BarChartData: ChartDataSets[] = [];


  getEmployeeStatus() {
    var req = {
      "rmgemployeelist":
      {
      },
      "transactiontype": "getempbytstatus"
    }
    this.rmg.getAllEmp(req).pipe(takeUntil(this.dashboardSubscription)).subscribe(use => {
      this.users = use;
      this.empInfo = this.users.rmgemployeelist;
      console.log("emp info", this.empInfo);
      this.getStatusActive = this.empInfo.map(x => x.employmentStatus);
      var year = moment().year();
      for (this.loop1 in this.empInfo) {
        var stDate = this.empInfo[this.loop1].statusDate.split("-");
        // var stDate = moment(this.empInfo[this.loop1].statusDate);
        // if (year == stDate.year()) {
        if (year == stDate[0]) {
          if (this.empInfo[this.loop1].employmentStatus == "Bench") {
            var a = this.empInfo[this.loop1].statusDate.split("-");
            console.log("a[1]",a[1]);
            
            if (a[1] == "01") {
              this.jancount0++;
            }
            if (a[1] == "02") {
              this.febcount0++;
            }
            if (a[1] == "03") {
              this.marcount0++;
            }
            if (a[1] == "04") {
              this.aprcount0++;
            }
            if (a[1] == "05") {
              this.maycount0++;
            }
            if (a[1] == "06") {
              this.juncount0++;
            }
            if (a[1] == "07") {
              this.julcount0++;
            }
            if (a[1] == "08") {
              this.augcount0++;
            }
            if (a[1] == "09") {
              this.sepcount0++;
            }
            if (a[1] == "10") {
              this.octcount0++;
            }
            if (a[1] == "11") {
              this.novcount0++;
            }
            if (a[1] == "12") {
              this.deccount0++;
            }

          }
          if (this.empInfo[this.loop1].employmentStatus == "Active") {

            // console.log("yes",this.empInfo[this.loop1]);

            var a = this.empInfo[this.loop1].statusDate.split("-");

            if (a[1] == "01") {
              this.jancount1++;
            }
            if (a[1] == "02") {
              this.febcount1++;
            }
            if (a[1] == "03") {
              this.marcount1++;
            }
            if (a[1] == "04") {
              this.aprcount1++;
            }
            if (a[1] == "05") {
              this.maycount1++;
            }
            if (a[1] == "06") {
              this.juncount1++;
            }
            if (a[1] == "07") {
              this.julcount1++;
            }
            if (a[1] == "08") {
              this.augcount1++;
            }
            if (a[1] == "09") {
              this.sepcount1++;
            }
            if (a[1] == "10") {
              this.octcount1++;
            }
            if (a[1] == "11") {
              this.novcount1++;
            }
            if (a[1] == "12") {
              this.deccount1++;
            }

          }
          if (this.empInfo[this.loop1].employmentStatus == "Inactive") {
            var a = this.empInfo[this.loop1].statusDate.split("-");
            if (a[1] == "01") {
              this.jancount2++;
            }
            if (a[1] == "02") {
              this.febcount2++;
            }
            if (a[1] == "03") {
              this.marcount2++;
            }
            if (a[1] == "04") {
              this.aprcount2++;
            }
            if (a[1] == "05") {
              this.maycount2++;
            }
            if (a[1] == "06") {
              this.juncount2++;
            }
            if (a[1] == "07") {
              this.julcount2++;
            }
            if (a[1] == "08") {
              this.augcount2++;
            }
            if (a[1] == "09") {
              this.sepcount2++;
            }
            if (a[1] == "10") {
              this.octcount2++;
            }
            if (a[1] == "11") {
              this.novcount2++;
            }
            if (a[1] == "12") {
              this.deccount2++;
            }

          }
          if (this.empInfo[this.loop1].employmentStatus == "Billable") {
            var a = this.empInfo[this.loop1].statusDate.split("-");
            if (a[1] == "01") {
              this.jancount3++;
            }
            if (a[1] == "02") {
              this.febcount3++;
            }
            if (a[1] == "03") {
              this.marcount3++;
            }
            if (a[1] == "04") {
              this.aprcount3++;
            }
            if (a[1] == "05") {
              this.maycount3++;
            }
            if (a[1] == "06") {
              this.juncount3++;
            }
            if (a[1] == "07") {
              this.julcount3++;
            }
            if (a[1] == "08") {
              this.augcount3++;
            }
            if (a[1] == "09") {
              this.sepcount3++;
            }
            if (a[1] == "10") {
              this.octcount3++;
            }
            if (a[1] == "11") {
              this.novcount3++;
            }
            if (a[1] == "12") {
              this.deccount3++;
            }

          }
          if (this.empInfo[this.loop1].employmentStatus == "Non Billable") {
            var a = this.empInfo[this.loop1].statusDate.split("-");
            if (a[1] == "01") {
              this.jancount4++;
            }
            if (a[1] == "02") {
              this.febcount4++;
            }
            if (a[1] == "03") {
              this.marcount4++;
            }
            if (a[1] == "04") {
              this.aprcount4++;
            }
            if (a[1] == "05") {
              this.maycount4++;
            }
            if (a[1] == "06") {
              this.juncount4++;
            }
            if (a[1] == "07") {
              this.julcount4++;
            }
            if (a[1] == "08") {
              this.augcount4++;
            }
            if (a[1] == "09") {
              this.sepcount4++;
            }
            if (a[1] == "10") {
              this.octcount4++;
            }
            if (a[1] == "11") {
              this.novcount4++;
            }
            if (a[1] == "12") {
              this.deccount4++;
            }

          }
          if (this.empInfo[this.loop1].employmentStatus == "Notice Period") {
            var a = this.empInfo[this.loop1].statusDate.split("-");
            if (a[1] == "01") {
              this.jancount5++;
            }
            if (a[1] == "02") {
              this.febcount5++;
            }
            if (a[1] == "03") {
              this.marcount5++;
            }
            if (a[1] == "04") {
              this.aprcount5++;
            }
            if (a[1] == "05") {
              this.maycount5++;
            }
            if (a[1] == "06") {
              this.juncount5++;
            }
            if (a[1] == "07") {
              this.julcount5++;
            }
            if (a[1] == "08") {
              this.augcount5++;
            }
            if (a[1] == "09") {
              this.sepcount5++;
            }
            if (a[1] == "10") {
              this.octcount5++;
            }
            if (a[1] == "11") {
              this.novcount5++;
            }
            if (a[1] == "12") {
              this.deccount5++;
            }

          }
          if (this.empInfo[this.loop1].employmentStatus == "Terminated") {
            var a = this.empInfo[this.loop1].statusDate.split("-");
            if (a[1] == "01") {
              this.jancount6++;
            }
            if (a[1] == "02") {
              this.febcount6++;
            }
            if (a[1] == "03") {
              this.marcount6++;
            }
            if (a[1] == "04") {
              this.aprcount6++;
            }
            if (a[1] == "05") {
              this.maycount6++;
            }
            if (a[1] == "06") {
              this.juncount6++;
            }
            if (a[1] == "07") {
              this.julcount6++;
            }
            if (a[1] == "08") {
              this.augcount6++;
            }
            if (a[1] == "09") {
              this.sepcount6++;
            }
            if (a[1] == "10") {
              this.octcount6++;
            }
            if (a[1] == "11") {
              this.novcount6++;
            }
            if (a[1] == "12") {
              this.deccount6++;
            }

          }
          if (this.empInfo[this.loop1].employmentStatus == "Seperated") {
            var a = this.empInfo[this.loop1].statusDate.split("-");
            if (a[1] == "01") {
              this.jancount7++;
            }
            if (a[1] == "02") {
              this.febcount7++;
            }
            if (a[1] == "03") {
              this.marcount7++;
            }
            if (a[1] == "04") {
              this.aprcount7++;
            }
            if (a[1] == "05") {
              this.maycount7++;
            }
            if (a[1] == "06") {
              this.juncount7++;
            }
            if (a[1] == "07") {
              this.julcount7++;
            }
            if (a[1] == "08") {
              this.augcount7++;
            }
            if (a[1] == "09") {
              this.sepcount7++;
            }
            if (a[1] == "10") {
              this.octcount7++;
            }
            if (a[1] == "11") {
              this.novcount7++;
            }
            if (a[1] == "12") {
              this.deccount7++;
            }

          }
        }

      }

      // this.getDates = this.empInfo.map(x => x.statusDate);

      //console.log(this.getDates[0]+" 1 all dates   .......");
      //console.log(this.getDates[1]+" 1 all dates   .......");
      //console.log(this.getDates[2]+" 1 all dates   .......");
      //console.log(this.getDates[3]+" 1 all dates   .......");
      // this.a = this.getDates[0].split("-");
      //console.log(this.a[1] +" dpa[ap");
      //console.log(this.getStatusActive+"fgfdgdgtyrty");
      // for (this.da in this.getDates) {
      // this.a = this.getDates[this.da].split("-");

      this.EmployeeStatus_BarChartData = [
        { data: [this.jancount0, this.febcount0, this.marcount0, this.aprcount0, this.maycount0, this.juncount0, this.julcount0, this.augcount0, this.sepcount0, this.octcount0, this.novcount0, this.deccount0], label: 'Bench' },
        { data: [this.jancount1, this.febcount1, this.marcount1, this.aprcount1, this.maycount1, this.juncount1, this.julcount1, this.augcount1, this.sepcount1, this.octcount1, this.novcount1, this.deccount1], label: 'Active' },
        { data: [this.jancount2, this.febcount2, this.marcount2, this.aprcount2, this.maycount2, this.juncount2, this.julcount2, this.augcount2, this.sepcount2, this.octcount2, this.novcount2, this.deccount2], label: 'Inactive' },
        { data: [this.jancount3, this.febcount3, this.marcount3, this.aprcount3, this.maycount3, this.juncount3, this.julcount3, this.augcount3, this.sepcount3, this.octcount3, this.novcount3, this.deccount3], label: 'Billable' },
        { data: [this.jancount4, this.febcount4, this.marcount4, this.aprcount4, this.maycount4, this.juncount4, this.julcount4, this.augcount4, this.sepcount4, this.octcount4, this.novcount4, this.deccount4], label: 'Non Billable' },
        { data: [this.jancount5, this.febcount5, this.marcount5, this.aprcount5, this.maycount5, this.juncount5, this.julcount5, this.augcount5, this.sepcount5, this.octcount5, this.novcount5, this.deccount5], label: 'Notice period' },
        { data: [this.jancount6, this.febcount6, this.marcount6, this.aprcount6, this.maycount6, this.juncount6, this.julcount6, this.augcount6, this.sepcount6, this.octcount6, this.novcount6, this.deccount6], label: 'Terminated' },
        { data: [this.jancount7, this.febcount7, this.marcount7, this.aprcount7, this.maycount7, this.juncount7, this.julcount7, this.augcount7, this.sepcount7, this.octcount7, this.novcount7, this.deccount7], label: 'Seperated' }
      ];
    })
  }
  // barchart data for Employee status ends

  // pie chart for reports starts
  public Reports_PieChartdata: number[] = new Array();
  public Reports_PieChartColors: {}[] = [{
    backgroundColor: ['lightgreen', 'orange', 'lightpink', 'lightblue', 'lightyellow', 'rgba(148,159,177,1)',
      'rgba(255, 0, 0)', 'rgba(255, 64, 0)', 'rgba(255, 128, 0)',
      'rgba(255, 191, 0)', 'rgba(255, 255, 0)', 'rgba(191, 255, 0)',
      'rgba(128, 255, 0)', 'rgba(64, 255, 0)', 'rgba(0, 255, 0)',
      'rgba(0, 255, 64)', 'rgba(0, 255, 128)', 'rgba(0, 255, 191)',
      'rgba(0, 255, 255)', 'rgba(0, 191, 255)', 'rgba(0, 128, 255)',
      'rgba(0, 64, 255)', 'rgba(0, 0, 255)', 'rgba(64, 0, 255)',
      'rgba(128, 0, 255)', 'rgba(191, 0, 255)', 'rgba(255, 0, 255)',
      'rgba(255, 0, 191)', 'rgba(255, 0, 128)', 'rgba(255, 0, 64)', 'rgba(255, 0, 0)']
  }];
  Reports_PieChartLabels = [];
  public Reports_PieChartType: string = 'pie';
  public Reports_PieChartOptions: ChartOptions = {
    responsive: true
  };
  public Reports_PieChartLegend: boolean = true;
  chartLabel: any;
  projLocation = [];
  public pieChartLabels3: string[] = new Array();
  index: any;
  pieCount: any = 0;
  i;
  StateList11 = new Array();
  user;
  proDet;
  projEndDates;

  getReportsChart() {

    var req =
    {
      "transactionType": "getAll"
    }

    this.hrms.getProjectDetails(req).pipe(takeUntil(this.dashboardSubscription)).subscribe(use => {
      // console.log("mmmmmmmmm", use)
      this.user = use;
      this.proDet = this.user.projectDetailsList;
      //this.projLocation = this.proDet.map(x => x.location);
      for (var i = 0; i < this.proDet.length; i++) {
        this.projLocation.push(this.proDet[i].location);

      }
      // console.log("454545", this.projLocation);
      //console.log("name location", this.StateList11);
      this.projEndDates = this.proDet.map(x => x.endDate);
      this.getData();
    })
  }
  getData() {

    this.chartLabel = Array.from(new Set(this.projLocation));

    this.Reports_PieChartdata = new Array(this.chartLabel.length);
    this.pieChartLabels3 = this.chartLabel;
    //console.log("locations2", this.chartLabel);
    for (this.index in this.chartLabel) {
      //console.log("Labels in loop : ", this.StateList11);


      this.pieCount = 0;
      for (this.i in this.projLocation) {

        if (this.projLocation[this.i] == this.chartLabel[this.index]) {
          console.log(this.projLocation[this.i], "abc loc");

          this.pieCount = this.pieCount + 1;

        }


        this.Reports_PieChartdata[this.index] = this.pieCount;


      }

    }
    // console.log("abc loc", this.Reports_PieChartdata);

    for (this.index in this.pieChartLabels3) {
      for (this.i in this.StateList11) {

        // console.log("Inside project for loop1", this.pieChartLabels3[this.index]);
        if (this.StateList11[this.i].id == this.pieChartLabels3[this.index]) {

          this.pieChartLabels3[this.index] = this.StateList11[this.i].stateName;
          //console.log("Inside project for loop2", this.pieChartLabels3[this.index]);

        }
      }
      // this.Reports_PieChartLabels.push(this.pieChartLabels[j]);
    }
    // console.log(this.Reports_PieChartdata + " last data")
    // console.log("Final labels", this.pieChartLabels3)
    for (var i in this.pieChartLabels3) {
      if (this.pieChartLabels3[i] != null)
        this.Reports_PieChartLabels.push(this.pieChartLabels3[i].charAt(0).toUpperCase() + this.pieChartLabels3[i].slice(1));
    }

  }
  // pie chart for reports ends


  // exit employee chart starts
  exitData: number[] = new Array();
  resData: number[] = new Array();
  public ExitEmployee_LineChartData: ChartDataSets[] = [
    { data: this.exitData, label: 'Onboard Employees' },
    { data: this.resData, label: 'Offboard Employees' },
  ];
  public ExitEmployee_LineChartLabels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'sep', 'oct', 'nov', 'Dec'];
  public ExitEmployee_LineChartOptions: ChartOptions = {
    responsive: true,
  };
  public ExitEmployee_LineChartColors: Color[] = [
    {
      backgroundColor: '#55B061',
    },
    {
      backgroundColor: '#F05959',
    }
  ];
  public ExitEmployee_LineChartLegend = true;
  public ExitEmployee_LineChartType = 'line';
  employmentdetailsss: any;
  onboarding: any;
  dojArr: any;
  exitDatesArr;
  exitCount: number;
  exit;
  dojData: number[] = new Array();

  resemployeesdetails: any;
  offboard: any;
  dolArr: any;
  exitCount1: number;
  r;

  getOnboard() {
    var count;
    var employmentdetailss =
    {
      "employmentDetailsList": [{}],
      "transactionType": "getAll"

    }
    this.hrms.getonboardingdetails(employmentdetailss).pipe(takeUntil(this.dashboardSubscription)).subscribe(response => {
      this.employmentdetailsss = response;
      this.onboarding = this.employmentdetailsss.employmentDetailsList;
      // console.log("OnBoard response ", this.onboarding);
      this.dojArr = this.onboarding.map(x => x.joiningDate);
      this.exitDatesArr = this.onboarding.map(x => x.exitDate);
      // console.log("DoJ Array", this.dojArr);
      // console.log("Exit Date Array", this.exitDatesArr);

      for (let month = 0; month < this.ExitEmployee_LineChartLabels.length; month++) {
        count = 0;
        this.count1 = 0;
        this.count2 = 0;
        this.exitCount = 0;
        for (this.i in this.dojArr) {
          let dojDate = new Date(this.dojArr[this.i]);
          if (dojDate.getMonth() == month) {
            count = count + 1;
          }
        }




        for (this.exit in this.exitDatesArr) {
          let exitDate = new Date(this.exitDatesArr[this.exit]);
          if (exitDate.getMonth() == month) {
            // console.log("Month of exit: ", exitDate.getMonth())
            this.exitCount = this.exitCount + 1;
          }
        }
        // this.exitData[month] = this.exitCount;

        //this.anniData.push(this.anniCount);
        //this.ExitEmployee_LineChartData[0].data[month] = this.exitCount;

        this.dojData.push(count);

        this.exitData.push(count);
      }
      // this.chartData[1].data = this.dojData;
      // this.chartData[2].data=this.dojData;
      this.ExitEmployee_LineChartData[0].data = this.exitData;
      //console.log("DoJ data: ", this.chartData[1].data)
      //console.log("Exit data: ", this.ExitEmployee_LineChartData[0].data)

    });
  }

  getResData() {
    var resInfo =
    {
      "resignation": [{}],
      "transactionType": "getAll"
    }
    this.hrms.getResignation(resInfo).pipe(takeUntil(this.dashboardSubscription)).subscribe(resresp => {
      this.resemployeesdetails = resresp;
      this.offboard = this.resemployeesdetails.resignationList;
      //console.log("resignation data", this.offboard);
      this.dolArr = this.offboard.map(x => x.leavingDate);
      //console.log("doarray daraalfakfla,f", this.dolArr)

      for (let month = 0; month < this.ExitEmployee_LineChartLabels.length; month++) {

        this.exitCount1 = 0;
        for (this.r in this.dolArr) {
          let dolDate = new Date(this.dolArr[this.r]);
          if (dolDate.getMonth() == month) {
            this.exitCount1 = this.exitCount1 + 1;
            //console.log("monthsdata", month)
          }
        }

        this.resData[month] = this.exitCount1;
        //this.anniData.push(this.anniCount);
        //this.ExitEmployee_LineChartData[0].data[month] = this.exitCount;
        this.resData.push(this.exitCount1);

      }

      this.ExitEmployee_LineChartData[1].data = this.resData;

      //console.log("RESIGNATION DATA dsgfgsdddddddddddddddddddddddddddsddgsdgsgsdgsdg", this.resData);


    });



  }


  // exit employee chart ends

  // events chart starts
  monthData: number[] = new Array();
  dojData1: number[] = new Array();
  monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  lastMonth = this.monthNames[(new Date()).getMonth() - 1];
  thisMonth = this.monthNames[(new Date()).getMonth()];

  public Events_BarChartData: ChartDataSets[] = [
    { data: this.monthData, label: 'Birthday' },
    { data: this.dojData1, label: 'Work Anniversary' }
  ];
  public Events_BarChartLabels = [this.lastMonth, this.thisMonth];
  public Events_BarChartOptions: ChartOptions = {
    responsive: true,

    scales: { yAxes: [{ ticks: { min: 0, stepSize: 5 } }] }

  };
  public Events_BarChartLegend = true
  public Events_BarChartType: ChartType = 'bar';

  empbasic: any;
  empbasicinfo: any;
  dobArr: any[];
  thisMonthNumber = ((new Date()).getMonth() + 1);
  lastMonthNumber = this.thisMonthNumber - 1;
  first0;
  first1;
  thisMonthNumber1 = ((new Date()).getMonth() + 1);
  lastMonthNumber1 = this.thisMonthNumber1 - 1;
  second0;
  second1;

  getEmpData() {
    var count;
    var empinfo =
    {
      "employeeInfo": [{}],
      "transactionType": "getAllInfo"
    }
    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.dashboardSubscription)).subscribe(res => {
      this.empbasic = res;
      this.empbasicinfo = this.empbasic.employeeInfo;
      // console.log("Employee: ", this.empbasicinfo);
      this.dobArr = this.empbasicinfo.map(x => x.dob);
      //this.chartData = [];
      //console.log("DoB Array", this.dobArr)
      for (let month = 0; month < this.Events_BarChartLabels.length; month++) {
        count = 0;
        for (this.i in this.dobArr) {
          let date = new Date(this.dobArr[this.i]);
          // console.log("Month", date.getMonth(), "Number", month)
          if (date.getMonth() == this.lastMonthNumber - 1) {
            count = count + 1;
          }
          // this.monthData[this.i] = this.count;
        }
        this.lastMonthNumber = this.thisMonthNumber;
        this.monthData.push(count)
      }
      // this.chartData[0].data = this.monthData;
      // console.log("Months: ", this.monthData)
      // console.log("Months data: ", this.monthData)
      this.first0 = this.monthData[0];
      this.first1 = this.monthData[1];
      // console.log("first0",this.first0);
      // console.log("first0",this.first1);
    })




    var employmentdetailss =
    {
      "employmentDetailsList": [{}],
      "transactionType": "getAll"

    }
    this.hrms.getonboardingdetails(employmentdetailss).pipe(takeUntil(this.dashboardSubscription)).subscribe(response => {
      this.employmentdetailsss = response;
      this.onboarding = this.employmentdetailsss.employmentDetailsList;

      this.dojArr = this.onboarding.map(x => x.joiningDate);

      // console.log("dojarrrrrrrrrrrrr",this.dojArr);
      for (let month = 0; month < this.Events_BarChartLabels.length; month++) {

        this.count2 = 0;

        for (this.i in this.dojArr) {
          let dojDate = new Date(this.dojArr[this.i]);
          if (dojDate.getMonth() == this.lastMonthNumber1 - 1) {
            this.count2 = this.count2 + 1;
          }
        }
        this.lastMonthNumber1 = this.thisMonthNumber1;

        this.dojData1.push(this.count2);

      }
      // this.chartData[1].data = this.dojData1;
      this.second0 = this.dojData1[0];
      this.second1 = this.dojData1[1];

      // console.log("second0",this.second0);
      // console.log("second1",this.second1);
    });

  }

  // events chart ends

  //  Employee monthly count chart starts
  finalcount = []
  ecount: number[] = new Array();
  public EmpMonthlyCount_BarChartData: ChartDataSets[] = [
    { data: this.finalcount, label: 'Employees Count' },

  ];
  public EmpMonthlyCount_BarChartLabels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  public EmpMonthlyCount_BarChartOptions: ChartOptions = {
    responsive: true,
    scales: { yAxes: [{ ticks: { min: 0, stepSize: 5 } }] }

  };
  public EmpMonthlyCount_BarChartColors: Color[] = [
    {
      borderColor: 'rgba(30, 169, 224, 0.8)',
      backgroundColor: 'rgba(30, 169, 224, 0.8)',
    },
  ];
  public EmpMonthlyCount_BarChartLegend = true;
  public EmpMonthlyCount_BarChartType: ChartType = 'bar';

  resigndummy: number[] = this.resData;
  employeecount: any;
  empcountinfo: any;
  dobArr1: any[];
  totalCount = 0;

  getEmpMonthlyCount() {


    var pcount = 0
    var currentdate = new Date()
    console.log("resigndummy", this.resigndummy)
    var empcount = {
      "employmentDetailsList": [{}],
      "transactionType": "getall"
    }
    this.hrms.getonboardingdetails(empcount).pipe(takeUntil(this.dashboardSubscription)).subscribe(res => {
      this.employeecount = res;
      console.log('fddfhd', this.employeecount);
      this.empcountinfo = this.employeecount.employmentDetailsList;
      console.log("empcountinfo: ", this.empcountinfo);
      this.dobArr1 = this.empcountinfo.map(x => x.joiningDate);
      console.log("Doj Array values", this.dobArr1)
      var previous = []
      for (let month = 0; month < this.EmpMonthlyCount_BarChartLabels.length; month++) {
        var count = 0
        var c = 0
        for (this.i in this.dobArr1) {
          let date = new Date(this.dobArr1[this.i]);
          if (date.getMonth() == month && date.getFullYear() == currentdate.getFullYear()) {
            count = count + 1
          } else if (date.getMonth() == month) {
            c = c + 1
          }
        }
        pcount = pcount + c
        previous.push(pcount)
        this.totalCount = this.totalCount + count
        this.ecount.push(this.totalCount)
      }
      console.log("previous years", previous);
      console.log("current year", this.ecount);

      for (var i = 0; i <= currentdate.getMonth(); i++) {
        this.finalcount.push(this.ecount[i] + previous[previous.length - 1])
      }
      console.log("final count", this.finalcount)
      // this.EmpMonthlyCount_BarChartData = this.finalcount;
    })
  }

  //  Employee monthly count chart ends

  // Gender Distribution chart starts
  public GenderDist_PieChartData: number[] = new Array();
  public GenderDist_PieChartColors: any = [
    {
      backgroundColor: ['rgba(30, 169, 224, 0.8)',
        'rgba(0, 255, 191,0.9)',
        'rgba(0, 0, 255, 0.3)',
        'rgba(255, 161, 181, 0.9)',
        'rgba(255, 102, 0, 0.9)'
      ]
    }
  ]
  // public GenderDist_PieChartLabels: string[] = ['Female', 'Male', 'Others'];
  public GenderDist_PieChartLabels: string[];
  public GenderDist_PieChartType: string = 'pie';

  public GenderDist_PieChartLegend: boolean = true
  empgender: any;
  empgenderinfo: any;
  gendInfo: any;
  chartLabel1: any;
  pieCount1: any;

  getGenderDistribution() {

    var genderinfo = {

      "employeeInfo": [{

      }],

      "transactionType": "getAllInfo"
    }

    this.hrms.getempinfo(genderinfo).pipe(takeUntil(this.dashboardSubscription)).subscribe(res => {
      this.empgender = res;
      this.empgenderinfo = this.empgender.employeeInfo;
      this.gendInfo = this.empgenderinfo.map(x => x.gender);
      // console.log("this.gendInfo", this.gendInfo);
      let gender = new Set()
      this.gendInfo.map(d => gender.add(d.toLowerCase()));
      this.getallData(gender);
    })
  }
  getallData(e) {
    this.chartLabel1 = Array.from(e);
    this.chartLabel1 = this.chartLabel1.map(d => d.replace(/\b\S/g, t => t.toUpperCase()))
    this.GenderDist_PieChartData = new Array(this.chartLabel1.length);
    this.GenderDist_PieChartLabels = this.chartLabel1;

    console.log("gender labels", this.GenderDist_PieChartLabels);

    for (this.index in this.GenderDist_PieChartLabels) {
      this.pieCount1 = 0;

      for (this.i in this.gendInfo) {

        if (this.gendInfo[this.i] == this.GenderDist_PieChartLabels[this.index]) {

          this.pieCount1 = this.pieCount1 + 1;

        }
        this.GenderDist_PieChartData[this.index] = this.pieCount1;

      }

    }

  }
  // Gender Distribution chart ends
  // years in service distribution chart starts
  expdate = []
  public YearsService_LineChartData: ChartDataSets[] = [
    { data: this.expdate, label: 'Number Of Employees' },
  ];
  public YearsService_LineChartLabels = ['<1', '1-3', '3-5', '5-7', '>7'];
  public YearsService_LineChartOptions: ChartOptions = {
    responsive: true,
  };
  public YearsService_LineChartColors: Color[] = [
    {
      backgroundColor: '#B5F8F8',
    },
  ];
  public YearsService_LineChartLegend = true;
  public YearsService_LineChartType = 'line';

  empexpd: any;
  expdata = []
  below_one: any = 0
  one_three: any = 0
  three_five: any = 0
  five_seven: any = 0
  above_seven: any = 0

  getYearsInDistService() {
    var empcount = {
      "employeeExperienceDetails": [{}],
      "transactionType": "getall"
    }
    this.hrms.getEmployeeExperienceDetails(empcount).pipe(takeUntil(this.dashboardSubscription)).subscribe(resp => {
      this.data = resp
      this.empexpd = this.data.employeeExperienceDetails
      // console.log("empexpdetails ",this.empexpd);
      // var joindates=this.empexpd.map(x=>x.joining_date);
      // console.log("joind date", joindates)
      for (let i in this.empexpd) {
        var exp = this.empexpd[i].experience;
        this.expdata.push(exp)
        if (exp < 1) {
          this.below_one++;
          continue;
        } else if (exp >= 1 && exp < 3) {
          this.one_three++;
          continue;
        } else if (exp >= 3 && exp < 5) {
          this.three_five++;
          continue;
        } else if (exp >= 5 && exp < 7) {
          this.five_seven++;
          continue;
        } else {
          this.above_seven++;
          continue;
        }
      }
      //  console.log("exp",this.expdata)
      //  console.log("below_one",this.below_one);
      //  console.log("one_three",this.one_three);
      //  console.log("three_five",this.three_five);
      //  console.log("five_seven",this.five_seven);
      //  console.log("above_seven",this.above_seven);
      this.expdate.push(this.below_one);
      this.expdate.push(this.one_three);
      this.expdate.push(this.three_five);
      this.expdate.push(this.five_seven);
      this.expdate.push(this.above_seven);


    })
  }

  // years in service distribution chart ends

  logoutAction() {
    swal({
      title: "Are you sure?",
      text: "Want to Logout?",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {


          if (this.authService.logOutAction()) {
            this.routerNavigate.navigate(['index'])
          }
          sessionStorage.removeItem("setUserRole");



        }
        else {

          this.toast.info('You did not logout', 'Info')
        }
      });



  }
  myInfo() {

    if (sessionStorage.getItem('setUserRole') != 'true') {
      sessionStorage.setItem("setUserRole", "true");
    }
    this.routerNavigate.navigate(["home/hrms/employeeedit/" + `${this.loggeduser}`])
  }
  changepassword() {
    this.routerNavigate.navigate(["home/changepassword"])
  }
  personaldetails() {
    this.routerNavigate.navigate(["home/personaldetails"])
  }
  directory() {
    this.routerNavigate.navigate(["home/directory"])
  }
  Careers() {
    this.routerNavigate.navigate(["home/careers"])
  }
}
