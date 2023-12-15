import { Component } from '@angular/core';
import { ShortHeaderComponent } from '../../components/short-header/short-header.component';
import { NgStyle, NgClass } from '@angular/common';
import {
  FormGroup,
  FormControl,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ShortHeaderComponent, NgStyle, NgClass, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  userData: FormGroup;

  constructor() {
    this.userData = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.email,
        Validators.maxLength(30),
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(30),
      ]),
    });
  }
  onSubmit() {
    if (this.userData.valid) {
      console.log(this.userData.value);
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
