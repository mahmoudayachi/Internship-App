package com.example.internship_app.Entities;


import com.example.internship_app.Enums.AccountStatus;
import com.example.internship_app.Enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Student extends User{

    private String bio ;
    private String resume ;
    private String  profileimage;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus ;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "saved_internships",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "internship_id")
    )
    private Set<InternshipPost> savedInternships = new HashSet<>();


    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Set<InternshipPost> getSavedInternships() {
        return savedInternships;
    }

    public void setSavedInternships(Set<InternshipPost> savedInternships) {
        this.savedInternships = savedInternships;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.STUDENT.name()));
    }

    @Override
    public String getUsername() {
        return super.getFullName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
