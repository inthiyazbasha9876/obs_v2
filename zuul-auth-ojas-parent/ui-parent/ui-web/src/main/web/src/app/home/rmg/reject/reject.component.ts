import { Component, OnInit } from '@angular/core';
import { RmgService } from '../../services/rmg.service';
import { PsaService } from '../../services/psa.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-reject',
  templateUrl: './reject.component.html',
  styleUrls: ['./reject.component.scss']
})
export class RejectComponent implements OnInit {
  specificMoreInfo: boolean;
  specificDataSp=[];
  dataTo: any;
  mindate: any;
  maxdate: any;
  hideAlternative: boolean;
  
  

  constructor(private rmgService: RmgService,private psaService:PsaService) { }

  ngOnInit() {
    this.getAllProject();
    this.getresources();
   
  }

  private projectAll: Subject<any> = new Subject();
  hidedtab=true;
  backhide=false;
  rejectmoreid=false;
  addhide=true;
  rmgdata: any;

  rmgDataResponse: any;
  rmgProposal=[];
  dataOfResource: any;
  pages:any=5;



  back(){
    this.hidedtab=true;
    this.backhide=false;
    this.rejectmoreid=false;
  
    this.addhide=true;
  
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



  getresources() {
    var resourcedata = {
      "rmgInfo": {


      },
      "transactiontype": "getall"
    }

    this.rmgService.getAllResource(resourcedata).subscribe(res => {
      this.rmgdata = res;
      this.rmgDataResponse = this.rmgdata.rmgInfo;
      console.log("resorcesss data",this.rmgDataResponse)
    
     

      for(let i in this.rmgDataResponse){
        var proName=this.dataTo.find(s=>s.projectId==this.rmgDataResponse[i].projectId)
        this.rmgDataResponse[i].projectName=proName.projectName
        if(this.rmgDataResponse[i].resourceType.toLowerCase()=='specific'){
 
             for(let j in this.rmgDataResponse[i].rmgspecific){

               if(this.rmgDataResponse[i].rmgspecific[j].specificStatus=='rejected'){
                this.rmgDataResponse[i].status='rejected'
                this.rmgProposal.push(this.rmgDataResponse[i])
                console.log(this.rmgDataResponse[i],"rejectedrecords")
                break;
               }

             }

        }
        
      }

     


    })
  }



  //  bookmore(id){
  //   this.hidedtab=false;
  //   this.rejectmoreid=true;
  //   this.backhide=true;
  //   this.addhide=false;

  //   let resoucedata;
  //   var getenpid = {
  //     "rmgInfo":
  //     {
  //       "bookingId": id
  //     },
  //     "transactiontype": "getbyid"
  //   }
  //   this.rmgService.getResourcesbyId(getenpid).subscribe(res => {
  //     resoucedata = res;
  //     this.dataOfResource=resoucedata.rmg

  //     console.log("resource dataaa", this.dataOfResource);


  //     if(this.dataOfResource.resourceType.toLowerCase()=='specific'){
  
  //       for(let i in this.dataOfResource.rmgspecific){
        
  //       if(this.dataOfResource.rmgspecific[i].specificStatus== 'rejected'){
              
  //         this.specificDataSp.push(this.dataOfResource.rmgspecific[i])
  //       }
  
  //       }
  //     }
  //     if (this.dataOfResource.resourceType.toLowerCase() == "specific") {
      
  //       this.specificMoreInfo=true;
  //     }
  //     else {
        
  //       this.specificMoreInfo=false;
  //     }
  //   })


   


  // }


 bookmore(id){
    this.hidedtab=false;
    this.rejectmoreid=true;
    this.backhide=true;
    this.addhide=false;

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
      this.dataOfResource=resoucedata.rmg;
   
      console.log(this.dataOfResource,"responnse")
      this.specificDataSp=[];
      if (this.dataOfResource.resourceType.toLowerCase() == 'specific') {

        for (let i in this.dataOfResource.rmgspecific) {

          if (this.dataOfResource.rmgspecific[i].specificStatus == 'rejected') {
              this.dataOfResource.status='rejected'
            this.specificDataSp.push(this.dataOfResource.rmgspecific[i])
            console.log(this.specificDataSp, "SPECIFICDATA");

          }
          if(this.dataOfResource.rmgspecific[i].alternateemployeeId==null){
            this.hideAlternative=false;
        }
        else{
          this.hideAlternative=true;
        }

        }
      }
      if (this.dataOfResource.resourceType.toLowerCase() == "specific") {
        this.specificMoreInfo=true;
      }
      else {
        
        this.specificMoreInfo=false;
      }

      

    })


  }



}
