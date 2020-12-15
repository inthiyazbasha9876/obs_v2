import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SbubusinessunitComponent } from './sbubusinessunit.component';

describe('SbubusinessunitComponent', () => {
  let component: SbubusinessunitComponent;
  let fixture: ComponentFixture<SbubusinessunitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SbubusinessunitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SbubusinessunitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
