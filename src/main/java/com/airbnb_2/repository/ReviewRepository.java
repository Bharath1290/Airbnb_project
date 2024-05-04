package com.airbnb_2.repository;

import com.airbnb_2.entity.Property;
import com.airbnb_2.entity.PropertyUser;
import com.airbnb_2.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.property = :property AND r.propertyUser = :user")
    Review findReviewByUserIdAndPropertyId(@Param("property") Property property, @Param("user") PropertyUser user);

    List<Review> findAll();

    List<Review> findByPropertyUser(PropertyUser user);
    //List<Review> findByPropertyUser_Id(long userId);
}