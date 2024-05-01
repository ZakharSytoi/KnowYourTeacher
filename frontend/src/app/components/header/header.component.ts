import {Component} from '@angular/core';
import {NgClass, NgIf} from "@angular/common";
import {Router, RouterModule} from '@angular/router';
import {JwtUtilsService} from "../../services/jwt-utils.service";
import {ClickOutsideDirective} from './clickOutside.directive';
import {NgbCollapse} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-header',
    standalone: true,
    imports: [RouterModule, NgIf, ClickOutsideDirective, NgbCollapse, NgClass],
    templateUrl: './header.component.html',
    styleUrl: './header.component.scss'
})
export class HeaderComponent {
    jwtUtilService: JwtUtilsService;
    isMenuCollapsed = true;

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

    changeMenuState(){
        this.isMenuCollapsed = !this.isMenuCollapsed;
    }
    clickedOutside(): void {
        console.log("clicked")
        this.isMenuCollapsed = true;
    }

    saveCurrentUrl = ()=>sessionStorage.setItem('previousUrl', this.router.url);

}
