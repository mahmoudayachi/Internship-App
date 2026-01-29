import { NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StudentService } from '../../Services/student.service';
import {ChangeDetectionStrategy, inject} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { ApplyDialogComponent } from '../apply-dialog/apply-dialog.component';

@Component({
  selector: 'app-internship-offer-detail',
  standalone: true,
  imports: [NgIf,MatDialogModule, MatButtonModule],
  templateUrl: './internship-offer-detail.component.html',
  styleUrl: './internship-offer-detail.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class InternshipOfferDetailComponent {
  InternshipPostdetails :any =[]
  postid :any 
  list_of_requirements :any =[]
  list_of_technoloiges:any=[]
 
  readonly dialog = inject(MatDialog);

  openDialog() {
    const dialogRef = this.dialog.open(ApplyDialogComponent,{data: { postId: this.postid } } );

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
  constructor(private studentService :StudentService, private route:ActivatedRoute){

  }

  Offerisexpired(status:any){
    if(status=="EXPIRED"){
      const applybtn = document.querySelector(".apply-btn")
      applybtn?.classList.toggle("disabled")
      return false
    }
    return true
    
  }


  ngOnInit(){
    this.postid = this.route.snapshot.paramMap.get('id');
    this.studentService.GetInternshipPostById(this.postid).subscribe({
      next:(res=>{
        this.InternshipPostdetails =res
        this.list_of_requirements=res.requirements
        this.list_of_technoloiges=res.skills
        console.log(this.list_of_technoloiges)
        console.log(this.InternshipPostdetails)
      }),
      error:(err=>{
        console.log(err)
      })
    })
  }
}


