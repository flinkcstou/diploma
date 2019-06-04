import {Injectable} from '@angular/core';
import {HttpService} from "../http.service";
import {Booking} from "../../model/booking.model";
import {Table} from "../../model/table.model";

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  public booking: Booking = new Booking();
  public table: Array<string> = [];
  public tableArray: Array<Table> = [];
  public processResponse = "";

  constructor(private httpService: HttpService) {
  }


  checkTime() {
    let booking = this.booking;
    return this.httpService.get('/booking/check-time', {booking: JSON.stringify(booking)}).toPromise();
  }

  saveBooking() {
    let booking = this.booking;
    return this.httpService.get('/booking/save', {booking: JSON.stringify(booking)}).toPromise();
  }
  getPersonId(username:string){
    return this.httpService.get('/booking/person-id', {username:username}).toPromise();
  }

  getRestaurantTable() {
    return this.httpService.get('/booking/restaurant-table').toPromise();

  }

  getRestaurantTableList() {
    return this.httpService.get('/booking/restaurant-table-list').toPromise();
  }

}
