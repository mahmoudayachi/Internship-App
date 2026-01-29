import { Component, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AdminserviceService } from '../../Services/adminservice.service';
import { RefreshService } from '../../Services/refresh.service';
import { StorageService } from '../../Storage/storage.service';
@Component({
  selector: 'app-adminlogin',
  standalone: true,
  imports: [ReactiveFormsModule,MatDialogModule],
  templateUrl: './adminlogin.component.html',
  styleUrl: './adminlogin.component.css'
})
export class AdminloginComponent {


  loginform !:FormGroup
  errormessage:any
  @ViewChild('successDialog') successDialog!: TemplateRef<any>;
  @ViewChild('errorDialog') errorDialog!: TemplateRef<any>;
  constructor(private authservice :AdminserviceService, private form :FormBuilder , private router :Router,private refreshService: RefreshService ,private dialog: MatDialog ){
   this.loginform = form.group({
     email: [null,Validators.required],
     password:[null,Validators.required],
   })

 }
 showSuccessDialog(title: string, message: string) {
  this.dialog.open(this.successDialog, {
    data: { title, message }
  });
}
 adminlogin(){
  console.log(this.loginform)
  if(!this.loginform.valid ){
    this.showSuccessDialog('warning !','all fields must not be empty')
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
            this.showSuccessDialog('success !','admin  logged in successfully')
            this.router.navigateByUrl("/admin")
       
          }
        }
      }),
         error :(err)=>{
         this.errormessage =err.error.error
         alert(this.errormessage) 
       }
  })
  
}
}
}

