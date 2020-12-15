import { Component, OnInit } from '@angular/core';
import { RmgService } from '../../services/rmg.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { PsaService } from '../../services/psa.service';

@Component({
  selector: 'app-psadashboard',
  templateUrl: './psadashboard.component.html',
  styleUrls: ['./psadashboard.component.scss']
})
export class PsadashboardComponent implements OnInit {
  dataTo: any
  bench: any
  count = [];
  benchCount = []
  private projectAll: Subject<any> = new Subject();
  deployed: any;
  totalRes: any;
  billableCount = [];
  nonbillablecount = [];
  lastcount = [];
  totCost = []
  totrevenue = [0, 0, 0, 0];
  javacount = [];
  angularcount: any[];
  nodejscount: any[];
  pythoncount: any[];
  searchByempid = []
  selectedEmparr = [];
  selectedemployee: any;
  colors = [];
  onboarding = [];
  releasing = [];
  public tot_Months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'July', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  ShowInModal = [];
  modalHeading: string;
  showModal = 'none';
  ResAllocList = [];
  emplist: number;
  lastbenchCount = [];
  charttype: string;
  empId: any;
  public resAllocTable = [
    { employeeid: [], hours: [] }
  ]

  constructor(private rmgService: RmgService, private psa: PsaService) {
    this.skillwiseMonthlyReven();
  }
  ngAfterViewInit() {
    this.getMonthlyPandL()

  }

  ngAfterContentInit() {
    this.OnboardVsRelease();
  }
  ngOnInit() {
    this.charttype = "doughnut"
    this.billVsNonBill()
  }
  public shareemployeedata = {
    jan: [], feb: [], mar: [], apr: [], may: [], jun: [],
    jul: [], aug: [], sep: [], oct: [], nov: [], dec: [],
    jan1: [], feb1: [], mar1: [], apr1: [], may1: [], jun1: [],
    jul1: [], aug1: [], sep1: [], oct1: [], nov1: [], dec1: []
  };

  public deployedMonthlyCount_bar: ChartDataSets[] = [
    { data: this.billableCount, label: 'Billable Count' },
    { data: this.nonbillablecount, label: 'Non-Billable Count' },
    { data: this.billableCount, label: "Billable Trend", type: "line" },
    { data: this.nonbillablecount, label: "Non-Billable Trend", type: "line" }

  ];

  public deployedMonthlyCount_barLabels = [];

