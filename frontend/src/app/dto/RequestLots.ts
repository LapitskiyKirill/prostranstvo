import {Pageable} from './Pageable';
import {Filter} from './Filter';

export class RequestLots {
  public pageable: Pageable;
  public filter: Filter;

  constructor(pageable: Pageable, filter: Filter) {
    this.pageable = pageable;
    this.filter = filter;
  }
}
