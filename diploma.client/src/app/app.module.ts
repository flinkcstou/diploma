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
import {NavbarComponent} from "./navbar/navbar.component";
import {ModalComponent} from "./modal/modal.component";

 /*Routing dlya PAGES*/

const routes =[
  {path :'' , component:LoginComponent},
  {path :'login' , component:LoginComponent },
  {path :'registr' , component:RegistrationComponent },
  {path :'home-page' , component:HomePageComponent }
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
    NavbarComponent,
    ModalComponent,
  ],
  imports: [
    BrowserModule, HttpClientModule, FormsModule,RouterModule.forRoot(routes , {enableTracing:true})
  ],
  providers: [HttpService, LoginService, ClientListService],
  bootstrap: [AppComponent]
})
export class AppModule {}
