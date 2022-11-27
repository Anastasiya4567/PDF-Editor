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
  isNotLogged = true;

  constructor(private cookieService: CookieService,
              private router: Router,
              private accountService: AccountService) { }

  ngOnInit(): void {

    this.accountService.getIsLoggedIn.subscribe(value => {
      if (this.isNotLogged && value) {
        this.isAuthenticated = value;
        this.isNotLogged = false;
      }
    });
  }

  logout() {
    this.cookieService.delete(ACCESS_TOKEN);
    this.isAuthenticated = false;
    this.isNotLogged = true;
    this.router.navigate(['/login']);
  }
}
