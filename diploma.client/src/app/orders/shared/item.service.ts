import {Injectable} from '@angular/core';
import {HttpService} from "../../http.service";

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private httpService: HttpService) {
  }

  getItemList() {
    return this.httpService.get('/restaurant/list-item').toPromise();
  }

  getItemListByCategory(category: string) {
    return this.httpService.get('/restaurant/list-item-by-category', {category: category}).toPromise();
  }

}
