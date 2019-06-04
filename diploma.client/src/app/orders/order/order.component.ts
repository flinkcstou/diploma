import {Component, OnInit} from '@angular/core';
import {OrderService} from "../shared/order.service";
import {NgForm} from "@angular/forms";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {OrderItemsComponent} from "../order-items/order-items.component";
import {CustomerService} from "../shared/customer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {Order} from "../../../model/order.model";
import {Item} from "../../../model/item.model";
import {BookingService} from "../../shared/booking.service";
import {LanguagesService} from "../../shared/languages.service";
import {LoginService} from "../../login/login.service";
import {Customer} from "../../../model/customer.model";
import {CommentsComponent} from "../../comments/comments.component";
import {Comments} from "../../../model/comments.model";
import {ItemService} from "../shared/item.service";
import {ItemList} from "../../../model/item-list.model";
import {OrderItem} from "../../../model/orderItem.model";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['../orders.component.css']
})
export class OrderComponent implements OnInit {

  customerList: Customer[];

  username: String = "anonymous";
  isAuthorized: boolean = false;

  isChangeOrder: boolean = false;

  isValid: boolean;

  comments: Array<Comments> = [];
  public itemList: Item[];
  public itemListList: ItemList[];
  category: string = 'menu-1';
  arrayOfCategory:Array<string>=[];
  isValidOrderItem: boolean = false;

  constructor(public service: OrderService,
              private itemService: ItemService,
              private dialog: MatDialog,
              private customerService: CustomerService,
              private toastr: ToastrService,
              private router: Router,
              private currentRoute: ActivatedRoute,
              private bookingService: BookingService,
              public languagesService: LanguagesService,
              public loginService: LoginService) {
  }

  ngOnInit() {
    this.service.getListCategory().then(value => {
      this.arrayOfCategory = value.body as Array<string>;
      this.category = this.arrayOfCategory[0]
    })
    this.service.items = [];
    let orderID: string;
    orderID = this.currentRoute.snapshot.paramMap.get('id');
    if (orderID == null) {
      this.resetForm();
      this.checkBooking();
    }
    else {
      this.service.getOrderByID(+orderID).then(res => {
        this.service.formData = OrderComponent.formDataAssign(res.body as Order);
        this.service.orderItems = res.body.orderItems;
      });
    }
    this.customerService.getCustomerList()
      .then(res => {
        this.customerList = res.body as Customer[];
      }).catch(e => console.log(e))


    this.getPersonId()
    this.selectListItem();

  }

  static formDataAssign(obj: Order): Order {
    let order: Order = new Order();
    order.orderId = obj.orderId;
    order.orderNo = obj.orderNo;
    order.personId = obj.personId;
    order.pMethod = obj.pMethod;
    order.gTotal = obj.gTotal;
    order.bookingId = obj.bookingId;
    order.status = obj.status
    return order
  }

  changeUserName(username: string) {

    var person = JSON.parse(localStorage.getItem('person'))
    person.name = username
    person.isChange = true;
    localStorage.setItem('person', JSON.stringify(person))
    console.log('1111')
  }

  getPersonId() {
    this.loginService.getPersonDisplay().then(value1 => {
      if (value1.username) {
        this.bookingService.getPersonId(value1.username).then(value => {
          this.isAuthorized = true;
          this.username = value1.username
          this.service.formData.personId = value.body
        })
      }
      else {
        this.userForLocalStorage()
      }

    });
  }

  userForLocalStorage() {
    let person: any = JSON.parse(localStorage.getItem('person'))
    if (!this.service.formData.personId) {
      this.service.formData.personId = person.id
    }
    if (person.isChange) {
      this.username = person.name
    }
  }

  resetForm(form?: NgForm) {
    if (form === null)
      form.resetForm();
    this.service.formData = {
      orderId: 0,
      orderNo: Math.floor(100000 + Math.random() * 900000).toString(),
      personId: JSON.parse(localStorage.getItem('person')).id,
      pMethod: this.languagesService.languages.cash,
      gTotal: 0,
      bookingId: null,
      status: 1,
    };
    this.service.orderItems = []
  }

  addOrEditOrderItem(orderItemIndex, orderId) {
    event.preventDefault();
    this.isChangeOrder = true;
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = "50%";
    dialogConfig.data = {orderItemIndex, orderId}
    this.dialog.open(OrderItemsComponent, dialogConfig).afterClosed().subscribe(res => {
      this.updateGrandTotal()
    })
  }

  onDeleteOrderItem(orderItemId: number, i: number) {
    event.preventDefault();
    this.isChangeOrder = true;
    this.service.orderItems.splice(i, 1)
    this.updateGrandTotal();
    this.service.offerPrepare().then(res => {
      this.service.items = res.body as Item[]
    })
  }

