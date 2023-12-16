import { Component } from '@angular/core';
import { ShortHeaderComponent } from '../../components/short-header/short-header.component';
import { NgStyle, NgClass } from '@angular/common';
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
  imports: [ShortHeaderComponent, NgStyle, NgClass, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  
  userData: FormGroup;
  jwtDto = new JwtResponseDto();


  constructor(private authService: AuthService) {
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
        .subscribe((jwtDto) => localStorage.setItem('token', jwtDto.token));

      console.log(localStorage.getItem('authToken'));
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
