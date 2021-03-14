import {Component} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {AuthService} from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(
    private router: Router,
    private authService: AuthService
  ) {
    this.router.events.subscribe(
      (event: any) => {
        if (event instanceof NavigationEnd) {
          this.autoLogin(event.url);
        }
      });
  }

  autoLogin(url: string): void {
    const lsToken = localStorage.getItem('token');
    if (lsToken) {
      this.authService.validate(lsToken).subscribe(user => {
        localStorage.setItem('userId', user.id.toString());
        if (url === '/' || url === '/auth') {
          this.router.navigate(['/main']);
        } else {
          this.router.navigate([url]);
        }
        console.log('autologin');
      }, e => {
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        this.router.navigate(['/auth']);
      });
    } else {
      this.router.navigate(['/auth']);
      console.log('token expired');
    }
  }
}
