import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SearchRequestDto} from "../models/SearchRequestDto";
import {Observable} from "rxjs";
import {TeacherPreviewDto} from "../models/TeacherPreviewDto";
import {Page} from "../models/Page";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private readonly http: HttpClient) { }

  search$ = (searchRequest: SearchRequestDto, pageNumber: number = 0, pageSize: number = 10): Observable<Page<TeacherPreviewDto>> =>
      this.http.get<Page<TeacherPreviewDto>>(`${environment.BASE_API_URL}teachers/search
      ?pageNumber=${pageNumber}
      &pageSize=${pageSize}}
      &teacherName=${searchRequest.teacherName}
      &teacherSurname=${searchRequest.teacherSurname}
      &subject=${searchRequest.subject}
      &universityId=${searchRequest.universityId}`);

}
