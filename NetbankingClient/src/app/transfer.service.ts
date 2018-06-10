import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
@Injectable()
export class TransferService {

  constructor(private http: Http) { }


  transfer(transferRequest) {
    let header = new Headers();
    header.append('content-type', 'application/json');
    console.log(transferRequest);
    return this.http.put('http://localhost:8080/transfer', transferRequest, header).map(res => res.json());

  }
}
