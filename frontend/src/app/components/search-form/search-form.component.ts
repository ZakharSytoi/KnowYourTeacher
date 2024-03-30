import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UniversityDto} from "../../models/UniversityDto";
import {AuthService} from "../../services/auth.service";
import {UniversityService} from "../../services/university.service";
import {Router} from "@angular/router";
import {NgFor} from '@angular/common';
import {animate, state, style, transition, trigger} from "@angular/animations";

@Component({
    selector: 'app-search-form',
    standalone: true,
    templateUrl: './search-form.component.html',
    imports: [
        ReactiveFormsModule,
        NgFor
    ],
    animations: [
        trigger('slideInOutLeft', [
            state('in', style({
                transform: 'translateX(0)',
                opacity: 1
            })),
            state('out', style({
                transform: 'translateX(-100%)',
                opacity: 0
            })),
            transition('in => out', [animate('500ms ease-out')]),
            transition('out => in', [animate('500ms ease-in')])
        ]),
        trigger('slideInOutRight', [
            state('in', style({
                transform: 'translateX(0)',
                opacity: 1
            })),
            state('out', style({
                transform: 'translateX(+100%)',
                opacity: 0
            })),
            transition('in => out', [animate('500ms ease-out')]),
            transition('out => in', [animate('500ms ease-in')])
        ])
    ],
    styleUrl: './search-form.component.scss'
})
export class SearchFormComponent {
    searchForm: FormGroup
    universityList: UniversityDto[];
    animationState: boolean
    constructor(
        private authService: AuthService,
        private universityService: UniversityService,
        private router: Router,
    ) {
        this.universityList = [];
        universityService.getUniversitiesList().subscribe({
            next: universitiesList => {
                this.universityList = universitiesList;
            },
        });

        this.searchForm = new FormGroup({
            teacherName: new FormControl("", [
                Validators.maxLength(100),
            ]),
            teacherSurname: new FormControl("", [
                Validators.maxLength(100),
            ]),
            subject: new FormControl("", [
                Validators.maxLength(50),
            ]),
            universityId: new FormControl("University"),
            searchType: new FormControl()
        });
       this.animationState = false
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
                                === "University" ? '' : this.searchForm.controls["universityId"].value,
                            searchType: this.searchForm.controls["searchType"].value === false? 'teacher' : 'review'
                        }
                });
        } else {
        }
    }

    getUniversityErrorStatus(): boolean {
        return this.searchForm.controls["university"].value === "University";
    }
}
