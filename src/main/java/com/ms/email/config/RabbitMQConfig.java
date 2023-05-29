package com.ms.email.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queues.email}")
    private String email;

    @Value("${spring.rabbitmq.queues.welcome}")
    private String welcome;

    @Value("${spring.rabbitmq.queues.recovery}")
    private String recovery;

    @Bean
    public Queue queueEmail() {
        return new Queue(email, true);
    }

    @Bean
    public Queue queueWelcome() {
        return new Queue(welcome, true);
    }

    @Bean
    public Queue queueRecovery() {
        return new Queue(recovery, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}