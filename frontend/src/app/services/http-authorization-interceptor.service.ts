import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import { JwtUtilsService } from './jwt-utils.service';

@Injectable({
  providedIn: 'root'
})
export class HttpAuthorizationInterceptorService implements HttpInterceptor
{
  constructor(private jwtService: JwtUtilsService){

  }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
  {
    let token = localStorage.getItem('token');
    if (token ) {
      if(this.jwtService.isTokenExpired(token)){
        localStorage.removeItem('token')
      }else{
        request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      }
      
    }
    return next.handle(request);
  }
}
