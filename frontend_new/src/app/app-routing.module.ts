import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {StartPageComponent} from "./start-page/start-page/start-page.component";
import {MyProfileComponent} from "./my-profile/my-profile.component";

const routes: Routes = [
  { path: 'home', component: StartPageComponent},
  { path: 'my-profile', component: MyProfileComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
