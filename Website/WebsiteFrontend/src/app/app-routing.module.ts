import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { ProjectsComponent } from './projects/projects.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard',  component: DashboardComponent },
  { path: 'projects/:id',  component: ProjectsComponent}
];

@NgModule({
  imports: [
   RouterModule.forRoot(routes, {
         scrollPositionRestoration: 'enabled'
       })
  ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