  onViewComment(orderItemId: number, i: number) {
    event.preventDefault();
    let itemId = this.service.orderItems[i].itemId;
    this.service.getCommentsbyItemId(itemId).then(value => {
      this.comments = value.body as Comments[];

      const dialogConfig = new MatDialogConfig();
      dialogConfig.autoFocus = true;
      dialogConfig.disableClose = false;
      dialogConfig.width = "70%";
      dialogConfig.height = "80%";
      dialogConfig.data = {
        comments: this.comments,
        itemId: itemId,
        isCommentsMessage: false,
      }
      dialogConfig.panelClass = 'backgound-mat-dialogs';
      this.dialog.open(CommentsComponent, dialogConfig).afterClosed().subscribe(res => {
      })

    });

  }

  updateGrandTotal() {
    this.service.formData.gTotal = this.service.orderItems.reduce((previousValue, currentValue) => {
      return previousValue + currentValue.total
    }, 0)
    this.service.formData.gTotal = parseFloat((this.service.formData.gTotal).toFixed(2))
  }

  validateForm() {
    this.isValid = true;
    if (this.service.formData.personId == "") {
      this.isValid = false;
    }
    else if (this.service.orderItems.length == 0) {
      this.isValid = false;
    }
    return this.isValid
  }

  onSubmit(form: NgForm) {
    event.preventDefault();
    if (this.validateForm()) {
      this.service.saveOrUpdateOrder().subscribe(res => {
        this.resetForm();
        this.toastr.success(this.languagesService.languages.submitsuccesfully, this.languagesService.languages.nameapp);
        this.router.navigate(['/orders']);
      })
    }
  }

  onSubmitMatButton() {
    event.preventDefault();
    if (this.validateForm()) {
      this.service.saveOrUpdateOrder().subscribe(res => {
        this.resetForm();
        this.toastr.success(this.languagesService.languages.submitsuccesfully, this.languagesService.languages.nameapp);
        this.router.navigate(['/orders']);
      })
    }
  }

  checkBooking() {
    if (this.bookingService.booking.bookingId) {
      this.service.formData.bookingId = this.bookingService.booking.bookingId;
    }
  }

  getList() {
    this.itemService.getItemListByCategory(this.category)
      .then(res => {
        this.itemList = res.body as Item[];
        console.table(this.itemList)
        this.itemListList = this.itemList.map((value, index) => {
          let orderItem = new OrderItem();
          orderItem = {
            orderItemId: null,
            orderId: this.service.formData.orderId,
            itemId: 0,
            itemName: '',
            price: value.price,
            quantity: 1,
            total: 0
          };
          orderItem.total = parseFloat((orderItem.quantity * orderItem.price).toFixed(2));

          let items = {
            orderItem: orderItem,
            ...value
          }
          return items;
        });
        this.itemListList.sort(function (a, b) {
          let keyA = a.description.length;
          let keyB = b.description.length;
          if (keyA < keyB) return 1;
          if (keyA > keyB) return -1;
          return 0
        })
      })
      .catch(e => console.log("NAZAR: ", e))
  }

  selectListItem() {
    this.itemService.getItemListByCategory(this.category)
      .then(res => {
        this.itemList = res.body as Item[];
        console.table(this.itemList)
        this.itemListList = this.itemList.map((value, index) => {
          let orderItem = new OrderItem();
          orderItem = {
            orderItemId: null,
            orderId: this.service.formData.orderId,
            itemId: value.itemId,
            itemName: value.name,
            price: value.price,
            quantity: 1,
            total: 0
          };
          orderItem.total = parseFloat((orderItem.quantity * orderItem.price).toFixed(2));

          let items = {
            orderItem: orderItem,
            ...value
          }
          return items;
        })
        this.itemListList.sort(function (a, b) {
          let keyA = a.description.length;
          let keyB = b.description.length;
          if (keyA < keyB) return 1;
          if (keyA > keyB) return -1;
          return 0
        })
      })
      .catch(e => console.log("NAZAR: ", e))
  }

  updateTotal(item: ItemList, e) {
    if (this.invalidChars.includes(e.key)) {
      e.preventDefault();
    }
    item.orderItem.quantity = +(item.orderItem.quantity + '').replace(/^0+/, '');
    item.orderItem.total = parseFloat((item.orderItem.quantity * item.price).toFixed(2))
  }

  invalidChars = [
    "-",
    "+",
    "e",
  ];

  validateFormOrderItem(item: ItemList) {
    this.isValidOrderItem = true;
    if (item.orderItem.itemId == 0) {
      this.isValidOrderItem = false
    }
    else if (!item.orderItem.quantity) {
      this.isValidOrderItem = false
    }
    return this.isValidOrderItem
  }

  submitOrderItem(item: ItemList) {
    if (!this.validateFormOrderItem(item)) {
      return
    }
    else {
      this.isChangeOrder = true;
      123
      this.service.orderItems.push(item.orderItem);
      this.service.offerPrepare().then(res => {
        console.log("OFFER PREPARE: ", res.body);
        if (res.body.length)
          this.service.items = res.body as Item[]
        this.updateGrandTotal();
      });

    }

  }

}
