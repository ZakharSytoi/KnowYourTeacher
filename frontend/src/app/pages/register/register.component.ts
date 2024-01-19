import { Component } from "@angular/core";
import { ShortHeaderComponent } from "../../components/short-header/short-header.component";
import { NgStyle, NgClass, NgFor } from "@angular/common";
import { RouterModule } from "@angular/router";
import { Router } from "@angular/router";
import {
    FormGroup,
    FormControl,
    ReactiveFormsModule,
    Validators,
} from "@angular/forms";
import { UserRegistrationRequestDto } from "../../models/UserRegistrationRequestDto";
import { AuthService } from "../../services/auth.service";
import { UniversityDto } from "../../models/UniversityDto";
import { UniversityService } from "../../services/university.service";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
    selector: "app-register",
    standalone: true,
    imports: [
        ShortHeaderComponent,
        NgStyle,
        NgClass,
        NgFor,
        ReactiveFormsModule,
        RouterModule,
    ],
    templateUrl: "./register.component.html",
    styleUrl: "./register.component.scss",
})
export class RegisterComponent {
    userData: FormGroup;
    universityList: UniversityDto[];

    existingEmailError: boolean = false;
    existingNicknameError: boolean = false;
    unknownRegistrationError: boolean = false;

    constructor(
        private authService: AuthService,
        private universityService: UniversityService,
        private router: Router
    ) {
        this.universityList = [];
        universityService.getUniversitiesList().subscribe({
            next: universitiesList => {
                this.universityList = universitiesList;
            },
        });

        this.userData = new FormGroup({
            email: new FormControl("", [
                Validators.required,
                Validators.email,
                Validators.maxLength(60),
            ]),
            nickname: new FormControl("", [
                Validators.required,
                Validators.minLength(6),
                Validators.maxLength(30),
            ]),
            university: new FormControl("Choose University", [
                Validators.required,
            ]),
            fieldOfStudies: new FormControl("", [
                Validators.required,
                Validators.minLength(3),
                Validators.maxLength(50),
            ]),
            password: new FormControl("", [
                Validators.required,
                Validators.minLength(8),
                Validators.maxLength(30),
            ]),
            repeatedPassword: new FormControl("", [
                Validators.required,
                Validators.minLength(8),
                Validators.maxLength(30)
            ]),
        });
    }

    onSubmit() {
        if (this.userData.valid) {
            this.authService
                .register(
                    new UserRegistrationRequestDto(
                        this.userData.controls["nickname"].value,
                        this.userData.controls["university"].value,
                        this.userData.controls["fieldOfStudies"].value,
                        this.userData.controls["email"].value,
                        this.userData.controls["password"].value
                    )
                )
                .subscribe({
                    next: () => this.router.navigate(["/login"]),
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
        if (errorMessage.includes(" email ")) {
            this.existingEmailError = true;
            this.existingNicknameError = false;
            this.unknownRegistrationError = false;
            console.log("email error");
        } else if (errorMessage.includes(" nickname ")) {
            this.existingNicknameError = true;
            this.existingEmailError = false;
            this.unknownRegistrationError = false;
            console.log("nickname error");
        } else if (!this.existingEmailError && !this.existingNicknameError) {
            this.unknownRegistrationError = true;
            this.existingEmailError = false;
            this.existingNicknameError = false;
            console.log("unknown error");
        }
    }

    getEmailErrorStatus(): boolean {
        return (
            this.userData.controls["email"].invalid &&
            (this.userData.controls["email"].touched ||
                this.userData.controls["email"].dirty)
        );
    }

    getNicknameErrorStatus(): boolean {
        return (
            this.userData.controls["nickname"].invalid &&
            (this.userData.controls["nickname"].touched ||
                this.userData.controls["nickname"].dirty)
        );
    }

    getUniversityErrorStatus(): boolean {
        if (
            this.userData.controls["university"].value === "Choose University"
        ) {
            return true;
        }
        return false;
    }

    getFieldOfStudiesErrorStatus(): boolean {
        return (
            this.userData.controls["fieldOfStudies"].invalid &&
            (this.userData.controls["fieldOfStudies"].touched ||
                this.userData.controls["fieldOfStudies"].dirty)
        );
    }

    getPasswordErrorStatus(): boolean {
        return (
            this.userData.controls["password"].invalid &&
            (this.userData.controls["password"].touched ||
                this.userData.controls["password"].dirty)
        );
    }

    getRepeatedPasswordErrorStatus(): boolean {
        return (
            this.userData.controls["repeatedPassword"].invalid &&
            (this.userData.controls["repeatedPassword"].touched ||
                this.userData.controls["repeatedPassword"].dirty)
        );
    }

    doesPasswordsMathch(): boolean {
        return (
            this.userData.controls["password"].value ===
            this.userData.controls["repeatedPassword"].value
        );
    }
}
