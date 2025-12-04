package com.url_shortner.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.url_shortner.entity.Users;

public class UserDetailsImp implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Collection<? extends GrantedAuthority> authorities;
    private String username;
    private String email;
    private String password;
    private Long id;

    public UserDetailsImp(){

    }
    public UserDetailsImp(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public static UserDetailsImp build(Users user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());
        return new UserDetailsImp(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(grantedAuthority)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

     public void setEmail(String email) {
        this.email = email;
    }
}