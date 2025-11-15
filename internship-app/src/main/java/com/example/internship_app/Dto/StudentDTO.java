package com.example.internship_app.Dto;


import com.example.internship_app.Entities.Student;
import com.example.internship_app.Enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentDTO {
    private Long id;

    private String fullName;

    private  String email;

    private LocalDateTime createdAt;

    private Role role ;

    private String bio ;

    private String resume ;

    private String  profileimage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public StudentDTO getstudentdto(Student student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setProfileimage(student.getProfileimage());
        studentDTO.setFullName(student.getFullName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setRole(student.getRole());
        studentDTO.setBio(student.getBio());
        studentDTO.setCreatedAt(student.getCreatedAt());
        studentDTO.setResume(student.getResume());
        return studentDTO;
    }
}
