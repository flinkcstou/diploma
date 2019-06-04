import {Component, OnInit} from '@angular/core';
import {LanguagesService} from "../shared/languages.service";

@Component({
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css',
    '../../style/style.css']
})
export class ContactUsComponent implements OnInit {

  constructor(public languagesService: LanguagesService) {
  }

  public contactNG: any = {}
  public isValidate: boolean;

  ngOnInit() {
    this.contactNG = {
      name: '',
      email: '',
      subject: '',
      messages: ''
    }
  }

  onSubmit() {
    for (let key in this.contactNG) {
      if (!this.contactNG[key].length) {
        this.isValidate = false
        break
      }
      else {
        this.isValidate = true
      }
    }
    if (!this.isValidate) {
      alert(this.languagesService.languages.contactmessageemtpty)
    }
    else {
      alert(this.languagesService.languages.contactmessagefull)
      this.contactNG = {
        name: '',
        email: '',
        subject: '',
        messages: ''
      }
    }
  }

}
