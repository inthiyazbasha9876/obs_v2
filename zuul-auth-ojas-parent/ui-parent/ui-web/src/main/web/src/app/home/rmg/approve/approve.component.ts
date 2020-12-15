import { Component, OnInit } from '@angular/core';
import { RmgService } from '../../services/rmg.service';
import { PsaService } from '../../services/psa.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-approve',
  templateUrl: './approve.component.html',
  styleUrls: ['./approve.component.scss']
})
export class ApproveComponent implements OnInit {

  specificMoreInfo: boolean;
  specificDataSp=[];
  dataTo: any;
  mindate: any;
  maxdate: any;
  hideAlternative: boolean;
  specificDataSp1=[];
 

  constructor(private rmgService: RmgService,private psaService:PsaService) { }

  ngOnInit() {
    this.getAllProject();
    this.getresources();
    
  }

  private projectAll: Subject<any> = new Subject();
  hidedtab=true;
  backhide=false;
  approvemoreid=false;
  addhide=true;
  rmgdata: any;

  rmgDataResponse: any;
  rmgProposal= [];
  dataOfResource: any;
  pages:any=5;
  dataset:any;

  back(){
    this.hidedtab=true;
    this.backhide=false;
    this.approvemoreid=false;
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


  

       this.rmgProposal = this.rmgDataResponse.filter(x => x.status.toLowerCase()=='approved' && x.resourceType.toLowerCase()=='generic')

      for (let i in this.rmgDataResponse) {
        var proName=this.dataTo.find(s=>s.projectId==this.rmgDataResponse[i].projectId)
        this.rmgDataResponse[i].projectName=proName.projectName
            if(this.rmgDataResponse[i].resourceType=='Specific'){
              for (let j in this.rmgDataResponse[i].rmgspecific) {
                if (this.rmgDataResponse[i].rmgspecific[j].specificStatus == 'approved') {
                  this.rmgDataResponse[i].status='approved'
               this.rmgProposal.push(this.rmgDataResponse[i]);
               break;
                }
      
      
              }

            }
          
       

      }
    
          
     
           
    })
  }
  


  bookmore(id){
    this.hidedtab=false;
    this.approvemoreid=true;
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
      //  this.specificDataSp=this.dataOfResource.rmgspecific
      console.log(this.dataOfResource,"responnse")
      
    if(this.dataOfResource.resourceType.toLowerCase()=='specific'){
      this.specificDataSp=[];
      for(let i in this.dataOfResource.rmgspecific){
       
      if(this.dataOfResource.rmgspecific[i].specificStatus== 'approved'){
            
        this.specificDataSp.push(this.dataOfResource.rmgspecific[i])
        console.log(this.specificDataSp,"SPECIFICDATA");
        
      }
      if(this.dataOfResource.rmgspecific[i].alternateemployeeId==null){
          this.hideAlternative=false;
      }
      else{
        this.hideAlternative=true;
      }

      }
    }
    if((this.dataOfResource.resourceType.toLowerCase()=='generic')&&(this.dataOfResource.status=='approved')){
      this.specificDataSp=[];
      for(let i in this.dataOfResource.rmggeneric){
        
        for(let j in this.dataOfResource.rmggeneric[i].rmggenericresourcemap){
          
               if(this.dataOfResource.rmggeneric[i].rmggenericresourcemap[j].genericStatus=='approved'){
                
                this.specificDataSp.push(this.dataOfResource.rmggeneric[i])
                
       
               
                break;
               }
        }
       
    

      }
    }
    // console.log(this.specificDataSp1,"Mapping of data")
  

      if (this.dataOfResource.resourceType == "Specific") {
        this.specificMoreInfo=true;
      }
      else {
        
        this.specificMoreInfo=false;
      }

     

    })


  }
  allocateResourseArray:any=[];

  allocate(value,dataArray,data){
    
   console.log("geneic value",value,dataArray,data);
  for(var i=0;i<value.mapresouce.length;i++){
    value.mapresouce[i].startDate=this.formatDate(value.mapresouce[i].startDate);
    value.mapresouce[i].endDate=this.formatDate(value.mapresouce[i].endDate);
    this.allocateResourseArray.push(value.mapresouce[i]);
    console.log(this.allocateResourseArray,"MAPPING DAATA");
    
    //console.log("resourceDataArray",this.allocateResourseArray.push(value.mapresouce[i].resourcegenericId))
  
  }
  
  
  let genresp;
  
  
  var request={
    "rmgInfo":
        {
  
        "bookingId":data.bookingId,
        "resourceType":data.resourceType,
        "status":"approved",
         "message":" avilable"
       
        },
     
      
         "rmgGenericList": [
        {
          "genericId":dataArray.genericId,
       
          "rmggenericresourcemap":this.allocateResourseArray
          
       
        }],
      
     
        "transactiontype":"update"
  }
  
  
  
  
  
  
  console.log("log of request",request);
  
   this.rmgService.saveresources(request).subscribe(res => {
     genresp = res;
    
     console.log(genresp, "GENERIC DATA");
     var message = "Resources allocated successfully"
    
  
  
   }
    )}
    formatDate(date) {
      var year = date.getFullYear().toString();
      var month = (date.getMonth() + 101).toString().substring(1);
      var day = (date.getDate() + 100).toString().substring(1);
      return year + "-" + month + "-" + day;
      }
    

}
