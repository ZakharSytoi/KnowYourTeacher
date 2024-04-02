import {Component, Input} from '@angular/core';
import {TeacherPreviewDto} from "../../models/TeacherPreviewDto";
import {RouterLink} from "@angular/router";

@Component({
  standalone: true,
  selector: 'app-teacher-preview',
  templateUrl: './teacher-preview.component.html',
  imports: [
    RouterLink
  ],
  styleUrl: './teacher-preview.component.scss'
})
export class TeacherPreviewComponent {
  @Input() teacher!: TeacherPreviewDto;
}
