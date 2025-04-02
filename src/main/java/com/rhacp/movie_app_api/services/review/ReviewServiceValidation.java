package com.rhacp.movie_app_api.services.review;

import com.rhacp.movie_app_api.exceptions.ResourceNotFoundException;
import com.rhacp.movie_app_api.models.entities.Review;

public interface ReviewServiceValidation {

    /**
     * Search for Review with the specified id and returns it.
     *
     * @param id Id to search for.
     * @param methodName Caller.
     * @return Review.
     * @throws ResourceNotFoundException if review not found.
     */
    Review getValidReview(Long id, String methodName);
}
