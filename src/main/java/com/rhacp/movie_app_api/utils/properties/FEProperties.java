package com.rhacp.movie_app_api.utils.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "fe.api")
public class FEProperties {

    @Value("${fe.api.base.urlFirst}")
    private String feUrlFirst;

    @Value("${fe.api.base.urlSecond}")
    private String feUrlSecond;
}
