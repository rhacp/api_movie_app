package com.rhacp.movie_app_api.models.entities.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rhacp.movie_app_api.models.entities.MovieList;
import com.rhacp.movie_app_api.models.entities.Review;
import com.rhacp.movie_app_api.utils.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "creation_time")
    private LocalTime creationTime;

    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "reviewUser")
    @JsonManagedReference(value = "review")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "userMovieList")
    @JsonManagedReference(value = "listMovieList")
    private List<MovieList> listMovieList = new ArrayList<>();
}
