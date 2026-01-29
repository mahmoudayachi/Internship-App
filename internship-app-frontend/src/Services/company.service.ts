import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const BASE_URL ="http://localhost:8080/Post";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http :HttpClient) {
    
   }
   GetInternshippostbycompanyById(id :any):Observable<any>{
    return this.http.get("http://localhost:8080/company/post/"+`${id}`)

  }

   GetcompanyById(id :any):Observable<any>{
     return this.http.get("http://localhost:8080/company/"+`${id}`)

   }

   AddInternshipPost(InternshipPost :any):Observable<any>{
     return this.http.post("http://localhost:8080/company" + "/create", InternshipPost);
   }


   DeleteInternshipPost(id :any):Observable<any>{
     return this.http.delete("http://localhost:8080/company" + "/delete/" + `${id}`,{ responseType: 'text' });
   }

   UpdateProfile(id:any, updateprofile:any):Observable<any>{
     return this.http.put("http://localhost:8080/company/updateprofile/" +`${id}`,updateprofile)
    }


    GetApplicationsByCompany(id :any):Observable<any>{
      return this.http.get("http://localhost:8080/company/application/"+`${id}`)
    }

    AcceptOffer( id :any ):Observable<any>{
      return this.http.put("http://localhost:8080/company/"+`${id}`+"/accept",null)
    }
    RejectOffer( id :any ):Observable<any>{
      return this.http.put("http://localhost:8080/company/"+`${id}`+"/reject",null)
    }
    
    UpdateInternshipPost(id :any , status:any ):Observable<any>{
      return this.http.put("http://localhost:8080/company/update/post/"+`${id}`+ "/"+`${status}`,null)
    }
   
        
  }
