import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProfessionalComponent } from './pages/professional/professional.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { ContactComponent } from './pages/contact/contact.component';
import { AboutComponent } from "./pages/about/about.component";
import { PersonalComponent } from './pages/personal/personal.component';
import { LoginComponent } from './pages/login';
import { RegisterComponent } from './pages/register';
import { AuthGuard } from './guards';
import { HomeComponent } from './pages/home';

const routes: Routes = [
  { path: '', redirectTo: 'professional', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'pages/index',  redirectTo: 'pages/index', pathMatch: 'full' },
  { path: 'professional',  component: ProfessionalComponent },
  { path: 'personal',  component: PersonalComponent },
  { path: 'contact',  component: ContactComponent },
  { path: 'projects/:id',  component: ProjectsComponent},
  { path: 'about', component: AboutComponent },
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