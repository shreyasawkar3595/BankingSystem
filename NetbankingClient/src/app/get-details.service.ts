import { Injectable } from '@angular/core';
import {Http } from '@angular/http';
import 'rxjs/add/operator/map'

@Injectable()
export class GetDetailsService {
message = '';
status ='';
  
  constructor(public http:Http) { }
  
  getAccountsByUserName(username){ 
    let header = new Headers();
    header.append('content-type','application/json');
    return this.http.get('http://localhost:8080/getAccountNumByUserName?username='+username).map(resp => resp.json())
  }
  
  
  getBenByAccount(accid){
    let header = new Headers();
    header.append('content-type','application/json');
    return this.http.get('http://localhost:8080/getBeneficiary?accNum='+accid).map(resp => resp.json())
  }
  
  getTransactionsByAccount(accid){
    return this.http.get('http://localhost:8080/transactionList?accNum='+accid).map(resp => resp.json())
  }
  
  getUserDetails(userId){
    return this.http.get('http://localhost:8080/user?userName='+userId).map(resp => resp.json())
  }
  
}
