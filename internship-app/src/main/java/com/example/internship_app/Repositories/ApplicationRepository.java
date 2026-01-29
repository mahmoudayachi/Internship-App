package com.example.internship_app.Repositories;

import com.example.internship_app.Dto.ApplicationDTO;
import com.example.internship_app.Entities.Application;
import com.example.internship_app.Entities.InternshipPost;
import com.example.internship_app.Entities.Student;
import com.example.internship_app.Enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application>  findByStudentId(Long studentId);
    List<Application> findByStudentIdAndStatus(Long studentId, ApplicationStatus status);
    List<Application> findByinternshipOffer(InternshipPost internshipOffer);
    List<Application> findByinternshipOffer_Company_Id(Long companyId);
    Boolean existsByStudent_IdAndInternshipOffer_Id(Long studentId, Long internshipOfferId);
}
