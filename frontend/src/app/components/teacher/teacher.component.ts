import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, RouterModule} from '@angular/router';
import {NgbRating} from "@ng-bootstrap/ng-bootstrap";
import {TeacherService} from "../../services/teacher.service";
import {TeacherCardDto} from "../../models/TeacherCardDto";
import {TeacherReviewsComponent} from "../teacher-reviews/teacher-reviews.component";
import {catchError, map, Observable, of, startWith} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {NgIf, NgSwitch} from "@angular/common";
import { CommonModule } from '@angular/common';
import { json } from "stream/consumers";
declare function initRatings(): void;

@Component({
    selector: "app-teacher",
    standalone: true,
    imports: [RouterModule, NgbRating, TeacherReviewsComponent, NgIf, NgSwitch,CommonModule],
    templateUrl: "./teacher.component.html",
    styleUrl: "./teacher.component.scss",
})
export class TeacherComponent implements OnInit {
    teacherId: string = '';
    teacherCardState$!: Observable<{ teacherCardState: string, teacherCardData?: TeacherCardDto, error?: HttpErrorResponse }>

    constructor(private authService: AuthService,
                private route: ActivatedRoute,
                private teacherService: TeacherService
    ) {
    }

    ngOnInit(): void {
        let id = this.route.snapshot.paramMap.get('id')
        if (id) {
            this.teacherCardState$ = this.teacherService.teacher$(id).pipe(
                map((response: TeacherCardDto) => {
                    console.log(response)
                        return ({teacherCardState: 'LOADED', teacherCardData: response})
                    }
                ),
                startWith({teacherCardState: 'LOADING'}),
                catchError((error: HttpErrorResponse) => of({teacherCardState: 'ERROR', error: error}))
            );
        }


        // let id = this.route.snapshot.paramMap.get('id');
        // if (id) {
        //     this.teacherId = id;
        //     this.teacherService.getTeacherById(this.teacherId).subscribe({
        //         next: (teacherCard) => {
        //             this.currentTeacherCard = teacherCard;
        //         }
        //     });
        //     // this.teacherService.getTeachersReviewsById(id).subscribe({
        //     //     next:
        //     // })
        // }

    }

    isLoggedIn(): boolean {
        return this.authService.isLoggedIn();
    }

    ngAfterViewChecked(): void {
        initRatings();
    }
}
