import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContentComponent } from './outer/content/content.component';
import { InnerAppComponent } from './inner/inner-app/inner-app.component';
import { InnerContentChallengeObserver } from './inner/inner-content/challenge-observer/challenge-observer.component';
import { InnerContentComponent } from './inner/inner-content/inner-content.component';
import { InnerProfileModal } from './inner/profile/profile.component';
import { InnerContentChallengeNotFound } from './inner/inner-content/challenge-404/challenge-404.component';
import { HistoryComponent } from './inner/inner-content/challenge-history/challenge-history.component';
import { ContestContentComponent } from './inner/contest-content/contest-content.component';
import { InnerContentContestObserver } from './inner/contest-content/contest-observer/contest-observer.component';

export const routes: Routes = [
  { path: '', component: ContentComponent },
  {
    path: 'app',
    component: InnerAppComponent,
    children: [
      {
        path: '',
        component: InnerContentComponent,
      },
      {
        path: 'contest',
        component: ContestContentComponent,
      },
      {
        path: '404',
        component: InnerContentChallengeNotFound,
      },
      {
        path: 'history',
        component: HistoryComponent,
      },
      {
        path: 'challenge/:id',
        component: InnerContentChallengeObserver,
      },
      {
        path: 'contest/:id',
        component: InnerContentContestObserver,
      },
      {
        path: 'profile',
        component: InnerProfileModal
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
