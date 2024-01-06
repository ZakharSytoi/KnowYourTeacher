import { Component } from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";

@Component({
  selector: 'app-guides',
  standalone: true,
    imports: [
        HeaderComponent
    ],
  templateUrl: './guides.component.html',
  styleUrl: './guides.component.scss'
})
export class GuidesComponent {

}
