import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectPsaComponent } from './project-psa.component';

describe('ProjectPsaComponent', () => {
  let component: ProjectPsaComponent;
  let fixture: ComponentFixture<ProjectPsaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectPsaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectPsaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
