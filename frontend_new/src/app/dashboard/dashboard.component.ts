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

  isAuthenticated = localStorage.getItem(ACCESS_TOKEN) != null;

  constructor(private cookieService: CookieService,
              private router: Router,
              private accountService: AccountService) { console.log(localStorage.getItem(ACCESS_TOKEN))}

  ngOnInit(): void {

    this.accountService.getIsLoggedIn.subscribe(value => {
      if (value) {
        this.isAuthenticated = value;
      }
    });
  }

  logout() {
    this.isAuthenticated = false;
    localStorage.removeItem(ACCESS_TOKEN);
    this.toLoginPage();
  }

  toLoginPage() {
    this.router.navigate(['/login'])
  }
}
