import { Component } from '@angular/core';
import {SearchFormComponent} from "../search-form/search-form.component";

@Component({
  selector: 'app-welcome',
  standalone: true,
    imports: [
        SearchFormComponent
    ],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.scss'
})
export class WelcomeComponent {

}
