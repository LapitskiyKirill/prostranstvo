import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../service/auth.service';
import {AuthUser} from '../../dto/AuthUser';
import {RegisterService} from '../../service/register.service';
import {RegisterUser} from '../../dto/RegisterUser';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {
  public authUser: AuthUser = new AuthUser(null, null, false);
  public registerUser: RegisterUser = new RegisterUser(null, null, null);
  public isAuth = true;
  public passwordConfirm: string;

  constructor(private authService: AuthService,
              private registerService: RegisterService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  authenticate(): void {
    if (this.authUser.password !== null &&
      this.authUser.username !== null) {
      this.authService.authenticate(this.authUser).subscribe(authResponse => {
        localStorage.setItem('token', authResponse.jwt);
        console.log(localStorage.getItem('token'));
        this.router.navigate(['/main']);
      });
    }
    this.authService.validate(localStorage.getItem('token')).subscribe(user => {
      localStorage.setItem('userId', user.id.toString());
      console.log(user.id.toString());
    });
  }

  register(): void {
    if (this.passwordConfirm === this.registerUser.password) {
      if (this.registerUser.password !== null &&
        this.registerUser.username !== null && this.registerUser.mail !== null) {
        this.registerService.register(this.registerUser).subscribe(s => {
          console.log(s);
          this.router.navigate(['/auth']);
        });
      }
    }
  }

  showFormAuth(): void {
    this.isAuth = true;

  }

  showFormReg(): void {
    this.isAuth = false;
  }

  logout(): void {
    this.authService.logout();
  }
}
