export class UserRegistrationRequestDto {
    private nickname: string = "";
    private universityId: number = 1;
    private fieldOfStudies: string = "";
    private email: string = "";
    private password: string = "";

    constructor(
        $nickname: string,
        $universityId: number,
        $fieldOfStudies: string,
        $email: string,
        $password: string
    ) {
        this.nickname = $nickname;
        this.universityId = $universityId;
        this.fieldOfStudies = $fieldOfStudies;
        this.email = $email;
        this.password = $password;
    }
}
