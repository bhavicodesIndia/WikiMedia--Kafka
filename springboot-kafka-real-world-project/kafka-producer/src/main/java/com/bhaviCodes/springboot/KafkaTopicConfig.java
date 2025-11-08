package com.bhaviCodes.springboot;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    // 1st Step to define a topic
    @Bean
    public NewTopic topic()
    {
        return TopicBuilder.name("wikimedia").build();
    }

}
