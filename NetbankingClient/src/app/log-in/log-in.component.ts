import {LogInService} from '../log-in.service';
import {Component, OnInit, EventEmitter, Output} from '@angular/core';
import {FormGroup, Validator, Validators, FormBuilder} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {


  loginform: FormGroup
  message: String

  constructor(public loginService: LogInService, private fb: FormBuilder,
    private router: Router) {}

  ngOnInit() {
    this.loginform = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required]
    })
  }
  onLoggedin(event) {
    console.log(this.loginform.value);
    this.loginService.login(this.loginform.value)
      .subscribe(res => {
        this.loginService.status = res.status;
        this.loginService.message = res.error;
        this.message = res.error;
        console.log(this.loginService.status)
        console.log(res.error)
        if (res.status === 'FAILED') {
          event.preventDefault();
          this.router.navigate(['/login']);
        }
        else {

          console.log("hello")
         sessionStorage.setItem('isLoggedin', 'true');
          sessionStorage.setItem('username', this.loginform.get('userName').value);
          console.log(sessionStorage.getItem('isLoggedin'));
           console.log(sessionStorage.getItem('username'));
          this.router.navigate(['/customerdashboard'])
        }
      })

  }



}
