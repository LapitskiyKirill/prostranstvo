import {Injectable} from '@angular/core';
import {SERVER_PATH} from '../../globals';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RequestLots} from '../dto/RequestLots';
import {Lot} from '../dto/Lot';
import {Pageable} from '../dto/Pageable';
import {EditLot} from '../dto/EditLot';

@Injectable({
  providedIn: 'root'
})
export class LotService {

  constructor(private http: HttpClient) {
  }

  create(newLot: FormData): Observable<string> {
    return this.http.post<string>(SERVER_PATH + '/lot/create', newLot, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      },
      params: {
        userId: localStorage.getItem('userId')
      }
    });
  }

  getLotsByUser(pageable: Pageable): Observable<Lot[]> {
    return this.http.post<Lot[]>(SERVER_PATH + '/lot/getAllByUser', pageable, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      },
      params: {userId: localStorage.getItem('userId')}
    });
  }

  getAll(requestLots: RequestLots): Observable<Lot[]> {
    return this.http.post<Lot[]>(SERVER_PATH + '/lot/getAll', requestLots, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      }
    });
  }

  getById(id: number): Observable<any> {
    return this.http.post<Lot>(SERVER_PATH + '/lot/getById', null, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      },
      params: {
        lotId: id.toString()
      }
    });
  }

  edit(editLot: FormData): Observable<string> {
    return this.http.post<string>(SERVER_PATH + '/lot/edit', editLot, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      }
    });
  }

  close(id: number): Observable<string> {
    return this.http.post<string>(SERVER_PATH + '/lot/close', null, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      },
      params: {
        id: id.toString()
      }
    });
  }
}
