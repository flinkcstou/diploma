import { Component, OnInit } from '@angular/core';
import {OrderService} from "./shared/order.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {LanguagesService} from "../shared/languages.service";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styles: []
})
export class OrdersComponent implements OnInit {
  orderList;

  constructor(private service: OrderService,
              private router: Router,
              private toastr: ToastrService,
              public languagesService:LanguagesService) { }

  ngOnInit() {
    this.refreshList();
  }

  refreshList() {
    this.service.getOrderList().then(res =>
    {
      console.log("RES::::", res)

      this.orderList = res.body
    });
  }

  openForEdit(orderID: number) {

    this.router.navigate(['/order/edit/' + orderID]);
  }

  onOrderDelete(id: number) {
    if (confirm('Are you sure to delete this record?')) {
      this.service.deleteOrder(id).then(res => {
        this.refreshList();
        this.toastr.warning("Deleted Successfully", "Restaurent App.");
      });
    }
  }

}
