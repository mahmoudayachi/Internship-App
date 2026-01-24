package com.example.internship_app.Controller;

import com.example.internship_app.Dto.*;
import com.example.internship_app.Entities.Admin;
import com.example.internship_app.Entities.Company;
import com.example.internship_app.Entities.Student;
import com.example.internship_app.Repositories.AdminRepository;
import com.example.internship_app.Repositories.AuthService;
import com.example.internship_app.Repositories.CompanyRepository;
import com.example.internship_app.Repositories.StudentRepository;
import com.example.internship_app.Service.ComposedUserDetailsService;
import com.example.internship_app.Utils.Jwtutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final Jwtutil jwtUtil;


    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final ComposedUserDetailsService composedService;
    private final AdminRepository adminRepository;
    public String logodirectory = System.getProperty("user.dir") + "./src/main/resources/logoimages";

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, Jwtutil jwtUtil, StudentRepository jobseekerRepository, StudentRepository studentRepository, CompanyRepository companyRepository, ComposedUserDetailsService composedService, AdminRepository adminRepository) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.composedService = composedService;
        this.adminRepository = adminRepository;
    }

   @PostMapping("/signup/company")
    public ResponseEntity<?> singupcompany(@ModelAttribute SignupRequest singuprequest, @RequestParam("logo") MultipartFile logo) throws IOException {
        if(authService.hascompanyWithEmail(singuprequest.getEmail())){
            Map<String,String> error = new HashMap<>();
            error.put("error","company already exists with this email");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
         String orignalfilename = logo.getOriginalFilename();
        Path filenameandpath = Paths.get(logodirectory,orignalfilename) ;
        Files.write(filenameandpath,logo.getBytes());
        singuprequest.setCompanyLogo(orignalfilename);
        CompanyDTO createdcompanyDTO = authService.signupCompany(singuprequest);
        if (createdcompanyDTO == null) {
            Map<String,String> error = new HashMap<>();
            error.put("error","company account not created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

       }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdcompanyDTO);
   }

    @PostMapping("/signup/student")
    public ResponseEntity<?> singupstudent(@RequestBody SignupRequest singuprequest) throws IOException {
        if(authService.hasstudentWithEmail(singuprequest.getEmail())){
            Map<String,String> error = new HashMap<>();
            error.put("error","student already exists with this email");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        StudentDTO createdjobseekerDTO = authService.signupstudent(singuprequest);
        if (createdjobseekerDTO == null) {
            String error = "student already exists with this email";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("student not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdjobseekerDTO);
    }

    @PostMapping("/student/login")
    public ResponseEntity<?> loginstudent(@RequestBody AuthenticationRequest authenticationRequest) {
        Optional<Student> student = studentRepository.findFirstByEmail(authenticationRequest.getEmail());
        if(student.isEmpty()){
            Map<String, String> error = new HashMap<>();
            error.put("error", "student account not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        Student existingstudent = student.get();
        if (!"ACTIVATED".equalsIgnoreCase(existingstudent.getAccountStatus().toString())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "your account is not activated yet you can't login for now");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            Map<String,String> error = new HashMap<>();
            error.put("error","Incorrect email or password ");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        final UserDetails userDetails = composedService.loadUserByUsername(authenticationRequest.getEmail());
        Optional<Student> optionalUser = studentRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwttoken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwttoken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getRole());
        }
        return  ResponseEntity.ok(authenticationResponse);

    }

    @PostMapping("/company/login")
    public ResponseEntity<?> logincompany(@RequestBody AuthenticationRequest authenticationRequest) {
        Optional<Company> company = companyRepository.findFirstByEmail(authenticationRequest.getEmail());
        if(company.isEmpty()){
            Map<String, String> error = new HashMap<>();
            error.put("error", "company account not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        Company existingcompany = company.get();
        if (!"ACTIVATED".equalsIgnoreCase(existingcompany.getAccountStatus().toString())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "your account is not activated yet you can't login for now");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            Map<String,String> error = new HashMap<>();
            error.put("error","Incorrect email or password ");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        final UserDetails userDetails = composedService.loadUserByUsername(authenticationRequest.getEmail());
        Optional<Company> optionalUser = companyRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwttoken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwttoken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getRole());
        }
        return  ResponseEntity.ok(authenticationResponse);

    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> loginadmin(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            Map<String,String> error = new HashMap<>();
            error.put("error","Incorrect email or password ");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        final UserDetails userDetails = composedService.loadUserByUsername(authenticationRequest.getEmail());
        Optional<Admin> optionalUser = adminRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwttoken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwttoken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getRole());
        }
        return  ResponseEntity.ok(authenticationResponse);

    }


}
