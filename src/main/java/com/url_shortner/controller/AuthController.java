package com.url_shortner.controller;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url_shortner.dtos.LoginRequest;
import com.url_shortner.dtos.RegisterRequest;
import com.url_shortner.entity.Users;

import com.url_shortner.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {


private UserService userService;


@PostMapping("/public/register")
  public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
    Users user = new Users();
    user.setEmail(registerRequest.getEmail());
    user.setPassword(registerRequest.getPassword());

    user.setRole("ROLE_USER");
    user.setUsername(registerRequest.getUsername());
userService.registeruser(user);
    return ResponseEntity.ok().body("user register succefully");
  }

  @PostMapping("/public/login")
public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
   
  return ResponseEntity.ok(userService.authenticateUser(loginRequest));


}

}
