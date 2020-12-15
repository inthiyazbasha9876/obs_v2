import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeexperienceComponent } from './employeeexperience.component';

describe('EmployeeexperienceComponent', () => {
  let component: EmployeeexperienceComponent;
  let fixture: ComponentFixture<EmployeeexperienceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeeexperienceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeexperienceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
