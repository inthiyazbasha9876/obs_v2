import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { C2hComponent } from './c2h.component';

describe('C2hComponent', () => {
  let component: C2hComponent;
  let fixture: ComponentFixture<C2hComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ C2hComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(C2hComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
