import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {HomeComponent} from "./system/home/home.component";
import {NotFoundComponent} from "./system/not-found/not-found.component";
import {PersonalAccountClientComponent} from "./system/personal-account-client/personal-account-client.component";
import {PersonalAccountAdminComponent} from "./system/personal-account-admin/personal-account-admin.component";
import {HeaderComponent} from "./system/shared/header/header.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { AboutCarwashComponent } from './system/about-carwash/about-carwash.component';
import {HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import {IConfig, NgxMaskModule} from "ngx-mask";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NotFoundComponent,
    PersonalAccountClientComponent,
    PersonalAccountAdminComponent,
    HeaderComponent,
    AboutCarwashComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    NgxMaskModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

export class AppConstants {
  public static get baseURL(): string { return "http://localhost:8080"; }
}
