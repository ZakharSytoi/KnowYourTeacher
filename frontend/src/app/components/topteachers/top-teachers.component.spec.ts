import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopTeachersComponent } from './top-teachers.component';

describe('TopteachersComponent', () => {
  let component: TopTeachersComponent;
  let fixture: ComponentFixture<TopTeachersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopTeachersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TopTeachersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
