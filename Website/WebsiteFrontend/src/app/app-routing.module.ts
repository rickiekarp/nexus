import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProfessionalComponent } from './components/professional/professional.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { ContactComponent } from './components/contact/contact.component';
import {AboutComponent} from "./about/about.component";
import { PersonalComponent } from './components/personal/personal.component';

const routes: Routes = [
  { path: '', redirectTo: '/professional', pathMatch: 'full' },
  { path: 'professional',  component: ProfessionalComponent },
  { path: 'personal',  component: PersonalComponent },
  { path: 'contact',  component: ContactComponent },
  { path: 'projects/:id',  component: ProjectsComponent},
  { path: 'about', component: AboutComponent }
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
