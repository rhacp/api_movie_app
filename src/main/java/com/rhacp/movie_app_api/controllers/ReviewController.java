package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.ReviewDTO;
import com.rhacp.movie_app_api.services.review.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * POST endpoint to create review.
     *
     * @param reviewDTO Received review DTO.
     * @return ResponseEntity.ok : ReviewDTO created.
     */
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    /**
     * GET endpoint to retrieve review by id.
     *
     * @param reviewId Id of the review.
     * @return ResponseEntity.ok : ReviewDTO retrieved.
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }
}
