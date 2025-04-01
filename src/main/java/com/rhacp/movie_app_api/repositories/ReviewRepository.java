package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.Review;
import com.rhacp.movie_app_api.models.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Query to retrieve review by id from DB.
     *
     * @param id Id of the movie.
     * @return Review.
     */
    Review findReviewById(Long id);

    List<Review> findReviewByReviewMovie(Movie movie);

    List<Review> findReviewByReviewUser(User user);
}
