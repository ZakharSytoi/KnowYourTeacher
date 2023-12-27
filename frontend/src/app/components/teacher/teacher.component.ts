import {AfterViewInit, Component, OnInit} from "@angular/core";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, RouterModule} from '@angular/router';
import {NgbRating} from "@ng-bootstrap/ng-bootstrap";
import {TeacherService} from "../../services/teacher.service";
import {TeacherCardDto} from "../../models/TeacherCardDto";
import {Page} from "../../models/Page";
import {ReviewDto} from "../../models/ReviewDto";
import {TeacherReviewsComponent} from "../teacher-reviews/teacher-reviews.component";

declare function initRatings(): void;

@Component({
    selector: "app-teacher",
    standalone: true,
    imports: [RouterModule, NgbRating, TeacherReviewsComponent],
    templateUrl: "./teacher.component.html",
    styleUrl: "./teacher.component.scss",
})
export class TeacherComponent implements OnInit {
    teacherId: string = '';
    currentTeacherCard!: TeacherCardDto;
    currentReviewsPage!: Page<ReviewDto>;

    constructor(private authService: AuthService,
                private route: ActivatedRoute,
                private teacherService: TeacherService
    ) {
    }

    isLoggedIn(): boolean {
        return this.authService.isLoggedIn();
    }

    ngAfterViewChecked(): void {
        initRatings();
    }

    ngOnInit(): void {
        let id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.teacherId = id;
            this.teacherService.getTeacherById(this.teacherId).subscribe({
                next: (teacherCard) => {
                    this.currentTeacherCard = teacherCard;
                }
            });
            // this.teacherService.getTeachersReviewsById(id).subscribe({
            //     next:
            // })
        }

    }
}
