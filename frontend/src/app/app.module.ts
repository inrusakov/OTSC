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

import { ButtonLogout } from './elements/button-logout/button-logout.component';
import { InnerAppComponent } from './inner/inner-app/inner-app.component'; 
import { InnerContentComponent } from './inner/inner-content/inner-content.component';

import { AxiosService } from './axios.service';
import { InnerHeaderComponent } from './inner/header/inner-header.component';
import { NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { InnerContentChallenge } from './inner/inner-content/challenge-table-content/challenge.component';
import { InnerChallengeModal } from './elements/bet/challenge-create-modal/challenge-modal.component';
import { InnerContentChallengeObserver } from './inner/inner-content/challenge-observer/challenge-observer.component';
import { InnerProfileModal } from './inner/profile/profile.component';
import { InnerContentChallengeNotFound } from './inner/inner-content/challenge-404/challenge-404.component';
import { NgbdAlertSelfclosing } from './elements/self-closing-alert/alert.component';
import { ProfileParagraph } from './elements/profile-paragraph/profile-p.component';
import { ImageView } from './elements/image-view/image-view.component';
import { Chat } from './elements/bet/chat/chat.component';
import { ChatMessage } from './elements/bet/chat-message/message.component';
import { MessageModal } from './elements/message-modal/message-modal.component';
import { AddOpponentModal } from './elements/bet/add-opponent-modal/add-opp-modal.component';
import { AddJudgeModal } from './elements/bet/add-judge-modal/add-judge-modal.component';
import { ResolveChallengeModal } from './elements/bet/resolve-challenge-modal/resolve-modal.component';
import { CopyButton } from './elements/copy-button/copy-button.component';
import { AvatarComponent } from './elements/avatar/avatar.component';
import { HistoryComponent } from './inner/inner-content/challenge-history/challenge-history.component';
import { ContestContentComponent } from './inner/contest-content/contest-content.component';
import { InnerContestModal } from './elements/contest/contest-create-modal/contest-modal.component';
import { InnerContestContent } from './inner/contest-content/contest-table-content/contest.component';
import { InnerContentContestObserver } from './inner/contest-content/contest-observer/contest-observer.component';
import { BetInListComponent } from './elements/contest/bet-in-contest-list/bet-in-list.component';
import { AddBetModal } from './elements/contest/add-bet-modal/add-bet-modal.component';
import { AddContestJudgeModal } from './elements/contest/add-contest-judge-modal/add-judge-modal.component';
import { AddContestOpponentModal } from './elements/contest/add-contest-opp-modal/add-opp-modal.component';

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
    InnerContentChallengeObserver,
    InnerContentChallengeNotFound,
    ProfileParagraph,
    ImageView,
    Chat,
    ChatMessage,
    AvatarComponent,
    HistoryComponent,
    ContestContentComponent,
    InnerContentContestObserver
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NgbModule,
    InnerChallengeModal,
    InnerProfileModal,
    NgbdAlertSelfclosing,
    MessageModal,
    InnerContentChallenge,
    NgbPaginationModule,
    AddOpponentModal,
    AddJudgeModal,
    ResolveChallengeModal,
    CopyButton,
    InnerContestModal,
    InnerContestContent,
    BetInListComponent,
    AddBetModal,
    AddContestJudgeModal,
    AddContestOpponentModal
  ],
  providers: [AxiosService, provideRouter(routes)],
  bootstrap: [AppComponent]
})
export class AppModule { }
