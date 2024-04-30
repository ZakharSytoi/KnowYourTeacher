import {Component, Input} from '@angular/core';

@Component({
    standalone: true,
    selector: 'app-info-field',
    templateUrl: './info-field.component.html',
    styleUrl: './info-field.component.scss'
})
export class InfoFieldComponent {
    @Input() fieldName: String = 'Field name';
    @Input() fieldValue: String = 'Field value';
}
