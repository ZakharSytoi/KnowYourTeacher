import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {RegisterComponent} from "./pages/register/register.component";
import {LoginComponent} from "./pages/login/login.component";
import {TeacherPageComponent} from "./pages/teacher-page/teacher-page.component";
import {SearchComponent} from "./pages/search/search.component";
import {GuidesComponent} from "./pages/guides/guides.component";
import {TeacherCreationComponent} from "./pages/teacher-creation/teacher-creation.component";
import {AboutUsComponent} from "./pages/about-us/about-us.component";
import {ContactsComponent} from "./pages/contacts/contacts.component";
import {QandaComponent} from "./pages/qanda/qanda.component";
import {UserPageComponent} from "./pages/user-page/user-page.component";
import {authorizedGuard} from "./guards/authorized.guard";

const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        title: 'KnowYourTeacher'
    },
    {
        path: 'login',
        component: LoginComponent,
        title: 'Login'
    },
    {
        path: 'register',
        component: RegisterComponent,
        title: 'Registration'
    },
    {
        path: 'profile',
        component: UserPageComponent,
        title: 'Profile',
        canActivate: [authorizedGuard]
    },
    {
        path: 'teacher/create',
        component: TeacherCreationComponent,
        title: 'KnowYourTeacher',
        canActivate: [authorizedGuard]
    },
    {
        path: 'teacher/:id',
        component: TeacherPageComponent,
        title: 'KnowYourTeacher'
    },
    {
        path: 'search',
        component: SearchComponent,
        title: 'KnowYourTeacher'
    },
    {
        path: 'guides',
        component: GuidesComponent,
        title: 'Guides'
    },
    {
        path: 'about-us',
        component: AboutUsComponent,
        title: 'About Us'
    },
    {
        path: 'contact',
        component: ContactsComponent,
        title: 'Contact'
    },
    {
        path: 'qa',
        component: QandaComponent,
        title: 'Q&A'
    },
    {
        path: '**',
        component: HomeComponent,
        redirectTo: ''
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
