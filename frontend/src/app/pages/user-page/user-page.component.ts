import {Component, OnInit} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {catchError, map, Observable, of, startWith} from "rxjs";
import {Page} from "../../models/Page";
import {SearchedReviewDto} from "../../models/SearchedReviewDto";
import {HttpErrorResponse} from "@angular/common/http";
import {UserResponseDto} from "../../models/UserResponseDto";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {UniversityService} from "../../services/university.service";
import {UserService} from "../../services/user.service";
import {AsyncPipe, NgIf, NgSwitch, NgSwitchCase} from "@angular/common";
import {ErrorComponent} from "../../components/error/error.component";
import {PaginationComponent} from "../../components/pagination/pagination.component";
import {SimpleReviewComponent} from "../../components/simple-review/simple-review.component";
import {TeacherSearchComponent} from "../../components/teacher-search/teacher-search.component";
import {InfoFieldComponent} from "../../components/SmallComponents/info-field/info-field.component";

@Component({
    standalone: true,
    selector: 'app-user-page',
    templateUrl: './user-page.component.html',
    imports: [
        HeaderComponent,
        AsyncPipe,
        ErrorComponent,
        NgIf,
        NgSwitchCase,
        PaginationComponent,
        RouterLink,
        SimpleReviewComponent,
        TeacherSearchComponent,
        NgSwitch,
        InfoFieldComponent
    ],
    styleUrl: './user-page.component.scss'
})
export class UserPageComponent implements OnInit {

    userReviews$!: Observable<{
        userReviewsState: string,
        reviewsPage?: Page<SearchedReviewDto>,
        error?: HttpErrorResponse
    }>
    userInfo$!: Observable<{ userInfoState: string, userInfo?: UserResponseDto, error?: HttpErrorResponse }>

    constructor(private readonly userService: UserService,
                protected readonly route: ActivatedRoute,
                private readonly universityService: UniversityService) {

    }

    ngOnInit(): void {
        this.loadPage();
        this.loadUser();
    }


    loadPage(pageNumber: number = 0): void {
        this.userReviews$ = this.userService.userReviews$(pageNumber).pipe(
            map((response: Page<SearchedReviewDto>) => {
                return ({userReviewsState: 'LOADED', reviewsPage: response})
            }),
            startWith({userReviewsState: 'LOADING'}),
            catchError(error => of({userReviewsState: 'ERROR', error: error}))
        )
    }

    loadUser(): void{
        this.userInfo$ = this.userService.user$().pipe(
            map((response: UserResponseDto) => {
                return ({userInfoState: 'LOADED', userInfo: response})
            }),
            startWith({userInfoState: 'LOADING'}),
            catchError(error => of({userInfoState: 'ERROR', error: error}))
        )
    }
}
