package com.example.internship_app.Dto;

import com.example.internship_app.Enums.ApplicationStatus;

import java.time.LocalDateTime;

public class ApplicationDTO {
    private Long id;

    private LocalDateTime applicationDate;

    private ApplicationStatus status;


    private String motivationLetter;


    private String cv ;

    private Long student_id;

    private Long internship_offer_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getMotivationLetter() {
        return motivationLetter;
    }

    public void setMotivationLetter(String motivationLetter) {
        this.motivationLetter = motivationLetter;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public Long getInternship_offer_id() {
        return internship_offer_id;
    }

    public void setInternship_offer_id(Long internship_offer_id) {
        this.internship_offer_id = internship_offer_id;
    }
}
