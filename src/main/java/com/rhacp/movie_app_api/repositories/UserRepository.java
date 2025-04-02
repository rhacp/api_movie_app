package com.rhacp.movie_app_api.repositories;

import com.rhacp.movie_app_api.models.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieve User by email.
     *
     * @param email Email to search for.
     * @return Optional : User.
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieve User by email.
     *
     * @param email Email to search for.
     * @return User.
     */
    User findUserByEmail(String email);

    /**
     * Retrieve User by id.
     *
     * @param id Id to search for.
     * @return User.
     */
    User findUserById(Long id);

    /**
     * Retrieve first User by id.
     *
     * @return User.
     */
    User findFirstByOrderById();
}
