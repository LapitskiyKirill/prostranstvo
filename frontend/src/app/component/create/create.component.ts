import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LotService} from '../../service/lot.service';
import {Router} from '@angular/router';
import {AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {
  public selectedFiles: File[] = [];
  public description: string;
  public name: string;
  public price: number;
  public imgUrls: any[] = [];

  constructor(private router: Router,
              private authService: AuthService,
              private lotService: LotService) {
  }

  ngOnInit(): void {
  }

  public onFileChanged(event): void {
    for (const file of event.target.files) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.imgUrls.push(reader.result);
      };
      this.selectedFiles.push(file);
    }
  }

  create(): void {
    const newLot = new FormData();
    console.log(this.selectedFiles);
    this.selectedFiles.forEach(file => {
      newLot.append('files', file);
    });
    newLot.append('description', this.description);
    newLot.append('name', this.name);
    newLot.append('price', this.price.toString());
    this.lotService.create(newLot).subscribe(s => console.log(s));
    this.router.navigate(['/main']);
  }

  logout(): void {
    this.authService.logout().subscribe(() => this.router.navigate(['/auth']));
  }

  deselectPicture(lotPicture: any): void {
    this.imgUrls = this.imgUrls.filter(pic => pic !== lotPicture);
  }
}
