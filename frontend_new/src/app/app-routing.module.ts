import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainPageComponent} from "./main-page/main-page/main-page.component";
import {MyProfileComponent} from "./my-profile/my-profile.component";
import {DocumentEditionWindowComponent} from "./document-edition-window/document-edition-window.component";

const routes: Routes = [
  { path: '', redirectTo: 'main-page', pathMatch: 'full'},
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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
