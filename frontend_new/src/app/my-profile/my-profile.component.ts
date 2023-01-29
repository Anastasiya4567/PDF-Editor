import {Component, OnInit} from '@angular/core';
import {AccountService} from "../services/account/account.service";
import {UserDetails} from "../models/UserDetails";
import {CookieService} from "ngx-cookie-service";
import {ACCESS_TOKEN} from "../constants/app-constants.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {

  userDetails = new UserDetails();
  imageUrl: string;
  message: string;

  constructor(private accountService: AccountService,
              private cookieService: CookieService,
              private modalService: NgbModal,
              private router: Router) {
  }

  ngOnInit(): void {
    this.accountService.getProfileDetails().subscribe(response => {
      this.userDetails = response;
      this.imageUrl = this.userDetails.imageUrl;
    });
  }

  deactivateAccount(modal: any) {
    this.closeModal(modal);
    this.accountService.deactivateAccount().subscribe(
      (response: any) => {
        this.message = response.message;
        localStorage.removeItem(ACCESS_TOKEN);
        this.router.navigate(['/login'])
      }, error => {
        this.message = error;
      })
  }

  openModal(content: any) {
    this.modalService.open(content);
  }

  closeModal(modal: any) {
    modal.close();
  }

}
