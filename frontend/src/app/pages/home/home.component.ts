import {Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import {HeaderComponent} from "../../components/header/header.component";
import { BASE_API_URL } from '../../services/consts';
import { Observable } from 'rxjs';
import {isPlatformBrowser} from "@angular/common";


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
  isBrowser: boolean;
  constructor(@Inject(PLATFORM_ID) private platformId:any,  private readonly http: HttpClient,) {
    this.isBrowser = isPlatformBrowser(platformId);
  }
  
  // headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`);
  
  getDataWithToken(): Observable<any>{
    if (this.isBrowser){
      if(localStorage.getItem('token')!= null){
        return this.http.get(BASE_API_URL+'security_checks/role_check', { headers: new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`), responseType: 'text' });
      }else{
        return this.http.get(BASE_API_URL+'security_checks/role_check', { responseType: 'text' });
      }
    } else return new Observable<any>
  }


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
