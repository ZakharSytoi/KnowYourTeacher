import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../models/Page";
import {ReviewDto} from "../models/ReviewDto";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ReviewService {

    constructor(private readonly http: HttpClient) {
    }

    // getTeachersReviewsById(id: string, pageNumber: number = 0, pageSize: number = 10): Observable<Page<ReviewDto>> {
    //     return this.http.get<Page<ReviewDto>>("${environment.BASE_API_URL}teachers/${id}/reviews?pageNumber=${pageNumber}&pageSize=${pageSize}");
    // }

    reviews$ = (id: string, pageNumber: number = 0, pageSize: number = 5): Observable<Page<ReviewDto>> =>
        this.http.get<Page<ReviewDto>>(`${environment.BASE_API_URL}teachers/${id}/reviews?pageNumber=${pageNumber}&pageSize=${pageSize}`);


}
