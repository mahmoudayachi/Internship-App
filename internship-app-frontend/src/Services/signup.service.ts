import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const BASE_URL ="http://localhost:8080/";
@Injectable({
  providedIn: 'root'
})
export class SignupService {

  constructor( private http:HttpClient) { 

  }

  signupstudent(signuprequest:any):Observable<any>{
    return this.http.post(BASE_URL+"api/signup/student",signuprequest)
  }
  signupcompany(signuprequest:any):Observable<any>{
    return this.http.post(BASE_URL+"api/signup/company",signuprequest)
  }

  
}
