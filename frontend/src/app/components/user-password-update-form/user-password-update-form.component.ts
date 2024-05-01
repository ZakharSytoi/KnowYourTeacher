import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {NgClass, NgForOf} from "@angular/common";
import {HoveringInputLabelComponent} from "../SmallComponents/hovering-input-label/hovering-input-label.component";

@Component({
    standalone: true,
    selector: 'app-user-password-update-form',
    templateUrl: './user-password-update-form.component.html',
    imports: [
        FormsModule,
        NgForOf,
        ReactiveFormsModule,
        NgClass,
        HoveringInputLabelComponent
    ],
    styleUrl: './user-password-update-form.component.scss'
})
export class UserPasswordUpdateFormComponent {
    userData: FormGroup;

    @Output() hideFormEvent = new EventEmitter();

    unknownPasswordUpdateError: boolean = false;
    passwordMismatchError: boolean = false;
    successfulUpdated: boolean = false;

    constructor(private readonly userService: UserService,
                private router: Router) {
        this.userData = new FormGroup({
            currentPassword: new FormControl("", [
                Validators.required,
                Validators.minLength(8),
                Validators.maxLength(30),
            ]),
            newPassword: new FormControl("", [
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
            this.userService.updateUserPassword
            (
                {
                    currentPassword: this.userData.controls["currentPassword"].value,
                    newPassword: this.userData.controls["newPassword"].value
                }
            )
                .subscribe({
                    next: () => {
                        this.passwordMismatchError = false;
                        this.successfulUpdated = true;
                        setTimeout(() => {
                            this.hideFormEvent.emit();
                            this.successfulUpdated = false;
                        }, 1000)
                    },
                    error: err => {
                        this.handleUnsuccessfulPasswordUpdate(err);
                    },
                });
        } else {
            console.log("invalid data");
        }
    }

    handleUnsuccessfulPasswordUpdate(error: HttpErrorResponse): any {
        if (error.status == 403) {
            this.unknownPasswordUpdateError = false;
            this.passwordMismatchError = true;
        } else this.unknownPasswordUpdateError = true;
    }


    getCurrentPasswordErrorStatus(): boolean {
        return (
            this.userData.controls["currentPassword"].invalid &&
            (this.userData.controls["currentPassword"].touched ||
                this.userData.controls["currentPassword"].dirty)
        );
    }

    getNewPasswordErrorStatus(): boolean {
        return (
            this.userData.controls["newPassword"].invalid &&
            (this.userData.controls["newPassword"].touched ||
                this.userData.controls["newPassword"].dirty)
        );
    }

    getRepeatedPasswordErrorStatus(): boolean {
        return (
            this.userData.controls["repeatedPassword"].invalid &&
            (this.userData.controls["repeatedPassword"].touched ||
                this.userData.controls["repeatedPassword"].dirty)
        );
    }

    doesPasswordsMatch(): boolean {
        return (
            this.userData.controls["newPassword"].value ===
            this.userData.controls["repeatedPassword"].value
        );
    }
}
