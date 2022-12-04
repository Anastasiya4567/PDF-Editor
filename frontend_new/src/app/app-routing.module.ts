import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./main-page/main-page/main-page.component";
import {MyProfileComponent} from "./my-profile/my-profile.component";
import {DocumentEditionWindowComponent} from "./document-edition-window/document-edition-window.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {AboutComponent} from "./about/about.component";
import {AuthGuardService} from "./services/guard/auth-guard.service";
import {OAuth2RedirectHandlerComponent} from "./redirect/oauth2-redirect-handler/oauth2-redirect-handler.component";

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'oauth/redirect', component: OAuth2RedirectHandlerComponent },
  { path: 'main-page', component: MainPageComponent, canActivate: [AuthGuardService]},
  { path: 'main-page',
    children: [
      {
        path: 'document/:id',
        component: DocumentEditionWindowComponent,
        canActivate: [AuthGuardService]
      }
    ]
  },
  { path: 'my-profile', component: MyProfileComponent, canActivate: [AuthGuardService]},
  { path: 'about', component: AboutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
