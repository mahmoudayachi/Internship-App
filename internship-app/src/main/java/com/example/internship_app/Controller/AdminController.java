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

@RestController
@RequestMapping("/Admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/all/students")
    public Page<Student> getInterns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return adminService.getAllStudents(pageable);
    }
    @GetMapping("/all/companies")
    public Page<Company> getAllcompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return adminService.getAllCompany(pageable);
    }

    @GetMapping("/all/internshipposts")
    public Page<InternshipPost> getAllinternshippost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return adminService.getAllInternshipPosts(pageable);
    }

     @PutMapping("update/status/{id}/{accountStatus}")
     public ResponseEntity<?> updatestudentaccounstatus(@PathVariable("id") Long id , @PathVariable("accountStatus") AccountStatus status) {
         adminService.changeStudentAccountStatus(id,status);
        return ResponseEntity.ok().body("account status changed successfully ");
     }

    @PutMapping("update/company/status/{id}/{accountStatus}")
    public ResponseEntity<?> updatecompanyaccounstatus(@PathVariable("id") Long id , @PathVariable("accountStatus") AccountStatus status) {
        adminService.changeCompanyAccountStatus(id,status);
        return ResponseEntity.ok().body("account status changed successfully ");
    }

}


