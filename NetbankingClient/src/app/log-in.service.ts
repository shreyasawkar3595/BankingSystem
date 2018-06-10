import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map'
@Injectable()
export class LogInService {
message = '';
status ='';
  
  constructor(public http:Http) { }
  
  login(loginreq){ 
    let header = new Headers();
    header.append('content-type','application/json');
    return this.http.post('http://localhost:8080/checkLoginDetails', loginreq,header).map(resp => resp.json())
  }
  logout(){
    localStorage.removeItem('isLoggedIn')
  }
  

}
