package com.rhacp.movie_app_api.services.review;

import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.entities.Review;
import com.rhacp.movie_app_api.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReviewServiceValidationImpl implements ReviewServiceValidation {

    private final ReviewRepository reviewRepository;

    public ReviewServiceValidationImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    @Override
    public Review getValidReview(Long reviewId, String methodName) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review with the id " + reviewId + " not found."));
        log.error("Review with the id {} retrieved. Method: {}", reviewId, methodName);

        return review;
    }
}
