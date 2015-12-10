package de.saphijaga.spoozer.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import de.saphijaga.spoozer.persistence.repository.AccountRepository;
import de.saphijaga.spoozer.persistence.repository.PlaylistRepository;
import de.saphijaga.spoozer.persistence.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "de.saphijaga.spoozer.persistence.repository",
        includeFilters = @ComponentScan.Filter(
                value = {
                        UserRepository.class,
                        PlaylistRepository.class,
                        AccountRepository.class
                },
                type = FilterType.ASSIGNABLE_TYPE
        )
)
public class MongoConfig {
    @Bean
    public MongoTemplate mongoTemplate(Mongo mongo) {
        return new MongoTemplate(mongo, "spoozer_v0_SNAPSHOT");
    }

    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("localhost");
    }
}