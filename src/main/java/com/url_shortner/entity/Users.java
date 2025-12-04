package com.url_shortner.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String password;
  private String role = "ROLE_USER";
  private String username;

  public Users() {
  }

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<UrlMapping> urlMapping;

  public Users(Long id, String email, String password, String role, String username) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
    this.username = username;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}