import { NgModule } from '@angular/core';
import {
  BrowserModule,
  provideClientHydration,
} from '@angular/platform-browser';
import { Router } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule , HTTP_INTERCEPTORS} from '@angular/common/http';
import {HttpAuthorizationInterceptorService} from "./services/http-authorization-interceptor.service";
import {FooterComponent} from "./components/footer/footer.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FooterComponent,
    NgbModule,
  ],
  providers: [provideClientHydration(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpAuthorizationInterceptorService,
      multi: true
    }],
  bootstrap: [AppComponent],
})
export class AppModule {}
