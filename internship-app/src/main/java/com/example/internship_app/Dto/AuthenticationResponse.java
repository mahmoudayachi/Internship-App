package com.example.internship_app.Dto;

import com.example.internship_app.Enums.Role;
import lombok.Data;


@Data
public class AuthenticationResponse {

    private String jwt ;
    private Long userId;
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getUserRole() {
        return this.role;
    }

    public void setUserRole(Role role) {
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
