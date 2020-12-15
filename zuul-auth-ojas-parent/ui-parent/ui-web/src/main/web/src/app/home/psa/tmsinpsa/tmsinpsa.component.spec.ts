import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TmsinpsaComponent } from './tmsinpsa.component';

describe('TmsinpsaComponent', () => {
  let component: TmsinpsaComponent;
  let fixture: ComponentFixture<TmsinpsaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TmsinpsaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TmsinpsaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
