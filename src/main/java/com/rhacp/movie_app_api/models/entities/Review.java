package com.rhacp.movie_app_api.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "requestor_user")
    private String requestorUser;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "date_time", unique = true)
    private LocalDateTime dateTime;
}
