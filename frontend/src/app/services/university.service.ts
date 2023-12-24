import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {UniversityDto} from "../models/UniversityDto";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UniversityService {

  constructor(private readonly http: HttpClient) { }

  public getUniversitiesList(): Observable<UniversityDto[]> {
    return this.http.get<UniversityDto[]>(environment.BASE_API_URL + 'universities');
  }
}
