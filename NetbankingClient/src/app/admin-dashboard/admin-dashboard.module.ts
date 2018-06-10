import { CustomerDashBoardModule } from '../customer-dash-board/customer-dash-board.module';
import { HeaderComponent } from '../header/header.component';
import { HomeModule } from '../home/home.module';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { AdminDashboardRoutingModule } from './admin-dashboard-routing.module';
import { AdminDashboardComponent } from './admin-dashboard.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule , AdminDashboardRoutingModule, HomeModule, ReactiveFormsModule
  ],
  declarations: [AdminDashboardComponent]
})
export class AdminDashboardModule { }
