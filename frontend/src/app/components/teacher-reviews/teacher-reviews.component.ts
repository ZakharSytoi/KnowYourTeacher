import {Component, OnInit} from '@angular/core';
import {catchError, map, Observable, of, startWith} from "rxjs";
import {Page} from "../../models/Page";
import {ReviewDto} from "../../models/ReviewDto";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ReviewService} from "../../services/review.service";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {AsyncPipe, DatePipe, NgClass, NgIf, NgSwitch, NgSwitchCase} from "@angular/common";
import {ErrorComponent} from "../error/error.component";
import {AuthService} from '../../services/auth.service';
import {NgbRating} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-teacher-reviews',
    standalone: true,
    imports: [
        NgIf,
        AsyncPipe,
        NgSwitch,
        NgSwitchCase,
        ErrorComponent,
        NgClass,
        DatePipe,
        RouterLink,
        NgbRating
    ],
    templateUrl: './teacher-reviews.component.html',
    styleUrl: './teacher-reviews.component.scss'
})
export class TeacherReviewsComponent implements OnInit {
    reviewsState$!: Observable<{ reviewsState: string, reviewsPage?: Page<ReviewDto>, error?: HttpErrorResponse }>
    likesDislikes!: { likes: number, dislikes: number, isLiked: boolean, isDisliked: boolean }[]

    constructor(private readonly reviewsService: ReviewService,
                private readonly route: ActivatedRoute,
                readonly authService: AuthService,
                readonly http: HttpClient,
    ) {
    }

    ngOnInit(): void {
        let id = this.route.snapshot.paramMap.get('id')
        if (id) {
            this.reviewsState$ = this.reviewsService.reviews$(id).pipe(
                map((response: Page<ReviewDto>) => {
                    this.likesDislikes = response.content.map(review => ({
                        likes: review.likeCount,
                        dislikes: review.dislikeCount,
                        isLiked: review.isLiked,
                        isDisliked: review.isDisliked
                    }));
                    return ({reviewsState: 'LOADED', reviewsPage: response})
                }),
                startWith({reviewsState: 'LOADING'}),
                catchError((error: HttpErrorResponse) => of({reviewsState: 'ERROR', error: error}))
            )
        } else this.reviewsState$ = of({reviewsState: 'ERROR'})
    }

    goToPage(pageNumber: number): void {
        let id = this.route.snapshot.paramMap.get('id')
        if (id) {
            this.reviewsState$ = this.reviewsService.reviews$(id, pageNumber).pipe(
                map((response: Page<ReviewDto>) => {
                    this.likesDislikes = response.content.map(review => ({
                        likes: review.likeCount,
                        dislikes: review.dislikeCount,
                        isLiked: review.isLiked,
                        isDisliked: review.isDisliked
                    }));
                    return ({reviewsState: 'LOADED', reviewsPage: response})
                }),
                startWith({reviewsState: 'LOADING'}),
                catchError((error: HttpErrorResponse) => of({reviewsState: 'LOADED', error: error}))
            )
        } else this.reviewsState$ = of({reviewsState: 'ERROR'})
    }

    goNextOrGoPrev(direction: boolean, actualPageNumber: number) {
        if (direction) this.goToPage(actualPageNumber + 1)
    }

    handleLikeOrDislike(url: string, i: number, likeOrDislike: boolean) {
        this.http.post(url, '').subscribe({
            error: error => console.log(error)
        })
        console.log(url)
        if (likeOrDislike) {
            if (this.likesDislikes[i].isLiked) {
                this.likesDislikes[i].likes = this.likesDislikes[i].likes - 1;
                this.likesDislikes[i].isLiked = false;
            } else if (this.likesDislikes[i].isDisliked) {
                this.likesDislikes[i].dislikes = this.likesDislikes[i].dislikes - 1;
                this.likesDislikes[i].likes = this.likesDislikes[i].likes + 1;
                this.likesDislikes[i].isLiked = true;
                this.likesDislikes[i].isDisliked = false;
            } else {
                this.likesDislikes[i].likes = this.likesDislikes[i].likes + 1;
                this.likesDislikes[i].isLiked = true;
            }
        } else {
            if (this.likesDislikes[i].isDisliked) {
                this.likesDislikes[i].dislikes = this.likesDislikes[i].dislikes - 1;
                this.likesDislikes[i].isDisliked = false;
            } else if (this.likesDislikes[i].isLiked) {
                this.likesDislikes[i].likes = this.likesDislikes[i].likes - 1;
                this.likesDislikes[i].dislikes = this.likesDislikes[i].dislikes + 1;
                this.likesDislikes[i].isLiked = false;
                this.likesDislikes[i].isDisliked = true;
            } else {
                this.likesDislikes[i].dislikes = this.likesDislikes[i].dislikes + 1;
                this.likesDislikes[i].isDisliked = true;
            }
        }
    }

}
