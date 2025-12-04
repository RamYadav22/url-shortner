package com.url_shortner.controller;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url_shortner.dtos.UrlMappingDTO;
import com.url_shortner.entity.UrlMapping;
import com.url_shortner.entity.Users;
import com.url_shortner.services.UrlMappingService;
import com.url_shortner.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {

  @Autowired
  private UrlMappingService urlMappingService;

  @Autowired
  private UserService userService;

  // {"originalUrl" : "https:example.com" }
  @PostMapping("/shorten")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<UrlMappingDTO> createShortUrl(@RequestBody Map<String, String> request, Principal principal) {
    String originalUrl = request.get("originalUrl");

    Users user = userService.findByUsername(principal.getName());

    UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(originalUrl, user);
    return ResponseEntity.ok(urlMappingDTO);
  }

  @GetMapping("/myurls")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal) {
    Users user = userService.findByUsername(principal.getName());
    List<UrlMappingDTO> urls = urlMappingService.getUrlByUser(user);
       for (UrlMappingDTO urls2 : urls) {
        System.out.println(urls2.getCount());
        
       }  
    return ResponseEntity.ok(urls);
  }

  @GetMapping("/getCount/{shorten}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Long> findAllCount(@PathVariable String shorten) {
    long count = urlMappingService.getAllCounts(shorten);
    return ResponseEntity.ok(count);

  }
  @GetMapping("/{shortCode}")
  @PreAuthorize("hasRole('USER')")
public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
    UrlMapping urlMapping = urlMappingService.findByShortUrl(shortCode);
    if (urlMapping == null) {
        return ResponseEntity.notFound().build();
    }

   
   

    return ResponseEntity.status(HttpStatus.FOUND) // 302 Redirect
            .location(URI.create(urlMapping.getOriginalUrl()))
            .build();
}


  @DeleteMapping("/removeUrl/{shorten}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> removeUrl(@PathVariable String shorten) {
    urlMappingService.remveUrlMapping(shorten); // fixed typo
    return ResponseEntity.noContent().build(); // 204 No Content
  }

}
