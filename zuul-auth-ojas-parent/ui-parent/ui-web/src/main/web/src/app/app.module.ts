import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from "@angular/router";
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import { AuthGuard } from './guards/auth.guard';
import {routes} from'./app-routing.module';
import { TokenInterceptor } from './services/token-interceptor';
import { NotificationService } from './services/toastr-notification/toastr-notification.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RECAPTCHA_SETTINGS, RecaptchaModule, RecaptchaSettings, RecaptchaFormsModule } from 'ng-recaptcha';
import { AuthService } from './services/auth.service';

@NgModule({
  declarations: [
    AppComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes,{useHash: true}),
    HttpClientModule,
    BrowserAnimationsModule,
    RecaptchaModule,
    RecaptchaFormsModule

  ],
  providers: [{
    provide: RECAPTCHA_SETTINGS,
    useValue: {
      siteKey: '6LcyYMgUAAAAAC-hGHOHuE9bZs843ZMPkGphz2PS',
    } as RecaptchaSettings,
  },AuthService, AuthGuard,{ useClass: TokenInterceptor,provide:HTTP_INTERCEPTORS,multi: true},NotificationService],
  bootstrap: [AppComponent],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
]
})
export class AppModule { }
