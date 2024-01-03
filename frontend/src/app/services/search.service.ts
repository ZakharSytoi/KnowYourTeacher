import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SearchRequestDto} from "../models/SearchRequestDto";
import {Observable} from "rxjs";
import {Page} from "../models/Page";
import {environment} from "../../environments/environment";
import {SearchedReviewDto} from "../models/SearchedReviewDto";

@Injectable({
    providedIn: 'root'
})
export class SearchService {

    constructor(private readonly http: HttpClient) {
    }

    search$ = (searchRequest: SearchRequestDto, pageNumber: number = 0, pageSize: number = 10): Observable<Page<SearchedReviewDto>> => {
        let requestParams: string = ''
        if (searchRequest.teacherName) requestParams = requestParams + '&teacherName=' + searchRequest.teacherName.trim();
        if (searchRequest.teacherSurname) requestParams = requestParams + '&teacherSurname=' + searchRequest.teacherSurname.trim();
        if (searchRequest.subject) requestParams = requestParams + '&subject=' + searchRequest.subject.trim();
        if (searchRequest.universityId && !isNaN(parseInt(searchRequest.universityId))) requestParams = requestParams + '&universityId=' + searchRequest.universityId.trim();
        return this.http.get<Page<SearchedReviewDto>>(`${environment.BASE_API_URL}reviews?pageNumber=${pageNumber}&pageSize=${pageSize}${requestParams}`)
    }
}
