package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.review.ReviewDTO;
import com.rhacp.movie_app_api.models.dtos.review.ReviewInputDTO;
import com.rhacp.movie_app_api.services.review.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
     * @param reviewInputDTO Received review DTO.
     * @return ResponseEntity.ok : ReviewDTO created.
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewInputDTO reviewInputDTO,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(reviewService.createReview(reviewInputDTO, token));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    /**
     * GET endpoint to retrieve review by id.
     *
     * @param id Id of the review.
     * @return ResponseEntity.ok : ReviewDTO retrieved.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id,
                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(reviewService.getReviewById(id, token));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ReviewDTO> updateReviewById(@PathVariable Long id,
                                                      @Valid @RequestBody ReviewDTO reviewDTO,
                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(reviewService.updateReviewById(id, reviewDTO, token));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteReviewById(@PathVariable Long id,
                                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(reviewService.deleteReviewById(id, token));
    }

    @GetMapping("/movies/{movieId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsForMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getAllReviewsForMovie(movieId));
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsForUser(@PathVariable Long userId,
                                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(reviewService.getAllReviewsForUser(userId, token));
    }
}
