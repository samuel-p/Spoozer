package de.saphijaga.spoozer.test.application.config;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by samuel on 06.12.15.
 */
@Component
public class TestInitialization {
    @Autowired
    private MongoTemplate mongo;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize() {
        mongo.getDb().dropDatabase();

        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("test");
        request.setPassword("Test1234");
        request.setEmail("test@spoozer.de");
        userService.registerUser(request);
    }
}