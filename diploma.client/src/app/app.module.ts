import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HttpService} from "./http.service";
import {HttpClientModule} from "@angular/common/http";
import {LoginService} from "./login/login.service";
import {FormsModule} from "@angular/forms";
import {ClientListComponent} from './client-list/client-list.component';
import {ClientListService} from "./client-list/client-list.service";
import { AboutComponent } from './about/about.component';
import { RegistrationComponent } from './registration/registration.component';
import { HomePageComponent } from './home-page/home-page.component';
import { FooterComponent } from './footer/footer.component';
import {RouterModule} from "@angular/router";

 /*Routing dlya PAGES*/

const routes =[
  {path :'' , component:HomePageComponent },
  {path :'login' , component:LoginComponent },
  {path :'registr' , component:RegistrationComponent }
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ClientListComponent,
    AboutComponent,
    RegistrationComponent,
    HomePageComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule, HttpClientModule, FormsModule,RouterModule.forRoot(routes , {enableTracing:true})
  ],
  providers: [HttpService, LoginService, ClientListService],
  bootstrap: [AppComponent]
})
export class AppModule {}
