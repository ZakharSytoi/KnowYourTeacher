import {Component} from '@angular/core';
import {RouterModule} from '@angular/router';
import {JwtUtilsService} from "../../services/jwt-utils.service";

@Component({
    selector: 'app-header',
    standalone: true,
    imports: [RouterModule],
    templateUrl: './header.component.html',
    styleUrl: './header.component.scss'
})
export class HeaderComponent {
    jwtUtilService: JwtUtilsService;


    constructor(jwtUtilService: JwtUtilsService) {
        this.jwtUtilService = jwtUtilService;
    }

    handleLogout() {
        localStorage.removeItem('token')
    }

    isLoggedIn(): boolean {
        return localStorage.getItem('token') != null;
    }

    getEmail(): string {
        return this.jwtUtilService.getUserEmail()
    }
    getRoles():string[]{
        return this.jwtUtilService.GetUserRoles()
    }

}
