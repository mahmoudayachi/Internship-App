package com.example.internship_app.Repositories;

import com.example.internship_app.Entities.Admin;
import com.example.internship_app.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Optional<Admin> findFirstByEmail(String username);
}
