import {Component, OnInit} from '@angular/core';
import {LanguagesService} from "../shared/languages.service";
import {map} from "rxjs/operators";
import {fromEvent} from "rxjs";

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css',
    '../../style/style.css']
})
export class AboutComponent implements OnInit {

  constructor(public languagesService: LanguagesService) {
  }
  public imagesUrl;
  ngOnInit() {
    this.imagesUrl = [
      'assets/img/images.jpeg',
      'assets/img/download.jpeg',
      'assets/img/download(1).jpeg',
      'assets/img/download(2).jpeg',
      'assets/img/download(3).jpeg',
      'assets/img/download(4).jpeg',
      'assets/img/download(5).jpeg',
    ];

  }




}
