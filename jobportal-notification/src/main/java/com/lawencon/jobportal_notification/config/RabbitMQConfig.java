package com.lawencon.jobportal_notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {
    public static final String VACANCY_NOTIFICATION_QUEUE = "vacancy.notification.queue";
    public static final String VACANCY_NOTIFICATION_EXCHANGE = "vacancy.notification.exchange";
    public static final String VACANCY_NOTIFICATION_ROUTING_KEY = "vacancy.created.*";

    @Bean
    public Queue vacancyNotificationQueue() {
        return new Queue(VACANCY_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public TopicExchange vacancyNotificationExchange() {
        return new TopicExchange(VACANCY_NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue vacancyNotificationQueue, TopicExchange vacancyNotificationExchange) {
        return BindingBuilder.bind(vacancyNotificationQueue).to(vacancyNotificationExchange).with(VACANCY_NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }

    @Bean
    public RabbitTemplate  rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }    
}
