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
@Table(name = "search_index")
public class SearchIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "search")
    private Integer search;

    @Column(name = "keyword")
    private String keyword;

    @OneToMany(mappedBy = "searchIndex")
    @JsonManagedReference(value = "movies")
    private List<Movie> movieList = new ArrayList<>();

    public SearchIndex(LocalDate date, LocalTime time, List<Movie> movieList, Integer search, String keyword) {
        this.date = date;
        this.time = time;
        this.movieList = movieList;
        this.search = search;
        this.keyword = keyword;
    }
}

