import {AuthGuardGuard} from './services/auth-guard.guard';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {FrontPageComponent} from './components/front-page/front-page.component';
import {ProfileComponent} from './components/profile/profile.component';
import {UploadFilesComponent} from './components/upload-files/upload-files.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'description', component: UploadFilesComponent, canActivate: [AuthGuardGuard] },
  { path: 'register', component: RegisterComponent },
  { path: 'profile/:name', component: ProfileComponent, canActivate: [AuthGuardGuard] },
  { path: '', component: FrontPageComponent },
  { path: '**', component: NotFoundComponent },
];

export const AppRoutingModule = RouterModule.forRoot(routes);
