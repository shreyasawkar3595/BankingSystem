import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map'

@Injectable()
export class PutDetailsService {
  
  message :''
  status : ''
  constructor(public http:Http) { }
  
  saveBeneficiary(request){ 
    let header = new Headers();
    header.append('content-type','application/json');
    return this.http.put('http://localhost:8080/createBeneficiary', request,header).map(resp => resp.json())
  }

}
