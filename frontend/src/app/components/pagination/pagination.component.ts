import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
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
export class PaginationComponent implements OnChanges{
  @Input() currentPageNumber!: number;
  @Input() totalPages!: number;
  currentRange!: { pagesToTheLeft: boolean, pagesToTheRight: boolean, range: number[], currentPageNumber: number }

  @Output() loadPageEvent = new EventEmitter<number>();

  goToPage(pageNumber: number){
    this.loadPageEvent.emit(pageNumber);
  }

  ngOnChanges(): void {
    this.currentRange = this.getCurrentRange(this.totalPages, this.currentPageNumber);
  }

  range = (start: number, stop: number) =>
      Array.from({length: (stop - start)}, (_, i) => start + i);
  getCurrentRange(totalPages: number, currentPage: number) {
    if (totalPages <= 10) {
      return {
        pagesToTheLeft: false,
        pagesToTheRight: false,
        range: this.range(0, totalPages),
        currentPageNumber: currentPage
      }
    } else {
      let right = 0;
      let left = 0;
      let pagesToTheLeft = true;
      let pagesToTheRight = true;
      if (totalPages >= 10) {
        right = currentPage + 5
        left = currentPage - 4;
        if (left <= 0) {
          right = currentPage + 5 - left;
          left = 0;
          pagesToTheLeft = false;
        } else if (right >= totalPages) {
          left = currentPage - right + totalPages - 4;
          right = totalPages
          pagesToTheRight = false;
        }
      }
      return {
        pagesToTheLeft: pagesToTheLeft,
        pagesToTheRight: pagesToTheRight,
        range: this.range(left, right),
        currentPageNumber: currentPage
      }
    }
  }


}
