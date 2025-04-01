package com.rhacp.movie_app_api.services.review;

import com.rhacp.movie_app_api.models.dtos.review.ReviewDTO;
import com.rhacp.movie_app_api.models.dtos.review.ReviewInputDTO;
import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.entities.Review;
import com.rhacp.movie_app_api.models.entities.user.User;
import com.rhacp.movie_app_api.repositories.ReviewRepository;
import com.rhacp.movie_app_api.services.movie.MovieService;
import com.rhacp.movie_app_api.services.user.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewServiceValidation reviewServiceValidation;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final MovieService movieService;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewServiceValidation reviewServiceValidation,
                             ModelMapper modelMapper,
                             UserService userService,
                             MovieService movieService) {
        this.reviewRepository = reviewRepository;
        this.reviewServiceValidation = reviewServiceValidation;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.movieService = movieService;
    }

    @Transactional
    @Override
    public ReviewDTO createReview(ReviewInputDTO reviewInputDTO,
                                  String token) {
        Review review = modelMapper.map(reviewInputDTO, Review.class);

        review.setCreationDate(LocalDate.now());
        review.setCreationTime(LocalTime.now().withNano(0));
        review.setReviewUser(userService.getUserByToken(token));
        review.setReviewMovie(movieService.getMovieEntityById(reviewInputDTO.getMovieId()));

        Review savedReview = reviewRepository.save(review);
        log.info("Review {} inserted. Method: {}.", savedReview.getId(), "createReview");

        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviewList = reviewRepository.findAll();
        log.info("Review list retrieved. Method: {}.", "getAllReviews");

        return reviewList.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .toList();
    }

    @Override
    public ReviewDTO getReviewById(Long id,
                                   String token) {
        Review review = reviewServiceValidation.getValidReview(id, "getReviewById");

        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReviewById(Long id,
                                      ReviewDTO reviewDTO,
                                      String token) {
        Review reviewFound = reviewServiceValidation.getValidReview(id, "updateReviewById");

        userService.checkIfUserTheSame(userService.getUserByToken(token), reviewFound.getReviewUser());

        updateReviewFromDTO(reviewFound, reviewDTO);
        Review savedReview = reviewRepository.save(reviewFound);
        log.info("Review {} updated. Method: {}.", savedReview.getId(), "updateReviewById");

        return modelMapper.map(reviewFound, ReviewDTO.class);
    }

    @Transactional
    @Override
    public Map<String, String> deleteReviewById(Long id,
                                                String token) {
        Review foundReview = reviewServiceValidation.getValidReview(id, "deleteReviewById");
        userService.checkIfUserTheSame(userService.getUserByToken(token), foundReview.getReviewUser());

        reviewRepository.deleteById(id);
        log.info("Review {} deleted. Method: {}.", id, "deleteReviewById");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Review with id " + id + " deleted.");

        return response;
    }

    @Transactional
    @Override
    public List<ReviewDTO> getAllReviewsForMovie(Long movieId) {
        Movie foundMovie = movieService.getMovieEntityById(movieId);

        List<Review> reviewList = reviewRepository.findReviewByReviewMovie(foundMovie);

        return reviewList.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .toList();
    }

    @Transactional
    @Override
    public List<ReviewDTO> getAllReviewsForUser(Long userId,
                                                String token) {
        User foundUser = userService.getUserEntityById(userId);
        userService.checkIfUserTheSame(userService.getUserByToken(token), foundUser);

        List<Review> reviewList = reviewRepository.findReviewByReviewUser(foundUser);

        return reviewList.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .toList();
    }

    private void updateReviewFromDTO(Review review,
                                     ReviewDTO reviewDTO) {
        if (reviewDTO.getReviewText() != null) {
            review.setReviewText(reviewDTO.getReviewText());
        }
    }
}
