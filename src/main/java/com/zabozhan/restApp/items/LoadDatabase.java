package com.zabozhan.restApp.items;

import com.zabozhan.restApp.order.Order;
import com.zabozhan.restApp.order.OrderRepository;
import com.zabozhan.restApp.order.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ItemRepository itemRepository, OrderRepository orderRepository) {

        return args -> {
            itemRepository.save(new Item("MacBook Pro", "Laptop"));
            itemRepository.save(new Item("MacBook Air", "Laptop"));

            itemRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });

        };
    }
}