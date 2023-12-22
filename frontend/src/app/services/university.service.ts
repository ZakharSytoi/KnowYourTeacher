import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {BASE_API_URL} from "./consts";
import {HttpClient} from "@angular/common/http";
import {UniversityDto} from "../models/UniversityDto";

@Injectable({
  providedIn: 'root'
})
export class UniversityService {

  constructor(private readonly http: HttpClient) { }

  public getUniversitiesList(): Observable<UniversityDto[]> {
    return this.http.get<UniversityDto[]>(BASE_API_URL + 'universities');
  }
}
