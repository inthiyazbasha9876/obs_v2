import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lms',
  templateUrl: './lms.component.html',
  styleUrls: ['./lms.component.scss']
})
export class LmsComponent implements OnInit {

  constructor(private route: Router) { }

  ngOnInit() {
  }

  navigateTo(){
    this.route.navigate(['home/dashboard']); 
  }

}
