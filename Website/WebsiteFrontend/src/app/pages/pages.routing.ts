import { Routes, RouterModule } from '@angular/router';
import { PagesComponent } from './pages.component';
import { LoginComponent } from './login/login.component';

export const childRoutes: Routes = [
    {
        path: 'login',
        component: LoginComponent,
    },
    {
        path: 'pages',
        component: PagesComponent,
        children: [
            { path: '', redirectTo: 'index', pathMatch: 'full' },
            { path: 'index', loadChildren: () => import('./index/index.module').then(m => m.IndexModule) },
            { path: 'profile', loadChildren: () => import('./profile/profile.module').then(m => m.ProfileModule) },
            { path: 'form', loadChildren: () => import('./form/form.module').then(m => m.FormModule) },
            { path: 'charts', loadChildren: () => import('./charts/charts.module').then(m => m.ChartsModule) },
            { path: 'ui', loadChildren: () => import('./ui/ui.module').then(m => m.UIModule) },
            { path: 'table', loadChildren: () => import('./table/table.module').then(m => m.TableModule) },
        ]
    }
];

export const routing = RouterModule.forChild(childRoutes);
