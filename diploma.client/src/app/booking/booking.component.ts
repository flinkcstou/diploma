import {Component, OnInit} from '@angular/core';
import {BookingService} from "../shared/booking.service";
import {MatDatepickerModule, MatDialog, MatDialogConfig} from "@angular/material";
import {NextOperationComponent} from "../next-operation/next-operation.component";
import {LoginService} from "../login/login.service";
import {Router} from "@angular/router";
import {AlertComponent} from "../alert/alert.component";
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {LanguagesService} from "../shared/languages.service";
import {TableSelectionComponent} from "../table-selection/table-selection.component";
import {AmazingTimePickerService} from 'amazing-time-picker';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {
  minDate = new Date();
  maxDate = new Date(2020, 0, 1);

  public isRegisteredClient = false;
  public form: FormGroup = new FormGroup({
    phoneNumber: new FormControl('', Validators.required),
    recordTimeTo: new FormControl('', Validators.required)
  });


  userForm: FormGroup;
  mask: any[] = ['8', '(', /[1-9]/, /\d/, /\d/, ')', /\d/, /\d/, /\d/, '-', /\d/, /\d/, '-', /\d/, /\d/];
  public isValidateForm: boolean = false;
  public arrayOfPeople = [1, 2, 3, 4, 5, 6, 10, 12, 14]

  constructor(public bookingService: BookingService,
              private dialog: MatDialog,
              private login: LoginService,
              private router: Router,
              public languagesService: LanguagesService,
              public fb: FormBuilder,
              public matDatePicker: MatDatepickerModule,
              private atp: AmazingTimePickerService,) {
  }


  ngOnInit() {
    this.userForm = this.fb.group({
      phoneNumber: ['', [
        Validators.required,
        Validators.pattern('[\\d]{1}\\(?[\\d]{3}\\)?[\\d]{3}-?[\\d]{2}-?[\\d]{2}')
      ]
      ],
      recordTimeTo: ['', [
        Validators.required,
      ]]
    });

    console.log("dasdasa!!!!:", this.userForm.getRawValue())

    this.bookingService.getRestaurantTable().then(value => {
        this.bookingService.table = value.body
        this.resetForm();
      }
    )
    this.bookingService.getRestaurantTableList().then(value => {
        this.bookingService.tableArray = value.body
      }
    )
    this.resetForm();
    this.getPhoneNumber();

  }

  recordTimeToValidate() {
    let today = new Date();
    let from = this.bookingService.booking.recordDateFrom.substring(0, 2);
    let to = this.bookingService.booking.recordDateTo.substring(0, 2);
    if (+from + 2 > +to) {
      this.isValidateForm = false;
      return true
    }
    else {
      this.isValidateForm = true;
      return false
    }
  }

  changeNumber() {
  }

  changeTimeFrom() {
    const amazingTimePicker = this.atp.open();
    let subs = amazingTimePicker.afterClose().subscribe(time => {
      this.bookingService.booking.recordDateFrom = time
      subs.unsubscribe();
    })

  }

  changeTimeTo() {
    const amazingTimePicker = this.atp.open();
    let subs = amazingTimePicker.afterClose().subscribe(time => {
      this.bookingService.booking.recordDateTo = time
      subs.unsubscribe();
    })
  }

  selectDateDay(today) {
    let dd = today.getDate();
    let mm = today.getMonth() + 1;
    let yyyy = today.getFullYear();
    if (dd < 10) {
      dd = '0' + dd;
    }

    if (mm < 10) {
      mm = '0' + mm;
    }
    this.bookingService.booking.recordDateDay = yyyy + '-' + mm + '-' + dd
  }

  defaultDateDay() {
    let today = new Date();
    let dd: string | number = today.getDate();
    let mm: string | number = today.getMonth() + 1;
    let yyyy: string | number = today.getFullYear();
    if (dd < 10) {
      dd = '0' + dd;
    }

    if (mm < 10) {
      mm = '0' + mm;
    }
    return yyyy + '-' + mm + '-' + dd
  }

  defaultDateHourFrom() {
    let today = new Date();
    let hours = today.getHours();
    return hours + ':00'
  }

  defaultDateHourTo() {
    let today = new Date();
    let hours = today.getHours() + 2;
    return hours + ':00'
  }

  unmask(val) {
    if (!val) return val;
    return val.replace(/\D+/g, '');
  }


  resetForm() {
    this.bookingService.booking = {
      bookingId: Math.floor(100000 + Math.random() * 900000),
      numberOfPeople: 1,
      recordTime: new Date(),
      recordDateDay: this.defaultDateDay(),
      recordDateFrom: this.defaultDateHourFrom(),
      recordDateTo: this.defaultDateHourTo(),
      tableType: this.bookingService.table.length ? this.bookingService.table[0] : "",
      phoneNumber: "8(707)",
      personId: "",
    }
    this.userForLocalStorage()
  }

  onSubmit(form: NgForm) {
    this.bookingService.booking.phoneNumber = this.userForm.getRawValue().phoneNumber
    this.unmask(this.bookingService.booking.phoneNumber);
    event.preventDefault();
    if (!this.userForm.get('phoneNumber').valid) {
      return
    }
    if (!this.isValidateForm) {
      alert(this.languagesService.languages.bookingInvalid)
      return
    }
    let self = this;
    console.log(this.bookingService.booking)
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = "50%";
    this.login.preloader = true;
    self.bookingService.checkTime().then(value => {
      this.login.preloader = false;
      dialogConfig.data = {response: value.body};
      this.dialog.open(NextOperationComponent, dialogConfig).afterClosed().subscribe(res => {
        switch (res) {
          case "orderComponent":
            this.bookingService.saveBooking().then(res => {
              this.router.navigate(['/order']);
            })
            break;
          case "submit":
            self.bookingService.checkTime().then(value => {
              this.showResponseAlert(value.body);
            });
            break;
          default:
            break;
        }

      })
    })

  }

  showResponseAlert(response) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = "50%";
    dialogConfig.data = {response: response}
    this.dialog.open(AlertComponent, dialogConfig).afterClosed().subscribe(res => {

      switch (res) {
        case this.languagesService.languages.full:
          break;
        case this.languagesService.languages.empty:
          this.resetForm();
          this.getPhoneNumber();
          break;
      }
    });
  }

  getPhoneNumber() {
    this.login.preloader = true;
    this.login.getPersonDisplay().then(value1 => {
      this.login.preloader = false;
      if (value1.username) {
        this.isRegisteredClient = true;
        this.bookingService.booking.phoneNumber = value1.username;

        this.bookingService.getPersonId(value1.username).then(value => {
          this.bookingService.booking.personId = value.body
        })
      }
      else {
        this.userForLocalStorage()
      }

    });
  }

  userForLocalStorage() {
    let person: any = JSON.parse(localStorage.getItem('person'))
    if (!this.bookingService.booking.personId) {
      this.bookingService.booking.personId = person.id
    }
  }

  selectTable() {
    let scope = this
    console.log(this.bookingService.table)
    console.log(this.bookingService.booking.tableType)
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "80%";
    dialogConfig.height = "60%";
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = false;
    dialogConfig.data = {
      count: scope.bookingService.table.length,
      response: scope.bookingService.table.findIndex(value => value == scope.bookingService.booking.tableType),
      table: this.bookingService.table,
      tableArray: this.bookingService.tableArray,
    }
    this.dialog.open(TableSelectionComponent, dialogConfig).afterClosed().subscribe(res => {

      if (res != undefined) {
        this.bookingService.booking.tableType = this.bookingService.table[+res.id]
        this.bookingService.booking.numberOfPeople = res.personNumber
      }
      else {
        this.bookingService.booking.tableType = this.bookingService.table[0]
      }
    });
  }


}
