import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShortHeaderComponent } from './short-header.component';

describe('ShortHeaderComponent', () => {
  let component: ShortHeaderComponent;
  let fixture: ComponentFixture<ShortHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShortHeaderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShortHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
