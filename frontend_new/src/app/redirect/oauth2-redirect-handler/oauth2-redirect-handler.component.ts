import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ACCESS_TOKEN} from "../../constants/app-constants.component";
import {BehaviorSubject} from "rxjs";
import {AccountService} from "../../services/account/account.service";

@Component({
  selector: 'app-oauth2-redirect-handler',
  templateUrl: './oauth2-redirect-handler.component.html',
  styleUrls: ['./oauth2-redirect-handler.component.scss']
})
export class OAuth2RedirectHandlerComponent implements OnInit {

  private isLoggedIn = new BehaviorSubject<boolean>(false);

  constructor(private accountService: AccountService,
              private router: Router,
              private route: ActivatedRoute) {
    this.route.queryParams
      .subscribe(params => {
        if (params.token) {
          localStorage.setItem(ACCESS_TOKEN, params.token);
          console.log(localStorage.getItem(ACCESS_TOKEN))
          this.accountService.setIsLoggedIn(true);
          this.isLoggedIn.next(true);
          this.router.navigate(['/main-page'])
        } else {
          console.log('No token in redirect parameters')
        }
      });
  }

  ngOnInit(): void {

  }

}
