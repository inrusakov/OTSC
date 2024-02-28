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
import { InnerContentComponent } from './inner/inner-content/inner-content.component';

import { AxiosService } from './axios.service';
import { InnerHeaderComponent } from './inner/inner-header/inner-header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InnerContentChallenge } from './inner/inner-content/challenge/challenge.component';
import { InnerChallengeModal } from './inner/inner-content/challenge-modal/challenge-modal.component';
import { InnerContentChallengeObserver } from './inner/inner-content/challenge-observer/challenge-observer.component';
import { InnerProfileModal } from './inner/inner-profile/profile.component';

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
    InnerHeaderComponent,
    InnerContentComponent,
    InnerContentChallenge,
    InnerContentChallengeObserver
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NgbModule,
    InnerChallengeModal,
    InnerProfileModal
  ],
  providers: [AxiosService, provideRouter(routes)],
  bootstrap: [AppComponent]
})
export class AppModule { }
