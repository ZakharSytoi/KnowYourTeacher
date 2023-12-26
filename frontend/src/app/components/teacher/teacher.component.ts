import { AfterViewInit, Component } from "@angular/core";
import { AuthService } from "../../services/auth.service";
import { RouterModule } from '@angular/router';
import {NgbRating} from "@ng-bootstrap/ng-bootstrap";
declare function initRatings():void;

@Component({
    selector: "app-teacher",
    standalone: true,
    imports: [RouterModule, NgbRating],
    templateUrl: "./teacher.component.html",
    styleUrl: "./teacher.component.scss",
})
export class TeacherComponent implements AfterViewInit{
  constructor(private authService:AuthService){

  }

    isLoggedIn():boolean {
        return this.authService.isLoggedIn();
    }
    ngAfterViewInit(): void {
        initRatings();
    }
}
