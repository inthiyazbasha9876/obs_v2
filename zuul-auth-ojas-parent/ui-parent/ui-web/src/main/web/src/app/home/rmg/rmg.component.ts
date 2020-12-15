import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { PsaService } from '../services/psa.service';
import { RmgService } from '../services/rmg.service';
import { HrmsService } from '../services/hrms.service';
import { Router } from '@angular/router';
import {Location} from '@angular/common';
import { BrowesinghistoryService } from '../services/browesinghistory.service';

@Component({
  selector: 'app-rmg',
  templateUrl: './rmg.component.html',
  styleUrls: ['./rmg.component.scss']
})
export class RmgComponent implements OnInit {

  eid: string;
  loggedUserName: string;
  empbasicin: any;
  empbasicinfo: any;
  previous:any
  constructor(private fb: FormBuilder,private history:BrowesinghistoryService, private psaService: PsaService,private rmgService:RmgService ,private hrms: HrmsService, private route:Router,private _location:Location) { }

  ngOnInit() {
    this.eid = localStorage.getItem('UserName');
    this.loggedUserName = localStorage.getItem('UserName');
  
   

  }
  ngOnDestroy(): any {
    
  }

  backClicked(){
    this._location.back();
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
}
