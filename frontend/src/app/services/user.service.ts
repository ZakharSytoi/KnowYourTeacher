import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {TeacherCardDto} from "../models/TeacherCardDto";
import {TeacherCreateRequestDto} from "../models/TeacherCreateRequestDto";
import {HttpClient} from "@angular/common/http";
import {UserResponseDto} from "../models/UserResponseDto";
import {Page} from "../models/Page";
import {SearchedReviewDto} from "../models/SearchedReviewDto";
import {PasswordUpdateDtoRequest} from "../models/PasswordUpdateDtoRequest";
import {UserInfoUpdateDtoRequest} from "../models/UserInfoUpdateDtoRequest";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(readonly http: HttpClient) {
    }

    user$ = (): Observable<UserResponseDto> => {
        return this.http.get<UserResponseDto>(`${environment.BASE_API_URL}user`);
    }

    userReviews$ = (pageNumber: number = 0, pageSize: number = 10): Observable<Page<SearchedReviewDto>> => {
        return this.http.get<Page<SearchedReviewDto>>(`${environment.BASE_API_URL}user/reviews?pageNumber=${pageNumber}&pageSize=${pageSize}`)
    }

    updateUserPassword = (passwordUpdateRequest: PasswordUpdateDtoRequest): Observable<string> =>
        this.http.post<string>(`${environment.BASE_API_URL}user/password`, passwordUpdateRequest)

    updateUserInfo = (userInfoUpdateDtoRequest: UserInfoUpdateDtoRequest): Observable<string> =>
        this.http.put<string>(`${environment.BASE_API_URL}user`, userInfoUpdateDtoRequest)

}
