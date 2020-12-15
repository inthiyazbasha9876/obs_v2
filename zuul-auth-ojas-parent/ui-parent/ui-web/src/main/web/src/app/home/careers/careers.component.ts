import { Component, OnInit } from '@angular/core';
import { HrmsService } from '../services/hrms.service';
import { DataService } from '../services';
import {Location} from '@angular/common';

@Component({
  selector: 'app-careers',
  templateUrl: './careers.component.html',
  styleUrls: ['./careers.component.scss']
})
export class CareersComponent implements OnInit {
  idd: any;
  backClicked() {
    this.hrms.navigateToDashboard();
    
    
  }
  projectDetails: any;
  projectDetailsLi: any;
  private pageSize: number = 5;
  constructor(private hrms:HrmsService, private dataservice:DataService,private _location: Location) { }
  idofemp;
  stateslist;
  d:any
  ngOnInit() {
    
    this.idofemp=sessionStorage.getItem("IdEmp");
    this.idd=Number(this.idofemp);

   
   this.getstateslist();
    this.getProject();
  }

  getstateslist(){
    var request =
    {
       
      "states":
       [{
        
       }],
            
      "sessionId":"1234",
       "transactionType": "getall"
       
      }
     this.hrms.getStateListMaster(request).subscribe(res=>{
      this.d=res
       this.stateslist=this.d.statesList;
       console.log(this.stateslist);
     })
  }

  getProject(){
    var jsonData =
    {
      "projectDetailsList" : [
        {
        "id":this.idd
        }
      ], 
          "transactionType":"getbyid"
     }
  //    {
     
  //     "transactionType":"getAll"
  // }
    this.hrms.getProjectDetails(jsonData).subscribe(response =>{
      this.projectDetails = response;
      this.projectDetailsLi = this. projectDetails.projectDetailsList;
      var state=this.stateslist.find(s => s.id == this.projectDetailsLi[0].location);
      console.log(state)
      this.projectDetailsLi[0].location=state.stateName;
      console.log("Project Details List", this.projectDetailsLi);
    })
    
    }

    
}