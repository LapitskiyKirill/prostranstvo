import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Lot} from '../../dto/Lot';
import {LotService} from '../../service/lot.service';
import {EditLot} from '../../dto/EditLot';
import {AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-edit-lot',
  templateUrl: './edit-lot.component.html',
  styleUrls: ['./edit-lot.component.scss']
})
export class EditLotComponent implements OnInit {
  public lotId: number;
  public lot: Lot;
  public selectedFiles: File[] = [];
  public deletingPictures: number[] = [];

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
              private lotService: LotService) {
    this.activatedRoute.params.subscribe(params => this.lotId = params['lotId']);
    this.lotService.getById(this.lotId).subscribe(lot => {
      this.lot = lot;
      this.lot.picturesList.forEach(lotPicture => lotPicture.image = 'data:image/jpeg;base64,' + lotPicture.image);
    });

  }

  ngOnInit(): void {
  }

  edit(): void {
    const editLot = new FormData();
    console.log(this.selectedFiles);
    this.deletingPictures.forEach(pictureId => {
      editLot.append('deletedPictures', pictureId.toString());
    });
    this.selectedFiles.forEach(file => {
      editLot.append('files', file);
    });
    editLot.append('description', this.lot.description);
    editLot.append('name', this.lot.name);
    editLot.append('price', this.lot.price.toString());
    editLot.append('id', this.lotId.toString());
    this.lotService.edit(editLot).subscribe(s => console.log(s));
    this.router.navigate(['/main']);
  }

  public onFileChanged(event): void {
    for (const file of event.target.files) {
      this.selectedFiles.push(file);
    }
  }

  addDeletingPicture(id: number): void {
    this.deletingPictures.push(id);
  }

  logout(): void {
    this.authService.logout().subscribe(() => this.router.navigate(['/auth']));
  }

  close(): void {
    this.lotService.close(this.lotId).subscribe(s => this.router.navigate(['/main']));
  }
}
