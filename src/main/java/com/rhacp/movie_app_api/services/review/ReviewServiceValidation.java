package com.rhacp.movie_app_api.services.review;

import com.rhacp.movie_app_api.models.entities.Review;

public interface ReviewServiceValidation {

    /**
     * Search for a user with the specified id and returns it.
     *
     * @param reviewId   Review id to search for.
     * @param methodName Method name.
     * @return Review found.
     * @throws com.rhacp.movie_app_api.exceptions.ReviewNotFoundException if review not found
     */
    Review getValidReview(Long reviewId, String methodName);
}
