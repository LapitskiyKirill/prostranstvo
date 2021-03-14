export class UserDetails {
  constructor(phoneNumber: string, firstname: string, lastname: string) {
    this.phoneNumber = phoneNumber;
    this.firstname = firstname;
    this.lastname = lastname;
  }
  public phoneNumber: string;
  public firstname: string;
  public lastname: string;
}
