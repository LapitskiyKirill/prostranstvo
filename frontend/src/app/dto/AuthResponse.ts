export class AuthResponse {
  public username: string;
  public jwt: string;

  constructor(jwt: string, username: string) {
    this.jwt = jwt;
    this.username = username;
  }
}
