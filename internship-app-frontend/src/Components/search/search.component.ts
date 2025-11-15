import { NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { StudentService } from '../../Services/student.service';
import { StorageService } from '../../Storage/storage.service';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [NgFor,NgIf],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  listofinternshippost: any =[]
  array_of_savedpost_id:any =[]
  totalElements = 0;
  page = 0;
  size = 2;
  status = '';
  type = '';
  duration='';
  location = '';
  search = '';
  currentstudent:any
  currentstudentid:any
  responsearray :any =[]
  list_of_saved_post  :any =[]
  constructor(private studentservice : StudentService,private router : Router){

  }
  loadInternships(): void {
    this.studentservice.searchInternships({
      status: this.status,
      type: this.type,
      location: this.location,
      duration:this.duration,
      search: this.search,
      page: this.page,
      size: this.size,
      sortBy: 'createdAt',
      sortDir: 'desc'
    }).subscribe({
      next: (data: any) => {
        this.listofinternshippost = data.content;
        console.log(this.listofinternshippost)
        this.totalElements = data.totalElements;
      },
      error: err => {
        console.error('Error loading internships', err);
      }
    });
  }


  ontypeChange(event:any){
    this.type =event.target.value
   this.loadInternships()
 
  }
  
  ondurationchange(event:any){
    this.duration = event.target.value
    this.loadInternships()
  }
  onlocationChange(event:any){
    this.location = event.target.value
    this.loadInternships()
  }

  onsearchChange(event:any){
    this.search = event.target.value
    this.loadInternships()
   }

  onstatusChange(event:any){
    this.status = event.target.value
    this.loadInternships()

  }
  onFilterChange(): void {
    this.page = 0; 

  }
  onPageChange(newPage: number): void {
    this.page = newPage;
  
  }

  viewofferdetails(id :string){
    this.router.navigate(['/offer-detail',id])
  }

  ngOnInit(){
    this.currentstudent = StorageService.getUser()
    const json = this.currentstudent;
     const obj = JSON.parse(json);
     this.currentstudentid =obj.id
     this.loadInternships()
     
     this.studentservice.getSavedInternships(this.currentstudentid).subscribe({
       next:(res=>{
         this.list_of_saved_post =res
         console.log(this.list_of_saved_post)
        
       }),
       error:(err=>{
         console.log(err)
       })
     })
    

  }

  savepost(postid:any ){
    const alreadySaved = this.list_of_saved_post.some(
      (post: { id: any; }) => post.id === postid
    );
  
    if (alreadySaved) {
      alert('⚠️ You already saved this internship!'); 
      return;
    }
  
    else{
    this.studentservice.saveInternship(this.currentstudentid,postid).subscribe({
      next:(res)=>{
        
        const isSaved = res.some((post: { id: any; }) => post.id === postid);
        
        if (isSaved) {
          alert('✅ Internship saved successfully!');
       
        } else {
          alert('✅ problem could not save internship offer !');
        }
      },
      error:(err=>{
        console.log(err)
      })
    })
  }
  }

}
