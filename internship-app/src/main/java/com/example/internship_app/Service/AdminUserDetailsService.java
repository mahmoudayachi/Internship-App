package com.example.internship_app.Service;

import com.example.internship_app.Repositories.AdminRepository;
import com.example.internship_app.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {


    @Autowired
    private AdminRepository adminRepository ;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.adminRepository.findFirstByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Admin not found "));
    }
}
