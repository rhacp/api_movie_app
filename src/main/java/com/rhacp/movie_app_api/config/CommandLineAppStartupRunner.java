package com.rhacp.movie_app_api.config;

import com.rhacp.movie_app_api.services.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserService userService;

    public CommandLineAppStartupRunner(UserService userService) {
        this.userService = userService;
    }

    /**
     * Runs once only when the application is started.
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        userService.createFirstUser();
    }
}
