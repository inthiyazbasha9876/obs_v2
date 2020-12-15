import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PsadashboardComponent } from './psadashboard.component';

describe('PsadashboardComponent', () => {
  let component: PsadashboardComponent;
  let fixture: ComponentFixture<PsadashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PsadashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PsadashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
