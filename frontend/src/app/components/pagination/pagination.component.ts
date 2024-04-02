import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgClass, NgStyle} from "@angular/common";

@Component({
  standalone: true,
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  imports: [
    NgClass,
    NgStyle
  ],
  styleUrl: './pagination.component.scss'
})
export class PaginationComponent{
  @Input() currentPageNumber!: number;
  @Input() totalPages!: number;
  @Input() currentRange!: { pagesToTheLeft: boolean, pagesToTheRight: boolean, range: number[], currentPage: number }

  @Output() loadPageEvent = new EventEmitter<number>();

  goToPage(pageNumber: number){
    this.loadPageEvent.emit(pageNumber);
  }

}
