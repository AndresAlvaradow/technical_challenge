package com.banco.cliente.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CLIENTE_QUEUE = "cliente.queue";
    public static final String CLIENTE_EXCHANGE = "cliente.exchange";
    public static final String CLIENTE_ROUTING_KEY = "cliente.created";

    @Bean
    public Queue clienteQueue() {
        return QueueBuilder.durable(CLIENTE_QUEUE).build();
    }

    @Bean
    public DirectExchange clienteExchange() {
        return new DirectExchange(CLIENTE_EXCHANGE);
    }

    @Bean
    public Binding clienteBinding() {
        return BindingBuilder.bind(clienteQueue())
                .to(clienteExchange())
                .with(CLIENTE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}