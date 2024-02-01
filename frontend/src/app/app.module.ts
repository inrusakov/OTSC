import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { provideRouter } from '@angular/router';
import { routes } from './app-routing.module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HeaderComponent } from './outer/header/header.component';
import { LoginFormComponent } from './outer/login-form/login-form.component';
import { WelcomeContentComponent } from './outer/content/welcome-content/welcome-content.component';
import { FeaturesComponent } from './outer/content/features-content/features.component';
import { AboutComponent } from './outer/content/about-content/about.component';
import { ContentComponent } from './outer/content/content.component';

import { ButtonLogout } from './inner/button-logout/button-logout.component';
import { InnerAppComponent } from './inner/inner-app/inner-app.component'; 

import { AxiosService } from './axios.service';
import { InnerHeaderComponent } from './inner/inner-header/inner-header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    ButtonLogout,
    HeaderComponent,
    LoginFormComponent,
    WelcomeContentComponent,
    FeaturesComponent,
    AboutComponent,
    ContentComponent,
    InnerAppComponent,
    InnerHeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NgbModule
  ],
  providers: [AxiosService, provideRouter(routes)],
  bootstrap: [AppComponent]
})
export class AppModule { }
