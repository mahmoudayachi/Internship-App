package com.example.internship_app.Dto;

import com.example.internship_app.Entities.Company;
import com.example.internship_app.Enums.InternshipPostStatus;
import com.example.internship_app.Enums.InternshipType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InternshipPostDto {

    private Long company_id;

    private String title;

    private String description;

    private String location;

    private String duration;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate applydeadline;

    private InternshipType internshiptype;

    private List<String> requirements;

    private List<String> skills;

    private LocalDateTime createdAt;

    private InternshipPostStatus status;




    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public LocalDate getApplydeadline() {
        return applydeadline;
    }

    public void setApplydeadline(LocalDate applydeadline) {
        this.applydeadline = applydeadline;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public InternshipType getInternshiptype() {
        return internshiptype;
    }

    public void setInternshiptype(InternshipType internshiptype) {
        this.internshiptype = internshiptype;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public InternshipPostStatus getStatus() {
        return status;
    }

    public void setStatus(InternshipPostStatus status) {
        this.status = status;
    }
}


