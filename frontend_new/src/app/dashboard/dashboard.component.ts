import { Component, OnInit } from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import {AccountService} from "../services/account/account.service";
import {ACCESS_TOKEN} from "../constants/app-constants.component";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  isAuthenticated = this.cookieService.check(ACCESS_TOKEN);

  constructor(private cookieService: CookieService,
              private router: Router,
              private accountService: AccountService) { }

  ngOnInit(): void {

    this.accountService.getIsLoggedIn.subscribe(value => {
      if (value) {
        this.isAuthenticated = true;
      }
    });
  }

  logout() {
    this.cookieService.delete(ACCESS_TOKEN);
    this.isAuthenticated = false;
    this.toLoginPage();
  }

  toLoginPage() {
    this.router.navigate(['/login'])
  }
}
