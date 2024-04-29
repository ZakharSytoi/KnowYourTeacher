import {UniversityDto} from "./UniversityDto";

export interface UserResponseDto{
    id: number;
    nickname: string;
    email: string;
    fieldOfStudies: string;
    university: UniversityDto;
}