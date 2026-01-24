import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const BASE_URL ="http://localhost:8080/api/admin/login";

@Injectable({
  providedIn: 'root'
})
export class AdminserviceService {

  constructor( private http:HttpClient) {

   }

   Adminlogin(loginrequest:any):Observable<any>{
     return this.http.post(BASE_URL,loginrequest)

   }
   GetAllstudents():Observable<any>{
     return this.http.get("http://localhost:8080/Admin/all/students")
   }
   GetAllcompanies():Observable<any>{
    return this.http.get("http://localhost:8080/Admin/all/companies")
  }
   GetAllinternshipposts():Observable<any>{
    return this.http.get("http://localhost:8080/Admin/all/internshipposts")
  }
  updateaccountstatus(id:any,status:any):Observable<any>{
    return this.http.put("http://localhost:8080/Admin/update/status/"+`${id}`+"/"+`${status}`,null)

  }
  updateCompanyaccountStatus(id:any,status:any):Observable<any>{
    return this.http.put("http://localhost:8080/Admin/update/company/status/"+`${id}`+"/"+`${status}`,null)
  }
  
  
}
