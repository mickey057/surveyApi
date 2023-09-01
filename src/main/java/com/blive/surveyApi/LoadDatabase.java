package com.blive.surveyApi;

import com.blive.surveyApi.model.Question;
import com.blive.surveyApi.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(QuestionRepository repository)
    {
        return args -> {
            log.info("Preloading " + repository.save(new Question("question"," 1","titre 1")));
            log.info("Preloading " + repository.save(new Question("question"," 2","titre 2")));
        };
    }
}
