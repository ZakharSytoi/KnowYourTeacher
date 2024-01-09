import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, RouterModule} from "@angular/router";
import {NgbRating} from "@ng-bootstrap/ng-bootstrap";
import {TeacherService} from "../../services/teacher.service";
import {TeacherCardDto} from "../../models/TeacherCardDto";
import {TeacherReviewsComponent} from "../teacher-reviews/teacher-reviews.component";
import {catchError, map, Observable, of, startWith} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {CommonModule, NgClass, NgIf, NgSwitch} from "@angular/common";
import {ErrorComponent} from "../error/error.component";
import {ShortReviewDto} from "../../models/ShortReviewDto";
import {ReviewService} from "../../services/review.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";

declare function initRatings(): void;

@Component({
    selector: "app-teacher",
    standalone: true,
    imports: [RouterModule, NgbRating, TeacherReviewsComponent, NgIf, NgClass, NgSwitch, CommonModule, ErrorComponent, ReactiveFormsModule],
    templateUrl: "./teacher.component.html",
    styleUrl: "./teacher.component.scss",
})
export class TeacherComponent implements OnInit {
    teacherId: string | null = "";
    teacherCardState$!: Observable<{
        teacherCardState: string;
        teacherCardData?: TeacherCardDto;
        error?: HttpErrorResponse;
    }>;
    inputPanelState$!: Observable<{
        currentInputPanelState: string;
        reviewData?: ShortReviewDto;
        error?: HttpErrorResponse;
    }>;
    selected = 0;
    reviewForm: FormGroup;

    constructor(
        private authService: AuthService,
        private route: ActivatedRoute,
        private teacherService: TeacherService,
        private readonly reviewsService: ReviewService
    ) {
        this.reviewForm = new FormGroup({
            subject: new FormControl("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
            reviewText: new FormControl("", [Validators.required, Validators.minLength(3), Validators.maxLength(325)]),
            score: new FormControl(0, [Validators.required, Validators.min(1), Validators.max(5)]),
        });
    }

    ngOnInit(): void {
        this.teacherId = this.route.snapshot.paramMap.get("id");
        if (this.teacherId) {
            this.teacherCardState$ = this.teacherService.teacher$(this.teacherId).pipe(
                map((response: TeacherCardDto) => {
                    console.log(response);
                    return {
                        teacherCardState: "LOADED",
                        teacherCardData: response,
                    };
                }),
                startWith({teacherCardState: "LOADING"}),
                catchError((error: HttpErrorResponse) => of({teacherCardState: "ERROR", error: error}))
            );
            this.inputPanelState$ = this.reviewsService.review$(this.teacherId).pipe(
                map(review => ({
                    currentInputPanelState: "LOADED",
                    reviewData: review,
                })),
                catchError((error: HttpErrorResponse) => {
                    if (error.status === 404) return of({currentInputPanelState: "EMPTY"});
                    else
                        return of({
                            currentInputPanelState: "ERROR",
                            error: error,
                        });
                })
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

    handleEditButtonClick(): void {
        this.inputPanelState$ = this.inputPanelState$.pipe(
            map(currentState => {
                this.selected = currentState.reviewData!.score;
                this.reviewForm.controls["score"].setValue(currentState.reviewData!.score)
                this.reviewForm.controls["subject"].setValue(currentState.reviewData!.subjectName)
                this.reviewForm.controls["reviewText"].setValue(currentState.reviewData!.reviewText)
                return {
                    currentInputPanelState: "EDIT",
                    reviewData: currentState.reviewData,
                };
            })
        );
    }

    handleDeleteButtonClick() {
        this.reviewsService.deleteReview$(this.teacherId!).subscribe({
            next:()=>{
                window.location.reload();
            },
            error: error=>{
                this.inputPanelState$ = this.inputPanelState$.pipe(
                    map(currentState => {
                        return {
                            currentInputPanelState: "ERROR",
                            error: error,
                        };
                    })
                )
            }
        })
    }

    onSubmit() {
        if (this.reviewForm.valid) {
            let id = this.route.snapshot.paramMap.get("id");
            if (id) {
                this.reviewsService
                    .postReview$(id, {
                        score: this.reviewForm.controls["score"].value,
                        subjectName: this.reviewForm.controls["subject"].value,
                        reviewText:
                        this.reviewForm.controls["reviewText"].value,
                    })
                    .subscribe({
                        next: ()=>window.location.reload(),
                        error: error => console.log(error),
                    });
            }
        }
    }

    getSubjectErrorStatus(): boolean {
        return (
            this.reviewForm.controls["subject"].invalid &&
            (this.reviewForm.controls["subject"].touched ||
                this.reviewForm.controls["subject"].dirty)
        );
    }

    getReviewTextErrorStatus(): boolean {
        return (
            this.reviewForm.controls["reviewText"].invalid &&
            (this.reviewForm.controls["reviewText"].touched ||
                this.reviewForm.controls["reviewText"].dirty)
        );
    }


    isLoggedIn(): boolean {
        return this.authService.isLoggedIn();
    }

    ngAfterViewChecked(): void {
        initRatings();
    }
}
