package de.saphijaga.spoozer.test.persistence.service;

import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.handler.UserPersistenceHandler;
import de.saphijaga.spoozer.test.cucumber.application.config.TestMongoConfig;
import de.saphijaga.spoozer.test.data.TestUserFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by samuel on 17.05.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestMongoConfig.class, UserPersistenceHandler.class})
public class MongoIntegrationTest {
    @Autowired
    private MongoOperations mongo;

    @Before
    public void setUp() throws Exception {
        dropAll(mongo);
        testingSetup(mongo);
    }

    @After
    public void tearDown() throws Exception {
        dropAll(mongo);
    }

    private void testingSetup(MongoOperations mongo) {
        mongo.insert(TestUserFactory.testUser());
        // TODO add all test domain objects
    }

    protected void dropAll(MongoOperations mongo) {
        mongo.dropCollection(User.class);
        // TODO add all persistence domain classes
    }

    protected User find(String id) {
        return find(id, User.class);
    }

    // TODO add methods to find domain objects in database

    private <T> T find(String id, Class<T> domainClass) {
        return mongo.findOne(new Query(where("_id").is(id)), domainClass);
    }
}