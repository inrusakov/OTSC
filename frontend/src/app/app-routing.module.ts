import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContentComponent } from './outer/content/content.component';
import { InnerAppComponent } from './inner/inner-app/inner-app.component';
import { InnerContentChallengeObserver } from './inner/inner-content/challenge-observer/challenge-observer.component';
import { InnerContentComponent } from './inner/inner-content/inner-content.component';
import { InnerProfileModal } from './inner/inner-profile/profile.component';
import { InnerContentChallengeNotFound } from './inner/inner-content/challenge-404/challenge-404.component';

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
        path: '404',
        component: InnerContentChallengeNotFound,
      },{
        path: 'challenge/:id',
        component: InnerContentChallengeObserver,
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
