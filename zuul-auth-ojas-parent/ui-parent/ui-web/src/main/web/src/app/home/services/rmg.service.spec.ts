import { TestBed } from '@angular/core/testing';

import { RmgService } from './rmg.service';

describe('RmgService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RmgService = TestBed.get(RmgService);
    expect(service).toBeTruthy();
  });
});
