import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../Services/auth.service';
import { StorageService } from '../../Storage/storage.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  selectedForm: 'student' | 'company' = 'student';
  loginform !: FormGroup
  errormessage: any
  constructor(private authservice :AuthService, private form :FormBuilder , private router :Router ){
    this.loginform = form.group({
      email: [null,Validators.required],
      password:[null,Validators.required],
    })

  }
  showcurrentaccounttype(){
   console.log(this.selectedForm)
  }
  login(){
    console.log(this.selectedForm)
    if(!this.loginform.valid ){
      alert("all fields must be filled ")
    } 
    else {
      this.authservice.login(this.loginform.value,this.selectedForm).subscribe({
        next:(res=>{
          console.log(res)
          if(res.userId!=null){
            alert(" user logged in successfully")
            const user = {
              id: res.userId,
              role: res.userRole
            };
            StorageService.saveUser(user);
            StorageService.saveToken(res.jwt);
            console.log(StorageService.getUserRole())
            if(StorageService.isStudentLoggedIn()){
              this.router.navigateByUrl("/student-dashboard")
            } 
            else if(StorageService.isCompanyLoggedIn()){
              this.router.navigateByUrl("/company-dashboard")
            }
          }
        }),
        error :(err)=>{
         this.errormessage =err.err
         alert(this.errormessage) 
        }
      })
    }
  
  
  }
  
}
