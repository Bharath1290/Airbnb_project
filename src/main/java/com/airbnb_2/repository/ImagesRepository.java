package com.airbnb_2.repository;

import com.airbnb_2.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long> {
}