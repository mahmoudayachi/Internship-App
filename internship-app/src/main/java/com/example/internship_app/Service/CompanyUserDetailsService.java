package com.example.internship_app.Service;


import com.example.internship_app.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompanyUserDetailsService implements UserDetailsService {

    @Autowired
    private CompanyRepository companyRepository ;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.companyRepository.findFirstByEmail(username).orElseThrow(()-> new UsernameNotFoundException("company not found "));
    }
}
