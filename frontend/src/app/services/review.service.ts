import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../models/Page";
import {ReviewDto} from "../models/ReviewDto";

@Injectable({
    providedIn: 'root'
})
export class ReviewService {

    constructor(private readonly http: HttpClient) {
    }

    public getTeachersReviewsById(id: string, pageNumber: number = 0, pageSize: number = 10): Observable<Page<ReviewDto>> {
        return this.http.get<Page<ReviewDto>>("${environment.BASE_API_URL}teachers/${id}/reviews?pageNumber=${pageNumber}&pageSize=${pageSize}")
    }
}
