import { Component, OnInit } from '@angular/core';
import {GetDetailsService} from '../get-details.service';
@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  user:any;

  constructor(public service : GetDetailsService) { }

  ngOnInit() {
    this.service.getUserDetails(sessionStorage.getItem('username')).subscribe(user=>{this.user=user; console.log(user)});
  }

}
