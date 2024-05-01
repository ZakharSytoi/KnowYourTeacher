import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
import {UserService} from "../../services/user.service";
import {UniversityService} from "../../services/university.service";
import {Router} from "@angular/router";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {UniversityDto} from "../../models/UniversityDto";
import {HttpErrorResponse} from "@angular/common/http";
import {NgClass, NgForOf} from "@angular/common";
import {UserResponseDto} from "../../models/UserResponseDto";

@Component({
    standalone: true,
    selector: 'app-user-update-form',
    templateUrl: './user-update-form.component.html',
    imports: [
        FormsModule,
        NgForOf,
        ReactiveFormsModule,
        NgClass
    ],
    styleUrl: './user-update-form.component.scss'
})
export class UserUpdateFormComponent implements OnChanges {

    @Input() userInfo?: UserResponseDto;
    @Output() hideFormEvent = new EventEmitter();

    userData: FormGroup;
    universityList: UniversityDto[];

    existingEmailError: boolean = false;
    existingNicknameError: boolean = false;
    unknownProfileUpdateError: boolean = false;

    successfulUpdated:boolean = false;

    constructor(private readonly userService: UserService,
                private readonly universityService: UniversityService,
                private router: Router) {
        this.universityList = [];
        universityService.getUniversitiesList().subscribe({
            next: universitiesList => {
                this.universityList = universitiesList;
            },
        });

        this.userData = new FormGroup({
            nickname: new FormControl("", [
                Validators.required,
                Validators.minLength(6),
                Validators.maxLength(30),
            ]),
            university: new FormControl("", [
                Validators.required,
            ]),
            fieldOfStudies: new FormControl("", [
                Validators.required,
                Validators.minLength(3),
                Validators.maxLength(50),
            ])
        });
    }

    ngOnChanges(): void {
        if (this.userInfo) this.userData.setValue({
                nickname: this.userInfo?.nickname,
                university: this.userInfo?.university.id,
                fieldOfStudies: this.userInfo?.fieldOfStudies
            }
        )
    }


    onSubmit() {
        if (this.userData.valid) {
            console.log({
                nickname: this.userData.controls["nickname"].value,
                universityId: this.userData.controls["university"].value,
                fieldOfStudies: this.userData.controls["fieldOfStudies"].value,
            })
            this.userService
                .updateUserInfo(
                    {
                        nickname: this.userData.controls["nickname"].value,
                        universityId: this.userData.controls["university"].value,
                        fieldOfStudies: this.userData.controls["fieldOfStudies"].value,
                    }
                )
                .subscribe({
                    next: () => {
                        this.successfulUpdated = true;
                        setTimeout(()=>{
                            this.successfulUpdated = false;
                            window.location.reload()
                        }, 1000)
                    },
                    error: err => {
                        this.handleUnsuccessfulLogin(err);
                    },
                });
        } else {
            console.log("invalid data");
        }
    }

    handleUnsuccessfulLogin(error: HttpErrorResponse): any {
        const errorMessage: string = JSON.stringify(error.error);
        console.log(errorMessage);
        if (errorMessage.includes(" nickname ")) {
            this.existingNicknameError = true;
            this.unknownProfileUpdateError = false;
            console.log("nickname error");
        } else {
            this.unknownProfileUpdateError = true;
            this.existingNicknameError = false;
            console.log("unknown error");
        }
    }

    getNicknameErrorStatus(): boolean {
        return (
            this.userData.controls["nickname"].invalid &&
            (this.userData.controls["nickname"].touched ||
                this.userData.controls["nickname"].dirty)
        );
    }

    getUniversityErrorStatus(): boolean {
        return this.userData.controls["university"].value === "Choose University";
    }

    getFieldOfStudiesErrorStatus(): boolean {
        return (
            this.userData.controls["fieldOfStudies"].invalid &&
            (this.userData.controls["fieldOfStudies"].touched ||
                this.userData.controls["fieldOfStudies"].dirty)
        );
    }
}
