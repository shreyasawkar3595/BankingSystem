import {GetDetailsService} from '../get-details.service';
import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  transactionButtonValue: string;
  isAccountSelected: boolean;
  selectedAccount;

  accounts: any[];
  constructor(public getdetailsservice: GetDetailsService) {}
transactions:any[];
  ngOnInit() {
    this.transactionButtonValue = 'View Transactions'
this.isAccountSelected = false;
    this.getdetailsservice.getAccountsByUserName(sessionStorage.getItem('username')).subscribe(res => {

      console.log(res);

      this.accounts = res;
    })
  }
  onAccountSelected(accountNum) {
    if(this.isAccountSelected===true){
      this.isAccountSelected = false;
      this.transactionButtonValue = 'View Transactions'
    }
    else{
    this.isAccountSelected = true;
    this.selectedAccount = accountNum;
    this.transactionButtonValue = 'Hide Transactions'
    this.getdetailsservice.getTransactionsByAccount(accountNum).subscribe(res=>{
      
       this.transactions = res;
      
    });
  }
  }
}
