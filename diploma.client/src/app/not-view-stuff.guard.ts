import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {LanguagesService} from "./shared/languages.service";
import {LoginService} from "./login/login.service";

@Injectable({
  providedIn: 'root'
})
export class NotViewStuffGuard implements CanActivate {
  constructor(private login: LoginService,
              public languagesService: LanguagesService) {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {


    if (!this.login.personDisplay || !this.login.personDisplay.cans.length) {
      return false
    }

    if (this.login.canViewWaiter) {
      return false
    }
    return true

  }
}
