package com.url_shortner.security.jwt;

import java.security.Key;
import java.util.Date;

import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.url_shortner.services.UserDetailsImp;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {


  @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

  // Header / Authorization/ Bearer<Token>

  public String getJwtFromHeader(HttpServletRequest request){
    String bearerToken = request.getHeader("Authorization");
    if(bearerToken != null && bearerToken.startsWith("Bearer")){
      return bearerToken.substring(7);
    }
    return null;
  }


  // generate token

  public String generateToken(UserDetailsImp userDetails){
    String username  = userDetails.getUsername();
    String roles = userDetails.getAuthorities().stream()
    .map(authority -> authority.getAuthority()).collect(Collectors.joining(","));
    return Jwts.builder().setSubject(username).
    claim("roles", roles).
    issuedAt(new Date())
    .expiration(new Date((new Date().getTime() + jwtExpirationMs))).
    signWith(key()).compact();
  }


  public String getUsernameFromJwtToken(String token){
    return Jwts.parser().
    verifyWith((SecretKey) key())
    .build().
    parseSignedClaims(token).getPayload().getSubject();
  }
  private Key key(){
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }
  public boolean validateToken(String authToken){

    try {
       Jwts.parser()
    .verifyWith((SecretKey) key()).
    build().parseSignedClaims(authToken);
    return true;
    } catch (IllegalArgumentException e) {
      throw new RuntimeException();
     
    }
   catch (Exception e) {
      throw new RuntimeException();
     
    }
   
  }

}
