import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatIconButton } from '@angular/material/button';
import { Router } from '@angular/router';
import { StudentService } from '../../Services/student.service';
import { StorageService } from '../../Storage/storage.service';

@Component({
  selector: 'app-student-dashbaord',
  standalone: true,
  imports: [NgIf,ReactiveFormsModule,MatIconButton],
  templateUrl: './student-dashbaord.component.html',
  styleUrl: './student-dashbaord.component.css'
})
export class StudentDashbaordComponent {
  currentstudentid: any
  currentstudent:any
  currentstudentname:any
  currenstudentdata :any =[]
  listofstudentapplications: any = []
  listofacceptedapplications :any = []
  list_of_saved_offers :any = []
  numberofapplications : number =0 
  number_of_saved_offers : number =0
  displayapplications = false
  displaydashboard = false
  displaysavedoffers =false
 updateprofile =false
  selectedFile: File | null = null;
  
  updateprofileform !: FormGroup
  numberofAcceptedApplications : number = 0


  constructor(private studentService :StudentService , private fb: FormBuilder,private router :Router){
   this.updateprofileform = this.fb.group({
     fullName :['',Validators.required],
     email:['',Validators.required],
     bio:['',Validators.required],
     password:['',Validators.required],
     image:[null]
   
   })
  }

  ngOnInit(){
    console.log(this.list_of_saved_offers)
    this.currentstudent = StorageService.getUser()
    const json = this.currentstudent;
     const obj = JSON.parse(json);
     this.currentstudentid =obj.id
     this.currentstudentname=obj.fullName
     
     console.log(this.currentstudentid)

     this.studentService.GetstudentByid(this.currentstudentid).subscribe({
       next:(res=>{
         console.log(res)
         this.currentstudentname=res.fullName
         console.log(this.currentstudentname)
         this.currenstudentdata =res
       }),
       error:(err=>{
         console.log(err)
       })
     })

     this.studentService.GetApplicationBystudent(this.currentstudentid).subscribe({
       next:(res=>{
         console.log(res)
         this.listofstudentapplications =res
         this.numberofapplications =this.listofstudentapplications.length
       }),
       error:(err=>{
         console.log(err)
       })
     })
     this.studentService.GetAcceptedApplications(this.currentstudentid).subscribe({
       next:(res=>{
         this.listofacceptedapplications =res
         this.numberofAcceptedApplications =   this.listofacceptedapplications.length
         console.log(this.listofacceptedapplications)
       })
     })


  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    console.log(this.selectedFile)
    this.updateprofileform.patchValue({ image: this.selectedFile })
  }


  
  onSubmit() {  
    const formdata = new FormData()
    formdata.append("fullName",this.updateprofileform.value.fullName)
    formdata.append("email",this.updateprofileform.value.email)
    formdata.append("password",this.updateprofileform.value.password)
    formdata.append("bio",this.updateprofileform.value.bio)
    formdata.append("image",this.updateprofileform.value.image)

    console.log(formdata.get("image"))

    this.studentService.updatestudentprofile(this.currentstudentid,formdata).subscribe({
      next:(res=>{
        if(res.id!=null){
          alert("profile updated successfully ")
        }
        console.log(res)
      }),
      error:(err=>{
        console.log(err)
      })
    })
  }

  displaydashboards(){
    this.displaydashboard = true
    this.displayapplications = false
    this.updateprofile = false
    this.displaysavedoffers =false
  }

  openInNewTab(url: any) {
    window.open(url.changingThisBreaksApplicationSecurity, '_blank');
  }
  
  displayapplication(){
   this.displayapplications = true
   this.displaydashboard = false
   this.updateprofile = false
   this.displaysavedoffers =false
  }

  displayupdateprofile(){
    this.updateprofileform.get("fullName")?.setValue(this.currenstudentdata.fullName)
    this.updateprofileform.get("email")?.setValue(this.currenstudentdata.email)

    this.displayapplications = false
    this.displaydashboard = false
    this.updateprofile = true
    this.displaysavedoffers =false
  }
  
  displaysavedoffer(){
    this.displayapplications = false
    this.displaydashboard = false
    this.updateprofile = false
    this.displaysavedoffers =true
    this.studentService.getSavedInternships(this.currentstudentid).subscribe({
      next:(res=>{
        this.list_of_saved_offers =res
        console.log(this.list_of_saved_offers)
      }),
      error:(err=>{
        console.log(err)
      })
    })
  }
  
  deletesavedoffer(postid:any){
    this.studentService.unsaveInternship(this.currentstudentid,postid).subscribe({
      next: (res) => {
        console.log(res);
        alert(' Internship unsaved successfully!');
        this.displaysavedoffer()
      },
      error: (err) => {
        console.error(err);
        alert(' Failed to unsave internship.');
      }
    });
  }

  logout(){
    StorageService.logout()
    this.router.navigateByUrl("/login")
  }
}
