package com.url_shortner.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
@Autowired
  private JwtUtils jwtTokenProvider;

  @Autowired 
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws SecurityException , IOException, ServletException
     {
      try {
        // get jwt from header

        String jwt = jwtTokenProvider.getJwtFromHeader(request);
        if(jwt != null && jwtTokenProvider.validateToken(jwt)){
          String username = jwtTokenProvider.getUsernameFromJwtToken(jwt);
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);

          if(userDetails != null){
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
        // validate token
        //if valid get userdetails
                // get username -> load Users  -> set the auth context

      } catch (Exception e) {
         e.printStackTrace();
        }
          filterChain.doFilter(request, response);
  }

}
