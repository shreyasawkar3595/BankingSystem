import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-log-out',
  templateUrl: './log-out.component.html',
  styleUrls: ['./log-out.component.css']
})
export class LogOutComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    console.log("log out");
    sessionStorage.clear();  
  console.log("log out"+localStorage.getItem('isLoggedIn'))
  }
}
