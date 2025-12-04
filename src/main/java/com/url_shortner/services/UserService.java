package com.url_shortner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.url_shortner.dtos.LoginRequest;
import com.url_shortner.entity.Users;
import com.url_shortner.repository.UserRepository;
import com.url_shortner.security.jwt.JwtAuthenticationResponse;
import com.url_shortner.security.jwt.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private AuthenticationManager authenticationManager;

  public Users registeruser(Users user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())

    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
    String jwt = jwtUtils.generateToken(userDetailsImp);
    return new JwtAuthenticationResponse(jwt);
  }

  public Users findByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("user not found with username" + username));
  }

}
