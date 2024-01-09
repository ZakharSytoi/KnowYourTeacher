import {Component} from '@angular/core';

import {HeaderComponent} from "../../components/header/header.component";
import {SearchFormComponent} from "../../components/search-form/search-form.component";
import {TopTeachersComponent} from "../../components/topteachers/top-teachers.component";


@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        HeaderComponent,
        SearchFormComponent,
        TopTeachersComponent
    ],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent  {
    //for testes add this


    //implements OnInit



    /*responseData: string = '';
    isBrowser: boolean;

    constructor(@Inject(PLATFORM_ID) private platformId: any, private readonly http: HttpClient,) {
        this.isBrowser = isPlatformBrowser(platformId);
    }

    // headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`);

    getDataWithToken(): Observable<any> {
        if (this.isBrowser) {
            return this.http.get(BASE_API_URL + 'security_checks/role_check', {responseType: 'text'});
        } else return new Observable<any>
    }


    ngOnInit() {
        this.getDataWithToken().subscribe(
            (data: string) => {
                this.responseData = data;
            },
            (error) => {
                console.error('Error fetching data:', error);
            }
        );
    }*/


}
