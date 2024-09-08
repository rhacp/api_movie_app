package com.rhacp.movie_app_api.services.search_index;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.dtos.movie_api_response.MovieAPIResponseDTO;
import com.rhacp.movie_app_api.models.entities.SearchIndex;
import com.rhacp.movie_app_api.repositories.SearchIndexRepository;
import com.rhacp.movie_app_api.services.movie.MovieService;
import com.rhacp.movie_app_api.utils.properties.Properties;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Slf4j
@Service
public class SearchIndexServiceImpl implements SearchIndexService {

    private final Properties properties;

    private final WebClient webClient;

    private final ModelMapper modelMapper;

    private final SearchIndexRepository searchIndexRepository;

    private final MovieService movieService;

    public SearchIndexServiceImpl(Properties properties, WebClient webClient, ModelMapper modelMapper, SearchIndexRepository searchIndexRepository, MovieService movieService) {
        this.properties = properties;
        this.webClient = webClient;
        this.modelMapper = modelMapper;
        this.searchIndexRepository = searchIndexRepository;
        this.movieService = movieService;
    }

    @Transactional
    @Override
    public List<MovieDTO> getMovieList(String keyword) {
        SearchIndex lastSearchIndex = searchIndexRepository.findFirstByOrderByIdDesc();
        List<SearchIndex> retrievedSearchIndexList = searchIndexRepository.findByKeyword(keyword);

        SearchIndex retrievedSearchIndex = new SearchIndex();
        retrievedSearchIndex.setTime(LocalTime.now().withNano(0).minus(100, MINUTES));

        if (!retrievedSearchIndexList.isEmpty()) {
            retrievedSearchIndex = retrievedSearchIndexList.getLast();
        }

        if (lastSearchIndex == null || retrievedSearchIndex == null || MINUTES.between(retrievedSearchIndex.getTime(), LocalTime.now()) > 1) {
            List<Movie> movieList = new ArrayList<>();
            if (keyword == null) {
                movieList = makeCallToMoviesApi(properties.getMovieApiLink());
            } else {
                movieList = makeCallToMoviesApi(properties.getMovieApiSearch() + keyword);
            }

            SearchIndex savedSearchIndex = searchIndexRepository.save(new SearchIndex(
                    LocalDate.now(),
                    LocalTime.now().withNano(0),
                    movieList,
                    ((keyword != null) ? 1 : 0),
                    keyword));
            log.info("SearchIndex {} inserted in db. Method: {}.", savedSearchIndex.getId(), "getMovieList");

            movieService.assignSearchIndex(savedSearchIndex.getMovieList(), savedSearchIndex);

            return savedSearchIndex.getMovieList().stream()
                    .map(movie -> modelMapper.map(movie, MovieDTO.class))
                    .toList();
        } else {
            log.warn("Keyword the same and not enough time passed to refresh. Not inserting searchIndex in db. Method {}.", "getMovieList");

            return retrievedSearchIndex.getMovieList().stream()
                    .map(movie -> modelMapper.map(movie, MovieDTO.class))
                    .toList();
        }
    }

    @Override
    public List<Movie> makeCallToMoviesApi(String url) {
        MovieAPIResponseDTO movieAPIResponseDTOMono = webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MovieAPIResponseDTO.class)
                .block();

        return movieAPIResponseDTOMono.getResults().stream()
                .map(movie -> modelMapper.map(movie, Movie.class))
                .toList();
    }
}
