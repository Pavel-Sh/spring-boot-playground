package com.playground.microservice.config;

import com.playground.microservice.web.MicroserviceController;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(MicroserviceController.MICROSERVICE_CREATED_TOPIC)
                .partitions(4)
                .build();
    }

}
