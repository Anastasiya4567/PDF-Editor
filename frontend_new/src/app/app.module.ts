import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CookieService } from 'ngx-cookie-service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StartPageComponent } from './start-page/start-page/start-page.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MyProfileComponent } from './my-profile/my-profile.component';
import {NgxPaginationModule} from "ngx-pagination";
import { HttpClientModule } from '@angular/common/http';
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    StartPageComponent,
    DashboardComponent,
    MyProfileComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgxPaginationModule,
        HttpClientModule,
        ReactiveFormsModule
    ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
