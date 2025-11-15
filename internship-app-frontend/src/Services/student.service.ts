import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const BASE_URL ="http://localhost:8080/student"
@Injectable({
  providedIn: 'root'
})
export class StudentService {
  

  constructor(private http :HttpClient) {

   }

  GetstudentByid(id :any ):Observable<any>{
    return this.http.get("http://localhost:8080/student/"+`${id}`)
  }
  updatestudentprofile(id :any ,profile :any):Observable<any>{
    return this.http.put(BASE_URL+"/update/student/"+`${id}`,profile)
  }

  Applytoffer(application : any):Observable<any>{
    return this.http.post(BASE_URL+'/job',application)
  }

  GetApplicationBystudent(id:any):Observable<any>{
    return this.http.get(BASE_URL+'/application/'+`${id}`)
  }
  GetAcceptedApplications(id :any):Observable<any>{
    return this.http.get(BASE_URL+'/application/'+`${id}`+'/accepted')
  }

  GetAllInternshipPosts():Observable<any>{
    return this.http.get(BASE_URL+'/search/all')
  }
  
  GetInternshipPostById(id:any):Observable<any>{
    return this.http.get(BASE_URL+'/search/'+`${id}`)
  }

  searchInternships(filters: {
    status?: string;
    type?: string;
    location?: string;
    duration?:string
    search?: string;
    page?: number;
    size?: number;
    sortBy?: string;
    sortDir?: string;
  }): Observable<any> {
    let params = new HttpParams();

    Object.keys(filters).forEach(key => {
      const value = (filters as any)[key];
      if (value !== null && value !== undefined && value !== '') {
        params = params.set(key, value);
      }
    });

    return this.http.get(`${BASE_URL}/search`, { params });
  }

  saveInternship(studentId: number, postId: number): Observable<any> {
    return this.http.post(`${BASE_URL}/${studentId}/save/${postId}`, {});
  }
  unsaveInternship(studentId: number, postId: number): Observable<any> {
    return this.http.delete(`${BASE_URL}/${studentId}/unsave/${postId}`);
  }
  
  getSavedInternships(studentId: number): Observable<any> {
    return this.http.get(`${BASE_URL}/${studentId}/saved`);
  }


}
