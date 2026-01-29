import { NgClass, NgFor, NgIf } from '@angular/common';
import { Component, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminserviceService } from '../../Services/adminservice.service';
import { StorageService } from '../../Storage/storage.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { RefreshService } from '../../Services/refresh.service';
@Component({
  selector: 'app-admindashboard',
  standalone: true,
  imports: [ReactiveFormsModule,NgFor,NgIf,NgClass,MatDialogModule],
  templateUrl: './admindashboard.component.html',
  styleUrl: './admindashboard.component.css'
})
export class AdmindashboardComponent {
   loginform !:FormGroup
   errormessage:any
   list_of_students :any =[];
   list_of_companies:any = [];
   main_dashboard_displayed :boolean =false
   student_management_displayed:boolean = false
   company_management_displayed:boolean = false
   
   accountstatus: any
   list_of_internshipposts:any = [];
   number_of_students  = 0
   number_of_companies = 0
   number_of_internshippost =0
   currentPage = 0;
   totalPages = 0;
   pageSize = 3;


   @ViewChild('successDialog') successDialog!: TemplateRef<any>;
   @ViewChild('errorDialog') errorDialog!: TemplateRef<any>;

   constructor(private adminservice :AdminserviceService, private form :FormBuilder , private router :Router,private refreshService: RefreshService ,private dialog: MatDialog ){
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
      this.adminservice.Adminlogin(this.loginform.value).subscribe({
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
              this.router.navigateByUrl("/")
         
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

  
  ngOnInit(){
    this.displaydashboard()
    this.adminservice.GetAllstudents().subscribe(res=>{
      this.list_of_students =res
      this.totalPages = res.totalPages;
      this.number_of_students = this.list_of_students.length
      console.log(this.list_of_students)
    })

    this.adminservice.GetAllcompanies().subscribe(res=>{
      this.list_of_companies =res
      this.number_of_companies =this.list_of_companies.length
      console.log(res)
    })

    this.adminservice.GetAllinternshipposts().subscribe(res=>{
      this.list_of_internshipposts =res
      this.number_of_internshippost =this.list_of_internshipposts.length
      console.log(this.list_of_internshipposts)
    })
  }

  updateCompanyAccountstatus(event :Event ,id :number){
    this.accountstatus = (event.target as HTMLSelectElement).value 
    console.log(this.accountstatus)
    this.adminservice.updateCompanyaccountStatus(id,this.accountstatus).subscribe({
      next:(res=>{
        alert("account status changed successfully")
        console.log(res)
      }),
      error:(err)=>{
        alert("error ! could not change account status")
        console.log(err)
      }
    })
  }


 updatestudentAccountstatus(event : Event , id : number ){
    this.accountstatus = (event.target as HTMLSelectElement).value 
    console.log(this.accountstatus)
    this.adminservice.updateaccountstatus(id,this.accountstatus).subscribe({
      next:(res=>{
        alert("account status changed successfully")
        console.log(res)
      }),
      error:(err)=>{
        alert("error ! could not change account status")
        console.log(err)
      }
    })
  
  }

  displaydashboard(){
    this.main_dashboard_displayed =true
    this.student_management_displayed=false
    this.company_management_displayed =false
  }
  
  Getallstudents(){
    this.student_management_displayed=true
    this.main_dashboard_displayed =false
    this.company_management_displayed =false
    this.adminservice.GetAllstudents().subscribe(res=>{
      this.list_of_students =res
      this.number_of_students = this.list_of_students.length
      console.log(res)
    })
  }
  Getallcompanies(){
    this.company_management_displayed=true
    this.student_management_displayed=false
    this.main_dashboard_displayed =false
    this.adminservice.GetAllcompanies().subscribe(res=>{
      this.list_of_companies =res
      console.log(res)
    })
  }

  GetallInternshipposts(){
    this.adminservice.GetAllinternshipposts().subscribe(res=>{
      this.list_of_internshipposts =res
      console.log(res)
    })
  }

  
  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.Getallstudents()
    
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      
    }
  }
  
  logout(){
    StorageService.logout()
    this.router.navigateByUrl("adminlogin")
  }
}
