import { Component, OnInit } from '@angular/core';
import { RmgService } from '../../services/rmg.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { AuthService } from 'src/app/services/auth.service';
import { PsaService } from '../../services/psa.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { HrmsService } from '../../services/hrms.service';

@Component({
  selector: 'app-proposals',
  templateUrl: './proposals.component.html',
  styleUrls: ['./proposals.component.scss']
})
export class ProposalsComponent implements OnInit {

  rmgdata: any;
  private projectAll: Subject<any> = new Subject();
  rmgDataResponse: any;
  rmgProposal = [];
  dataOfResource: any;
  pages: any = 5;
  genericformbook: any;
  allocateResourseArray: any;
  specificMoreInfo: boolean;
  specificDataSp = [];
  dataOfResourceOF: any;
  proposalData: any;
  bookingidre: any;
  specificShow: boolean;
  specificToBook: any;
  specificbook: any;
  dataresponse: any;
  dataToBook: any[];
  openBook: boolean;
  encryptedRole: string;
  role: any;
  role1: string;
  loggedUserName: any;
  dataTo: any;
  mindate: any;
  maxdate: any;
  empbasicin: any;
  empObj: any;
  manager: unknown[];
  employee: unknown[];
  showRequestbutton: boolean;

  constructor(private rmgService: RmgService, private toastr: NotificationService,private authservice:AuthService,private psaService:PsaService, private hrms:HrmsService) { 
    this.getAllEmployees();
  }

  ngOnInit() {
    this.encryptedRole = sessionStorage.getItem('Role');
    this.role = this.authservice.decryptData(this.encryptedRole);
console.log("ROLE ",this.role);

    this.role1 = sessionStorage.getItem('UserName');
    this.loggedUserName = this.authservice.decryptData(this.role1);
    this.getAllProject();
    this.getresources();
  
  }

  proposalscard = false;
  hidedtab = true;
  backhide = false;
  proposalsmoreid = false;
  addhide = true;


  book() {
    this.hidedtab = false;
    this.backhide = true;

    this.proposalscard = true;
    this.addhide = false;
  }

