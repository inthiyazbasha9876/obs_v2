import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import { Router } from '@angular/router';
import { BrowesinghistoryService } from '../services/browesinghistory.service';

declare var $: any;
@Component({
  selector: 'app-psa',
  templateUrl: './psa.component.html',
  styleUrls: ['./psa.component.scss']
})



export class PsaComponent implements OnInit {

  previous:any
  backClicked() {
    //this._location.back();
    
    this.previous=this.history.routeHistory.length
    if(this.previous>1){
        this.route.navigateByUrl(this.history.routeHistory[this.previous-2])
        this.history.routeHistory.splice(this.previous-1,1)
        this.history.routeHistory.splice(this.previous-2,1)   
    }
  }



  constructor(private _location: Location, private route:Router,private history:BrowesinghistoryService) { 
    
  }
  navigateTo(){
    this.route.navigate(['home/dashboard']);
  }
  
  ngOnInit() {

    $( '#topheader .navbar-nav li a' ).on( 'click', function () {
      $( '#topheader .navbar-nav' ).find( 'li.active' ).removeClass( 'active' );
      $( this ).parent( 'li' ).addClass( 'active' );
    });
  }

}
