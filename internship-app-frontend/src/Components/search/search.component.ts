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
  size = 10;
  status = '';
  type = '';
  duration='';
  location = 'sousse';
  title = '';
  skills ='';
  description ='';
  currentstudent:any
  currentstudentid:any
  responsearray :any =[]
  list_of_saved_post  :any =[]
  list_of_technologies :any = []

  constructor(private studentservice : StudentService,private router : Router){

  }
  loadInternships(): void {
    this.studentservice.searchInternships({
      title: this.title.toLowerCase(),
      skills: this.skills,
      description:this.description,
      location: this.location.toLowerCase(),
      duration:this.duration,
      status: this.status,
      type: this.type,
      page: this.page,
      size: this.size,
      sortBy: 'createdAt',
      sortDir: 'desc'
    }).subscribe({
      next: (data: any) => {
        this.listofinternshippost = data.content;
        this.list_of_technologies  = data.content.skills
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
    this.location =''
    this.loadInternships()
  }
  onlocationChange(event:any){
    this.location = event.target.value
    this.loadInternships()
  }

  onsearchChange(event:any){
    this.title = event.target.value
    this.loadInternships()
   }

  onstatusChange(event:any){
    this.status = event.target.value
    this.location =''
    this.loadInternships()

  }
  onFilterChange(event:any)  {
    this.skills =event?.target.value
    this.location=''
    this.loadInternships()

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
     
     this.Getsavedinternships();
     
    

  }

  Getsavedinternships(){
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
  disablebtn(postid:any){
    const alreadySaved = this.list_of_saved_post.some(
      (post: { id: any; }) => post.id === postid
    );
    if (alreadySaved) {
      const savebtn =document.querySelector(".save-btn");
      savebtn?.classList.toggle("clicked")
      return true
    }
    return false
  }

  savepost(postid:any ){
    this.Getsavedinternships()
    const alreadySaved = this.list_of_saved_post.some(
      (post: { id: any; }) => post.id === postid
    );
    if (alreadySaved) {
      alert(' You already saved this internship!'); 
     
      return;
    }
  
    else{
    this.studentservice.saveInternship(this.currentstudentid,postid).subscribe({
      next:(res)=>{
        const isSaved = res.some((post: { id: any; }) => post.id === postid);
        
        if (isSaved) {
          alert(' Internship saved successfully!');
          this.Getsavedinternships()
       
        } else {
          alert(' problem could not save internship offer !');
        }
      },
      error:(err=>{
        console.log(err)
      })
    })
  }
  }

}
