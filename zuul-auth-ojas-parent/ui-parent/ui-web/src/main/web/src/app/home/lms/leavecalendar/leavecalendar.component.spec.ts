import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeavecalendarComponent } from './leavecalendar.component';

describe('LeavecalendarComponent', () => {
  let component: LeavecalendarComponent;
  let fixture: ComponentFixture<LeavecalendarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeavecalendarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeavecalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
