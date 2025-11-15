package com.example.internship_app.Repositories;

import com.example.internship_app.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    Optional<Company> findFirstByEmail(String username);
}
