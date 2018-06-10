import {AdminAuthGuard} from './admin-auth.guard';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {CustomerDashBoardComponent} from './customer-dash-board/customer-dash-board.component';
import {AdminDashboardComponent} from './admin-dashboard/admin-dashboard.component';
import {AdminDashboardModule} from './admin-dashboard/admin-dashboard.module';
import {AppRoutingModule} from './app-routing.module';
import {CustomerAuthGuard} from './customer-auth.guard';
import {CustomerDashBoardModule} from './customer-dash-board/customer-dash-board.module';
import {HomeModule} from './home/home.module';
import {AboutUsComponent} from './about-us/about-us.component';
import {ContactUsComponent} from './contact-us/contact-us.component';
import {LogInService} from './log-in.service';
import {LogInComponent} from './log-in/log-in.component';
import {RegisterComponent} from './register/register.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {HeaderComponent} from './header/header.component';
import {SidebarComponent} from './sidebar/sidebar.component';
import {MyProfileComponent} from './my-profile/my-profile.component';
import {RegisterService} from './register.service';
import {AccountsComponent} from './accounts/accounts.component';
import {BenificiariesComponent} from './benificiaries/benificiaries.component';
import {GetDetailsService} from './get-details.service';
import {PaymentComponent} from './payment/payment.component';
import {TransfersComponent} from './transfers/transfers.component';
import {LastLogInComponent} from './last-log-in/last-log-in.component';
import {LogOutComponent} from './log-out/log-out.component';
import {DeleteService} from './delete.service';
import {PutDetailsService} from './put-details.service';
import {TransferService} from './transfer.service';
import {HomePageComponent} from './home-page/home-page.component';
import {OtpService} from './otp.service';
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule, HttpModule, AppRoutingModule, HomeModule, CustomerDashBoardModule, AdminDashboardModule, ReactiveFormsModule
  ],
  providers: [CustomerAuthGuard, AdminAuthGuard,
    LogInService, RegisterService,
    GetDetailsService, PutDetailsService,OtpService,
    DeleteService, TransferService],
  bootstrap: [AppComponent]
})
export class AppModule {

}
