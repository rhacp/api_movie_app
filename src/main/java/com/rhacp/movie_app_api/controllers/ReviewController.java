package com.rhacp.movie_app_api.controllers;

import com.rhacp.movie_app_api.models.dtos.review.ReviewDTO;
import com.rhacp.movie_app_api.models.dtos.review.ReviewInputDTO;
import com.rhacp.movie_app_api.models.dtos.review.ReviewUpdateDTO;
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
     * POST endpoint to create Review.
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
     * GET endpoint to retrieve Review by id.
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

    /**
     * PUT endpoint to update Review by id.
     *
     * @param id Id of the review.
     * @param reviewUpdateDTO DTO containing the new info.
     * @param token User token.
     * @return ResponseEntity.ok : ReviewDTO retrieved.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ReviewDTO> updateReviewById(@PathVariable Long id,
                                                      @Valid @RequestBody ReviewUpdateDTO reviewUpdateDTO,
                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(reviewService.updateReviewById(id, reviewUpdateDTO, token));
    }

    /**
     * DELETE endpoint to remove Review by id.
     *
     * @param id Id of the Review to be deleted.
     * @param token User token.
     * @return ResponseEntity.ok : Map of String and string, confirmation message.
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> deleteReviewById(@PathVariable Long id,
                                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(reviewService.deleteReviewById(id, token));
    }

    @GetMapping("/movies/{movieId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsForMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getAllReviewsForMovie(movieId));
    }

    /**
     * GET endpoint to receive all Reviews for specific User.
     *
     * @param userId Id of the User.
     * @param token User token.
     * @return ResponseEntity.ok : List of MovieListDTO.
     */
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsForUser(@PathVariable Long userId,
                                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(reviewService.getAllReviewsForUser(userId, token));
    }
}
