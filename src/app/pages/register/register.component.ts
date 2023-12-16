import { Component } from '@angular/core';
import { ShortHeaderComponent } from '../../components/short-header/short-header.component';
import { NgStyle, NgClass } from '@angular/common';
import { RouterModule } from '@angular/router';
import {
  FormGroup,
  FormControl,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ShortHeaderComponent,
    NgStyle,
    NgClass,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  userData: FormGroup;

  constructor() {
    this.userData = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.email,
        Validators.maxLength(30),
      ]),
      nickname: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(20),
      ]),
      fieldOfStudies: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50),
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(30),
      ]),
      repeatedPassword: new FormControl('', [
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

  getNicknameErrorStatus(): boolean {
    return (
      this.userData.controls['nickname'].invalid &&
      (this.userData.controls['nickname'].touched ||
        this.userData.controls['nickname'].dirty)
    );
  }

  getFieldOfStudiesErrorStatus(): boolean {
    return (
      this.userData.controls['fieldOfStudies'].invalid &&
      (this.userData.controls['fieldOfStudies'].touched ||
        this.userData.controls['fieldOfStudies'].dirty)
    );
  }

  getPasswordErrorStatus(): boolean {
    return (
      this.userData.controls['password'].invalid &&
      (this.userData.controls['password'].touched ||
        this.userData.controls['password'].dirty)
    );
  }

  getRepeatedPasswordErrorStatus(): boolean {
    return (
      this.userData.controls['repeatedPassword'].invalid &&
      (this.userData.controls['repeatedPassword'].touched ||
        this.userData.controls['repeatedPassword'].dirty)
    );
  }

  doesPasswordsMathch(): boolean {
    return (
      this.userData.controls['password'].value ===
      this.userData.controls['repeatedPassword'].value
    );
  }
}
