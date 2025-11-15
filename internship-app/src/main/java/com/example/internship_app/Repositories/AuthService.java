package com.example.internship_app.Repositories;

import com.example.internship_app.Dto.CompanyDTO;
import com.example.internship_app.Dto.StudentDTO;
import com.example.internship_app.Dto.SignupRequest;

import java.io.IOException;

public interface AuthService {
    CompanyDTO signupCompany(SignupRequest signupRequest) throws IOException;
    StudentDTO signupstudent(SignupRequest signupRequest);
    boolean hascompanyWithEmail(String email);
    boolean hasstudentWithEmail(String email);
}
