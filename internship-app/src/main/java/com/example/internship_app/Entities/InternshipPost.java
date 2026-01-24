package com.example.internship_app.Entities;


import com.example.internship_app.Enums.InternshipPostStatus;
import com.example.internship_app.Enums.InternshipType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class InternshipPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @Column ( nullable = false)
    private String title;

    private String description;

    private String location;

    private String  duration;

    private LocalDate startDate ;

    private LocalDate  endDate;

    private LocalDate Applydeadline ;

    @Enumerated(EnumType.STRING)
    private InternshipType internshiptype ;



    private LocalDateTime createdAt ;
    @Enumerated(EnumType.STRING)
    private InternshipPostStatus status;

    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "skills")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<String> skills = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "requirements", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "requirements")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<String> requirements = new ArrayList<>();



    @ManyToOne
    @JoinColumn( name ="company_id")
    private Company company ;

    @ManyToMany(mappedBy = "savedInternships")
    @JsonIgnore
    private Set<Student> savedByStudents = new HashSet<>();


    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }



    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Set<Student> getSavedByStudents() {
        return savedByStudents;
    }

    public void setSavedByStudents(Set<Student> savedByStudents) {
        this.savedByStudents = savedByStudents;
    }

    public LocalDate getApplydeadline() {
        return Applydeadline;
    }

    public void setApplydeadline(LocalDate applydeadline) {
        Applydeadline = applydeadline;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InternshipPost)) return false;
        InternshipPost that = (InternshipPost) o;
        return id != null && id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
