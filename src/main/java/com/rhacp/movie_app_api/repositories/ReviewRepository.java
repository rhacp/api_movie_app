package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Query to retrieve review by id from DB.
     *
     * @param id Id of the movie.
     * @return Review.
     */
    Review findReviewById(Long id);
}
