import { Routes } from '@angular/router';
import { CompanyDashboardComponent } from '../Components/company-dashboard/company-dashboard.component';
import { HomeComponent } from '../Components/home/home.component';
import { InternshipOfferDetailComponent } from '../Components/internship-offer-detail/internship-offer-detail.component';
import { LoginComponent } from '../Components/login/login.component';
import { SearchComponent } from '../Components/search/search.component';
import { SignupComponent } from '../Components/signup/signup.component';
import { StudentDashbaordComponent } from '../Components/student-dashbaord/student-dashbaord.component';

export const routes: Routes = [ 
    { path: '', component: HomeComponent, pathMatch: 'full' },
    {path:'login',component:LoginComponent},
    {path:'signup',component:SignupComponent},
    {path:'student-dashboard',component:StudentDashbaordComponent},
    {path:'company-dashboard',component:CompanyDashboardComponent},
    {path:'search',component:SearchComponent},
    {path:'offer-detail/:id',component:InternshipOfferDetailComponent}





];
