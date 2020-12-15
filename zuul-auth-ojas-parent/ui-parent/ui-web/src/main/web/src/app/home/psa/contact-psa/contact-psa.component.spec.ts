import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactPsaComponent } from './contact-psa.component';

describe('ContactPsaComponent', () => {
  let component: ContactPsaComponent;
  let fixture: ComponentFixture<ContactPsaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContactPsaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactPsaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
