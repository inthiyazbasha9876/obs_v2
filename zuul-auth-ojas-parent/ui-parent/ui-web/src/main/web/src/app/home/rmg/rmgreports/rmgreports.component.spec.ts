import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RmgreportsComponent } from './rmgreports.component';

describe('RmgreportsComponent', () => {
  let component: RmgreportsComponent;
  let fixture: ComponentFixture<RmgreportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RmgreportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RmgreportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
