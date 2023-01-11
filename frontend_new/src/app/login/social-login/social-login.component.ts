import { Component, OnInit } from '@angular/core';
import { GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL } from '../../constants/app-constants.component';

@Component({
  selector: 'app-social-login',
  templateUrl: './social-login.component.html',
  styleUrls: ['./social-login.component.scss']
})
export class SocialLoginComponent implements OnInit {

  googleAuthUrl = GOOGLE_AUTH_URL;
  facebookAuthUrl = FACEBOOK_AUTH_URL;

  constructor() { }

  ngOnInit(): void {
  }

}
