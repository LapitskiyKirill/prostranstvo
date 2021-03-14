import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {AuthComponent} from './component/auth/auth.component';
import {AppRoutingModule} from './app-routing.module';
import { MainComponent } from './component/main/main.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CreateComponent } from './component/create/create.component';
import { LotInfoComponent } from './component/lot-info/lot-info.component';
import { EditLotComponent } from './component/edit-lot/edit-lot.component';
import { ProfileComponent } from './component/profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    MainComponent,
    CreateComponent,
    LotInfoComponent,
    EditLotComponent,
    ProfileComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
