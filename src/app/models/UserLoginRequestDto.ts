export class UserLoginRequestDto {
  private username: string = '';
  private password: string = '';
  
  constructor($email: string, $password: string) {
    this.username = $email;
    this.password = $password;
  }
}
