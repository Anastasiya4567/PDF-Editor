import {Component, OnInit} from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {ActivatedRoute, Router} from "@angular/router";
import {ACCESS_TOKEN} from "../../constants/app-constants.component";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-oauth2-redirect-handler',
  templateUrl: './oauth2-redirect-handler.component.html',
  styleUrls: ['./oauth2-redirect-handler.component.scss']
})
export class OAuth2RedirectHandlerComponent implements OnInit {

  private isLoggedIn = new BehaviorSubject<boolean>(false);

  constructor(private cookieService: CookieService,
              private router: Router,
              private route: ActivatedRoute) {
    this.route.queryParams
      .subscribe(params => {
        this.isLoggedIn.next(true);
        this.cookieService.set(ACCESS_TOKEN, params.token);
        this.router.navigate(['/main-page']);
        }
      );
  }

  ngOnInit(): void {

  }

}
