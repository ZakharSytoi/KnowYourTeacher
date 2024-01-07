import {Component, ElementRef, ViewChild} from "@angular/core";
import {ImageCropperModule} from "ngx-image-cropper";
import {HeaderComponent} from "../../components/header/header.component";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {UniversityService} from "../../services/university.service";
import {UniversityDto} from "../../models/UniversityDto";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {TeacherService} from "../../services/teacher.service";
import {response} from "express";
import {NgbRating} from "@ng-bootstrap/ng-bootstrap";
import {ReviewService} from "../../services/review.service";
import {HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";


@Component({
    selector: "app-teacher-creation",
    standalone: true,
    imports: [ImageCropperModule, HeaderComponent, NgIf, NgForOf, ReactiveFormsModule, NgClass, NgbRating],
    templateUrl: "./teacher-creation.component.html",
    styleUrl: "./teacher-creation.component.scss",
})
export class TeacherCreationComponent {
    @ViewChild("input") inputRef!: ElementRef;
    @ViewChild("input") submitRef!: ElementRef;
    teacherData: FormGroup;
    reviewForm: FormGroup;
    selected = 0;
    image!: File;
    imagePreview!: any;
    universityList: UniversityDto[];

    constructor(
        private universityService: UniversityService,
        private teacherService: TeacherService,
        private readonly reviewsService: ReviewService,
        private router: Router
    ) {
        this.universityList = [];
        universityService.getUniversitiesList().subscribe({
            next: universitiesList => {
                this.universityList = universitiesList;
            },
        });

        this.teacherData = new FormGroup({
            name: new FormControl("", [Validators.minLength(2), Validators.maxLength(50), Validators.required]),
            surname: new FormControl("", [Validators.minLength(2), Validators.maxLength(50), Validators.required]),
            universityId: new FormControl("University", [Validators.required]),
            photo: new FormControl(null, [Validators.required]),
        });

        this.reviewForm = new FormGroup({
            subject: new FormControl("", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
            reviewText: new FormControl("", [Validators.required, Validators.minLength(3), Validators.maxLength(325)]),
            score: new FormControl(0, [Validators.required, Validators.min(1), Validators.max(5)]),
        });
    }

    onFileUpload(event: any) {
        const file = event.target.files[0];
        this.image = file;

        const reader = new FileReader();
        reader.onload = () => {
            this.imagePreview = reader.result;
        };
        reader.readAsDataURL(file);

        this.teacherData.patchValue({photo: file});
    }

    onSubmit() {
        if (this.teacherData.valid && this.reviewForm.valid) {
            this.teacherService
                .create$(
                    {
                        name: this.teacherData.controls["name"].value,
                        surname: this.teacherData.controls["surname"].value,
                        universityId: this.teacherData.controls["universityId"].value,
                    },
                    this.teacherData.controls["photo"].value
                )
                .subscribe({
                    next: (response: HttpResponse<any>) => {
                        if (response.status === 201) {
                            // @ts-ignore
                            const locationHeader = response.headers.get('Location').split("/");
                            let id = locationHeader[locationHeader.length - 1];
                            if (id) {
                                this.reviewsService
                                    .postReview$(id, {
                                        score: this.reviewForm.controls["score"].value,
                                        subjectName: this.reviewForm.controls["subject"].value,
                                        reviewText:
                                        this.reviewForm.controls["reviewText"].value,
                                    })
                                    .subscribe({
                                        next: () => this.router.navigate([`/teacher/${id}`]),
                                        error: error => console.log(error),
                                    });
                            }
                        }
                    },
                });
        }
    }

    triggerUpload() {
        this.inputRef.nativeElement.click();
    }

    getUniversityErrorStatus(): boolean {
        if (this.teacherData.controls["universityId"].value === "University") {
            return true;
        }
        return false;
    }

    getNameErrorStatus(): boolean {
        return this.teacherData.controls["name"].invalid && (this.teacherData.controls["name"].touched || this.teacherData.controls["name"].dirty);
    }

    getSurnameErrorStatus(): boolean {
        return this.teacherData.controls["surname"].invalid && (this.teacherData.controls["surname"].touched || this.teacherData.controls["surname"].dirty);
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
}
