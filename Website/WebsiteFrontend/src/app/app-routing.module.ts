import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { ProjectsComponent } from './projects/projects.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home',  component: HomeComponent },
  { path: 'projects/:id',  component: ProjectsComponent}
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