  public deployedMonthlyCount_barOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [{ ticks: { min: 0, stepSize: 1 } }]
    },
  };
  public deployedMonthlyCount_barLegend = true;
  public deployedMonthlyCount_barType: ChartType = 'bar';

  public deployedMonthlyCount_barColors: Array<any> = [
    { backgroundColor: 'rgb(255,161,181)' },
    { backgroundColor: 'rgb(134,199,243)' },

    {
      backgroundColor: 'rgb(255,226,154)',
      borderColor: 'rgb(255,226,154)'
    },
    {
      backgroundColor: 'rgb(147,217,217)',
      borderColor: 'rgb(147,217,217)'
    },

  ];

  billVsNonBill() {
    var response
    var data
    var obj = []

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

    var jan1 = 0;
    var feb1 = 0;
    var mar1 = 0;
    var apr1 = 0;
    var may1 = 0;
    var jun1 = 0;
    var jul1 = 0;
    var aug1 = 0;
    var sep1 = 0;
    var oct1 = 0;
    var nov1 = 0;
    var dec1 = 0;

    var jan2 = 0;
    var feb2 = 0;
    var mar2 = 0;
    var apr2 = 0;
    var may2 = 0;
    var jun2 = 0;
    var jul2 = 0;
    var aug2 = 0;
    var sep2 = 0;
    var oct2 = 0;
    var nov2 = 0;
    var dec2 = 0;

    var jan3 = 0;
    var feb3 = 0;
    var mar3 = 0;
    var apr3 = 0;
    var may3 = 0;
    var jun3 = 0;
    var jul3 = 0;
    var aug3 = 0;
    var sep3 = 0;
    var oct3 = 0;
    var nov3 = 0;
    var dec3 = 0;

    var month: any = new Date().getMonth();
    console.log(month, "This month");

    var resourcedata = {
      "rmgInfo": {


      },
      "transactiontype": "getall"
    }

    this.rmgService.getAllResource(resourcedata).pipe(takeUntil(this.projectAll)).subscribe(res => {
      response = res
      data = response.rmgInfo
      data.map(d => {
        if (d.resourceType == "Specific") {
          for (let i in d.rmgspecific) {
            if (d.rmgspecific[i].flag == true) {
              obj.push(d.rmgspecific[i])
            }
          }
        }
        else {
          for (let i in d.rmggeneric) {
            for (let j in d.rmggeneric[i].rmggenericresourcemap) {
              if (d.rmggeneric[i].rmggenericresourcemap[j].flag == true) {
                obj.push(d.rmggeneric[i].rmggenericresourcemap[j])
              }
            }

          }
        }
      })
      this.deployed = obj
      console.log(this.deployed, "Deployed");
      for (let i in this.deployed) {
        if(this.deployed[i].employeeId!=null){
        this.ResAllocList[i] = this.deployed[i].employeeId}

        if (new Date(this.deployed[i].startDate).getFullYear() == new Date().getFullYear()) {

          if (new Date(this.deployed[i].startDate).getMonth() == 0) {
            jan++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 1) {
            feb++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 2) {
            mar++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 3) {
            apr++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 4) {
            may++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 5) {
            jun++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 6) {
            jul++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 7) {
            aug++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 8) {
            sep++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 9) {
            oct++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 10) {
            nov++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 11) {
            dec++;
          }
        }
      }


      this.ResAllocList = Array.from(new Set(this.ResAllocList))
      this.ResAllocList = this.ResAllocList.sort((n1, n2) => n1 - n2);

      this.count.push(jan) //this year billable count
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

      for (let k = 12 - month, j = 0; k < month + 11; k++, j++) {

        this.billableCount[k] = this.count[j]
      }
      console.log("this year", this.billableCount);

      for (let i in this.deployed) {
        if (new Date(this.deployed[i].startDate).getFullYear() == new Date().getFullYear() - 1) {
          console.log("last year");

          if (new Date(this.deployed[i].startDate).getMonth() == 0) {
            jan2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 1) {
            feb2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 2) {
            mar2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 3) {
            apr2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 4) {
            may2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 5) {
            jun2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 6) {
            jul2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 7) {
            aug2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 8) {
            sep2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 9) {
            oct2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 10) {
            nov2++;
          }
          if (new Date(this.deployed[i].startDate).getMonth() == 11) {
            dec2++;
          }
        }
      }
      this.lastcount.push(jan2) //last year billable count
      this.lastcount.push(feb2)
      this.lastcount.push(mar2)
      this.lastcount.push(apr2)
      this.lastcount.push(may2)
      this.lastcount.push(jun2)
      this.lastcount.push(jul2)
      this.lastcount.push(aug2)
      this.lastcount.push(sep2)
      this.lastcount.push(oct2)
      this.lastcount.push(nov2)
      this.lastcount.push(dec2)

      console.log(this.lastcount, "last year count");

      for (let k = 0, j = month; k < 12 - month; k++, j++) {

        this.billableCount[k] = this.lastcount[j]
      }
      console.log(" this.billableCount", this.billableCount);
      var billrescount = 0

      // bench api starts
      var empinfo =
      {
        "rmgemployeelist":
        {
        },
        "transactiontype": "getempbytstatus"
      }
      this.rmgService.getAllEmp(empinfo).pipe(takeUntil(this.projectAll)).subscribe(res => {
        var response: any = res;
        this.bench = response.rmgemployeelist;
        console.log(this.bench, "Bench");
        this.totalRes = this.bench.length + this.deployed.length
        console.log(this.totalRes, "lengths count");
        jan3 = this.totalRes - this.lastcount[0]
        feb3 = jan3 - this.lastcount[1]
        mar3 = feb3 - this.lastcount[2]
        apr3 = mar3 - this.lastcount[3]
        may3 = apr3 - this.lastcount[4]
        jun3 = may3 - this.lastcount[5]
        jul3 = jun3 - this.lastcount[6]
        aug3 = jul3 - this.lastcount[7]
        sep3 = aug3 - this.lastcount[8]
        oct3 = sep3 - this.lastcount[9]
        nov3 = oct3 - this.lastcount[10]
        dec3 = nov3 - this.lastcount[11]

        this.lastbenchCount.push(jan3)
        this.lastbenchCount.push(feb3)
        this.lastbenchCount.push(mar3)
        this.lastbenchCount.push(apr3)
        this.lastbenchCount.push(may3)
        this.lastbenchCount.push(jun3)
        this.lastbenchCount.push(jul3)
        this.lastbenchCount.push(aug3)
        this.lastbenchCount.push(sep3)
        this.lastbenchCount.push(oct3)
        this.lastbenchCount.push(nov3)
        this.lastbenchCount.push(dec3)

        console.log(this.lastbenchCount, "last year benchCount");

        jan1 = dec3 - this.count[0]
        feb1 = jan1 - this.count[1]
        mar1 = feb1 - this.count[2]
        apr1 = mar1 - this.count[3]
        may1 = apr1 - this.count[4]
        jun1 = may1 - this.count[5]
        jul1 = jun1 - this.count[6]
        aug1 = jul1 - this.count[7]
        sep1 = aug1 - this.count[8]
        oct1 = sep1 - this.count[9]
        nov1 = oct1 - this.count[10]
        dec1 = nov1 - this.count[11]

        this.benchCount.push(jan1)
        this.benchCount.push(feb1)
        this.benchCount.push(mar1)
        this.benchCount.push(apr1)
        this.benchCount.push(may1)
        this.benchCount.push(jun1)
        this.benchCount.push(jul1)
        this.benchCount.push(aug1)
        this.benchCount.push(sep1)
        this.benchCount.push(oct1)
        this.benchCount.push(nov1)
        this.benchCount.push(dec1)
        console.log(this.benchCount, "benchcount");


        for (let k = month; k <= 11; k++) {
          this.deployedMonthlyCount_barLabels.push(this.tot_Months[k] + ' ' + (new Date().getFullYear() - 1)) //last year labels
        }
        for (let k = 0; k <= month; k++) {
          this.deployedMonthlyCount_barLabels.push(this.tot_Months[k] + ' ' + new Date().getFullYear()) //this year labels
        }
        for (let k = 12 - month, j = 0; k < month + 11; k++, j++) {

          this.nonbillablecount[k] = this.benchCount[j] //this year nonbillable count
        }
        console.log("non bill", this.nonbillablecount);
        for (let k = month, j = 0; k <= 11; j++, k++) {
          this.nonbillablecount[j] = this.lastbenchCount[k] //last year nonbillable count

        }
      })//bench close

      this.emplist = this.ResAllocList[0]
      this.getresAlloc(this.emplist)

    })//deployed close
  }


  // skill wise monthly revenue starts
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

  public skillMonReven_bar: ChartDataSets[] = []

  public skillMonReven_barLabels = this.tot_Months

  public skillMonReven_barOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [{ ticks: { min: 0, stepSize: 100000 } }]
    },
  };
  public skillMonReven_barLegend = true;
  public skillMonReven_barType: ChartType = 'bar';
  public skillMonReven_barColors: Array<any> = [
    { backgroundColor: 'rgb(255,161,181)' },
    { backgroundColor: 'rgb(134,199,243)' },

    {
      backgroundColor: 'rgb(255,226,154)',
      borderColor: 'rgb(255,226,154)'
    },
    {
      backgroundColor: 'rgb(147,217,217)',
      borderColor: 'rgb(147,217,217)'
    },

  ];


  skillwiseMonthlyReven() {

    var req = {
      "employeeskills":
      {
        "skills": ["java", "Angular", "nodejs", "python"]

      },

      "transactiontype": "getdeployedresourcesbyskills"
    }
    this.psa.getEmployeeSkill(req).subscribe(res => {

      console.log(res, "skills res");
      var response: any = res;
      var employeeskills = response.employeeskills;
      console.log("skill out", employeeskills);
      // this.searchByempid[0]="All Employees"
      for (let i in employeeskills) {
        this.searchByempid[i] = employeeskills[i].employeeId //monthly profit and loss employees list
        console.log("emp skills", i, employeeskills[i].skillId);
        if (employeeskills[i].skillId == 'Java') {
          // console.log(employeeskills[i].skillId, "is java");
          var totdays = this.betweenDays(employeeskills[i].startDate, employeeskills[i].endDate);
          var holidays = this.betweenDays(employeeskills[i].fromDate, employeeskills[i].toDate);
          var nonWorking = this.weekendCount(employeeskills[i].startDate, employeeskills[i].endDate) + holidays;
          var workingDays = totdays - nonWorking;
          this.totrevenue[0] += workingDays * 8 * employeeskills[i].billRate;
          console.log("java revenue", this.totrevenue[0]);
          var months = this.getMonthsWorked(employeeskills[i].startDate, employeeskills[i].endDate)
          console.log("months worked", months);
          for (let j in months) {
            if (months[j] == 0)
              this.jancount0 = this.totrevenue[0]
            if (months[j] == 1)
              this.febcount0 = this.totrevenue[0]
            if (months[j] == 2)
              this.marcount0 = this.totrevenue[0]
            if (months[j] == 3)
              this.aprcount0 = this.totrevenue[0]
            if (months[j] == 4)
              this.maycount0 = this.totrevenue[0]
            if (months[j] == 5)
              this.juncount0 = this.totrevenue[0]
            if (months[j] == 6)
              this.julcount0 = this.totrevenue[0]
            if (months[j] == 7)
              this.augcount0 = this.totrevenue[0]
            if (months[j] == 8)
              this.sepcount0 = this.totrevenue[0]
            if (months[j] == 9)
              this.octcount0 = this.totrevenue[0]
            if (months[j] == 10)
              this.novcount0 = this.totrevenue[0]
            if (months[j] == 11)
              this.deccount0 = this.totrevenue[0]
          }

        }
        if (employeeskills[i].skillId == 'Angular') {
          // console.log(employeeskills[i].skillId, "is angular");

          var totdays = this.betweenDays(employeeskills[i].startDate, employeeskills[i].endDate)
          var holidays = this.betweenDays(employeeskills[i].fromDate, employeeskills[i].toDate);
          var nonWorking = this.weekendCount(employeeskills[i].startDate, employeeskills[i].endDate) + holidays
          var workingDays = totdays - nonWorking;
          this.totrevenue[1] += workingDays * 8 * employeeskills[i].billRate
          console.log("angular reven", this.totrevenue[1]);
          var months = this.getMonthsWorked(employeeskills[i].startDate, employeeskills[i].endDate)
          console.log("months worked", months);
          for (let j in months) {
            if (months[j] == 0)
              this.jancount1 = this.totrevenue[1]
            if (months[j] == 1)
              this.febcount1 = this.totrevenue[1]
            if (months[j] == 2)
              this.marcount1 = this.totrevenue[1]
            if (months[j] == 3)
              this.aprcount1 = this.totrevenue[1]
            if (months[j] == 4)
              this.maycount1 = this.totrevenue[1]
            if (months[j] == 5)
              this.juncount1 = this.totrevenue[1]
            if (months[j] == 6)
              this.julcount1 = this.totrevenue[1]
            if (months[j] == 7)
              this.augcount1 = this.totrevenue[1]
            if (months[j] == 8)
              this.sepcount1 = this.totrevenue[1]
            if (months[j] == 9)
              this.octcount1 = this.totrevenue[1]
            if (months[j] == 10)
              this.novcount1 = this.totrevenue[1]
            if (months[j] == 11)
              this.deccount1 = this.totrevenue[1]
          }
        }
        if (employeeskills[i].skillId == 'NodeJs') {
          // console.log(employeeskills[i].skillId, "is nodejs");

          var totdays = this.betweenDays(employeeskills[i].startDate, employeeskills[i].endDate)
          var holidays = this.betweenDays(employeeskills[i].fromDate, employeeskills[i].toDate);
          var nonWorking = this.weekendCount(employeeskills[i].startDate, employeeskills[i].endDate) + holidays
          var workingDays = totdays - nonWorking;
          this.totrevenue[2] += workingDays * 8 * employeeskills[i].billRate
          console.log("nodejs reven", this.totrevenue[2]);
          var months = this.getMonthsWorked(employeeskills[i].startDate, employeeskills[i].endDate)
          console.log("months worked", months);
          for (let j in months) {
            if (months[j] == 0)
              this.jancount2 = this.totrevenue[2]
            if (months[j] == 1)
              this.febcount2 = this.totrevenue[2]
            if (months[j] == 2)
              this.marcount2 = this.totrevenue[2]
            if (months[j] == 3)
              this.aprcount2 = this.totrevenue[2]
            if (months[j] == 4)
              this.maycount2 = this.totrevenue[2]
            if (months[j] == 5)
              this.juncount2 = this.totrevenue[2]
            if (months[j] == 6)
              this.julcount2 = this.totrevenue[2]
            if (months[j] == 7)
              this.augcount2 = this.totrevenue[2]
            if (months[j] == 8)
              this.sepcount2 = this.totrevenue[2]
            if (months[j] == 9)
              this.octcount2 = this.totrevenue[2]
            if (months[j] == 10)
              this.novcount2 = this.totrevenue[2]
            if (months[j] == 11)
              this.deccount2 = this.totrevenue[2]
          }
        }
        if (employeeskills[i].skillId == 'Python') {
          // console.log(employeeskills[i].skillId, "is python");

          var totdays = this.betweenDays(employeeskills[i].startDate, employeeskills[i].endDate)
          var holidays = this.betweenDays(employeeskills[i].fromDate, employeeskills[i].toDate);
          var nonWorking = this.weekendCount(employeeskills[i].startDate, employeeskills[i].endDate) + holidays
          var workingDays = totdays - nonWorking;
          this.totrevenue[3] += workingDays * 8 * employeeskills[i].billRate
          console.log("python revenue", this.totrevenue[3]);
          var months = this.getMonthsWorked(employeeskills[i].startDate, employeeskills[i].endDate)
          console.log("months worked", months);
          for (let j in months) {
            if (months[j] == 0)
              this.jancount3 = this.totrevenue[3]
            if (months[j] == 1)
              this.febcount3 = this.totrevenue[3]
            if (months[j] == 2)
              this.marcount3 = this.totrevenue[3]
            if (months[j] == 3)
              this.aprcount3 = this.totrevenue[3]
            if (months[j] == 4)
              this.maycount3 = this.totrevenue[3]
            if (months[j] == 5)
              this.juncount3 = this.totrevenue[3]
            if (months[j] == 6)
              this.julcount3 = this.totrevenue[3]
            if (months[j] == 7)
              this.augcount3 = this.totrevenue[3]
            if (months[j] == 8)
              this.sepcount3 = this.totrevenue[3]
            if (months[j] == 9)
              this.octcount3 = this.totrevenue[3]
            if (months[j] == 10)
              this.novcount3 = this.totrevenue[3]
            if (months[j] == 11)
              this.deccount3 = this.totrevenue[3]
          }
        }
      }//for loop close
      this.searchByempid = Array.from(new Set(this.searchByempid))
      this.searchByempid = this.searchByempid.sort((n1, n2) => n1 - n2);

      console.log("Filtered employee ids", this.searchByempid);

      this.javacount = [this.jancount0, this.febcount0, this.marcount0, this.aprcount0, this.maycount0, this.juncount0, this.julcount0, this.augcount0, this.sepcount0, this.octcount0, this.novcount0, this.deccount0]
      this.angularcount = [this.jancount1, this.febcount1, this.marcount1, this.aprcount1, this.maycount1, this.juncount1, this.julcount1, this.augcount1, this.sepcount1, this.octcount1, this.novcount1, this.deccount1]
      this.nodejscount = [this.jancount2, this.febcount2, this.marcount2, this.aprcount2, this.maycount2, this.juncount2, this.julcount2, this.augcount2, this.sepcount2, this.octcount2, this.novcount2, this.deccount2]
      this.pythoncount = [this.jancount3, this.febcount3, this.marcount3, this.aprcount3, this.maycount3, this.juncount3, this.julcount3, this.augcount3, this.sepcount3, this.octcount3, this.novcount3, this.deccount3]
      console.log(this.javacount, "javacount");
      this.skillMonReven_bar = [
        { data: this.javacount, label: 'Java' },
        { data: this.angularcount, label: 'Angular' },
        { data: this.nodejscount, label: 'Node Js' },
        { data: this.pythoncount, label: 'Python' },
        { data: this.javacount, label: 'Java Trend', type: 'line' },
        { data: this.angularcount, label: 'Angular Trend', type: 'line' },
        { data: this.nodejscount, label: 'Node Js Trend', type: 'line' },
        { data: this.pythoncount, label: 'Python Trend', type: 'line' }

      ]
    }) //api call close
  }
  // skill wise monthly revenue ends

  // Monthly Profit & Loss starts
  public MonthlyPandL_bar: ChartDataSets[] = [{ data: [], label: 'profit and loss' }]

  public MonthlyPandL_barLabels = this.tot_Months

  public MonthlyPandL_barOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [{ ticks: { min: 0, stepSize: 100000 } }]
    },
  };
  public MonthlyPandL_barLegend = true;
  public MonthlyPandL_barType: ChartType = 'bar';
  public MonthlyPandL_barColors: Array<any> = [
    { backgroundColor: 'rgb(255,161,181)' }

  ];


  getMonthlyPandL(selectedempId?) {
    console.log("selectedempId", selectedempId);
    for (let i = 0; i < this.searchByempid.length; i++) {

      if (selectedempId != this.searchByempid[i]) {
        if (i == this.searchByempid.length - 1) {
          selectedempId = false;
          this.empId = ''
        }
      }
      else {
        break;
      }
    }
    var workingDays = 0
    var revenue = 0
    var totalcost = []
    var months: any
    this.selectedEmparr = []

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
    console.log(jan, "jan value");

    var req = {
      "employeeskills":
      {
        "skills": ["java", "Angular", "nodejs", "python"]
      },
      "transactiontype": "getdeployedresourcesbyskills"
    }
    this.psa.getEmployeeSkill(req).subscribe(res => {
      var response: any = res;
      var employeeskills = response.employeeskills;

      if (!selectedempId) {
        console.log("selectedempId all employee condition");

        this.selectedEmparr = employeeskills
        console.log("all employeeskills", this.selectedEmparr);
        this.selectedemployee = 'all Employees'

      }
      else {
        for (let i in employeeskills) {
          if (employeeskills[i].employeeId == selectedempId) {
            this.selectedEmparr.push(employeeskills[i])
            this.selectedemployee = this.selectedEmparr[0].empName
          }
        }
      }
      console.log(this.selectedEmparr, "selected Emp push");
      console.log("total working days, revenue, profit/loss before", workingDays, revenue, totalcost);

      for (let k in this.selectedEmparr) {
        console.log("salary", this.selectedEmparr[k].skillId, this.selectedEmparr[k].empName);
        var totdays = this.betweenDays(this.selectedEmparr[k].startDate, this.selectedEmparr[k].endDate);
        var holidays = this.betweenDays(this.selectedEmparr[k].fromDate, this.selectedEmparr[k].toDate);
        console.log("holidays", holidays);

        var nonWorking = this.weekendCount(this.selectedEmparr[k].startDate, this.selectedEmparr[k].endDate) + holidays;
        workingDays += totdays - nonWorking;
        revenue += workingDays * 8 * this.selectedEmparr[k].billRate;
        months = this.getMonthsWorked(this.selectedEmparr[k].startDate, this.selectedEmparr[k].endDate)
        for (let j in months) {
          console.log("months", months);

          if (months[j] == 0)
            jan += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 31)
          if (months[j] == 1)
            feb += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 28)
          if (months[j] == 2)
            mar += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 31)
          if (months[j] == 3)
            apr += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 30)
          if (months[j] == 4)
            may += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 31)
          if (months[j] == 5)
            jun += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 30)
          if (months[j] == 6)
            jul += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 31)
          if (months[j] == 7)
            aug += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 31)
          if (months[j] == 8)
            sep += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 30)
          if (months[j] == 9)
            oct += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 31)
          if (months[j] == 10)
            nov += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 30)
          if (months[j] == 11)
            dec += revenue - ((this.selectedEmparr[k].currentCompanySalary * workingDays) / 31)
        }

      }//for loop close
      totalcost.push(Math.floor(jan))
      totalcost.push(Math.floor(feb))
      totalcost.push(Math.floor(mar))
      totalcost.push(Math.floor(apr))
      totalcost.push(Math.floor(may))
      totalcost.push(Math.floor(jun))
      totalcost.push(Math.floor(jul))
      totalcost.push(Math.floor(aug))
      totalcost.push(Math.floor(sep))
      totalcost.push(Math.floor(oct))
      totalcost.push(Math.floor(nov))
      totalcost.push(Math.floor(dec))
      console.log("total working days, revenue, profit/loss", workingDays, revenue, totalcost);
      console.log("this.selectedemployee", this.selectedemployee);

      this.MonthlyPandL_bar = [
        { data: totalcost, label: 'Profit/loss of ' + this.selectedemployee }
      ]
      // for(let i = 0; i < this.MonthlyPandL_bar[0].data.length; i++){
      //   let colorCode = this.MonthlyPandL_bar[0].data[i] < 0 ? 'red' : 'green';
      //   this.colors.push(colorCode);
      //   console.log(colorCode,"colorCode");

      // }
      // console.log("this.colors",this.colors);

      // this.MonthlyPandL_barColors[0].backgroundColor=this.colors;

    }) //api close
  }
  // Monthly Profit & Loss ends

  // onboarding vs releasing starts
  public OnboardVsRelease_bar: ChartDataSets[] = [
    { data: this.onboarding, label: 'Onboard' },
    { data: this.releasing, label: 'Release' }
  ]

  public OnboardVsRelease_barLabels = this.tot_Months

  public OnboardVsRelease_barOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [{ ticks: { min: 0, stepSize: 10 } }]
    },
  };
  public OnboardVsRelease_barLegend = true;
  public OnboardVsRelease_barType: ChartType = 'bar';
  public OnboardVsRelease_barColors: Array<any> = [
    { backgroundColor: 'rgb(255,161,181)' },
    { backgroundColor: 'rgb(134,199,243)' },

    {
      backgroundColor: 'rgb(255,226,154)',
      borderColor: 'rgb(255,226,154)'
    },
    {
      backgroundColor: 'rgb(147,217,217)',
      borderColor: 'rgb(147,217,217)'
    },

  ];

  OnboardVsRelease() {
    console.log("onboard employeeskills called");

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

    var jan1 = 0;
    var feb1 = 0;
    var mar1 = 0;
    var apr1 = 0;
    var may1 = 0;
    var jun1 = 0;
    var jul1 = 0;
    var aug1 = 0;
    var sep1 = 0;
    var oct1 = 0;
    var nov1 = 0;
    var dec1 = 0;

    var req = {
      "employeeskills":
      {
        "skills": ["java", "Angular", "nodejs", "python"]
      },
      "transactiontype": "getdeployedresourcesbyskills"
    }
    this.psa.getEmployeeSkill(req).subscribe(res => {
      var response: any = res;
      console.log("onboard employeeskills", response.employeeskills);
      var employeeskills = response.employeeskills;
      // this.shareemployeedata=employeeskills;
      for (let i in employeeskills) {
        if (new Date(employeeskills[i].startDate).getMonth() == 0) {
          jan++;
          this.shareemployeedata.jan.push(employeeskills[i])
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 1) {
          this.shareemployeedata.feb.push(employeeskills[i])

          feb++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 2) {
          this.shareemployeedata.mar.push(employeeskills[i])

          mar++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 3) {
          this.shareemployeedata.apr.push(employeeskills[i])

          apr++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 4) {
          this.shareemployeedata.may.push(employeeskills[i])

          may++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 5) {
          this.shareemployeedata.jun.push(employeeskills[i])

          jun++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 6) {
          this.shareemployeedata.jul.push(employeeskills[i])

          jul++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 7) {
          this.shareemployeedata.aug.push(employeeskills[i])

          aug++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 8) {
          this.shareemployeedata.sep.push(employeeskills[i])

          sep++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 9) {
          this.shareemployeedata.oct.push(employeeskills[i])

          oct++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 10) {
          this.shareemployeedata.nov.push(employeeskills[i])

          nov++;
        }
        if (new Date(employeeskills[i].startDate).getMonth() == 11) {
          this.shareemployeedata.dec.push(employeeskills[i])

          dec++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 0) {
          this.shareemployeedata.jan1.push(employeeskills[i])

          jan1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 1) {
          this.shareemployeedata.feb1.push(employeeskills[i])

          feb1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 2) {
          this.shareemployeedata.mar1.push(employeeskills[i])
          mar1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 3) {
          this.shareemployeedata.apr1.push(employeeskills[i])
          apr1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 4) {
          this.shareemployeedata.may1.push(employeeskills[i])
          may1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 5) {
          this.shareemployeedata.jun1.push(employeeskills[i])
          jun1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 6) {
          this.shareemployeedata.jul1.push(employeeskills[i])
          jul++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 7) {
          this.shareemployeedata.aug1.push(employeeskills[i])
          aug1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 8) {
          this.shareemployeedata.sep1.push(employeeskills[i])
          sep1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 9) {
          this.shareemployeedata.oct1.push(employeeskills[i])
          oct1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 10) {
          this.shareemployeedata.nov1.push(employeeskills[i])
          nov1++;
        }
        if (new Date(employeeskills[i].endDate).getMonth() == 11) {
          this.shareemployeedata.dec1.push(employeeskills[i])
          dec1++;
        }
      }

      this.onboarding.push(jan)
      this.onboarding.push(feb)
      this.onboarding.push(mar)
      this.onboarding.push(apr)
      this.onboarding.push(may)
      this.onboarding.push(jun)
      this.onboarding.push(jul)
      this.onboarding.push(aug)
      this.onboarding.push(sep)
      this.onboarding.push(oct)
      this.onboarding.push(nov)
      this.onboarding.push(dec)

      this.releasing.push(jan1)
      this.releasing.push(feb1)
      this.releasing.push(mar1)
      this.releasing.push(apr1)
      this.releasing.push(may1)
      this.releasing.push(jun1)
      this.releasing.push(jul1)
      this.releasing.push(aug1)
      this.releasing.push(sep1)
      this.releasing.push(oct1)
      this.releasing.push(nov1)
      this.releasing.push(dec1)
    }) //api close
  }
  getChartData(e) {
    if (e.active.length > 0) {
      var chartElement = e.active[0]._chart.getElementAtEvent(event);
      console.log("label names event", chartElement[0]._model.datasetLabel, chartElement[0]._model.label)
      var monthname = chartElement[0]._model.label
      console.log("monthname event", monthname);

      if (chartElement[0]._model.datasetLabel == "Onboard") {
        this.modalHeading = "Onboarded Employees in " + monthname

        if (monthname == 'Jan') {
          this.ShowInModal = this.shareemployeedata.jan
          console.log("jan", this.shareemployeedata.jan);
        }
        if (monthname == 'Feb') {
          this.ShowInModal = this.shareemployeedata.feb
          console.log("feb", this.shareemployeedata.feb);
        }
        if (monthname == 'Mar') {
          this.ShowInModal = this.shareemployeedata.mar
          console.log("mar", this.shareemployeedata.mar);
        }
        if (monthname == 'Apr') {
          this.ShowInModal = this.shareemployeedata.apr
          console.log("apr", this.shareemployeedata.apr);
        }
        if (monthname == 'May') {
          this.ShowInModal = this.shareemployeedata.may
          console.log("may", this.shareemployeedata.may);
        }
        if (monthname == 'Jun') {
          this.ShowInModal = this.shareemployeedata.jun
          console.log("jun", this.shareemployeedata.jun);
        }
        if (monthname == 'Jul') {
          this.ShowInModal = this.shareemployeedata.jul
          console.log("jul", this.shareemployeedata.jul);
        }
        if (monthname == 'Aug') {
          this.ShowInModal = this.shareemployeedata.aug

          console.log("aug", this.shareemployeedata.aug);
        }
        if (monthname == 'Sep') {
          this.ShowInModal = this.shareemployeedata.sep

          console.log("sep", this.shareemployeedata.sep);
        }
        if (monthname == 'Oct') {
          this.ShowInModal = this.shareemployeedata.oct

          console.log("oct", this.shareemployeedata.oct);
        }
        if (monthname == 'Nov') {
          this.ShowInModal = this.shareemployeedata.nov

          console.log("nov", this.shareemployeedata.nov);
        }
        if (monthname == 'Dec') {
          this.ShowInModal = this.shareemployeedata.dec
          console.log("dec", this.shareemployeedata.dec);
        }
      }
      if (chartElement[0]._model.datasetLabel == "Release") {
        this.modalHeading = "Releasing employees in " + monthname
        if (monthname == 'Jan') {
          this.ShowInModal = this.shareemployeedata.jan1

          console.log(this.shareemployeedata.jan1);
        }
        if (monthname == 'Feb') {
          this.ShowInModal = this.shareemployeedata.feb1
          console.log(this.shareemployeedata.feb1);
        }
        if (monthname == 'Mar') {
          this.ShowInModal = this.shareemployeedata.mar1
          console.log(this.shareemployeedata.mar1);
        }
        if (monthname == 'Apr') {
          this.ShowInModal = this.shareemployeedata.apr1
          console.log(this.shareemployeedata.apr1);
        }
        if (monthname == 'May') {
          this.ShowInModal = this.shareemployeedata.may1

          console.log(this.shareemployeedata.may1);
        }
        if (monthname == 'Jun') {
          this.ShowInModal = this.shareemployeedata.jun1
          console.log(this.shareemployeedata.jun1);
        }
        if (monthname == 'Jul') {
          this.ShowInModal = this.shareemployeedata.jul1
          console.log(this.shareemployeedata.jul1);
        }
        if (monthname == 'Aug') {
          this.ShowInModal = this.shareemployeedata.aug1
          console.log(this.shareemployeedata.aug1);
        }
        if (monthname == 'Sep') {
          this.ShowInModal = this.shareemployeedata.sep1
          console.log(this.shareemployeedata.sep1);
        }
        if (monthname == 'Oct') {
          this.ShowInModal = this.shareemployeedata.oct1
          console.log(this.shareemployeedata.oct1);
        }
        if (monthname == 'Nov') {
          this.ShowInModal = this.shareemployeedata.nov1
          console.log(this.shareemployeedata.nov1);
        }
        if (monthname == 'Dec') {
          this.ShowInModal = this.shareemployeedata.dec1
          console.log(this.shareemployeedata.dec1);
        }
      }
      this.showModal = 'block';

    }
  }

  closeModal() {
    this.showModal = 'none';
  }

  // onboarding vs releasing ends

  // resource allocation starts
  public ResAlloc_bar: ChartDataSets[] = [
    { data: [], label: 'Hours of allocation' },
  ]

  public ResAlloc_barLabels = []

  public ResAlloc_barOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [{ ticks: { min: 0, stepSize: 10 } }]
    },
  };
  public ResAlloc_barLegend = true;
  chartType(type) {
    console.log(type, "type");
    this.ResAlloc_barType = type
  }
  public ResAlloc_barType: ChartType = 'doughnut';
  public ResAlloc_barColors: {}[] = [{
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
  getresAlloc(empid) {
    if (!empid)
      this.emplist = empid = this.ResAllocList[0]
    else
      for (let i = 0; i < this.ResAllocList.length; i++) {

        if (empid != this.ResAllocList[i]) {
          if (i == this.ResAllocList.length - 1) {
            this.emplist = empid = this.ResAllocList[0]
          }
        }
        else {
          break;
        }
      }

    this.ResAlloc_barLabels = []
    console.log("empid", empid);

    var req = {
      "projectlist":
      {
        "empId": empid
      },
      "transactiontype": "getprojects"
    }
    console.log("empid res alloc", empid);
    this.psa.getEmployeeSkill(req).subscribe(res => {
      var response: any = res;
      console.log("response", response);

      if (response.message == "success") {
        var projectsList = response.projectlist
        var hoursArray = []
        var workedhrs = 0
        for (let i = 0; i < projectsList.length; i++) {
          this.ResAlloc_barLabels[i] = 'No of Hours in ' + projectsList[i].projectName
          hoursArray[i] = projectsList[i].hoursOfAllocation
          workedhrs += projectsList[i].hoursOfAllocation
          console.log(i, "length", projectsList.length)
          if (i == projectsList.length - 1) {
            console.log(i);
            // if (workedhrs < 8) {
            //   this.resAllocTable[0].employeeid.push(projectsList[i].empId)
            //   this.resAllocTable[0].hours.push(projectsList[i].hoursOfAllocation)
            // }
            if (workedhrs < 16) {
              this.ResAlloc_barLabels[i + 1] = "Available Hours"
              hoursArray[i + 1] = 16 - workedhrs
            }
            else {
              this.ResAlloc_barLabels[i + 1] = "Extended Hours"

              hoursArray[i + 1] = workedhrs - 16
            }
          }
        }
        this.ResAlloc_bar = [
          { data: hoursArray }
        ]
        console.log(this.ResAlloc_bar, "this.ResAlloc_bar", workedhrs);

      }
      else {
        console.log(response.message);

      }
    })//api close

  }
  // resource allocation ends

 
  betweenDays(p, q) {
    var _MS_PER_DAY = 1000 * 60 * 60 * 24;
    var a = new Date(p);
    var b = new Date(q);
    var array = []
    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
    var ndays = Math.floor((utc2 - utc1) / _MS_PER_DAY) + 1
    var n = 0;
    console.log("dates", ndays);

    while (n < ndays) {
      var from = new Date(a.getFullYear(), a.getMonth(), a.getDate() + n)
      array.push(from)
      n++;
    }
    // console.log("final dates", array);
    return ndays;
  }
  weekendCount(p, q) {
    var start = new Date(p);
    var end = new Date(q)
    if (end < start) return; //avoid infinite loop;
    for (var count = 0; start < end; start.setDate(start.getDate() + 1)) {
      if (start.getDay() == 0) count++;
      else if (start.getDay() == 6) count++;
    }
    // console.log("Weekend count ", count);
    return count;
  }

  getMonthsWorked(from, to) {
    var monthNum = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
    // var datFrom = new Date('1 ' + from);
    // var datTo = new Date('1 ' + to);
    var datFrom = new Date(from);
    var datTo = new Date(to);
    var arr1 = monthNum.slice(datFrom.getMonth(), datTo.getMonth() + 1);
    console.log("month names", arr1);
    return arr1;
  }

}