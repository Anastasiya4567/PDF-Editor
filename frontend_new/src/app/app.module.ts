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
import {NewDocumentModalComponent} from './main-page/new-document-modal/new-document-modal.component';
import {DocumentEditionWindowComponent} from './document-edition-window/document-edition-window.component';
import {NgxKeyboardShortcutModule} from "ngx-keyboard-shortcuts";
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    DashboardComponent,
    MyProfileComponent,
    AllDocumentsComponent,
    NewDocumentModalComponent,
    DocumentEditionWindowComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    NgxPaginationModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxKeyboardShortcutModule
  ],
  providers: [
    CookieService,
    AllDocumentsComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
