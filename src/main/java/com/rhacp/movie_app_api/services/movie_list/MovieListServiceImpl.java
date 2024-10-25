package com.rhacp.movie_app_api.services.movie_list;

import com.rhacp.movie_app_api.models.dtos.MovieListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieListServiceImpl implements MovieListService {


    @Override
    public MovieListDTO createMovieList(MovieListDTO movieListDTO, String token) {
        return null;
    }
}
