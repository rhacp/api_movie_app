package com.rhacp.movie_app_api.services.review;

import com.rhacp.movie_app_api.models.dtos.ReviewDTO;

import java.util.List;

public interface ReviewService {

    /**
     * Creates a user based on the given userDTO.
     *
     * @param reviewDTO Given userDTO.
     * @return ReviewDTO of the saved user.
     */
    ReviewDTO createReview(ReviewDTO reviewDTO);

    /**
     * Returns the list of all existing users.
     *
     * @return List of all Reviews as DTOs.
     */
    List<ReviewDTO> getAllReviews();

    /**
     * Return a review based on the given id.
     *
     * @param reviewId Review id to search for.
     * @return ReviewDTO with the specified id.
     */
    ReviewDTO getReviewById(Long reviewId);

    /**
     * Update review based on given id and DTO, and returns it.
     *
     * @param reviewId  Review id to search for.
     * @param reviewDTO ReviewDTO to update from.
     * @return updated ReviewDTO.
     */
    ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO);

    /**
     * Delete review based on given id.
     *
     * @param reviewId Review id to delete.
     * @return String delete message.
     */
    String deleteReview(Long reviewId);
}
