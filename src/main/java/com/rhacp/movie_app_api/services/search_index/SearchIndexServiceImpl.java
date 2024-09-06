package com.rhacp.movie_app_api.services.search_index;

import com.rhacp.movie_app_api.models.dtos.MovieDTO;
import com.rhacp.movie_app_api.models.entities.Movie;
import com.rhacp.movie_app_api.models.dtos.movie_api_response.MovieAPIResponseDTO;
import com.rhacp.movie_app_api.models.entities.SearchIndex;
import com.rhacp.movie_app_api.repositories.SearchIndexRepository;
import com.rhacp.movie_app_api.services.movie.MovieService;
import com.rhacp.movie_app_api.utils.properties.Properties;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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

    @Override
    public List<MovieDTO> getMovieList() {
        List<Movie> movieList = makeCallToMoviesApi(properties.getMovieApiLink());
        SearchIndex lastSearchIndex = searchIndexRepository.findFirstByOrderByIdDesc();

        if (lastSearchIndex == null || !movieService.compareListsIdentical(movieList, lastSearchIndex.getMovieList())) {
            SearchIndex savedSearchIndex = searchIndexRepository.save(new SearchIndex(movieList));
            log.info("SearchIndex {} inserted in db. Method: {}.", savedSearchIndex.getId(), "getMovieList");

            movieService.assignSearchIndexAndSave(savedSearchIndex.getMovieList(), savedSearchIndex);

            return savedSearchIndex.getMovieList().stream()
                    .map(movie -> modelMapper.map(movie, MovieDTO.class))
                    .toList();
        } else {
            log.info("SearchIndex the same. Not inserting in db. Method {}.", "getMovieList");

            return lastSearchIndex.getMovieList().stream()
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
