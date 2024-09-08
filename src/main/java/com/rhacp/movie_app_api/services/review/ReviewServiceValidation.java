package com.rhacp.movie_app_api.services.review;

import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.entities.Review;

public interface ReviewServiceValidation {

    /**
     * Search for a review with the specified id and returns it.
     *
     * @param reviewId Review id to search for.
     * @param methodName Method name.
     * @return Review found.
     * @throws ResourceNotFoundException if review not found.
     */
    Review getValidReview(Long reviewId, String methodName);
}
