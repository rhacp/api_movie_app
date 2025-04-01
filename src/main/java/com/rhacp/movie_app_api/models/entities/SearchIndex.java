package com.rhacp.movie_app_api.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "search_indexes")
public class SearchIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "creation_time")
    private LocalTime creationTime;

    @Column(name = "search")
    private Integer search;

    @Column(name = "keyword")
    private String keyword;

    @OneToMany(mappedBy = "searchIndex")
    @JsonManagedReference(value = "searchIndexMovies")
    private List<Movie> movieList = new ArrayList<>();

    public SearchIndex(LocalDate creationDate, LocalTime creationTime, List<Movie> movieList, Integer search, String keyword) {
        this.creationDate = creationDate;
        this.creationTime = creationTime;
        this.movieList = movieList;
        this.search = search;
        this.keyword = keyword;
    }
}

