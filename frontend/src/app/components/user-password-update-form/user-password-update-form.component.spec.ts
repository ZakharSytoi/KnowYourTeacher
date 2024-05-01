import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPasswordUpdateFormComponent } from './user-password-update-form.component';

describe('UserPasswordUpdateFormComponent', () => {
  let component: UserPasswordUpdateFormComponent;
  let fixture: ComponentFixture<UserPasswordUpdateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserPasswordUpdateFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UserPasswordUpdateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
