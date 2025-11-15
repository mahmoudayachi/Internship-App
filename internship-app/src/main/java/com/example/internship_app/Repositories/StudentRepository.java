package com.example.internship_app.Repositories;

import com.example.internship_app.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findFirstByEmail(String username);
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM saved_internships 
        WHERE student_id = :studentId 
        AND internship_id = :postId
    """, nativeQuery = true)
    void unsaveInternship(@Param("studentId") Long studentId, @Param("postId") Long postId);
}
