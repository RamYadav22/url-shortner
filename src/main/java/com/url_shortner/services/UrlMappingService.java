package com.url_shortner.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.url_shortner.dtos.UrlMappingDTO;
import com.url_shortner.entity.ClickEvent;
import com.url_shortner.entity.UrlMapping;
import com.url_shortner.entity.Users;
import com.url_shortner.repository.ClickEventRepository;
import com.url_shortner.repository.UrlMappingRepository;

@Service
public class UrlMappingService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;


   
    @Autowired
    private ClickEventRepository clickEventRepository;

    
    public UrlMappingDTO createShortUrl(String originalUrl, Users user) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(generateShortUrl());
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        urlMapping.setClickCount(0L);

        UrlMapping saved = urlMappingRepository.save(urlMapping);
        return convertToDto(saved);
    }

    public List<UrlMappingDTO> getUrlByUser(Users user) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
        return urlMappings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UrlMapping getShortUrl(String shortUrl) {
    UrlMapping urlMapping  =  urlMappingRepository.findByShortUrl(shortUrl);
        if( urlMapping != null){
          urlMapping.setClickCount(urlMapping.getClickCount()+1);
          urlMappingRepository.save(urlMapping);

          ClickEvent clickEvent = new ClickEvent();
          clickEvent.setClickDate(LocalDateTime.now());
          clickEvent.setUrlMapping(urlMapping);
          clickEventRepository.save(clickEvent);


        }
   return urlMapping;

        
    }

    public List<UrlMappingDTO> getAllUrlMappings() {
        List<UrlMapping> urlMappings = urlMappingRepository.findAll();
        return urlMappings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void deleteUrlMapping(Long id) {
        urlMappingRepository.deleteById(id);
    }

    private UrlMappingDTO convertToDto(UrlMapping urlMapping) {
        UrlMappingDTO dto = new UrlMappingDTO();
        dto.setId(urlMapping.getId());
        dto.setOriginalUrl(urlMapping.getOriginalUrl());
        dto.setShortUrl(urlMapping.getShortUrl());
        dto.setCreatedDate(urlMapping.getCreatedDate());
        dto.setUsername(urlMapping.getUser().getUsername());
        dto.setCount(urlMapping.getClickCount());
      

        return dto;
    }

    private String generateShortUrl() {
        
       String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

       StringBuilder stringBuilder  = new StringBuilder(8);

       Random random= new Random();

       for( int i =0; i < 8; i++){
        stringBuilder.append(character.charAt(random.nextInt(character.length())));
       }
       return stringBuilder.toString();

    }

   public Long getAllCounts(String shortUrl){
    return urlMappingRepository.getClickCounts(shortUrl);
   }

   public void remveUrlMapping(String shortUrl){
   UrlMapping urlMapping =   urlMappingRepository.findByShortUrl(shortUrl);
        urlMappingRepository.delete(urlMapping);
   }

   public UrlMapping findByShortUrl(String shortCode) {

    UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortCode);
        urlMapping.setClickCount(urlMapping.getClickCount()+1);
        urlMappingRepository.save(urlMapping);
        return urlMapping;
   }



}