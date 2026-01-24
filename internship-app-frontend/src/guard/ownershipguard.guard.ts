import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { StorageService } from '../Storage/storage.service';

export const ownershipguardGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  const routeId = Number(route.paramMap.get('id'));
  const userId = StorageService.getUserId();

  if (!userId || routeId !== Number(userId)) {
    router.navigate(['/login']);
    return false;
  }

  return true;
};
