import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourceBillingComponent } from './resource-billing.component';

describe('ResourceBillingComponent', () => {
  let component: ResourceBillingComponent;
  let fixture: ComponentFixture<ResourceBillingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourceBillingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceBillingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
