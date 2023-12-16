import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import {HeaderComponent} from "../../components/header/header.component";
import { BASE_API_URL } from '../../services/consts';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-home',
  standalone: true,
    imports: [
        HeaderComponent
    ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  responseData: string = '';

  headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`);
  
  getDataWithToken(): Observable<any>{
    if(localStorage.getItem('token')!= null){
      return this.http.get(BASE_API_URL+'security_checks/role_check', { headers: this.headers, responseType: 'text' });
    }else{
      return this.http.get(BASE_API_URL+'security_checks/role_check', { responseType: 'text' });
    }
    
  }

  constructor(
    private readonly http: HttpClient,
  ) {}

  ngOnInit() {
    this.getDataWithToken().subscribe(
      (data : string) => {
        this.responseData = data;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }


}
