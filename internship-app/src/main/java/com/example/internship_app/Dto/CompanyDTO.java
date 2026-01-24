package com.example.internship_app.Dto;


import com.example.internship_app.Entities.Company;
import com.example.internship_app.Enums.AccountStatus;
import com.example.internship_app.Enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class CompanyDTO {
    private Long id;
    private String fullName;
    private  String email;
    private LocalDateTime createdAt;
    private Role role ;
    private String location;
    private String companysize;
    private String description;
    private String companyLogo ;
    private AccountStatus accountStatus;

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanysize() {
        return companysize;
    }

    public void setCompanysize(String companysize) {
        this.companysize = companysize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
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
        companyDTO.setAccountStatus(AccountStatus.ACTIVATED);
        companyDTO.setEmail(company.getEmail());
        companyDTO.setCompanysize(company.getCompanysize());
        companyDTO.setCompanyLogo(company.getCompanyLogo());

        return companyDTO;
    }
}
