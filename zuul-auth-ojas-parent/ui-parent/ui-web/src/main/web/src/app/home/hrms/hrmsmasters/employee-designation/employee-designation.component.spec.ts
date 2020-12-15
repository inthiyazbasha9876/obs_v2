import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeDesignationComponent } from './employee-designation.component';

describe('EmployeeDesignationComponent', () => {
  let component: EmployeeDesignationComponent;
  let fixture: ComponentFixture<EmployeeDesignationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeeDesignationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeDesignationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
