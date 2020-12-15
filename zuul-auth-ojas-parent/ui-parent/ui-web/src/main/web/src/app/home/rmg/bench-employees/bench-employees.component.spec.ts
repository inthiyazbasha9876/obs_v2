import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BenchEmployeesComponent } from './bench-employees.component';

describe('BenchEmployeesComponent', () => {
  let component: BenchEmployeesComponent;
  let fixture: ComponentFixture<BenchEmployeesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BenchEmployeesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BenchEmployeesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