  back() {

    this.hidedtab = true;
    this.backhide = false;
    this.proposalsmoreid = false;
    this.proposalscard = false;
    this.addhide = true;

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

  //Get all resources data

  getresources() {
    var resourcedata = {
      "rmgInfo": {


      },
      "transactiontype": "getAll"
    }

    this.rmgService.getAllResource(resourcedata).subscribe(res => {
      console.log(res, "alltghedata")
      this.rmgdata = res;
      this.rmgDataResponse = this.rmgdata.rmgInfo;

      for (let i in this.rmgDataResponse) {

        var proName=this.dataTo.find(s=>s.projectId==this.rmgDataResponse[i].projectId)
        this.rmgDataResponse[i].projectName=proName.projectName
        if (this.rmgDataResponse[i].resourceType.toLowerCase() == 'specific') {

          for (let j in this.rmgDataResponse[i].rmgspecific) {

            if (this.rmgDataResponse[i].rmgspecific[j].specificStatus == 'proposed') {
              this.rmgDataResponse[i].status='proposed'
              this.rmgProposal.push(this.rmgDataResponse[i])
              break;
            }
          }

        }


      }
      console.log("proposal resourcessss", this.rmgProposal);




    })
  }



  bookmore(id) {
    this.hidedtab = false;
    this.proposalsmoreid = true;
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
      //  this.specificDataSp=this.dataOfResource.rmgspecific
      console.log(this.dataOfResource, "responnse")
      if (this.dataOfResource.resourceType.toLowerCase() == 'specific') {
        this.specificDataSp = [];
        for (let i in this.dataOfResource.rmgspecific) {

          if (this.dataOfResource.rmgspecific[i].specificStatus == 'proposed') {
                 this.dataOfResource.status='proposed'
            this.specificDataSp.push(this.dataOfResource.rmgspecific[i])
            console.log(this.specificDataSp, "SPECIFICDATA");

          }

        }
      }
      if (this.dataOfResource.resourceType.toLowerCase() == "specific") {
        this.specificMoreInfo = true;
      }
      else {

        this.specificMoreInfo = false;
      }



    })


  }



  allocate(value, dataArray, data) {

    console.log("geneic value", value, dataArray, data);
    for (var i = 0; i < value.mapresouce.length; i++) {
      value.mapresouce[i].startDate = this.formatDate(value.mapresouce[i].startDate);
      value.mapresouce[i].endDate = this.formatDate(value.mapresouce[i].endDate);
      this.allocateResourseArray.push(value.mapresouce[i]);
      //console.log("anusha",this.allocateResourseArray.push(value.mapresouce[i].resourcegenericId))

    }

    let genresp;
    var request = {
      "rmgInfo":
      {

        "bookingId": data.bookingId,
        "resourceType": data.resourceType,
        "status": "approved",



      },
      "rmgGenericList": [{
        "genericId": dataArray.genericId,
        "rmggenericresourcemap": this.allocateResourseArray
      }],



      "transactiontype": "update"
    }


    console.log("log of request", request);

    this.rmgService.saveresources(request).subscribe(res => {
      genresp = res;
      this.getresources();
      console.log(genresp, "GENERIC DATA");
      var message = "Approved Successfully"
      if (res) {
        this.genericformbook.reset();
      }
      this.toastr.success(message)


    }
      , err => {
        console.log(err)
        this.toastr.info('not approved')


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
          "employeeId": a.resourceId1,
          "billRate": this.proposalData.billRate,
          "startDate": this.proposalData.startDate,
          "endDate": this.proposalData.endDate,
          "reason": a.reason,
          "specificStatus": "proposed"

        }],


      "transactiontype": "update"
    }



    console.log("app data", request);

    this.rmgService.updateresources(request).subscribe(res => {
      appResources = res;
      console.log("proposed", appResources);
      // this.getAllResources();
      var message = appResources.message;
      if (res) {
        // this.resourceForm.reset();
      }
      this.toastr.success(message)


    }
      , err => {
        this.toastr.info('Resource Not Added')

      })
  }





  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }


  specidataList=[];
  approveByMananger(data,arrayofdata){
    var appResources
    console.log(data,"basha needed data");


     console.log(arrayofdata,"basha  data");
     arrayofdata.specificStatus='approved'
     arrayofdata.startDate= arrayofdata.startDate.replace('T18:30:00.000Z','')
    arrayofdata.endDate=arrayofdata.endDate.replace('T18:30:00.000Z','')
    
    
    var request=
    {
      "rmgInfo":
          {
   
         "bookingId": data.bookingId,
          "resourceType":data.resourceType,
          "projectId":data.projectId,
          "status":"approved"
        
        
         
          },
       
        
          "rmgSpecificList":[ arrayofdata],

        
       
          "transactiontype":"update"
  }
  console.log(request,"reqobject");


  this.rmgService.updateresources(request).subscribe(res => { console.log(res,"response")
    appResources = res;
    console.log(appResources,"Approval from the manager");
  
  
    var message = appResources.message;
  
    this.toastr.success(message)


  }
    , err => {
      this.toastr.info('Resource Not Added')

    }
  

  )
}
rejectByManager(data,arrayofdata){
  var appResources
  console.log(data,"basha needed data");


   console.log(arrayofdata,"basha  data");
   arrayofdata.specificStatus='rejected'
   arrayofdata.startDate= arrayofdata.startDate.replace('T18:30:00.000Z','')
  arrayofdata.endDate=arrayofdata.endDate.replace('T18:30:00.000Z','')
  
  
  var request=
  {
    "rmgInfo":
        {
 
       "bookingId": data.bookingId,
        "resourceType":data.resourceType,
        "projectId":data.projectId,
        "status":"rejected"
      
      
       
        },
     
      
        "rmgSpecificList":[ arrayofdata],

      
     
        "transactiontype":"update"
}
console.log(request,"reqobject");


this.rmgService.updateresources(request).subscribe(res => { console.log(res,"response")
  appResources = res;
  console.log(appResources,"rejection from the manager");

// this.hidedtab=false;
// this.proposalsmoreid=false;
  var message = appResources.message;

  this.toastr.success(message)


}
  , err => {
    this.toastr.info('Resource Not Added')

  }


)
}




getAllEmployees() {
  let empbasic;
  var empinfo =
  {
    "employeeInfo": [{
    }],
    "transactionType": "getAllInfo"
  }

  this.hrms.getempinfo(empinfo).pipe(takeUntil(this.projectAll)).subscribe(res => {
    empbasic = res;
    this.empbasicin = empbasic.employeeInfo;
    this.manager = Array.from(new Set(this.empbasicin.map(x => x.reportingManager)));
    this.employee = Array.from(new Set(this.empbasicin.map(x => x.employeeId)));
   
   
    this.showRequestbutton=this.manager.includes(this.loggedUserName);

    console.log('show manager',this.showRequestbutton);

  

  });


}


}
