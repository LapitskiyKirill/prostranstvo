import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {LotService} from '../../service/lot.service';
import {Lot} from '../../dto/Lot';
import * as moment from 'moment';
import {AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-lot-info',
  templateUrl: './lot-info.component.html',
  styleUrls: ['./lot-info.component.css']
})
export class LotInfoComponent implements OnInit {
  public lotId: number;
  public lot: Lot;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
              private lotService: LotService) {
    console.log('start');
    this.activatedRoute.params.subscribe(params => this.lotId = params['lotId']);
    console.log(this.lotId);
    this.lotService.getById(this.lotId).subscribe(lot => {
      this.lot = lot;
      this.lot.picturesList.forEach(lotPicture => lotPicture.image = 'data:image/jpeg;base64,' + lotPicture.image);
      console.log(lot.userDto);
    });
  }

  ngOnInit(): void {
  }

  getFormattedDate(creationDate: Date): string {
    return moment(creationDate).format('LLLL');
  }

  logout(): void {
    this.authService.logout().subscribe(() => this.router.navigate(['/auth']));
  }
}
