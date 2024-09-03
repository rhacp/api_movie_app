package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.ReviewDTO;
import com.rhacp.movie_app_api.services.review.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }
}
