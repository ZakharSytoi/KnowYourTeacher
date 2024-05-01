import {Component, Input} from '@angular/core';
import {NgClass} from "@angular/common";

@Component({
  standalone: true,
  selector: 'app-hovering-input-label',
  templateUrl: './hovering-input-label.component.html',
  imports: [
    NgClass
  ],
  styleUrl: './hovering-input-label.component.scss'
})
export class HoveringInputLabelComponent {
  @Input() show: boolean = false;
  @Input() labelText: string = "Text"
}
