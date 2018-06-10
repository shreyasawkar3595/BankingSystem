import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map'
@Injectable()
export class RegisterService {
message = '';
status ='';
  
  constructor(public http:Http) { }
  
  register(registerreq){ 
    let header = new Headers();
    header.append('content-type','application/json');
    return this.http.put('http://localhost:8080/addUser', registerreq,header).map(resp => resp.json())
  }

}
