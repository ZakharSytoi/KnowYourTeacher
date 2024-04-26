import {Component} from '@angular/core';
import {NgIf} from "@angular/common";
import {Router, RouterModule} from '@angular/router';
import {JwtUtilsService} from "../../services/jwt-utils.service";
import { ClickOutsideDirective } from './clickOutside.directive';

@Component({
    selector: 'app-header',
    standalone: true,
    imports: [RouterModule,NgIf, ClickOutsideDirective],
    templateUrl: './header.component.html',
    styleUrl: './header.component.scss'
})
export class HeaderComponent {
    jwtUtilService: JwtUtilsService;


    constructor(jwtUtilService: JwtUtilsService, readonly router:Router) {
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

    isMenuOpened: boolean = false;

    toggleMenu(): void {
        this.isMenuOpened = !this.isMenuOpened;
    }

    clickedOutside(): void {
        this.isMenuOpened = false;
    }

    saveCurrentUrl = ()=>sessionStorage.setItem('previousUrl', this.router.url);

}
