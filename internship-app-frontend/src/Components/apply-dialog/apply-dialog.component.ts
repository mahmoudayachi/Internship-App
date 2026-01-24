import { Component, Inject } from '@angular/core';
import {ChangeDetectionStrategy, inject} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatDialog, MatDialogModule, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { ActivatedRoute, Router, RouterLinkActive } from '@angular/router';
import { StudentService } from '../../Services/student.service';
import { StorageService } from '../../Storage/storage.service';
@Component({
  selector: 'app-apply-dialog',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule],
  templateUrl: './apply-dialog.component.html',
  styleUrl: './apply-dialog.component.css'
})
export class ApplyDialogComponent {
  cvFile: File | null = null;
  motivationLetter: File | null = null;
  studentid: any
  internshippostid :any
  currentstudent:any
  constructor(private router: Router,private route: ActivatedRoute,private studentservice :StudentService, @Inject(MAT_DIALOG_DATA) public data :any){

  }
   ngOnInit(){
   
    this.internshippostid = this.data.postId;
    console.log(this.internshippostid)
    this.currentstudent = StorageService.getUser()
    const json = this.currentstudent;
     const obj = JSON.parse(json);
     this.studentid =obj.id
     console.log(this.studentid)
    
   }

  onCvSelected(event: any): void {
    this.cvFile = event.target.files[0];
    console.log(this.cvFile)
  }

  onMotivationSelected(event: any): void {
    this.motivationLetter = event.target.files[0];
    console.log(this.motivationLetter)
  }

  submit(){
    if (!this.cvFile || !this.motivationLetter) {
      alert('Please upload both files.');
      return;
    }

    const formData = new FormData();
    formData.append('cvfile', this.cvFile);
    formData.append('letter', this.motivationLetter);
    formData.append('student_id',this.studentid)
    formData.append('internship_offer_id',this.internshippostid)

    this.studentservice.Applytoffer(formData).subscribe({
      next:(res=>{
        console.log(res)
        if(res.id!=null){
          alert("application send successfully")
        }
      }),
      error:(err=>{
        console.log(err)
      })
    })
  }


}
