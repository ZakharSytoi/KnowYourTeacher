import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  handleLogout(){
    localStorage.removeItem('token')
  }
  isLoggedIn(): boolean{
    return localStorage.getItem('token') != null;
  }

  
}
