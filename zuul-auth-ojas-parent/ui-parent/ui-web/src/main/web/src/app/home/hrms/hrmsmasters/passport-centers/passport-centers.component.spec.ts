import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PassportCentersComponent } from './passport-centers.component';

describe('PassportCentersComponent', () => {
  let component: PassportCentersComponent;
  let fixture: ComponentFixture<PassportCentersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PassportCentersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PassportCentersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
