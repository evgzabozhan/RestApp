package com.zabozhan.restApp.items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    public static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ItemRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Item("Macbook Pro","Laptop")));
            log.info("Preloading " + repository.save(new Item("Macbook Air","Laptop")));
        };
    }
}
