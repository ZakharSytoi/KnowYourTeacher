import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UniversityDto} from "../../models/UniversityDto";
import {AuthService} from "../../services/auth.service";
import {UniversityService} from "../../services/university.service";
import {Router} from "@angular/router";
import {NgFor} from '@angular/common';

@Component({
    selector: 'app-search-form',
    standalone: true,
    templateUrl: './search-form.component.html',
    imports: [
        ReactiveFormsModule,
        NgFor
    ],
    styleUrl: './search-form.component.scss'
})
export class SearchFormComponent {
    searchForm: FormGroup
    universityList: UniversityDto[];

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

        this.searchForm = new FormGroup({
            teacherName: new FormControl("", [
                Validators.minLength(3),
                Validators.maxLength(100),
            ]),
            teacherSurname: new FormControl("", [
                Validators.minLength(3),
                Validators.maxLength(100),
            ]),
            subject: new FormControl("", [
                Validators.minLength(3),
                Validators.maxLength(50),
            ]),
            universityId: new FormControl("University"),
        });
    }

    onSubmit() {
        if (this.searchForm.valid) {
            this.router.navigate(['/search'],
                {
                    queryParams:
                        {
                            teacherName: this.searchForm.controls["teacherName"].value,
                            teacherSurname: this.searchForm.controls["teacherSurname"].value,
                            subject: this.searchForm.controls["subject"].value,
                            universityId: this.searchForm.controls["universityId"].value
                                === "University" ? '' : this.searchForm.controls["universityId"].value
                        }
                });
        } else {
        }
    }

    getUniversityErrorStatus(): boolean {
        if (
            this.searchForm.controls["university"].value === "University"
        ) {
            return true;
        }
        return false;
    }
}
