import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PsareportsComponent } from './psareports.component';

describe('PsareportsComponent', () => {
  let component: PsareportsComponent;
  let fixture: ComponentFixture<PsareportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PsareportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PsareportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
