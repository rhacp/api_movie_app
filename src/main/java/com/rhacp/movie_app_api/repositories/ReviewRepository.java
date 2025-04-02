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
     * Retrieve Review by id.
     *
     * @param id Id of the Review.
     * @return Review.
     */
    Review findReviewById(Long id);

    /**
     * Retrieve Reviews by Movie.
     *
     * @param movie Movie to search for.
     * @return List of Review.
     */
    List<Review> findReviewByReviewMovie(Movie movie);

    /**
     * Retrieve Reviews by User.
     *
     * @param user User to search for.
     * @return List of Review.
     */
    List<Review> findReviewByReviewUser(User user);
}
