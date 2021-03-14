import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthUser} from '../dto/AuthUser';
import {RegisterUser} from '../dto/RegisterUser';
import {SERVER_PATH} from '../../globals';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private auth: AuthUser;

  constructor(private http: HttpClient) {
  }

  register(registerUser: RegisterUser): Observable<string> {
    return this.http.post<string>(SERVER_PATH + '/auth/signup', registerUser, {responseType: 'text' as 'json'});
  }
}
