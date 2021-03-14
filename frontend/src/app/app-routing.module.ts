import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthComponent} from './component/auth/auth.component';
import {MainComponent} from './component/main/main.component';
import {CreateComponent} from './component/create/create.component';
import {LotInfoComponent} from './component/lot-info/lot-info.component';
import {EditLotComponent} from './component/edit-lot/edit-lot.component';
import {ProfileComponent} from './component/profile/profile.component';

const routes: Routes = [
  {
    path: 'auth',
    component: AuthComponent
  },
  {
    path: 'main',
    component: MainComponent
  },
  {
    path: 'create',
    component: CreateComponent
  },
  {
    path: 'lotInfo/:lotId',
    component: LotInfoComponent
  },
  {
    path: 'editLot/:lotId',
    component: EditLotComponent
  },
  {
    path: 'profile',
    component: ProfileComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
