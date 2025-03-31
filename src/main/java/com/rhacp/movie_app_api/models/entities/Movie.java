package com.rhacp.movie_app_api.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "overview", columnDefinition = "varchar(1000)")
    private String overview;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "movie_id")
    private Long movieId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "search_index_id")
    @JsonBackReference(value = "searchIndexMovies")
    private SearchIndex searchIndex;

    @ManyToMany(mappedBy = "movies")
    @JsonIgnoreProperties("movies")
    private List<MovieList> movieLists;
}
