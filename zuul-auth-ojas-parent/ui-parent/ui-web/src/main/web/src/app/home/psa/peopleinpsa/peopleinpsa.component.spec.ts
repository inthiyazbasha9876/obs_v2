import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PeopleinpsaComponent } from './peopleinpsa.component';

describe('PeopleinpsaComponent', () => {
  let component: PeopleinpsaComponent;
  let fixture: ComponentFixture<PeopleinpsaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PeopleinpsaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PeopleinpsaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
