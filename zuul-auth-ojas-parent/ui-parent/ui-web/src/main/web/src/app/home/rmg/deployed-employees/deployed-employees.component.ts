import { Component, OnInit } from '@angular/core';
import { RmgService } from '../../services/rmg.service';
import { AuthService } from 'src/app/services/auth.service';
import { PsaService } from '../../services/psa.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { HrmsService } from '../../services/hrms.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import swal from 'src/assets/sweetalert';

@Component({
  selector: 'app-deployed-employees',
  templateUrl: './deployed-employees.component.html',
  styleUrls: ['./deployed-employees.component.scss']
})
export class DeployedEmployeesComponent implements OnInit {
  rmgdata: any;
  originalData: any
  role: any
  managerbtn: any
  dataTo: any
  mindate: any
  maxdate: any
  pages: any = 5
  empall: any
  private projectAll: Subject<any> = new Subject();
  constructor(private rmgService: RmgService, private auth: AuthService, private psaService: PsaService, private hrms: HrmsService,private toastr: NotificationService) {
    this.getAllProject()
    this.getallEmp()
    this.role = this.auth.decryptData(sessionStorage.getItem('Role'))
    if (this.role == "ROLE_STAFFAUGHEAD") {
      this.managerbtn = false
    } else {
      this, this.managerbtn = true
    }
  }

  ngOnInit() {
    this.getresources()
  }

  ngOnDestroy(): any {
    this.projectAll.next();
    this.projectAll.complete();
  }

  getresources() {
    var response
    var data
    var obj = []
    var resourcedata = {
      "rmgInfo": {


      },
      "transactiontype": "getall"
    }

    this.rmgService.getAllResource(resourcedata).pipe(takeUntil(this.projectAll)).subscribe(res => {
      response = res
      data = response.rmgInfo
      console.log("resorcesss data", data)
      data.map(d => {
        if (d.resourceType == "Specific") {
          for (let i in d.rmgspecific) {
            if (d.rmgspecific[i].flag == true) {
              var project = this.dataTo.find(e => e.projectId == d.projectId)
              var final = new Object({ bookingId: d.bookingId, resourceType: d.resourceType, projectId: d.projectId, projectName: project.projectName, resource: d.rmgspecific[i] })
              obj.push(final)
            }
          }
        }
        else {
          for (let i in d.rmggeneric) {
            for (let j in d.rmggeneric[i].rmggenericresourcemap) {
              if (d.rmggeneric[i].rmggenericresourcemap[j].flag == true) {
                var project = this.dataTo.find(e => e.projectId == d.projectId)
                var final = new Object({ bookingId: d.bookingId, resourceType: d.resourceType, projectId: d.projectId, projectName: project.projectName, resource: d.rmggeneric[i].rmggenericresourcemap[j], genericId: d.rmggeneric[i].genericId })
                obj.push(final)
              }
            }

          }
        }
      })
      console.log(obj, "final object");
      this.originalData = obj
      this.rmgdata = obj

    })
  }

  getallEmp() {
    var response
    var empinfo =
    {
      "employeeInfo": [{

      }],
      "transactionType": "getall"
    }

    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.projectAll)).subscribe(res => {
      console.log("all employe", res)
      response = res
      var data = response.employeeInfo
      this.empall = data.map(d => new Object({ name: d.firstname + " " + d.lastname, id: d.employeeId }))
      console.log("filter data", this.empall);
    })
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

      this.mindate = this.dataTo.map(x => x.startDate)
      this.maxdate = this.dataTo.map(y => y.endDate);
      console.log(this.mindate, "startdate");
      console.log(this.maxdate, "endDate");

    });

  }


  filter(e) {
    console.log("filter value", e.value)
    this.rmgdata = this.originalData.filter(d => d.projectName == e.value)
  }


  releaseRequestObject(e) {
    console.log("release function", e);
    var response
    var employmentdetailss =
    {
      "employmentDetails": [{
        "employeeId": null
      }],
      "transactionType": "getAll"
    }
    if (e.resourceType == "Specific") {
      employmentdetailss.employmentDetails[0].employeeId = e.resource.employeeId
      this.hrms.getonboardingdetails(employmentdetailss).pipe(takeUntil(this.projectAll)).subscribe(res => {
        response = res
        if (response.employmentDetailsList[0].resourceType == "FTE") {
          status = "Lateral"
        } else if (response.employmentDetailsList[0].resourceType == "Bench") {
          status = "Bench"
        }
        var obj =
        {
          "rmgInfo":
          {

            "bookingId": e.bookingId,
            "resourceType": e.resourceType

          },

          "rmgSpecificList": [{
            "specificId": e.resource.specificId,
            "specificEmployeeStatus": status,
            "employeeId": e.resource.employeeId,
            "flag": 0

          }],

          "transactiontype": "release"
        }
        this.releaseResource(obj)
      })
    } else {
      employmentdetailss.employmentDetails[0].employeeId = e.resource.empId
      this.hrms.getonboardingdetails(employmentdetailss).pipe(takeUntil(this.projectAll)).subscribe(res => {
        response = res
        if (response.employmentDetailsList[0].resourceType == "FTE") {
          status = "Lateral"
        } else if (response.employmentDetailsList[0].resourceType == "Bench") {
          status = "Bench"
        }
        var obj = {
          "rmgInfo":
          {

            "bookingId": e.bookingId,
            "resourceType": e.resourceType

          },
          "rmgGenericList": [
            {
              "genericId": e.genericId,


              "rmggenericresourcemap": [
                {
                  "resourcegenericId":e.resource.resourcegenericId,
                  "empId": e.resource.empId,
                  "genericEmployeeStatus": status,
                  "flag": 0
                }
              ]

            }],

          "transactiontype": "release"
        }
        this.releaseResource(obj)
      })
    }
  }

  releaseResource(e) {
    var response
    console.log(e);
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
          this.rmgService.releaseRes(e).subscribe(res=>{
            response=res
            console.log(res);
            this.getresources()
            this.toastr.success(response.message)
          },err=>{
            response=err
            this.toastr.error("Resource release has not done");
          })
        }
      });
  }

}
