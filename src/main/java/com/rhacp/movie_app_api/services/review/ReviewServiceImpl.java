package com.rhacp.movie_app_api.services.review;

import com.rhacp.movie_app_api.models.dtos.ReviewDTO;
import com.rhacp.movie_app_api.models.entities.Review;
import com.rhacp.movie_app_api.repositories.ReviewRepository;
import com.rhacp.movie_app_api.utils.properties.Properties;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewServiceValidation reviewServiceValidation;
    private final ModelMapper modelMapper;
    private final Properties properties;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewServiceValidation reviewServiceValidation, ModelMapper modelMapper, Properties properties) {
        this.reviewRepository = reviewRepository;
        this.reviewServiceValidation = reviewServiceValidation;
        this.modelMapper = modelMapper;
        this.properties = properties;
    }


    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setDateTime(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        log.info("Review {} inserted in db. Method: {}.", savedReview.getId(), "createReview");

        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        return List.of();
    }

    @Override
    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewServiceValidation.getValidReview(reviewId, "getReviewById");

        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
        return null;
    }

    @Override
    public String deleteReview(Long reviewId) {
        return "";
    }
}
