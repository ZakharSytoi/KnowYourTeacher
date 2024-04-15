import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {LikeOnReviewResponseDto} from "../models/likedislike/LikeOnReviewResponseDto";
import {environment} from "../../environments/environment";
import {DislikeOnReviewResponseDto} from "../models/likedislike/DislikeOnReviewResponseDto";

@Injectable({
  providedIn: 'root'
})
export class LikeDislikeService {

  constructor(private readonly http: HttpClient) { }

  like$ = (reviewId: number): Observable<LikeOnReviewResponseDto> => {
    return this.http.post<LikeOnReviewResponseDto>(`${environment.BASE_API_URL_V2}reviews/${reviewId}/like`,'')
  }
  unLike$ = (reviewId: number): Observable<LikeOnReviewResponseDto> => {
    return this.http.delete<LikeOnReviewResponseDto>(`${environment.BASE_API_URL_V2}reviews/${reviewId}/unlike`)
  }
  dislike$ = (reviewId: number): Observable<DislikeOnReviewResponseDto> => {
    return this.http.post<DislikeOnReviewResponseDto>(`${environment.BASE_API_URL_V2}reviews/${reviewId}/dislike`,'')
  }
  unDislike$ = (reviewId: number): Observable<DislikeOnReviewResponseDto> => {
    return this.http.delete<DislikeOnReviewResponseDto>(`${environment.BASE_API_URL_V2}reviews/${reviewId}/undislike`)
  }

}
