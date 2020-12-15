import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router, ActivationEnd } from '@angular/router';
import { DataService } from 'src/app/home/services';
import { Subscription } from 'rxjs';
import {Location} from '@angular/common';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { BrowesinghistoryService } from 'src/app/home/services/browesinghistory.service';

//import * as $ from 'jquery';
declare var $: any;

@Component({
  selector: 'app-employeeedit',
  templateUrl: './employeeedit.component.html',
  styleUrls: ['./employeeedit.component.scss']
})
export class EmployeeeditComponent implements OnInit {
 


eid:any;
previous:any
backClicked() {
  // this._location.back();
  this.previous=this.history.routeHistory.length
  if(this.previous>1){
      this.route.navigateByUrl(this.history.routeHistory[this.previous-2])
      this.history.routeHistory.splice(this.previous-1,1)
      this.history.routeHistory.splice(this.previous-2,1)   
  }
}
navigateTo(){
  this.route.navigate(['home/dashboard']);
}

  subscription: Subscription;
  constructor(private hrms:HrmsService,private history:BrowesinghistoryService,private paramroute: ActivatedRoute, private dataservice:DataService,private route:Router,private _location: Location) { 
   
    // this.eid = this.paramroute.snapshot.paramMap.get('employee_Id');
    // console.log("Snapshot", this.eid);
    this.paramroute.params.subscribe(Response=>{
      this.eid= parseInt(Response['employee_Id']);
      console.log("The message from Employee Edit sibling "+this.eid);
     
      this.dataservice.sendMessage(this.eid);
      
    });
   
  }
  setUserRole;
  user=false;
  ngOnInit() {
    $( '#topheader .navbar-nav li a' ).on( 'click', function () {
      $( '#topheader .navbar-nav' ).find( 'li.active' ).removeClass( 'active' );
      $( this ).parent( 'li' ).addClass( 'active' );
    });
    this.setUserRole = sessionStorage.getItem("setUserRole");
    //console.log('role to check user', this.setUserRole);
    if (this.setUserRole == "true") {
      this.user=true;
      
    }
  }
 role:any;
 userData:any;
  // basicinfofun(){
  //   this.route.navigateByUrl('/employeeedit/basicinfo', this.eid);
  // }

  // education(){
  //   this.route.navigateByUrl('/employeeedit/experience', this.eid);
  // }
}