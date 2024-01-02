import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {RegisterComponent} from "./pages/register/register.component";
import {LoginComponent} from "./pages/login/login.component";
import {TeacherPageComponent} from "./pages/teacher-page/teacher-page.component";
import {SearchComponent} from "./pages/search/search.component";

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
    path: '**',
    component: HomeComponent,
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
