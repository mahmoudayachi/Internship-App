import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { ownershipguardGuard } from './ownershipguard.guard';

describe('ownershipguardGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => ownershipguardGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
