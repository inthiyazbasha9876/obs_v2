import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpreportsComponent } from './empreports.component';

describe('EmpreportsComponent', () => {
  let component: EmpreportsComponent;
  let fixture: ComponentFixture<EmpreportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmpreportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmpreportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
