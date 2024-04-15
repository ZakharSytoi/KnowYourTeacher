import {Component, OnInit} from '@angular/core';
import {catchError, map, Observable, of, startWith} from "rxjs";
import {Page} from "../../models/Page";
import {ReviewDto} from "../../models/ReviewDto";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ReviewService} from "../../services/review.service";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {AsyncPipe, DatePipe, NgClass, NgIf, NgStyle, NgSwitch, NgSwitchCase} from "@angular/common";
import {ErrorComponent} from "../error/error.component";
import {NgbRating} from "@ng-bootstrap/ng-bootstrap";
import {PaginationComponent} from "../pagination/pagination.component";
import {SimpleReviewComponent} from "../simple-review/simple-review.component";

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
        PaginationComponent,
        SimpleReviewComponent
    ],
    templateUrl: './teacher-reviews.component.html',
    styleUrl: './teacher-reviews.component.scss'
})
export class TeacherReviewsComponent implements OnInit {
    reviewsState$!: Observable<{ reviewsState: string, reviewsPage?: Page<ReviewDto>, error?: HttpErrorResponse }>

    constructor(private readonly reviewsService: ReviewService,
                private readonly route: ActivatedRoute,
                readonly http: HttpClient,
    ) {
    }

    loadPage(pageNumber: number = 0): void {
        let id = this.route.snapshot.paramMap.get('id')
        if (id) {
            this.reviewsState$ = this.reviewsService.reviews$(id, pageNumber).pipe(
                map((response: Page<ReviewDto>) => {
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
}
