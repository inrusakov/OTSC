import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContentComponent } from './outer/content/content.component';
import { InnerAppComponent } from './inner/inner-app/inner-app.component';

export const routes: Routes = [
  {path: '', component:ContentComponent},
  {path: 'app', component:InnerAppComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
