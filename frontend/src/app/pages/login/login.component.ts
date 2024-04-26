import {Component} from "@angular/core";
import {ShortHeaderComponent} from "../../components/short-header/short-header.component";
import {NgClass, NgStyle} from "@angular/common";
import {Router, RouterModule} from "@angular/router";
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {UserLoginRequestDto} from "../../models/UserLoginRequestDto";
import {JwtResponseDto} from "../../models/JwtResponseDto";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
    selector: "app-login",
    standalone: true,
    imports: [
        ShortHeaderComponent,
        NgStyle,
        NgClass,
        ReactiveFormsModule,
        RouterModule,
    ],
    templateUrl: "./login.component.html",
    styleUrl: "./login.component.scss",
})
export class LoginComponent {
    userData: FormGroup;
    jwtDto = new JwtResponseDto();
    wrongLoginOrPassword: boolean = false;

    constructor(private authService: AuthService, private router: Router) {
        this.userData = new FormGroup({
            email: new FormControl("", [
                Validators.required,
                Validators.email,
                Validators.maxLength(30),
            ]),
            password: new FormControl("", [
                Validators.required,
                Validators.minLength(4),
                Validators.maxLength(30),
            ]),
        });
        // this.wrongLoginOrPassword = false;
    }

    onSubmit() {
        if (this.userData.valid) {
            this.authService
                .login(
                    new UserLoginRequestDto(
                        this.userData.controls["email"].value,
                        this.userData.controls["password"].value
                    )
                )
                .subscribe({
                    next: (jwtDto) => {
                        this.handleSuccessfulLogin(jwtDto);
                    },
                    error: (err) => {
                        this.handleUnsuccessfulLogin(err);
                    },
                });
        } else {
            console.log("invalid data");
        }
    }

    handleSuccessfulLogin(jwtDto: JwtResponseDto): any {
        if (localStorage.getItem("token") === null) {
            localStorage.setItem("token", jwtDto.token);
        } else {
            localStorage.removeItem("token");
            localStorage.setItem("token", jwtDto.token);
        }
        const prevUrl = sessionStorage.getItem('previousUrl');
        console.log(prevUrl);
        this.router.navigateByUrl(prevUrl? prevUrl : "/");
    }

    handleUnsuccessfulLogin(error: HttpErrorResponse): any {
        this.wrongLoginOrPassword = true;
    }

    getEmailErrorStatus(): boolean {
        return (
            this.userData.controls["email"].invalid &&
            (this.userData.controls["email"].touched ||
                this.userData.controls["email"].dirty)
        );
    }

    getPasswordErrorStatus(): boolean {
        return (
            this.userData.controls["password"].invalid &&
            (this.userData.controls["password"].touched ||
                this.userData.controls["password"].dirty)
        );
    }
}
