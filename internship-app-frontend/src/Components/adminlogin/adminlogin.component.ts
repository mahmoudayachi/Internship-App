import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminserviceService } from '../../Services/adminservice.service';
import { StorageService } from '../../Storage/storage.service';
@Component({
  selector: 'app-adminlogin',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './adminlogin.component.html',
  styleUrl: './adminlogin.component.css'
})
export class AdminloginComponent {


  loginform !:FormGroup
  errormessage:any
  constructor(private authservice :AdminserviceService, private form :FormBuilder , private router :Router ){
   this.loginform = form.group({
     email: [null,Validators.required],
     password:[null,Validators.required],
   })

 }

  adminlogin(){
    console.log(this.loginform)
    if(!this.loginform.valid ){
      alert("all fields must be filled ")
    } 
    else {
      this.authservice.Adminlogin(this.loginform.value).subscribe({
        next:(res=>{
          console.log(res)
          if(res.userId!=null){
            const user = {
              id: res.userId,
              role: res.userRole
            };
            StorageService.saveUser(user);
            StorageService.saveToken(res.jwt);
            console.log(StorageService.getUserRole())
            console.log(StorageService.isAdminLoggedIn())            
            if(StorageService.isAdminLoggedIn()){
              alert(" admin  logged in successfully")
              this.router.navigateByUrl("/admin")
         
            }
          }
        }),
           error :(err)=>{
             console.log(err)
           this.errormessage =err.error.error
           alert(this.errormessage) 
         }
    })
    
}
  }
}

