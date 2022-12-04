import { Injectable } from '@angular/core';
import {ACCESS_TOKEN} from "../../constants/app-constants.component";
import {CookieService} from "ngx-cookie-service";
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from "@angular/router";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  private isLoggedIn = new BehaviorSubject<boolean>(false);

  isAuthenticated = this.cookieService.check(ACCESS_TOKEN);

  constructor(private cookieService: CookieService,
              private router: Router) { }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    console.log(this.cookieService.get(ACCESS_TOKEN));
    if (this.isAuthenticated) {
      this.isLoggedIn.next(true);
      return true;
    } else {
      this.isLoggedIn.next(false);
      this.router.navigate(['/login'], {
        queryParams: {
          return: state.url
        }
      });
      return false;
    }
  }
}
