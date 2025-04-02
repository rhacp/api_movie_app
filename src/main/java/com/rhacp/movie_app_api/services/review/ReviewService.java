package com.rhacp.movie_app_api.services.review;

import com.rhacp.movie_app_api.models.dtos.review.ReviewDTO;
import com.rhacp.movie_app_api.models.dtos.review.ReviewInputDTO;
import com.rhacp.movie_app_api.models.dtos.review.ReviewUpdateDTO;

import java.util.List;
import java.util.Map;

public interface ReviewService {

    /**
     * Creates a user based on the given userDTO.
     *
     * @param reviewInputDTO Given reviewInputDTO.
     * @return ReviewDTO of the saved user.
     */
    ReviewDTO createReview(ReviewInputDTO reviewInputDTO, String token);

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
    ReviewDTO getReviewById(Long reviewId, String token);

    /**
     * Update review based on given id and DTO, and returns it.
     *
     * @param reviewId  Review id to search for.
     * @param reviewUpdateDTO ReviewUpdateDTO to update from.
     * @return updated ReviewDTO.
     */
    ReviewDTO updateReviewById(Long reviewId, ReviewUpdateDTO reviewUpdateDTO, String token);

    /**
     * Delete review based on given id.
     *
     * @param reviewId Review id to delete.
     * @return String delete message.
     */
    Map<String, String> deleteReviewById(Long reviewId, String token);

    List<ReviewDTO> getAllReviewsForMovie(Long movieId);

    List<ReviewDTO> getAllReviewsForUser(Long userId, String token);
}
