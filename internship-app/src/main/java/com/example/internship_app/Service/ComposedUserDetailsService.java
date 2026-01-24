package com.example.internship_app.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComposedUserDetailsService implements UserDetailsService {

    @Autowired
    private JobseekerUserDetailsService jobseekerUserDetailsService;

    @Autowired
    private   CompanyUserDetailsService companyUserDetailsService;

    @Autowired
    private   AdminUserDetailsService adminUserDetailsService;

    private List<UserDetailsService> services;

    @PostConstruct
    public void setServices() {
        List<UserDetailsService> new_services = new ArrayList<>();
        new_services.add(this.jobseekerUserDetailsService);
        new_services.add(this.companyUserDetailsService);
        new_services.add(this.adminUserDetailsService);
        this.services = new_services;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       for(UserDetailsService service : services){
         try{
          UserDetails user =service.loadUserByUsername(username);
          return user;
         }catch (UsernameNotFoundException e ){
              continue;
         }
       }
       throw new UsernameNotFoundException("User not Found");
    }
}
