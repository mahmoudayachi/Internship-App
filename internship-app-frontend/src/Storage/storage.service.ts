import { Injectable } from '@angular/core';


const Token ="token";
const USER ="user";
@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }
  static saveToken(token:string):void{
    window.localStorage.removeItem(Token);
    window.localStorage.setItem(Token,token)
  }

  static saveUser(user:any):void{
    window.localStorage.removeItem(user);
    window.localStorage.setItem(USER,JSON.stringify(user));
  }

  static getToken(): any {
    return localStorage.getItem(Token);
  }
  
  static getUser(): any {
    return localStorage.getItem(USER);
  }


  static getUserRole():any{
   const role   = localStorage.getItem(USER)
      if(role?.includes("STUDENT")){
        return "STUDENT"
      }
      else
      return "COMPANY"
  }

  static isAdminLoggedIn():boolean{
    if(this.getToken()=== null)
      return false

     const role:string = this.getUserRole();
     return role =="ADMIN";
  }

  static isStudentLoggedIn():boolean{

    if(this.getToken()=== null)
      return false

     const role:string = this.getUserRole();
     return role =='STUDENT';
  }


  static isCompanyLoggedIn():boolean{

    if(this.getToken()=== null)
      return false

     const role:string = this.getUserRole();
     return role == 'COMPANY'
  }

  static getUserId():string {
    const user = this.getUser();
    if(user == null)
      return "";
    return user.id;
  }
  
  static logout() :void{
     window.localStorage.removeItem(Token);
     window.localStorage.removeItem(USER);
  }
}
