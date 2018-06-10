import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import 'rxjs/add/operator/map'

@Injectable()
export class DeleteService {

  constructor(public http : Http) { }
  
  
  deleteBeneficiary(account,benid){ 
    
    let header = new Headers();
    header.append('content-type','application/json');
    //console.log(reqbody)
    console.log("Method called")
    return this.http.get('http://localhost:8080/deleteBeneficiary?account='+account+"&benId="+benid)
      .map(resp=>{resp.json();console.log(resp.json());})
  }

}
