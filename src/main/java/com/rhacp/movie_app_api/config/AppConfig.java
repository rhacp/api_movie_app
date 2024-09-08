package com.rhacp.movie_app_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .build();
    }

//    @Bean(name = "propertyMapper")
//    public TypeMap<MovieAPIDTO, Movie> propertyMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        TypeMap<MovieAPIDTO, Movie> propertyMapper = modelMapper.createTypeMap(MovieAPIDTO.class, Movie.class);
//        propertyMapper.addMapping(MovieAPIDTO::getMovieId, Movie::setMovieId);
//        propertyMapper.addMapping(MovieAPIDTO::getTitle, Movie::setTitle);
//        propertyMapper.addMapping(MovieAPIDTO::getOverview, Movie::setOverview);
//        propertyMapper.addMapping(MovieAPIDTO::getPosterPath, Movie::setPosterPath);
//        propertyMapper.addMapping(null, Movie::setId);
//
//        return propertyMapper;
//    }
}
