import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UserLoginRequestDto } from "../models/UserLoginRequestDto";

import { UserRegistrationRequestDto } from "../models/UserRegistrationRequestDto";
import { Observable, catchError } from "rxjs";
import { JwtResponseDto } from "../models/JwtResponseDto";
import { environment } from "../../environments/environment";
import { JwtUtilsService } from "./jwt-utils.service";

@Injectable({
    providedIn: "root",
})
export class AuthService {
    constructor(
        private readonly http: HttpClient,
        private jwtUtil: JwtUtilsService
    ) {}

    public login(user: UserLoginRequestDto): Observable<JwtResponseDto> {
        return this.http.post<JwtResponseDto>(
            environment.BASE_API_URL + "login",
            user,
            { withCredentials: true }
        );
    }
    public register(user: UserRegistrationRequestDto): Observable<string> {
        return this.http.post(environment.BASE_API_URL + "register", user, {
            responseType: "text",
        });
    }
    public isLoggedIn(): boolean {
        let token = localStorage.getItem("token");
        if (token) {
            return !this.jwtUtil.isTokenExpired(token);
        } else return false;
    }
}
