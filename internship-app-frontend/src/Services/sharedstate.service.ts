import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedstateService {
  

  private applicationChangedSource = new BehaviorSubject<boolean>(false);
  applicationChanged$ = this.applicationChangedSource.asObservable();

  notifyChange() {
    this.applicationChangedSource.next(true);
  }
  constructor() { }
}
