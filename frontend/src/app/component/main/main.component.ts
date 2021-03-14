import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LotService} from '../../service/lot.service';
import {RequestLots} from '../../dto/RequestLots';
import {Filter} from '../../dto/Filter';
import {Pageable} from '../../dto/Pageable';
import {Lot} from '../../dto/Lot';
import * as moment from 'moment';
import {AuthService} from '../../service/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  public lots: Lot[] = [];
  public userLots: Lot[] = [];
  public isUserLots = false;
  public pageNumber = 0;
  public filter = new Filter(null, null, true);

  constructor(private httpClient: HttpClient,
              private router: Router,
              private lotService: LotService,
              private authService: AuthService) {
    this.get(0);
    this.getLotsByUser(0);
  }

  ngOnInit(): void {
  }

  getLotsByUser(pageNumber: number): void {
    this.lotService.getLotsByUser(new Pageable(pageNumber, 4))
      .subscribe(
        lots => {
          lots.forEach(lot => {
            lot.picturesList.forEach(lotPicture => lotPicture.image = 'data:image/jpeg;base64,' + lotPicture.image);
          });
          this.userLots = lots;
        }
      );
  }

  get(pageNumber: number): void {
    this.lotService.getAll(new RequestLots(new Pageable(pageNumber, 4), this.filter))
      .subscribe(
        lots => {
          lots.forEach(lot => {
            lot.picturesList.forEach(lotPicture => lotPicture.image = 'data:image/jpeg;base64,' + lotPicture.image);
          });
          this.lots = lots;
        }
      );
  }

  showUserLots(b: boolean): void {
    this.isUserLots = b;
    this.pageNumber = 0;
    this.getLotsByUser(0);
  }

  setOrderBy(): void {
    this.filter.orderByPrice = !this.filter.orderByPrice;
  }

  getPage(pageNumber: number, b: boolean): void {
    if (pageNumber >= 0) {
      if (b) {
        this.pageNumber--;
      } else {
        this.pageNumber++;
      }
      this.get(pageNumber);
    }
  }

  getUserPage(pageNumber: number, b: boolean): void {
    if (pageNumber >= 0) {
      if (b) {
        this.pageNumber--;
      } else {
        this.pageNumber++;
      }
      this.getLotsByUser(pageNumber);
    }
  }

  getFormattedDate(creationDate: Date): string {
    return moment(creationDate).format('LLLL');
  }

  logout(): void {
    this.authService.logout().subscribe(() => this.router.navigate(['/auth']));
  }
}
