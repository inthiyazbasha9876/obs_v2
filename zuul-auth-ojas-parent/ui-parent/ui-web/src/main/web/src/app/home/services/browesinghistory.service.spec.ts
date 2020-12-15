import { TestBed } from '@angular/core/testing';

import { BrowesinghistoryService } from './browesinghistory.service';

describe('BrowesinghistoryService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BrowesinghistoryService = TestBed.get(BrowesinghistoryService);
    expect(service).toBeTruthy();
  });
});
