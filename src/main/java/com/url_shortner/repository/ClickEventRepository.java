package com.url_shortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url_shortner.entity.ClickEvent;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent,Long> {


}
