import {Component, OnInit} from '@angular/core';
import {catchError, map, Observable, of, startWith} from "rxjs";
import {Page} from "../../models/Page";
import {ReviewDto} from "../../models/ReviewDto";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ReviewService} from "../../services/review.service";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {AsyncPipe, DatePipe, NgClass, NgIf, NgStyle, NgSwitch, NgSwitchCase} from "@angular/common";
import {ErrorComponent} from "../error/error.component";
import {AuthService} from '../../services/auth.service';
import {NgbRating} from "@ng-bootstrap/ng-bootstrap";
import {PaginationComponent} from "../pagination/pagination.component";

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
        NgStyle,
        DatePipe,
        RouterLink,
        NgbRating,
        PaginationComponent
    ],
    templateUrl: './teacher-reviews.component.html',
    styleUrl: './teacher-reviews.component.scss'
})
export class TeacherReviewsComponent implements OnInit {
    reviewsState$!: Observable<{ reviewsState: string, reviewsPage?: Page<ReviewDto>, error?: HttpErrorResponse }>
    likesDislikes!: { likes: number, dislikes: number, isLiked: boolean, isDisliked: boolean }[]
    currentRange!: { pagesToTheLeft: boolean, pagesToTheRight: boolean, range: number[], currentPage: number }

    constructor(private readonly reviewsService: ReviewService,
                private readonly route: ActivatedRoute,
                readonly authService: AuthService,
                readonly http: HttpClient,
    ) {
    }

    loadPage(pageNumber: number = 0): void {
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

    ngOnInit(): void {
        this.loadPage();
    }

    handleLikeOrDislike(url: string, i: number, likeOrDislike: boolean) {
        this.http.post(url, '').subscribe({
            error: error => console.log(error)
        })
        const item = this.likesDislikes[i];

        if (likeOrDislike) {
            if (item.isLiked) {
                item.likes--;
                item.isLiked = false;
            } else {
                item.likes++;
                item.isLiked = true;

                if (item.isDisliked) {
                    item.dislikes--;
                    item.isDisliked = false;
                }
            }
        } else {
            if (item.isDisliked) {
                item.dislikes--;
                item.isDisliked = false;
            } else {
                item.dislikes++;
                item.isDisliked = true;

                if (item.isLiked) {
                    item.likes--;
                    item.isLiked = false;
                }
            }
        }
    }

}
