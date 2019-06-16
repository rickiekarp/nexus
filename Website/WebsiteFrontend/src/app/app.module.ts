import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { ProfessionalComponent } from './components/professional/professional.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { PersonalComponent } from './components/personal/personal.component';
import { ContactComponent } from './components/contact/contact.component';
import { ContactService } from './service/contact.service';
import { SafePipe } from './core/pipe/safepipe.service';
import { AboutComponent } from './components/about/about.component';

import { AppRoutingModule } from './app-routing.module';
import { ResumeService } from './service/resume.service';
import { ReactiveFormsModule }    from '@angular/forms';

// used to create fake backend
import { fakeBackendProvider } from './helpers';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { routing } from './app-routing.module';

import { AlertComponent } from './components/alert';
import { JwtInterceptor, ErrorInterceptor } from './helpers';
import { HomeComponent } from './components/home';
import { LoginComponent } from './components/login';
import { RegisterComponent } from './components/register';

import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { SatPopoverModule } from '@ncstate/sat-popover';

import { EditExample } from './components/popover/edit-example/edit-example.component';
import { EditForm } from './components/popover/edit-example/edit-form.component';
import { MenuExample } from './components/popover/profile/profile-popover.component';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
    HttpModule,
    HttpClientModule,
    routing,
    SatPopoverModule
  ],
  declarations: [
    AppComponent,
    ProfessionalComponent,
    PersonalComponent,
    ProjectsComponent,
    ContactComponent,
    SafePipe,
    AboutComponent,
    AlertComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    EditExample,
    EditForm,
    MenuExample,
    ],
  providers: [ 
    ContactService, 
    ResumeService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: LocationStrategy, useClass: HashLocationStrategy },

    // provider used to create fake backend
    fakeBackendProvider 
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
