import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProjectsComponent } from './pages/projects/projects.component';
import { ProjectComponent } from './pages/project/project.component';
import { ContactComponent } from './pages/contact/contact.component';
import { ResumeComponent } from "./pages/resume/resume.component";
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login';
import { RegisterComponent } from './pages/register';
import { AuthGuard } from './guards';
import { UserAreaComponent } from './pages/userarea';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'userarea', component: UserAreaComponent, canActivate: [AuthGuard] },
  { path: 'pages/index',  redirectTo: 'pages/index', pathMatch: 'full' },
  { path: 'projects',  component: ProjectsComponent },
  { path: 'home',  component: HomeComponent },
  { path: 'contact',  component: ContactComponent },
  { path: 'projects/:id',  component: ProjectComponent},
  { path: 'resume', component: ResumeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];

/* 
@NgModule({
  imports: [
   RouterModule.forRoot(routes, {
         scrollPositionRestoration: 'enabled',
         useHash: true
       })
  ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {} */

@NgModule({
  exports: [ RouterModule ]
})
export class AppRoutingModule {}

export const routing = RouterModule.forRoot(routes);