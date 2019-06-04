import {Component, OnInit} from '@angular/core';
import {LoginService} from "./login.service";
import {LanguagesService} from "../shared/languages.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(public loginService: LoginService,
              public languagesService: LanguagesService) {
  }

  async ngOnInit() {
    await this.loginService.start();
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
