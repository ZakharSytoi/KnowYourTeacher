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
        RouterLink
    ],
    templateUrl: './search.component.html',
    styleUrl: './search.component.scss'
})
export class SearchComponent implements OnInit {

    search$!: Observable<{searchState: string, reviewsPage?:Page<SearchedReviewDto>, error?:HttpErrorResponse}>
    likesDislikes!: { likes: number, dislikes: number, isLiked: boolean, isDisliked: boolean }[]

    constructor(private readonly searchService: SearchService,
                private readonly route: ActivatedRoute,
                readonly authService: AuthService,
                private readonly http: HttpClient) {

    }
    getSearchRequestFromCurrentRoot():SearchRequestDto{
        let searchRequest = new SearchRequestDto()
        this.route.queryParams.subscribe({
            next: params=>{
                searchRequest.teacherName = params['teacherName']
                searchRequest.teacherSurname = params['teacherSurname']
                searchRequest.subject = params['subject']
                searchRequest.universityId = params['universityId']
            }
        })
        return searchRequest;
    }
    ngOnInit(): void {
        this.route.queryParams.subscribe({
            next: params=>{
                this.search$ = this.searchService.search$({
                    teacherName: params['teacherName'],
                    teacherSurname: params['teacherSurname'],
                    subject: params['subject'],
                    universityId: params['universityId']
                }).pipe(
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
                    catchError(error => of({searchState: 'ERROR', error: error})))
            }
        })
        // this.search$ = this.searchService.search$(this.getSearchRequestFromCurrentRoot()).pipe(
        //     map((response: Page<SearchedReviewDto>) => {
        //         this.likesDislikes = response.content.map(review => ({
        //             likes: review.likeCount,
        //             dislikes: review.dislikeCount,
        //             isLiked: review.isLiked,
        //             isDisliked: review.isDisliked
        //         }));
        //         return ({searchState: 'LOADED', reviewsPage: response})}),
        //     startWith({searchState: 'LOADING'}),
        //     catchError(error=> of({searchState: 'ERROR', error: error}))
        // )

    }
    goToPage(pageNumber: number): void {
        this.search$ = this.searchService.search$(this.getSearchRequestFromCurrentRoot(), pageNumber).pipe(
            map((response: Page<SearchedReviewDto>) => {
                this.likesDislikes = response.content.map(review => ({
                    likes: review.likeCount,
                    dislikes: review.dislikeCount,
                    isLiked: review.isLiked,
                    isDisliked: review.isDisliked
                }));
                return ({searchState: 'LOADED', reviewsPage: response})}),
            startWith({searchState: 'LOADING'}),
            catchError(error=> of({searchState: 'ERROR', error: error}))
        )
    }
    goNextOrGoPrev(direction: boolean, actualPageNumber: number) {
        if (direction) this.goToPage(actualPageNumber + 1)
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
