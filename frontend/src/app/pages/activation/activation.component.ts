import {Component, OnInit} from '@angular/core';
import {catchError, map, Observable, of, startWith} from "rxjs";
import {TeacherCardDto} from "../../models/TeacherCardDto";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {TeacherService} from "../../services/teacher.service";
import {ReviewService} from "../../services/review.service";
import {AsyncPipe, NgIf, NgSwitch, NgSwitchCase} from "@angular/common";
import {TeacherPreviewDto} from "../../models/TeacherPreviewDto";
import {environment} from "../../../environments/environment";
import {ErrorComponent} from "../../components/error/error.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgbRating} from "@ng-bootstrap/ng-bootstrap";
import {TeacherReviewsComponent} from "../../components/teacher-reviews/teacher-reviews.component";
import {ShortHeaderComponent} from "../../components/short-header/short-header.component";

@Component({
    selector: 'app-activation',
    standalone: true,
    imports: [
        AsyncPipe,
        NgIf,
        ErrorComponent,
        FormsModule,
        NgSwitchCase,
        NgbRating,
        ReactiveFormsModule,
        RouterLink,
        TeacherReviewsComponent,
        NgSwitch,
        ShortHeaderComponent
    ],
    templateUrl: './activation.component.html',
    styleUrl: './activation.component.scss'
})
export class ActivationComponent implements OnInit {
    userId: string | null = "";
    activationState$!: Observable<{
        teacherCardState: string;
        error?: HttpErrorResponse;
    }>;
    constructor(
        private route: ActivatedRoute,
        private readonly http: HttpClient
    ) {
    }

    ngOnInit(): void {
        this.userId = this.route.snapshot.paramMap.get("id");
        console.log(this.userId)
        if (this.userId) {
            this.activationState$ = this.http.get(`${environment.BASE_API_URL}activate/${this.userId}`).pipe(
                map(() => {
                    return {
                        teacherCardState: "LOADED",
                    };
                }),
                startWith({teacherCardState: "LOADING"}),
                catchError((error: HttpErrorResponse) => of({teacherCardState: "ERROR", error: error}))
            );
        }
    }


}
