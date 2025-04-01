package com.rhacp.movie_app_api.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rhacp.movie_app_api.models.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "creation_time")
    private LocalTime creationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "review")
    private User reviewUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id")
    @JsonBackReference(value = "reviewMovie")
    private Movie reviewMovie;
}
