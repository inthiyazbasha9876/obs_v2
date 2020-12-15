import { TestBed } from '@angular/core/testing';

import { LeaveserviceService } from './leaveservice.service';

describe('LeaveserviceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LeaveserviceService = TestBed.get(LeaveserviceService);
    expect(service).toBeTruthy();
  });
});
