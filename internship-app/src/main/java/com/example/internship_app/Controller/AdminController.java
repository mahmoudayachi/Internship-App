package com.example.internship_app.Controller;

import com.example.internship_app.Entities.Company;
import com.example.internship_app.Entities.InternshipPost;
import com.example.internship_app.Entities.Student;
import com.example.internship_app.Enums.AccountStatus;
import com.example.internship_app.Repositories.AdminRepository;
import com.example.internship_app.Service.AdminService;
import org.apache.poi.xssf.usermodel.helpers.XSSFIgnoredErrorHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/all/students")
    public List<Student> getInterns() {
        return adminService.getAllStudents();
    }
    @GetMapping("/all/companies")
    public List<Company> getAllcompanies() {
        return adminService.getAllCompany();
    }

    @GetMapping("/all/internshipposts")
    public List<InternshipPost> getAllinternshippost() {

        return adminService.getAllInternshipPosts();
    }

     @PutMapping("update/status/{id}/{accountStatus}")
     public ResponseEntity<?> updatestudentaccounstatus(@PathVariable("id") Long id , @PathVariable("accountStatus") AccountStatus status) {

        return ResponseEntity.ok(adminService.changeStudentAccountStatus(id,status));
     }

    @PutMapping("update/company/status/{id}/{accountStatus}")
    public ResponseEntity<?> updatecompanyaccounstatus(@PathVariable("id") Long id , @PathVariable("accountStatus") AccountStatus status) {

        return ResponseEntity.ok().body(adminService.changeCompanyAccountStatus(id,status));
    }

}


