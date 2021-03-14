import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../service/auth.service';
import {PasswordChangeRequest} from '../../dto/PasswordChangeRequest';
import {User} from '../../dto/User';
import {UserService} from '../../service/user.service';
import {UserDetails} from '../../dto/UserDetails';
import {Router} from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public user: User;
  public passwordChangeRequest: PasswordChangeRequest = new PasswordChangeRequest();

  constructor(private authService: AuthService,
              private router: Router,
              private userService: UserService) {
    this.authService.validate(localStorage.getItem('token')).subscribe(user => this.user = user);
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.authService.logout().subscribe(() => this.router.navigate(['/auth']));
  }

  edit(): void {

  }

  updateDetails(): void {
    if (this.user.firstname != null || this.user.lastname != null || this.user.phoneNumber != null) {
      this.userService.updateDetails(new UserDetails(this.user.phoneNumber, this.user.firstname, this.user.lastname))
        .subscribe(s => console.log(s));
    }
  }

  changePassword(): void {
    if (this.passwordChangeRequest.newPassword != null && this.passwordChangeRequest.oldPassword != null) {
      this.userService.changePassword(this.passwordChangeRequest).subscribe(s => console.log(s));
    }

  }
}
