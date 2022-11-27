import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./main-page/main-page/main-page.component";
import {MyProfileComponent} from "./my-profile/my-profile.component";
import {DocumentEditionWindowComponent} from "./document-edition-window/document-edition-window.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {AboutComponent} from "./about/about.component";

const routes: Routes = [
  { path: '', redirectTo: 'main-page', pathMatch: 'full'},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'main-page', component: MainPageComponent },
  { path: 'main-page',
    children: [
      {
        path: 'document/:title',
        component: DocumentEditionWindowComponent
      }
    ]
  },
  { path: 'my-profile', component: MyProfileComponent},
  { path: 'about', component: AboutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
