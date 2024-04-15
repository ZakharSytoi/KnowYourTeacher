import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimpleReviewComponent } from './simple-review.component';

describe('SimpleReviewComponent', () => {
  let component: SimpleReviewComponent;
  let fixture: ComponentFixture<SimpleReviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SimpleReviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SimpleReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
