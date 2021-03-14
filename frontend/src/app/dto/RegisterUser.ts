export class RegisterUser {
  public username: string;
  public password: string;
  public mail: string;

  constructor(username: string, password: string, mail: string) {
    this.username = username;
    this.password = password;
    this.mail = mail;
  }
}
