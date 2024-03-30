import {Component, Input, OnChanges} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UniversityDto} from "../../models/UniversityDto";
import {AuthService} from "../../services/auth.service";
import {UniversityService} from "../../services/university.service";
import {Router} from "@angular/router";
import {NgFor} from '@angular/common';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {SearchRequestDto} from "../../models/SearchRequestDto";

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
export class SearchFormComponent implements OnChanges {
    @Input() defaultFormValues: SearchRequestDto = {
        searchType: "",
        subject: "",
        teacherName: "",
        teacherSurname: "",
        universityId: "University"

    };
    searchForm: FormGroup;
    universityList: UniversityDto[];

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
            searchType: new FormControl(false)
        });

    }

    ngOnChanges(): void {
        if (this.defaultFormValues) {
            this.searchForm.setValue(this.defaultFormValues)
            if (this.searchForm.controls["universityId"].value === "")
                this.searchForm.patchValue({universityId: "University"})
            this.searchForm.patchValue({searchType: this.defaultFormValues.searchType !== "teacher"})
        }
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
                            searchType: this.searchForm.controls["searchType"].value === false ? 'teacher' : 'review'
                        }
                });
        } else {
        }
    }

    getUniversityErrorStatus(): boolean {
        return this.searchForm.controls["university"].value === "University";
    }
}
