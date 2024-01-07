import {NgModule} from '@angular/core';
import {BrowserModule, provideClientHydration,} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {HttpAuthorizationInterceptorService} from "./services/http-authorization-interceptor.service";
import {FooterComponent} from "./components/footer/footer.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ImageCropperModule} from "ngx-image-cropper";

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FooterComponent,
    NgbModule,
    ImageCropperModule,
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
