package com.rhacp.movie_app_api.utils.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties class which maps values from the YAML config file to variables.
 */
@Data
@Configuration
@ConfigurationProperties
public class Properties {

    @Value("${fe.api.base.urlFirst}")
    private String feUrlFirst;

    @Value("${fe.api.base.urlSecond}")
    private String feUrlSecond;

    @Value("${movie.api.link}")
    private String movieApiLink;

    @Value("${movie.img.path}")
    private String movieImgPath;

    @Value("${movie.api.seach}")
    private String movieApiSearch;

    @Value("${movie.api.key}")
    private String movieApiKey;

    @Value("${security.secret}")
    private String secret;

    @Value("${retrieve.interval}")
    private Integer retrieveInterval;

    @Value("${token.lifetime.minutes}")
    private Long tokenLifetime;

    @Value("${fa.username}")
    private String faUsername;

    @Value("${fa.password}")
    private String faPassword;
}
