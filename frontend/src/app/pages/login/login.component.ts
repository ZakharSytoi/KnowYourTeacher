import { Component } from '@angular/core';
import { ShortHeaderComponent } from '../../components/short-header/short-header.component';
import { NgStyle, NgClass } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import {
  FormGroup,
  FormControl,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { UserLoginRequestDto } from '../../models/UserLoginRequestDto';
import { JwtResponseDto } from '../../models/JwtResponseDto';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ShortHeaderComponent,
    NgStyle,
    NgClass,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  userData: FormGroup;
  jwtDto = new JwtResponseDto();

  constructor(private authService: AuthService, private router: Router) {
    this.userData = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.email,
        Validators.maxLength(30),
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(30),
      ]),
    });
  }

  onSubmit() {
    if (this.userData.valid) {
      this.authService
        .login(
          new UserLoginRequestDto(
            this.userData.controls['email'].value,
            this.userData.controls['password'].value
          )
        )
        .subscribe((jwtDto) => {
          if(localStorage.getItem('token') === null){
            localStorage.setItem('token', jwtDto.token);
          }
          else{
            localStorage.removeItem('token');
            localStorage.setItem('token', jwtDto.token);
          }
          this.router.navigate(['/']);
        });
    } else {
      console.log('invalid data');
    }
  }

  getEmailErrorStatus(): boolean {
    return (
      this.userData.controls['email'].invalid &&
      (this.userData.controls['email'].touched ||
        this.userData.controls['email'].dirty)
    );
  }

  getPasswordErrorStatus(): boolean {
    return (
      this.userData.controls['password'].invalid &&
      (this.userData.controls['password'].touched ||
        this.userData.controls['password'].dirty)
    );
  }
}
