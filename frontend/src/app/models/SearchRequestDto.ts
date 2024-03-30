export class SearchRequestDto {

    private _teacherName?: string;
    private _teacherSurname?: string;
    private _subject?: string;
    private _universityId?: string;
    private _searchType?: string;


    set teacherName(value: string) {
        this._teacherName = value;
    }

    set teacherSurname(value: string) {
        this._teacherSurname = value;
    }

    set subject(value: string) {
        this._subject = value;
    }

    get searchType(): string {
        return <string>this._searchType;
    }

    set searchType(value: string) {
        this._searchType = value;
    }

    set universityId(value: string) {
        this._universityId = value;
    }

    get teacherName(): string {
        return <string>this._teacherName;
    }

    get teacherSurname(): string {
        return <string>this._teacherSurname;
    }

    get subject(): string {
        return <string>this._subject;
    }

    get universityId(): string {
        return <string>this._universityId;
    }
}