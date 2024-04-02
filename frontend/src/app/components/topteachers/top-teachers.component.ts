import {Component, OnInit} from '@angular/core';
import {AsyncPipe, NgFor, NgIf, NgSwitch, NgSwitchCase} from "@angular/common";


import {TeacherPreviewDto} from "../../models/TeacherPreviewDto";
import {RouterLink} from "@angular/router";
import {TeacherService} from "../../services/teacher.service";
import {TeacherPreviewComponent} from "../teacher-preview/teacher-preview.component";
import {ErrorComponent} from "../error/error.component";
import {catchError, map, Observable, of, startWith} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";

declare function initRatings(): void;

@Component({
    selector: 'app-topteachers',
    standalone: true,
    imports: [NgFor, RouterLink, TeacherPreviewComponent, AsyncPipe, ErrorComponent, NgIf, NgSwitchCase, NgSwitch],
    templateUrl: './top-teachers.component.html',
    styleUrl: './top-teachers.component.scss'
})
export class TopTeachersComponent implements OnInit {

    topTeachers$!: Observable<{ state: string, teachers?: TeacherPreviewDto[], error?: HttpErrorResponse }>

    constructor(
        private topTeacherService: TeacherService
    ) {
    }

    ngOnInit(): void {
        this.topTeachers$ = this.topTeacherService.topTeachers$().pipe(
            map((response: TeacherPreviewDto[]) => {
                return {state: 'LOADED', teachers: response}
            }),
            startWith({state: 'LOADING'}),
            catchError(error => of({state: 'ERROR', error: error}))
        );
    }


    ngAfterViewChecked(): void {
        initRatings();
    }

    // ngAfterViewInit(): void {
    //     initRatings();
    // }


}
