export class UserRegistrationRequestDto {
	private nickname: string = '';
    private universityId: number = 1;
    private fieldOfStudies: string = '';
    private email: string = '';
    private password: string = '';

	// TODO set university id form constructor instead of implicit 1 when university 
	// drop down menu is ready
	
	constructor($nickname: string , $universityId: number , $fieldOfStudies: string , $email: string , $password: string ) {
		this.nickname = $nickname;
		this.universityId = 1;
		this.fieldOfStudies = $fieldOfStudies;
		this.email = $email;
		this.password = $password;
	}
  }
  