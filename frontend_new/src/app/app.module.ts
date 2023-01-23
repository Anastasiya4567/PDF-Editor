import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {CookieService} from 'ngx-cookie-service';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainPageComponent} from './main-page/main-page/main-page.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {MyProfileComponent} from './my-profile/my-profile.component';
import {NgxPaginationModule} from "ngx-pagination";
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AllDocumentsComponent} from './main-page/all-documents-list/all-documents-list.component';
import {DocumentEditionWindowComponent} from './document-edition-window/document-edition-window.component';
import {NgxKeyboardShortcutModule} from "ngx-keyboard-shortcuts";
import {RouterModule} from "@angular/router";
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {LoginFormComponent} from './login/login-form/login-form.component';
import {SocialLoginComponent} from './login/social-login/social-login.component';
import {RegisterFormComponent} from './register/register-form/register-form.component';
import {AboutComponent} from './about/about.component';
import {OAuth2RedirectHandlerComponent} from './redirect/oauth2-redirect-handler/oauth2-redirect-handler.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    DashboardComponent,
    MyProfileComponent,
    AllDocumentsComponent,
    DocumentEditionWindowComponent,
    LoginComponent,
    RegisterComponent,
    LoginFormComponent,
    SocialLoginComponent,
    RegisterFormComponent,
    AboutComponent,
    OAuth2RedirectHandlerComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    NgxPaginationModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxKeyboardShortcutModule,
    NgbModule
  ],
  providers: [
    CookieService,
    AllDocumentsComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
