package com.url_shortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url_shortner.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long>{
  public Optional<Users> findByUsername(String username);

}
