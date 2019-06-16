import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProfessionalComponent } from './components/professional/professional.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { ContactComponent } from './components/contact/contact.component';
import { AboutComponent } from "./components/about/about.component";
import { PersonalComponent } from './components/personal/personal.component';
import { LoginComponent } from './components/login';
import { RegisterComponent } from './components/register';
import { AuthGuard } from './guards';
import { HomeComponent } from './components/home';

const routes: Routes = [
  { path: '', redirectTo: 'professional', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'professional',  component: ProfessionalComponent },
  { path: 'personal',  component: PersonalComponent },
  { path: 'contact',  component: ContactComponent },
  { path: 'projects/:id',  component: ProjectsComponent},
  { path: 'about', component: AboutComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];

@NgModule({
  imports: [
   RouterModule.forRoot(routes, {
         scrollPositionRestoration: 'enabled',
         useHash: true
       })
  ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}

export const routing = RouterModule.forRoot(routes);