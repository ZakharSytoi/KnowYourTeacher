import { Component } from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {TeacherComponent} from "../../components/teacher/teacher.component";

@Component({
  selector: 'app-teacher-page',
  standalone: true,
  imports: [
    HeaderComponent,
    TeacherComponent
  ],
  templateUrl: './teacher-page.component.html',
  styleUrl: './teacher-page.component.scss'
})
export class TeacherPageComponent {

}
