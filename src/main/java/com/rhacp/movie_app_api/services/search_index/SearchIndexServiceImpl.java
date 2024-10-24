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
import java.util.List;

import static java.lang.Math.abs;
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
        // Get last SearchIndex list by keyword.
        List<SearchIndex> searchIndexListByKeyword = searchIndexRepository.findByKeyword(keyword);

        // Initialize last SearchIndex by Keyword.
        SearchIndex lastSearchIndexByKeyword = new SearchIndex();
        if (!searchIndexListByKeyword.isEmpty()) {
            lastSearchIndexByKeyword = searchIndexListByKeyword.getLast();
        }
        lastSearchIndexByKeyword.setTime(LocalTime.now().withNano(0).minus(100, MINUTES));

        // Check if enough time passed.
        if (abs(MINUTES.between(lastSearchIndexByKeyword.getTime(), LocalTime.now())) < properties.getRetrieveInterval()) {
            return returnLastMovieList(lastSearchIndexByKeyword);
        }

        // Retrieve movie list.
        List<Movie> movieList;
        if (keyword == null) {
            movieList = makeCallToMoviesApi(properties.getMovieApiLink());
        } else {
            movieList = makeCallToMoviesApi(properties.getMovieApiSearch() + keyword);
        }

        // Create and save new SearchIndex.
        SearchIndex savedSearchIndex = searchIndexRepository.save(new SearchIndex(
                LocalDate.now(),
                LocalTime.now().withNano(0),
                movieList,
                ((keyword != null) ? 1 : 0),
                keyword));
        log.info("SearchIndex {} inserted in db. Method: {}.", savedSearchIndex.getId(), "getMovieList");

        // Save movies/ update existing ones with new SearchIndex id.
        movieService.assignSearchIndex(savedSearchIndex.getMovieList(), savedSearchIndex);
        movieService.saveMovieList(savedSearchIndex.getMovieList());

        return savedSearchIndex.getMovieList().stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .toList();
    }

    /**
     * Helper function that executes the call to the movie API to retrieve the movie list.
     *
     * @param url Movies API url.
     * @return API movie list.
     */
    private List<Movie> makeCallToMoviesApi(String url) {
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

    private List<MovieDTO> returnLastMovieList(SearchIndex retrievedSearchIndex) {
        log.warn("Keyword the same and not enough time passed to refresh. Not inserting searchIndex in db. Method {}.", "getMovieList");

        return retrievedSearchIndex.getMovieList().stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .toList();
    }
}
