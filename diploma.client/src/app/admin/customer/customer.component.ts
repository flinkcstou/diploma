import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AdminService} from "../admin.service";
import {UserCans} from "../../../model/UserCans.model";
import {PersonDisplays} from "../../../model/PersonDisplays";
import {UserCan} from "../../../model/UserCan";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  public personDisplaysList: PersonDisplays[] = [];
  public UserCansList: UserCans[] = [];

  public ngPersonDisplay: PersonDisplays = new PersonDisplays();
  public ngModelSelect: any[] = [];
  public ngUserCan: UserCan;
  public responseCustomer: any = {}
  public responseObject: any = [
    {
      response: 'empty',
      alertText: 'Успешно добавлено'
    }, {
      response: 'full',
      alertText: 'Успешно обнавлено'
    },
    {
      response: 'update',
      alertText: 'Успешно Обнавлено'
    },
  ];

  constructor(private adminService: AdminService,
              r: ActivatedRoute) {
    adminService.setTitle(r);
  }

  ngOnInit() {
    this.ngUserCan = UserCan.EMPTY;
    this.adminService.getUserCanList().then(value => {
      this.UserCansList = value.body as UserCans[]
    })
    this.adminService.getPersonDisplayList().then(
      value => {
        this.personDisplaysList = value.body as PersonDisplays[]
        this.ngPersonDisplay.role = ""
        this.ngPersonDisplay.fio = this.personDisplaysList[0].fio
        this.ngPersonDisplay.id = this.personDisplaysList[0].id
        this.ngPersonDisplay.cans = [...this.personDisplaysList[0].cans]
        this.ngPersonDisplay.username = this.personDisplaysList[0].username
      }
    )
  }


  chooseUserName() {
    if (this.personDisplaysList.find(value => (value.id == this.ngPersonDisplay.id))) {
      for (var key in this.personDisplaysList) {
        if (this.personDisplaysList[key].id == this.ngPersonDisplay.id) {
          this.ngPersonDisplay.fio = this.personDisplaysList[key].fio
          this.ngPersonDisplay.cans = [...this.personDisplaysList[key].cans];
          this.ngPersonDisplay.username = this.personDisplaysList[key].username;

        }
      }
    }

  }

  chooseUserCan() {
    for (var key in this.ngPersonDisplay.cans) {
      if (this.ngUserCan == this.ngPersonDisplay.cans[key]) {
        this.ngPersonDisplay.cans.splice(+key, 1)
        setTimeout(() => {
          this.ngUserCan = UserCan.EMPTY
        }, 0)
        return
      }
    }
    if (this.ngUserCan != UserCan.EMPTY) {
      this.ngPersonDisplay.cans.push(this.ngUserCan);
    }
    setTimeout(() => {
      this.ngUserCan = UserCan.EMPTY
    }, 0)

  }

  onSumbit(form: NgForm) {

    this.adminService.updatePersonCan(this.ngPersonDisplay).then(
      value => {
        debugger
        this.ngOnInit();
        for (let key in this.responseObject) {
          if (value.body == this.responseObject[key].response) {
            this.responseCustomer = this.responseObject[key]
          }
        }
        this.removeOpacityClass()

      }
    )
  }

  removeOpacityClass() {
    document.getElementById(this.responseCustomer.response).classList.add("response-capacity-to");
    setTimeout(() => {
      document.getElementById(this.responseCustomer.response).classList.remove("response-capacity-to");
    }, 1000)

  }
}
