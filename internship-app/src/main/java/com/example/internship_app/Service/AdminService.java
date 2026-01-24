package com.example.internship_app.Service;

import com.example.internship_app.Dto.AuthenticationRequest;
import com.example.internship_app.Dto.AuthenticationResponse;
import com.example.internship_app.Dto.StudentDTO;
import com.example.internship_app.Entities.Admin;
import com.example.internship_app.Entities.Company;
import com.example.internship_app.Entities.InternshipPost;
import com.example.internship_app.Entities.Student;
import com.example.internship_app.Enums.AccountStatus;
import com.example.internship_app.Enums.Role;
import com.example.internship_app.Repositories.AdminRepository;
import com.example.internship_app.Repositories.CompanyRepository;
import com.example.internship_app.Repositories.InternshipPostRepository;
import com.example.internship_app.Repositories.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final InternshipPostRepository internshipPostRepository;
    private final EmailService emailService;

    public AdminService(AdminRepository adminRepository, StudentRepository studentRepository, CompanyRepository companyRepository, InternshipPostRepository internshipPostRepository, EmailService emailService) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.internshipPostRepository = internshipPostRepository;
        this.emailService = emailService;
    }


    @PostConstruct
    public void createAdminAccount() {
        List<Admin> existingadmin = adminRepository.findAll();
        if (existingadmin.isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail("admin@test.com");
            admin.setFullName("adminnn");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin123/*-"));
            admin.setRole(Role.ADMIN);
            adminRepository.save(admin);
            System.out.print("admin account created successfully");
        }
        else {
            System.out.print("admin account already exists");
        }
    }


    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Page<Company> getAllCompany(Pageable pageable){
        return companyRepository.findAll(pageable);
    }

    public Page<InternshipPost> getAllInternshipPosts(Pageable pageable){
        return internshipPostRepository.findAll(pageable);
    }

   public void changeStudentAccountStatus(Long id , AccountStatus status){
    Student   student = studentRepository.findById(id).orElseThrow(()->new RuntimeException("student not found"));
    student.setAccountStatus(status);
    studentRepository.save(student);
       if(status.toString().equals("ACTIVATED")){
           emailService.sendmail(student.getEmail(),"account verification done","your account has been activated you can login now");
       }
       else if (status.toString().equals("DESACTIVATED")){
           emailService.sendmail(student.getEmail(),"account desactivation","your account has been desactivated ");
       }
   }

    public void changeCompanyAccountStatus(Long id , AccountStatus status){
        Company   company = companyRepository.findById(id).orElseThrow(()->new RuntimeException("student not found"));
        company.setAccountStatus(status);
        companyRepository.save(company);
        if(status.toString().equals("ACTIVATED")){
            emailService.sendmail(company.getEmail(),"account verification done","your account has been activated you can login now");
        }
        else if (status.toString().equals("DESACTIVATED")){
            emailService.sendmail(company.getEmail(),"account desactivation","your account has been desactivated ");
        }
    }













}
