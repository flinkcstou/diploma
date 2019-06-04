import {Injectable} from '@angular/core';
import {Order} from "../../../model/order.model";
import {OrderItem} from "../../../model/orderItem.model";
import {HttpService} from "../../http.service";
import {Item} from "../../../model/item.model";
import {Comments} from "../../../model/comments.model";
import {CommentsLike} from "../../../model/comments-like.model";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  public formData: Order;
  public orderItems: OrderItem[];
  public items: Item[] = [];

  constructor(private httpService: HttpService) {
  }

  saveOrUpdateOrder() {

    let orderItems = {
      ...this.formData,
      orderItems: this.orderItems
    };

    return this.httpService.post('/restaurant' + '/order-items', {orderItems: JSON.stringify(orderItems)})
  }

  getOrderListById(id: string | number) {
    return this.httpService.get('/restaurant/get-orders-by-id', {id}).toPromise();
  }

  getOrderList() {
    return this.httpService.get('/restaurant/get-orders',).toPromise();
  }

  getOrderByID(id: number): any {
    return this.httpService.get('/restaurant/get-order', {id}).toPromise();
  }

  deleteOrder(id: number) {
    return this.httpService.get('/restaurant/delete-order', {id}).toPromise();
  }

  offerPrepare(): Promise<any> {

    if (this.orderItems.length) {
      return this.httpService.get('/restaurant/offer', {orderItems: JSON.stringify(this.orderItems)}).toPromise();
    }
    else {
      return new Promise((resolve, reject) => {
        let obj = {
          body: []
        }
        resolve(obj);
      });
    }
  }

  updateOrderStatus(item: any) {
    item.recordDateDay = null
    return this.httpService.get('/restaurant/update-order-status', {orderList: JSON.stringify(item)}).toPromise();
  }

  getCommentsbyItemId(itemId: number) {
    return this.httpService.get('/restaurant/get-comments-by-item-id', {itemId}).toPromise();
  }

  setComments(comments: Comments) {
    return this.httpService.get('/restaurant/set-comments', {comments: JSON.stringify(comments)}).toPromise()
  }

  setCommentsLike(commentsLike: CommentsLike) {
    return this.httpService.get('/restaurant/set-comments-like', {commentsLike: JSON.stringify(commentsLike)}).toPromise()

  }

  setCommentsLikeByPersonid(personId: string) {
    return this.httpService.get('/restaurant/set-comments-like-by-person-id', {personId: personId}).toPromise()
  }
  getListCategory(){
    return this.httpService.get('/restaurant/get-list-category').toPromise()

  }

}
