import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { StorageService } from '../Storage/storage.service';


export const roleGuard: CanActivateFn = (route, state) => {
   
  const router = inject(Router);

  const expectedRole = route.data?.['role'];
  const userRole = StorageService.getUserRole();


  if (!userRole) {
    router.navigate(['/login']);
    return false;
  }

  
  if (expectedRole && userRole !== expectedRole) {
    router.navigate(['/login']);
    return false;
  }
    
  return true;
};
