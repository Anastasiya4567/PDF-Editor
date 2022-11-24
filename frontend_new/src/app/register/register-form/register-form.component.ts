import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Account} from "../../models/Account";
import {Router} from "@angular/router";
import {AccountService} from "../../services/account/account.service";
import {first} from "rxjs";

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {

  registerForm: FormGroup;
  accountData: Account;
  submitted = false;
  registrationSuccess = false;
  registrationError = false;
  message = '';
  loading = false;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private accountService: AccountService) {
    this.accountData = new Account();
  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required, Validators.maxLength(100)])],
      email: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(50)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(40)])],
      repeatPassword: ['']
    }, {
      validator: this.checkPasswords('password', 'repeatPassword')
    });
  }

  get getFormControls(): { [p: string]: AbstractControl } {
    return this.registerForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    this.registrationSuccess = false;
    this.registrationError = false;

    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;

    this.accountData.name = this.registerForm.controls.name.value;
    this.accountData.email = this.registerForm.controls.email.value;
    this.accountData.password = this.registerForm.controls.password.value;


    this.accountService.register(this.accountData)
      .pipe(first())
      .subscribe(data => {
          this.message = data.message;
          console.log(data);
          this.registrationSuccess = true;
          this.registrationError = false;
          this.loading = false;
          this.resetRegisterForm();
        },
        error => {
          console.log(error);
          this.message = error.error.message;
          this.registrationSuccess = false;
          this.registrationError = true;
          this.loading = false;
        });

  }

  checkPasswords = (controlName: string, matchingControlName: string) => (registerForm: FormGroup) => {
    const control = registerForm.controls[controlName];
    const matchingControl = registerForm.controls[matchingControlName];

    if (matchingControl.errors && !matchingControl.errors.mustMatch) {
      return;
    }

    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({mustMatch: true});
    } else {
      matchingControl.setErrors(null);
    }
  }

  resetRegisterForm() {
    this.submitted = false;
    this.registerForm.reset();
  }
}
