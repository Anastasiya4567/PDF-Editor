import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {LoginData} from "../../models/LoginData";
import {AccountService} from "../../services/account/account.service";

import {ACCESS_TOKEN} from "../../constants/app-constants.component";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  loginForm: FormGroup;
  loginData: LoginData;
  submitted = false;
  badLogin = '';
  loading = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private accountService: AccountService
  ) {
    this.loginData = new LoginData();
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get form(): { [p: string]: AbstractControl } {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    this.loading = true;

    if (this.loginForm.invalid) {
      this.loading = false;
      console.log('inv');
      return;
    }

    this.loginData.email = this.form.email.value;
    this.loginData.password = this.form.password.value;

    this.accountService.login(this.loginData).subscribe(() => {
        this.router.navigate(['/main-page']);
      },
      error => {
        this.badLogin = 'Invalid user or not allowed to log-in';
        this.loading = false;
      });
  }

}
