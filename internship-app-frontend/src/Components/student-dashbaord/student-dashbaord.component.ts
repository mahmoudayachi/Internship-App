import { NgIf } from '@angular/common';
import { Component, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatIconButton } from '@angular/material/button';
import { Router } from '@angular/router';
import { RefreshService } from '../../Services/refresh.service';
import { StudentService } from '../../Services/student.service';
import { StorageService } from '../../Storage/storage.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { SharedstateService } from '../../Services/sharedstate.service';
@Component({
  selector: 'app-student-dashbaord',
  standalone: true,
  imports: [NgIf,ReactiveFormsModule,MatIconButton,MatDialogModule],
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
  display_placeholder_image :boolean =false
  updateprofileform !: FormGroup
  numberofAcceptedApplications : number = 0
  @ViewChild('successDialog') successDialog!: TemplateRef<any>;
  @ViewChild('errorDialog') errorDialog!: TemplateRef<any>;
  constructor(private studentService :StudentService , private fb: FormBuilder,private router :Router,private refreshService: RefreshService ,private dialog: MatDialog,private appState: SharedstateService ){
   this.updateprofileform = this.fb.group({
     fullName :['',Validators.required],
     email:['',Validators.required],
     bio:['',Validators.required],
     password:['',Validators.required],
     image:[null]
   
   })
  }
  
  showSuccessDialog(title: string, message: string) {
    this.dialog.open(this.successDialog, {
      data: { title, message }
    });
  }

  showerrorDialog(title:string,message:string){
    this.dialog.open(this.errorDialog,{
      data:{title,message}
    })
  }
  
  ngOnInit(){
    this.displaydashboards()
    this.currentstudent = StorageService.getUser();
    const obj = JSON.parse(this.currentstudent);
    this.currentstudentid = obj.id;
     this.currentstudentname = obj.fullName;
     this.loadAllData(); 

    
  
  this.refreshService.refresh$.subscribe(() => {
    this.loadAllData(); 
  });
     
    

  }

  loadAllData() {
    
    this.studentService.GetstudentByid(this.currentstudentid).subscribe({
      next: (res) => {
        this.currenstudentdata = res;
        this.currentstudentname = res.fullName;
        this.display_placeholder_image = !res.profileimage;
        this.updateprofileform.get("fullName")?.setValue(res.fullName);
        this.updateprofileform.get("email")?.setValue(res.email);
      },
      error: (err) => console.log(err)
    });
  
    
    this.studentService.GetApplicationBystudent(this.currentstudentid).subscribe({
      next: (res) => { 
    
        this.listofstudentapplications = res;
        this.numberofapplications = res.length;
      },
      error: (err) => console.log(err)
    });
  
    
    this.studentService.GetAcceptedApplications(this.currentstudentid).subscribe({
      next: (res) => {
        this.listofacceptedapplications = res;
        this.numberofAcceptedApplications = res.length;
      }
    });
  
    
    this.studentService.getSavedInternships(this.currentstudentid).subscribe({
      next: (res) => {
        this.list_of_saved_offers = res;
        this.number_of_saved_offers = res.length;
      },
      error: (err) => console.log(err)
    });
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
          this.showSuccessDialog('Success !', ' profile updated  successfully!')
          this.refreshService.trigger();
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
  this.refreshService.trigger(); 
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
        this.refreshService.trigger(); 
        this.list_of_saved_offers =res
        this.number_of_saved_offers = this.list_of_saved_offers.length
        
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
        this.showSuccessDialog('Success !', 'Internship unsaved successfully!')
        this.refreshService.trigger(); 
      },
      error: (err) => {
        console.error(err);
        this.showerrorDialog("error!",'Failed to unsave internship')
      }
    });
  }

  logout(){
    StorageService.logout()
    this.router.navigateByUrl("/login")
  }
}
