package com.rhacp.movie_app_api.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {

    private String token;

    private LocalDateTime expires;
}
