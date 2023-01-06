import {Component, OnInit} from '@angular/core';
import {AccountService} from "../services/account/account.service";
import {UserDetails} from "../models/UserDetails";

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {

  userDetails = new UserDetails();
  imageUrl: string;

  constructor(private accountService: AccountService) {
  }

  ngOnInit(): void {
    this.accountService.getProfileDetails().subscribe(response => {
      this.userDetails = response;
      this.imageUrl = this.userDetails.imageUrl;
    });
  }

}
