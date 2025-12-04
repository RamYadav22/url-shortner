package com.url_shortner.controller;



import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.url_shortner.entity.UrlMapping;
import com.url_shortner.services.UrlMappingService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RedirectController {

  private UrlMappingService urlMappingService;

  @GetMapping("/{shortUrl}")
  public ResponseEntity<Void>redirect(@PathVariable String shortUrl){

    UrlMapping urlMapping = urlMappingService.getShortUrl(shortUrl);

    if( urlMapping != null){
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add("Location", urlMapping.getOriginalUrl());
      return ResponseEntity.status(302).headers(httpHeaders).build();
     
    }
   
     return ResponseEntity.notFound().build();
    

  }

}
