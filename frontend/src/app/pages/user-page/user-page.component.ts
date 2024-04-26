import { Component } from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";

@Component({
  standalone: true,
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  imports: [
    HeaderComponent
  ],
  styleUrl: './user-page.component.scss'
})
export class UserPageComponent {

}
