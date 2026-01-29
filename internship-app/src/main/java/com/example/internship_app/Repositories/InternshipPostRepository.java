package com.example.internship_app.Repositories;

import com.example.internship_app.Dto.InternshipPostDto;
import com.example.internship_app.Entities.InternshipPost;
import com.example.internship_app.Enums.InternshipPostStatus;
import com.example.internship_app.Enums.InternshipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipPostRepository extends JpaRepository<InternshipPost,Long> {

    List<InternshipPost>  findByCompanyId(Long companyId);


    @Query(" SELECT i FROM InternshipPost i LEFT JOIN i.skills s "+
            " WHERE i.title  LIKE  %:title%  " +
            "OR i.description LIKE %:description% "+
            " OR i.location LIKE  %:location% "+
            "OR i.duration LIKE %:duration%  " +
            "OR i.status = :status "+
            "OR i.internshiptype = :type "+
            "AND :skills is Null OR s IN (:skills) "
    )
    Page<InternshipPost> searchInternships(
                    @Param("title") String title,
                    @Param("description") String description,
                    @Param("location") String location,
                    @Param("duration") String duration,
                    @Param("status") InternshipPostStatus status,
                    @Param("type") InternshipType type,
                    @Param("skills") List<String> skills,
                    Pageable pageable
            );
}
