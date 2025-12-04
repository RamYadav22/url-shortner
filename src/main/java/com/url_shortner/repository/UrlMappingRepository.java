package com.url_shortner.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.url_shortner.entity.UrlMapping;
import com.url_shortner.entity.Users;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    List<UrlMapping> findByUser(Users user);

    UrlMapping findByShortUrl(String shortUrl);

    @Query("SELECT u.clickCount FROM UrlMapping u WHERE u.shortUrl = :shortUrl")
    Long getClickCounts(@Param("shortUrl") String shortUrl);

}