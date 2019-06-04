import { Component, OnInit } from '@angular/core';
import {LoginService} from "../login/login.service";
import {LanguagesService} from "../shared/languages.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(public loginService: LoginService,
              public languagesService:LanguagesService) {}

  async ngOnInit() {

  }


  invalidChars = [
    "-",
    "+",
    "e",
  ];

  deleteE(e){
    if (this.invalidChars.includes(e.key)) {
      e.preventDefault();
    }
  }
}
