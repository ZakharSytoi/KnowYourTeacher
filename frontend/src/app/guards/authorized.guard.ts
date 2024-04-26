import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../services/auth.service";

export const authorizedGuard: CanActivateFn = (route, state) => {
    if (inject(AuthService).isLoggedIn()) {
        return true;
    } else {
        inject(Router).navigate(['/login']);
        return false;
    }
};
