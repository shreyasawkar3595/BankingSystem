import { AboutUsComponent } from '../about-us/about-us.component';
import { ContactUsComponent } from '../contact-us/contact-us.component';
import { HomePageComponent } from '../home-page/home-page.component';
import { LogInComponent } from '../log-in/log-in.component';
import { LogOutComponent } from '../log-out/log-out.component';
import { RegisterComponent } from '../register/register.component';
import {HomeComponent} from './home.component';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Routes, RouterModule} from '@angular/router';


const routes: Routes = [

  {
    path: '', component: HomeComponent,
    children : [
    {path: '', component:HomePageComponent},
    {
      path: 'aboutus',
      component: AboutUsComponent
    }, {
      path: 'contactus',
      component: ContactUsComponent
    }, {
      path: 'login',
      component: LogInComponent
    }, {
      path: 'register',
      component: RegisterComponent
    }, {
      path: 'logout',
      component: LogOutComponent
    }]

  }, 
]

@NgModule({
  imports: [
    CommonModule, RouterModule.forChild(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class HomeRoutingModule {}
