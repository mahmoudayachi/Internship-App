import { Routes } from '@angular/router';
import { AdmindashboardComponent } from '../Components/admindashboard/admindashboard.component';
import { AdminloginComponent } from '../Components/adminlogin/adminlogin.component';
import { ApplyDialogComponent } from '../Components/apply-dialog/apply-dialog.component';
import { CompanyDashboardComponent } from '../Components/company-dashboard/company-dashboard.component';
import { HomeComponent } from '../Components/home/home.component';
import { InternshipOfferDetailComponent } from '../Components/internship-offer-detail/internship-offer-detail.component';
import { LoginComponent } from '../Components/login/login.component';
import { SearchComponent } from '../Components/search/search.component';
import { SignupComponent } from '../Components/signup/signup.component';
import { StudentDashbaordComponent } from '../Components/student-dashbaord/student-dashbaord.component';
import { ownershipguardGuard } from '../guard/ownershipguard.guard';
import { roleGuard } from '../guard/role.guard';

export const routes: Routes = [ 
    { path: '', component: HomeComponent, pathMatch: 'full' },
    {path:'login',component:LoginComponent},
    {path:'signup',component:SignupComponent},
    {path:'student-dashboard',component:StudentDashbaordComponent,canActivate:[roleGuard],data: { role: 'STUDENT' }
},
    {path:'company-dashboard',component:CompanyDashboardComponent,canActivate:[roleGuard],data: { role: 'COMPANY' }},
    {path:'search',component:SearchComponent},
    {path:'',component:ApplyDialogComponent ,canActivate:[roleGuard] ,data:{ role: 'STUDENT' }},
    {path:'offer-detail/:id',component:InternshipOfferDetailComponent ,canActivate:[roleGuard] ,data:{ role: 'STUDENT' }},
    {path:'admin',component:AdmindashboardComponent,canActivate:[roleGuard],data:{role: 'ADMIN'}},
    {path:'adminlogin',component:AdminloginComponent},





];
