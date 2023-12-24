import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UniversityDto} from "../models/UniversityDto";
import {BASE_API_URL} from "./consts";
import {TeacherPreviewDto} from "../models/TeacherPreviewDto";

@Injectable({
    providedIn: 'root'
})
export class TopTeacherService {

    constructor(private readonly http: HttpClient) {}
    public getTopTeachersList(): Observable<TeacherPreviewDto[]> {
        return this.http.get<TeacherPreviewDto[]>(BASE_API_URL + 'teachers/topten');
    }
}
