import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UniversityDto} from "../../models/UniversityDto";
import {AuthService} from "../../services/auth.service";
import {UniversityService} from "../../services/university.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search',
  standalone: true,
  templateUrl: './search.component.html',
  imports: [
    ReactiveFormsModule
  ],
  styleUrl: './search.component.scss'
})
export class SearchComponent {
    searchForm: FormGroup
    universityList: UniversityDto[];

  constructor(
      private authService: AuthService,
      private universityService: UniversityService,
  ) {
    this.universityList = [];
    universityService.getUniversitiesList().subscribe({
      next: universitiesList => {
        this.universityList = universitiesList;
      },
    });

    this.searchForm = new FormGroup({
      teacherName: new FormControl("", [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100),
      ]),
      subject: new FormControl("", [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50),
      ]),
      university: new FormControl("Choose University", [
        Validators.required,
      ]),

    });
  }

  onSubmit() {
    if(this.searchForm.valid){

    }
  }
}
