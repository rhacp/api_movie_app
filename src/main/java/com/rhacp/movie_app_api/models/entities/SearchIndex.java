package com.rhacp.movie_app_api.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "search_index")
public class SearchIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "searchIndex")
    @JsonManagedReference(value = "movies")
    private List<Movie> movieList = new ArrayList<>();

    public SearchIndex(List<Movie> movieList) {
        this.movieList = movieList;
    }
}

