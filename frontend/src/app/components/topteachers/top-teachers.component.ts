import {AfterViewChecked, AfterViewInit, Component} from '@angular/core';
import {NgStyle, NgClass, NgFor} from "@angular/common";


import {TeacherPreviewDto} from "../../models/TeacherPreviewDto";
import {TopTeacherService} from "../../services/top-teacher.service";
import {RouterLink} from "@angular/router";
declare function initRatings():void;
@Component({
    selector: 'app-topteachers',
    standalone: true,
    imports: [NgFor, RouterLink],
    templateUrl: './top-teachers.component.html',
    styleUrl: './top-teachers.component.scss'
})
export class TopTeachersComponent{

    topTeachersList: TeacherPreviewDto[];

    constructor(
        private topTeacherService: TopTeacherService
    ) {
        this.topTeachersList = [];
        topTeacherService.getTopTeachersList().subscribe({
            next: (topTeachersList) => {
                this.topTeachersList = topTeachersList;
            }
        })
    }
    ngAfterViewChecked(): void{
        initRatings();
    }
    // ngAfterViewInit(): void {
    //     initRatings();
    // }


}
