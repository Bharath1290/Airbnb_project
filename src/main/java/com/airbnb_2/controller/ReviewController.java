package com.airbnb_2.controller;

import com.airbnb_2.entity.Property;
import com.airbnb_2.entity.PropertyUser;
import com.airbnb_2.entity.Review;
import com.airbnb_2.repository.PropertyRepository;
import com.airbnb_2.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable long propertyId,
            @RequestBody Review review,
            @AuthenticationPrincipal PropertyUser user){
       /* Review r = reviewRepository.findReviewByUserIdAndPropertyId(property, user);
        if(r!= null){
            return new ResponseEntity<>("You have already added a review for this property", HttpStatus.NOT_ACCEPTABLE);
        }*/
        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();

        Review r = reviewRepository.findReviewByUserIdAndPropertyId(property, user);
        if(r!= null){
            return new ResponseEntity<>("You have already added a review for this property", HttpStatus.NOT_ACCEPTABLE);
        }

        review.setProperty(property);
        review.setPropertyUser(user);
        reviewRepository.save(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }
    @GetMapping("/getReviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /*@GetMapping("/reviews/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable long userId) {
        List<Review> byPropertyUserId = reviewRepository.findByPropertyUser_Id(userId);
        return new ResponseEntity<>(byPropertyUserId,HttpStatus.OK);
    }*/

    @GetMapping("/userReviews")
    public ResponseEntity<List<Review>> getUserReviews(@AuthenticationPrincipal PropertyUser user){
        List<Review> byPropertyUserId = reviewRepository.findByPropertyUser(user);
        return new ResponseEntity<>(byPropertyUserId,HttpStatus.OK);
    }
}
