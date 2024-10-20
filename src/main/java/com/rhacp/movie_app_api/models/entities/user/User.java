package com.rhacp.movie_app_api.models.entities.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rhacp.movie_app_api.models.entities.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "review")
    private List<Review> reviewList = new ArrayList<>();
}
