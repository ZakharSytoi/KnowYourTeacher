import {Component, ElementRef, ViewChild} from '@angular/core';
import {ImageCropperModule} from "ngx-image-cropper";
import {HeaderComponent} from "../../components/header/header.component";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {UniversityService} from '../../services/university.service';
import {UniversityDto} from '../../models/UniversityDto';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {TeacherService} from "../../services/teacher.service";

@Component({
    selector: 'app-teacher-creation',
    standalone: true,
    imports: [
        ImageCropperModule,
        HeaderComponent,
        NgIf,
        NgForOf,
        ReactiveFormsModule,
        NgClass
    ],
    templateUrl: './teacher-creation.component.html',
    styleUrl: './teacher-creation.component.scss'
})
export class TeacherCreationComponent {

    @ViewChild('input') inputRef!: ElementRef
    teacherData: FormGroup;
    image!: File;
    imagePreview!: any;
    universityList: UniversityDto[];

    constructor(private universityService: UniversityService,
                private teacherService: TeacherService) {
        this.universityList = [];
        universityService.getUniversitiesList().subscribe({
            next: universitiesList => {
                this.universityList = universitiesList;
            },
        });

        this.teacherData = new FormGroup({
            name: new FormControl('', [Validators.minLength(2), Validators.maxLength(50), Validators.required]),
            surname: new FormControl('', [Validators.minLength(2), Validators.maxLength(50), Validators.required]),
            universityId: new FormControl('University', [Validators.required]),
            photo: new FormControl(null, [Validators.required])
        })

    }

    onFileUpload(event: any) {
        const file = event.target.files[0]
        this.image = file;

        const reader = new FileReader();
        reader.onload = () => {
            this.imagePreview = reader.result;
        }
        reader.readAsDataURL(file);

        this.teacherData.patchValue({photo: file})
    }


    onSubmit() {
        if(this.teacherData.valid){
            this.teacherService.create$({
                name: this.teacherData.controls['name'].value,
                surname: this.teacherData.controls['surname'].value,
                universityId: this.teacherData.controls['universityId'].value,
            }, this.teacherData.controls['photo'].value)
        }
    }

    triggerUpload() {
        this.inputRef.nativeElement.click()
    }
    getUniversityErrorStatus(): boolean {
        if (
            this.teacherData.controls["universityId"].value === "University"
        ) {
            return true;
        }
        return false;
    }

    getNameErrorStatus(): boolean {
        return (
            this.teacherData.controls["name"].invalid &&
            (this.teacherData.controls["name"].touched ||
                this.teacherData.controls["name"].dirty)
        );
    }

    getSurnameErrorStatus(): boolean {
        return (
            this.teacherData.controls["surname"].invalid &&
            (this.teacherData.controls["surname"].touched ||
                this.teacherData.controls["surname"].dirty)
        );
    }
}
