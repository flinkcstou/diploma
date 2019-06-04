import {Component, OnInit} from '@angular/core';
import {OrderService} from "./shared/order.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {LanguagesService} from "../shared/languages.service";
import {LoginService} from "../login/login.service";
import {BookingService} from "../shared/booking.service";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  orderList;
  todayTime;
  isAuthorized: boolean = false;
  personId: number | string;

  constructor(private service: OrderService,
              private router: Router,
              private toastr: ToastrService,
              public languagesService: LanguagesService,
              public loginService: LoginService,
              public bookingService: BookingService,) {
  }

  ngOnInit() {
    this.getPersonId()

    // this.refreshList();
    this.todayTime = this.formatDate(new Date())
  }

  getPersonId() {
    this.loginService.getPersonDisplay().then(value1 => {
      if (value1.username) {
        this.bookingService.getPersonId(value1.username).then(value => {
          this.isAuthorized = true
          this.personId = value1.username;
          this.refreshList(this.personId)

        })
      }
      else {
        this.userForLocalStorage();
        this.refreshList(this.personId)
      }

    });
  }

  userForLocalStorage() {
    let person: any = JSON.parse(localStorage.getItem('person'))
    this.personId = person.id
  }


  expectUser() {
    if (this.loginService.personDisplay && this.loginService.personDisplay.username) {
      if (this.loginService.canViewWaiter || this.loginService.canViewAdmin) {
        return true
      }
    }
    return false

  }


  refreshList(id: string | number) {

    if (this.expectUser()) {
      this.service.getOrderList().then(res => {
        this.orderList = res.body

        this.showDate()
        this.sortByKey(this.orderList, "orderNo")
      });
    }
    else {
      this.service.getOrderListById(id).then(res => {

        this.orderList = res.body;
        this.showDate()
        this.sortByKey(this.orderList, "orderNo")
      });
    }

  }

  sortByKey(array, key) {
    return array.sort(function (a, b) {
      var x = a[key];
      var y = b[key];
      return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    });
  }

  showDate() {
    for (let key in this.orderList) {
      if (!this.orderList[key].recordDateDay) {
        this.orderList[key].recordDateDay = "online"
        continue
      }
      this.orderList[key].recordDateDay = this.formatDate(new Date(this.orderList[key].recordDateDay))
    }
  }

  formatDate(date) {
    var monthNames = [
      "January", "February", "March",
      "April", "May", "June", "July",
      "August", "September", "October",
      "November", "December"
    ];

    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();

    return day + ' ' + monthNames[monthIndex] + ' ' + year;
  }

  changeStatusAccept(item: any) {

    item.status = 2;
    this.service.updateOrderStatus(item).then(value => {
      this.refreshList(this.personId)
    })

  }

  changeStatusComplete(item: any) {

    item.status = 3;
    this.service.updateOrderStatus(item).then(value => {
      this.refreshList(this.personId)
    })
  }

  openForEdit(item: any) {

    if (item.status > 1 || this.loginService.canViewWaiter) {
      this.router.navigate(['/order-view/edit/' + item.orderId]);
      return
    }

    /*if (this.loginService.canViewWaiter) {
      return
    }*/


    this.router.navigate(['/order/edit/' + item.orderId]);
  }

  onOrderDelete(id: number) {
    if (confirm('Are you sure to delete this record?')) {
      this.service.deleteOrder(id).then(res => {
        this.refreshList(this.personId);
        this.toastr.warning("Deleted Successfully", "Restaurent App.");
      });
    }
  }

}
