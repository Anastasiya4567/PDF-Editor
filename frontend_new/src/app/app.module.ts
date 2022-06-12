import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CookieService } from 'ngx-cookie-service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainPageComponent } from './main-page/main-page/main-page.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MyProfileComponent } from './my-profile/my-profile.component';
import {NgxPaginationModule} from "ngx-pagination";
import { HttpClientModule } from '@angular/common/http';
import {ReactiveFormsModule} from "@angular/forms";
import { AllDocumentsComponent } from './main-page/all-documents/all-documents.component';
import { NewDocumentModalComponent } from './main-page/new-document-modal/new-document-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    DashboardComponent,
    MyProfileComponent,
    AllDocumentsComponent,
    NewDocumentModalComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgxPaginationModule,
        HttpClientModule,
        ReactiveFormsModule
    ],
  providers: [
    CookieService,
    AllDocumentsComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
