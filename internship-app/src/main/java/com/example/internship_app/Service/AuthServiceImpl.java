package com.example.internship_app.Service;

import com.example.internship_app.Dto.CompanyDTO;
import com.example.internship_app.Dto.StudentDTO;
import com.example.internship_app.Dto.SignupRequest;
import com.example.internship_app.Entities.Company;
import com.example.internship_app.Entities.Student;
import com.example.internship_app.Enums.Role;
import com.example.internship_app.Repositories.AuthService;
import com.example.internship_app.Repositories.CompanyRepository;
import com.example.internship_app.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl  implements AuthService {

     @Autowired
    private StudentRepository studentRepository;
     @Autowired
    private CompanyRepository companyRepository;



    StudentDTO getstudentdto(Student student){
          StudentDTO studentDTO = new StudentDTO();
          studentDTO.setId(student.getId());
          studentDTO.setFullName(student.getFullName());
          studentDTO.setEmail(student.getEmail());
         studentDTO.setRole(student.getRole());
         studentDTO.setBio(student.getBio());
          studentDTO.setCreatedAt(student.getCreatedAt());
          studentDTO.setResume(student.getResume());
          return studentDTO;
      }

      public CompanyDTO getcompanydto(Company company ){
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setFullName(company.getFullName());
        companyDTO.setCreatedAt(company.getCreatedAt());
        companyDTO.setCompanysize(company.getCompanysize());
         companyDTO.setDescription(company.getDescription());
         companyDTO.setLocation(company.getLocation());
         companyDTO.setRole(company.getRole());
        companyDTO.setEmail(company.getEmail());
        companyDTO.setCompanysize(company.getCompanysize());
        companyDTO.setCompanyLogo(company.getCompanyLogo());

        return companyDTO;
    }


    @Override
      public StudentDTO signupstudent (SignupRequest signupRequest){
         Student student =  new Student();
         student.setFullName(signupRequest.getFullName());
         student.setRole(Role.STUDENT);
         student.setCreatedAt(LocalDateTime.now());
         student.setEmail(signupRequest.getEmail());
         student.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
         Student createdjobseeker = studentRepository.save(student);
         return getstudentdto(createdjobseeker);
     }

    @Override
    public boolean hascompanyWithEmail(String email) {
        return companyRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public boolean hasstudentWithEmail(String email) {
        return studentRepository.findFirstByEmail(email).isPresent();
    }

    @Override
     public CompanyDTO signupCompany( SignupRequest signupRequest){
         Company company = new Company();
         company.setFullName(signupRequest.getFullName());
         company.setCreatedAt(LocalDateTime.now());
         company.setEmail(signupRequest.getEmail());
         company.setRole(Role.COMPANY);
         company.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
         company.setCompanysize(signupRequest.getCompanysize());
         company.setLocation(signupRequest.getLocation());
         company.setCompanyLogo(signupRequest.getCompanyLogo());
         company.setDescription(signupRequest.getDescription());
         Company createdcompany =companyRepository.save(company);
         return getcompanydto(createdcompany);
     }

}
