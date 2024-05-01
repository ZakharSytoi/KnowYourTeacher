import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HoveringInputLabelComponent } from './hovering-input-label.component';

describe('HoveringInputLabelComponent', () => {
  let component: HoveringInputLabelComponent;
  let fixture: ComponentFixture<HoveringInputLabelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HoveringInputLabelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HoveringInputLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
