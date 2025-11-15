import { NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ShareConfig } from 'rxjs';
import { CompanyService } from '../../Services/company.service';
import { StorageService } from '../../Storage/storage.service';

@Component({
  selector: 'app-company-dashboard',
  standalone: true,
  imports: [RouterLink,NgIf,NgFor,ReactiveFormsModule],
  templateUrl: './company-dashboard.component.html',
  styleUrl: './company-dashboard.component.css'
})
export class CompanyDashboardComponent {
  sidebarOpen = false;
  companyName = '';
  companyLogo = '';
  activeOffers = 0;
  applicants = 0;
  acceptedInterns = 0;
  maindashboardisclicked =false
  addpostclicked =false
  currentinternshipofferclicked =false
  updateprofileclicked =false
  receivedapplications =false
  listofinternshipposts :any = []
  listofapplications:any=[]
  currentcompanyinfo : any = {}
  AVAILABLE :any  = 'AVAILABLE';
  EXPIRED :any  = 'EXPIRED';
  currentuser:string=""
  currentuserid:any
  Logo :any 
  updateProfileForm: FormGroup;
  logoPreview: string | ArrayBuffer | null = null;

  internshippost !:FormGroup

  constructor(private router :Router,private fb: FormBuilder,private companyservice :CompanyService ){
    this.updateProfileForm = this.fb.group({
      fullName: ['', Validators.required],
      companysize: ['', Validators.required],
      description: [''],
      location: ['', Validators.required],
      email:['',Validators.required],
      password:['',Validators.required],
      logo: [null]
    });

    this.internshippost = this.fb.group({
      company_id:this.currentuserid,
       title:['',Validators.required],
       duration : ['',Validators.required],
       internshiptype : ['',Validators.required],
       location:['',Validators.required],
       startDate : ['',Validators.required],
       endDate : ['',Validators.required],
       applydeadline : ['',Validators.required],
       requirements: ['',Validators.required],
       description: ['',Validators.required],

      
    })
  }
  recentApplications = [
    { studentName: 'John Doe', offerTitle: 'Frontend Developer', status: 'Pending' },
    { studentName: 'Sarah Lee', offerTitle: 'Data Analyst Intern', status: 'Accepted' },
    { studentName: 'Ahmed Ben', offerTitle: 'Backend Developer', status: 'Rejected' },
  ];

  ngOnInit(){
   
    
    this.currentuser = StorageService.getUser()
    const json = this.currentuser;
     const obj = JSON.parse(json);
     this.currentuserid =obj.id
     this.internshippost.patchValue({ company_id: this.currentuserid });

     this.companyservice.GetInternshippostbycompanyById(this.currentuserid).subscribe({
      next:(res=>{
        this.listofinternshipposts = res
        this.activeOffers = this.listofinternshipposts.length
        console.log(this.listofinternshipposts)
      }),
      error:(err=>{
        console.log(err)
      })
    })
     this.companyservice.GetcompanyById(this.currentuserid).subscribe({
       next:(res=>{
         this.currentcompanyinfo =res
         this.Logo = this.currentcompanyinfo.companyLogo
         this.companyName  = this.currentcompanyinfo.fullName
        console.log(this.currentcompanyinfo)
       }),
       error:(err=>{
         console.log(err)
       })
     })
   
     this.companyservice.GetApplicationsByCompany(this.currentuserid).subscribe({
       next:(res=>{
         this.listofapplications =res
         this.applicants = this.listofapplications.length
         console.log(this.listofapplications)
       }),
       error:(err=>{
         console.log(err)
       })
     })


    
  }




  Submitpost(){
    if(!this.internshippost.valid){
      alert("all fields must be filled ")
    }
    else{
      console.log(this.internshippost.value)
    this.companyservice.AddInternshipPost(this.internshippost.value).subscribe({
      next:(res=>{
         console.log(res)
         if(res.id!=null){
           alert("internshippost added successfully");
         }
      }),
      error :(err)=>{
        console.log(err)
      }
    })



  }
  }


