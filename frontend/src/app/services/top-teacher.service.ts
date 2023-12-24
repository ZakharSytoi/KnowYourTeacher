import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TeacherPreviewDto} from "../models/TeacherPreviewDto";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class TopTeacherService {

    constructor(private readonly http: HttpClient) {}
    public getTopTeachersList(): Observable<TeacherPreviewDto[]> {
        return this.http.get<TeacherPreviewDto[]>(environment.BASE_API_URL + 'teachers/topten');
    }
}
