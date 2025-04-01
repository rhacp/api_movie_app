package com.rhacp.movie_app_api.models.entities.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class for JWT linked to the local <code>User</code> entity.
 */
public class UserInfoDetails implements UserDetails {

    private final String username; // Changed from 'name' to 'username' for clarity
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfoDetails(User user) {
        this.username = user.getEmail(); // Assuming 'name' is used as 'username'
        this.password = user.getPassword();

//        this.authorities = List.of(user.getRole().split(","))
        this.authorities = Stream.of(user.getRole().getRoleLabel())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement your logic if you need this
    }
}
