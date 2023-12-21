import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserLoginRequestDto } from '../models/UserLoginRequestDto';
import { BASE_API_URL } from './consts';
import { UserRegistrationRequestDto } from '../models/UserRegistrationRequestDto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private readonly http: HttpClient,
  ) {}

  public login(user: UserLoginRequestDto): Observable<any> {
    return this.http.post(BASE_API_URL + 'login', user,{withCredentials: true});
  }
  public register(user: UserRegistrationRequestDto): Observable<string> {
    return this.http.post(BASE_API_URL + 'register', user, {
      responseType: 'text',
    });
  }
}