  changestatus(event:any,id:any){
    const status = event.target.value
    this.companyservice.UpdateInternshipPost(id,status).subscribe({
      next:(res=>{
        console.log(res)
        if(res.id!=null){
          alert(" internship post status changed successfully ")
        }
      }),
      error:(err=>{
        console.log(err)
      })
    })
  }

  deleteInternshipPost(postid :any){
     const anwser = window.prompt("Do you really want to delete this post ? type yes or no")
     if(anwser=="yes"){
     this.companyservice.DeleteInternshipPost(postid).subscribe({
       next:(res=>{
         alert("post deleted successfully")
         console.log(res)
       }),
       error:(err=>{
         console.log(err)
       })
     })
    }
  }
  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }

  displayReceivedApplicaitons(){
    this.receivedapplications =true
    this.addpostclicked =false
    this.maindashboardisclicked=false
    this.currentinternshipofferclicked =false
    this.updateprofileclicked =false
  }

  displaymaindashboard(){
    this.receivedapplications =false
    this.addpostclicked =false
    this.maindashboardisclicked=true
    this.currentinternshipofferclicked =false
    this.updateprofileclicked =false
  }

  DispalyAddInternshipPost(){
    this.receivedapplications =false
    this.addpostclicked =true
    this.maindashboardisclicked=false
    this.currentinternshipofferclicked =false
    this.updateprofileclicked =false
  }
  Displaycurrentoffers(){
  
    this.receivedapplications =false
    this.currentinternshipofferclicked =true
    this.addpostclicked =false
    this.maindashboardisclicked=false
    this.updateprofileclicked =false
  }

  displayupdateprofile(){
    this.receivedapplications =false
    this.currentinternshipofferclicked =false
    this.addpostclicked =false
    this.maindashboardisclicked=false
    this.updateprofileclicked =true
   
    this.updateProfileForm.get("fullName")?.setValue(this.currentcompanyinfo.fullName)
    this.updateProfileForm.get("companysize")?.setValue(this.currentcompanyinfo.companysize)
    this.updateProfileForm.get("description")?.setValue(this.currentcompanyinfo.description)
    this.updateProfileForm.get("location")?.setValue(this.currentcompanyinfo.location)
    this.updateProfileForm.get("email")?.setValue(this.currentcompanyinfo.email)
    this.Logo = this.currentcompanyinfo.companyLogo
    
  

 
  }


  logout(){
    StorageService.logout()
    this.router.navigateByUrl("/login")
  }

  AcceptOffer(id :any ){
    this.companyservice.AcceptOffer(id).subscribe({
      next:(res=>{
        alert("offer accepted  ")
       
      }),
      error:(err=>{
        console.log(err)
      })
    })
  }
  RejectOffer(id :any ){
    this.companyservice.RejectOffer(id).subscribe({
      next:(res=>{
        alert("offer Rejected ")
      }),
      error:(err=>{
        console.log(err)
      })
    })
  }
  

  onLogoSelected(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files[0]) {
      const file = fileInput.files[0];
      this.updateProfileForm.patchValue({ logo: file });

      const reader = new FileReader();
      reader.onload = e => (this.logoPreview = reader.result);
      reader.readAsDataURL(file);
    }
  }

  onSubmit() {
    const form = new FormData()
    form.append("fullName",this.updateProfileForm.value.fullName);
    form.append("companysize",this.updateProfileForm.value.companysize);
    form.append('description', this.updateProfileForm.value.description);
    form.append('location', this.updateProfileForm.value.location);
    form.append('logo', this.updateProfileForm.value.logo);
    form.append('email', this.updateProfileForm.value.email);
    form.append('password', this.updateProfileForm.value.email);
    form.append("role","COMPANY")
    console.log(form.get('logo'))
    this.companyservice.UpdateProfile(this.currentuserid,form).subscribe({
      next:(res=>{
        if(res.id!=null){
          alert("company profile updated successfully ")
        }
      }),
      error:(err=>{
        console.log(err)
      })
    })
   
  }
}
