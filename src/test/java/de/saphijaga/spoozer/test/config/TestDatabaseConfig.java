package de.saphijaga.spoozer.test.config;

import com.mongodb.Mongo;
import de.saphijaga.spoozer.config.MongoConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by samuel on 16.05.16.
 */
public class TestDatabaseConfig extends MongoConfig {
    @Bean
    public MongoTemplate mongoTemplate(Mongo mongo) {
        return new MongoTemplate(mongo, "spoozer_automated_test");
    }
}