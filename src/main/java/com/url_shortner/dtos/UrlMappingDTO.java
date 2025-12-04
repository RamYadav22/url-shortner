package com.url_shortner.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class UrlMappingDTO {
  private Long id;
  private String originalUrl;
  private String shortUrl;
  
  private String username;
  private LocalDateTime createdDate;
  private Long count;


 

}