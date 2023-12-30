import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../models/Page";
import {ReviewDto} from "../models/ReviewDto";
import {environment} from "../../environments/environment";
import {ShortReviewDto} from '../models/ShortReviewDto';

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

    review$ = (teacherId: string): Observable<ShortReviewDto> =>
        this.http.get<ReviewDto>(`${environment.BASE_API_URL}teachers/${teacherId}/review`);

    postReview$ = (teacherId: string, review: ShortReviewDto): Observable<ShortReviewDto> =>
        this.http.post<ShortReviewDto>(`${environment.BASE_API_URL}teachers/${teacherId}/review`, review)

    deleteReview$ = (teacherId: string): Observable<any> =>
        this.http.delete(`${environment.BASE_API_URL}teachers/${teacherId}/review`)

}
