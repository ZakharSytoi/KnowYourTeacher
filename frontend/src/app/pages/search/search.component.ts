import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {SearchFormComponent} from "../../components/search-form/search-form.component";
import {SearchService} from "../../services/search.service";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {catchError, map, Observable, of, startWith} from "rxjs";
import {Page} from "../../models/Page";
import {SearchedReviewDto} from "../../models/SearchedReviewDto";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {SearchRequestDto} from "../../models/SearchRequestDto";
import {AsyncPipe, DatePipe, NgClass, NgIf, NgStyle, NgSwitch, NgSwitchCase} from "@angular/common";
import {AuthService} from "../../services/auth.service";
import {ErrorComponent} from "../../components/error/error.component";
import {UniversityService} from "../../services/university.service";
import {TeacherSearchComponent} from "../../components/teacher-search/teacher-search.component";
import {PaginationComponent} from "../../components/pagination/pagination.component";
import {SimpleReviewComponent} from "../../components/simple-review/simple-review.component";

declare function initRatings(): void;

@Component({
    selector: 'app-search',
    standalone: true,
    imports: [
        HeaderComponent,
        SearchFormComponent,
        NgIf,
        NgClass,
        NgStyle,
        NgSwitch,
        DatePipe,
        NgSwitchCase,
        AsyncPipe,
        ErrorComponent,
        RouterLink,
        TeacherSearchComponent,
        PaginationComponent,
        SimpleReviewComponent
    ],
    templateUrl: './search.component.html',
    styleUrl: './search.component.scss'
})
export class SearchComponent implements OnInit {

    search$!: Observable<{ searchState: string, reviewsPage?: Page<SearchedReviewDto>, error?: HttpErrorResponse }>
    likesDislikes!: { likes: number, dislikes: number, isLiked: boolean, isDisliked: boolean }[]
    currentSearchRequest!: SearchRequestDto;
    currentUniversityName!: string;

    constructor(private readonly searchService: SearchService,
                protected readonly route: ActivatedRoute,
                private readonly universityService: UniversityService,
                readonly authService: AuthService,
                private readonly http: HttpClient) {

    }

    constructSearchResultsText(): string {
        let result: string = 'Results on ';
        if (this.currentSearchRequest.teacherName === '' && this.currentSearchRequest.teacherSurname === '') {
            result = result.concat('<b>someone</b> ')
        } else result = result.concat((`<b>${this.currentSearchRequest.teacherName}</b> <b>${this.currentSearchRequest.teacherSurname}</b>`).trim());
        if (this.currentSearchRequest.subject === '') {
            result = result.concat(' who teaches <b>something</b>')
        } else result = result.concat(` who teaches <b>${this.currentSearchRequest.subject}</b>`)
        if (this.currentSearchRequest.universityId === '') {
            result = result.concat(' in <b>some university</b>')
        } else {
            result = result.concat(` in <b>${this.currentUniversityName}</b>`)
        }
        return result.concat(':');
    }

    params$: Observable<SearchRequestDto> = this.route.queryParams.pipe(
        map(params => {
            return {
                teacherName: params['teacherName'],
                teacherSurname: params['teacherSurname'],
                subject: params['subject'],
                universityId: params['universityId'],
                searchType: params['searchType']
            }
        })
    );

    ngOnInit(): void {
        this.route.queryParams.subscribe({
            next: params => {
                this.currentSearchRequest = {
                    teacherName: params['teacherName'],
                    teacherSurname: params['teacherSurname'],
                    subject: params['subject'],
                    universityId: params['universityId'],
                    searchType: params['searchType']
                }
                if (params['universityId'] !== '') {
                    this.universityService.getUniversity(params['universityId']).subscribe({next: uni => this.currentUniversityName = uni.name})
                }
                this.loadPage();
            }
        })
    }

    loadPage(pageNumber: number = 0): void {
        this.search$ = this.searchService.searchReviews$(this.currentSearchRequest, pageNumber).pipe(
            map((response: Page<SearchedReviewDto>) => {
                this.likesDislikes = response.content.map(review => ({
                    likes: review.likeCount,
                    dislikes: review.dislikeCount,
                    isLiked: review.isLiked,
                    isDisliked: review.isDisliked
                }));
                return ({searchState: 'LOADED', reviewsPage: response})
            }),
            startWith({searchState: 'LOADING'}),
            catchError(error => of({searchState: 'ERROR', error: error}))
        )
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

    ngAfterViewChecked(): void {
        initRatings();
    }

    isLoggedIn(): boolean {
        return this.authService.isLoggedIn();
    }
}
