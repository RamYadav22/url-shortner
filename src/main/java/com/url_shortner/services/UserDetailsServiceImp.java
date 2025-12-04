package com.url_shortner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url_shortner.entity.Users;
import com.url_shortner.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found with username" + username)); 
    return UserDetailsImp.build(user);


  }

}
