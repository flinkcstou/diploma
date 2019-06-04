import {Component, OnInit} from '@angular/core';
import {LoginService} from "./login/login.service";
import {ActivatedRoute} from "@angular/router";
import {LanguagesService} from "./shared/languages.service";
import {AdminService} from "./admin/admin.service";
import {MessagingService} from "./messaging.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public const_languages: { RU_LANGUAGE: string; EN_LANGUAGE: string };

  constructor(public login: LoginService,
              private currentRoute: ActivatedRoute,
              public languagesService: LanguagesService,
              public adminService: AdminService,
              private messagingService: MessagingService) {

    this.const_languages = languagesService.CONST_LANGUAGES;
  }

  async ngOnInit() {
    await this.login.start();
    this.login.getPersonDisplay().then(value => {
      this.createLocalStorageUser()
    })
    // document.getElementById("navItem1").classList.add("active")

    this.messagingService.requestPermission().toPromise().then();
  }

  addClass() {
  }

  createLocalStorageUser() {
    var person: any = {};
    if (!this.isLocalStorageUser()) {
      person.name = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
      person.id = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
      localStorage.setItem('person', JSON.stringify(person))
    }

  }

  isLocalStorageUser() {
    return localStorage.getItem('person')
  }

  showMainPage(title: string, id?: any) {
  }

  changeLanguage(translate: string) {
    this.languagesService.changeLanguages(translate);
  }

  linkTelegram() {
    window.open("https://telegram.me/booking_diploma_bot", "_blank")
  }

  /*showMainPage(name: string, id): any {
    this.li = event.currentTarget
    let ids = this.li.id;
    waitTime(ids);

    function waitTime(ids) {
      setTimeout(function () {
        document.getElementById(ids).classList.add("active")
      }, 0)
    }

  }

  addClass() {
    let scope = this;
    for (let i = 0; i < event.currentTarget.children.length; i++) {
      AppComponent.removeActiveClass(event.currentTarget.children[i].id)
    }
  }

  static removeActiveClass(id) {
    document.getElementById(id).classList.remove("active")
  }*/


}
