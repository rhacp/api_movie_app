package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findUserByEmail(String email);
}
