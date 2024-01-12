import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { provideRouter } from '@angular/router';
import { routes } from './app-routing.module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { ButtonLogin } from './outer/button-login/button-login.component';
import { HeaderComponent } from './outer/header/header.component';
import { LoginFormComponent } from './outer/login-form/login-form.component';
import { WelcomeContentComponent } from './outer/welcome-content/welcome-content.component';
import { AuthContentComponent } from './outer/auth-content/auth-content.component';
import { ContentComponent } from './outer/content/content.component';

import { ButtonLogout } from './inner/button-logout/button-logout.component';
import { InnerAppComponent } from './inner/inner-app/inner-app.component'; 

import { AxiosService } from './axios.service';

@NgModule({
  declarations: [
    AppComponent,
    ButtonLogin,
    ButtonLogout,
    HeaderComponent,
    LoginFormComponent,
    WelcomeContentComponent,
    AuthContentComponent,
    ContentComponent,
    InnerAppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [AxiosService, provideRouter(routes)],
  bootstrap: [AppComponent]
})
export class AppModule { }
