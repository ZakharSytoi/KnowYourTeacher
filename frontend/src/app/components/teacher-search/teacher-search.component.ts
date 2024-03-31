import {Component, Input, OnChanges, OnDestroy, OnInit} from '@angular/core';
import {AsyncPipe, NgForOf, NgIf, NgSwitch, NgSwitchCase} from "@angular/common";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {catchError, map, Observable, of, startWith, Subscription} from "rxjs";
import {Page} from "../../models/Page";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {TeacherPreviewDto} from "../../models/TeacherPreviewDto";
import {SearchService} from "../../services/search.service";
import {UniversityService} from "../../services/university.service";
import {AuthService} from "../../services/auth.service";
import {SearchRequestDto} from "../../models/SearchRequestDto";
import {ErrorComponent} from "../error/error.component";

@Component({
    selector: 'app-teacher-search',
    templateUrl: './teacher-search.component.html',
    standalone: true,
    imports: [
        NgForOf,
        RouterLink,
        AsyncPipe,
        NgSwitch,
        NgIf,
        NgSwitchCase,
        ErrorComponent
    ],
    styleUrl: './teacher-search.component.scss'
})
export class TeacherSearchComponent implements OnChanges {
    @Input() params: SearchRequestDto = {
        searchType: "", subject: "", teacherName: "", teacherSurname: "", universityId: ""

    };
    search$!: Observable<{ searchState: string, teachersPage?: Page<TeacherPreviewDto>, error?: HttpErrorResponse }>
    currentRange!: { pagesToTheLeft: boolean, pagesToTheRight: boolean, range: number[], currentPage: number }

    constructor(private readonly searchService: SearchService,
                protected readonly route: ActivatedRoute,
                private readonly universityService: UniversityService,
                readonly authService: AuthService,
                private readonly http: HttpClient) {

    }

    loadPage(pageNumber: number = 0){
        this.search$ = this.searchService.searchTeachers$(this.params, pageNumber).pipe(
            map((response: Page<TeacherPreviewDto>) => {
                this.currentRange = this.getCurrentRange(response.totalPages, response.number);

                return ({searchState: 'LOADED', teachersPage: response})
            }),
            startWith({searchState: 'LOADING'}),
            catchError(error => of({searchState: 'ERROR', error: error}))
        )
    }

    ngOnChanges(): void {
        this.loadPage()
    }

    range = (start: number, stop: number) =>
        Array.from({length: (stop - start)}, (_, i) => start + i);

    getCurrentRange(totalPages: number, currentPage: number) {
        if (totalPages <= 10) {
            return {
                pagesToTheLeft: false,
                pagesToTheRight: false,
                range: this.range(0, totalPages),
                currentPage: currentPage
            }
        } else {
            let right = 0;
            let left = 0;
            let pagesToTheLeft = true;
            let pagesToTheRight = true;
            if (totalPages >= 10) {
                right = currentPage + 5
                left = currentPage - 4;
                if (left <= 0) {
                    right = currentPage + 5 - left;
                    left = 0;
                    pagesToTheLeft = false;
                } else if (right >= totalPages) {
                    left = currentPage - right + totalPages - 4;
                    right = totalPages
                    pagesToTheRight = false;
                }
            }
            return {
                pagesToTheLeft: pagesToTheLeft,
                pagesToTheRight: pagesToTheRight,
                range: this.range(left, right),
                currentPage: currentPage
            }
        }
    }

    protected readonly JSON = JSON;
}
