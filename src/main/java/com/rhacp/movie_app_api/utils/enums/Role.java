package com.rhacp.movie_app_api.utils.enums;

import lombok.Getter;

@Getter
public enum Role {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String roleLabel;

    Role(String roleLabel) {
        this.roleLabel = roleLabel;
    }
}
