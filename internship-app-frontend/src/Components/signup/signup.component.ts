import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignupService } from '../../Services/signup.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [NgIf,ReactiveFormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  selectedForm: 'student' | 'company' = 'student';
  signupstudent !:FormGroup
  signupcompany !: FormGroup
  errormessage: string = ''
  selectedFile: File | null = null;
  selectfiletouched :boolean = false
  

  onFileSelected(event: any) {
    this.selectfiletouched =true
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile)
  }

  constructor( private singupservice:SignupService ,private fb: FormBuilder, private form:FormBuilder ,private router:Router){
  this.signupstudent = form.group({
    fullName:[null,[Validators.required]],
    email:[null,[Validators.required]],
    password :[null,[Validators.required]],
    Role:"STUDENT"
  }),
  this.signupcompany = fb.group({
    fullName: [null,[Validators.required]],
    email:[null,[Validators.required]],
    password :[null,[Validators.required]],
    companysize:[null,[Validators.required]],
    location :[null,[Validators.required]],
    description:[null,[Validators.required]]
  })
  }

  singupstudent(){
    if(!this.signupstudent.valid){
      alert("all fields must be filled ")
    }
    else{
    console.log(this.signupstudent.value)
    this.singupservice.signupstudent(this.signupstudent.value).subscribe({
    next:(res)=>{
      console.log(res)
      if(res.id!=null){
        alert("student singned up successfully ")
        this.router.navigateByUrl("/login")
      }
    },
    error:(err)=>{
      console.log(err);
      this.errormessage =err
      alert(this.errormessage)
    }

    })
  }
}
  singupcompany(){
    if(!this.signupcompany.valid){
      alert("all fields must be filled ")
    }
    else{
    console.log(this.signupcompany.value)
    const   form = new FormData()
    form.append('fullName', this.signupcompany.value.fullName);
    form.append('email', this.signupcompany.value.email);
    form.append('password', this.signupcompany.value.password);
    form.append('companysize', this.signupcompany.value.companysize);
    form.append('description', this.signupcompany.value.description);
    form.append('location', this.signupcompany.value.location);
    form.append("role","COMPANY")
    if (this.selectedFile) {
      form.append('logo', this.selectedFile);
    }
    this.singupservice.signupcompany(form).subscribe({
      next :(res)=>{
        console.log(res)
        if(res.id!=null){
          alert("company signed up successfully ")
          this.router.navigateByUrl("/login")
        }

      },
      error:(err)=>{
       this.errormessage =err
       alert(this.errormessage)
      }
    })
  }
  }

}
