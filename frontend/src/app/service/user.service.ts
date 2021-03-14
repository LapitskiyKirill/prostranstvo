import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserDetails} from '../dto/UserDetails';
import {SERVER_PATH} from '../../globals';
import {PasswordChangeRequest} from '../dto/PasswordChangeRequest';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  updateDetails(userDetails: UserDetails): Observable<string> {
    return this.http.post<string>(SERVER_PATH + '/user/updateDetails', userDetails, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      }
    });
  }

  changePassword(passwordChangeRequest: PasswordChangeRequest): Observable<string> {
    return this.http.post<string>(SERVER_PATH + '/user/updatePassword', passwordChangeRequest, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      }
    });
  }
}
