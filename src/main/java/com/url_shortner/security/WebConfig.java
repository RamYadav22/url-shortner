package com.url_shortner.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig  implements WebMvcConfigurer{


  @Value("${frontend.url}")
  private String frontedUrl;
  

  @Bean
    public WebMvcConfigurer webMvcConfigurer(){
      return new WebMvcConfigurer(){

    @Override
    public void addCorsMappings(CorsRegistry registry){
      registry.addMapping("/**")
      .allowedOrigins(frontedUrl).
      allowedMethods("GET","POST","DELETE","PUT","OPTIONS")
      .allowedHeaders("*")
      .allowCredentials(false).
      maxAge(3600);
    }


  };
    }
  


}
