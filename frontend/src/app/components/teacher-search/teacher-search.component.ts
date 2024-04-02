import {Component, Input, OnChanges} from '@angular/core';
import {AsyncPipe, NgClass, NgForOf, NgIf, NgStyle, NgSwitch, NgSwitchCase} from "@angular/common";
import {RouterLink} from "@angular/router";
import {catchError, map, Observable, of, startWith} from "rxjs";
import {Page} from "../../models/Page";
import {HttpErrorResponse} from "@angular/common/http";
import {TeacherPreviewDto} from "../../models/TeacherPreviewDto";
import {SearchService} from "../../services/search.service";
import {SearchRequestDto} from "../../models/SearchRequestDto";
import {ErrorComponent} from "../error/error.component";
import {TeacherPreviewComponent} from "../teacher-preview/teacher-preview.component";
import {PaginationComponent} from "../pagination/pagination.component";

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
        ErrorComponent,
        NgClass,
        NgStyle,
        TeacherPreviewComponent,
        PaginationComponent
    ],
    styleUrl: './teacher-search.component.scss'
})
export class TeacherSearchComponent implements OnChanges {
    @Input() params: SearchRequestDto = {
        searchType: "", subject: "", teacherName: "", teacherSurname: "", universityId: ""

    };
    search$!: Observable<{ searchState: string, teachersPage?: Page<TeacherPreviewDto>, error?: HttpErrorResponse }>

    constructor(private readonly searchService: SearchService) {}

    loadPage(pageNumber: number = 0){
        this.search$ = this.searchService.searchTeachers$(this.params, pageNumber).pipe(
            map((response: Page<TeacherPreviewDto>) => {
                return ({searchState: 'LOADED', teachersPage: response})
            }),
            startWith({searchState: 'LOADING'}),
            catchError(error => of({searchState: 'ERROR', error: error}))
        )
    }

    ngOnChanges(): void {
        this.loadPage()
    }


}
