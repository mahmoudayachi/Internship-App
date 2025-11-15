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


    @Query("""
    SELECT i FROM InternshipPost i
    WHERE (:status IS NULL OR i.status = :status)
      AND (:type IS NULL OR i.internshiptype = :type)
      AND (:location IS NULL OR LOWER(i.location) LIKE LOWER(CONCAT('%', :location, '%')))
      AND (:duration IS NULL OR LOWER(i.duration) LIKE LOWER(CONCAT('%', :duration, '%')))
      AND (:search IS NULL OR LOWER(i.title) LIKE LOWER(CONCAT('%', :search, '%'))
          OR LOWER(i.description) LIKE LOWER(CONCAT('%', :search, '%'))
      )
""")
    Page<InternshipPost> searchAndFilter(
            @Param("status") InternshipPostStatus status,
            @Param("location") String location,
            @Param("duration") String duration,
            @Param("type") InternshipType type,
            @Param("search") String search,
            Pageable pageable
    );
}
