import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./main-page/main-page/main-page.component";
import {MyProfileComponent} from "./my-profile/my-profile.component";

const routes: Routes = [
  { path: 'home', component: MainPageComponent},
  { path: 'my-profile', component: MyProfileComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
