import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const BASE_URL ="http://localhost:8080/api";
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) {

   }
   
   login(loginrequest:any,accounttype:string):Observable<any>{
     if(accounttype=="student")
     return this.http.post(BASE_URL+"/student/login",loginrequest)
     else{
       return this.http.post(BASE_URL+"/company/login",loginrequest)
     }
   }
  

}
