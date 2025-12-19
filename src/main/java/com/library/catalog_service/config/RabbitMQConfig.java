package com.library.catalog_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ configuration for event publishing
 */
@Configuration
public class RabbitMQConfig {

    // Exchange names
    public static final String RESOURCE_EXCHANGE = "resource.events";
    public static final String AUDIT_EXCHANGE = "audit.events";
    public static final String BOOKING_EXCHANGE = "booking.events";

    // Queue names for resources
    public static final String RESOURCE_CREATED_QUEUE = "resource.created";
    public static final String RESOURCE_UPDATED_QUEUE = "resource.updated";
    public static final String RESOURCE_DELETED_QUEUE = "resource.deleted";

    // Queue names for listening to booking events (consumed by catalog-service)
    public static final String CATALOG_BOOKING_CREATED_QUEUE = "catalog.booking.created";
    public static final String CATALOG_BOOKING_CANCELED_QUEUE = "catalog.booking.canceled";

    // Routing keys for resources
    public static final String RESOURCE_CREATED_ROUTING_KEY = "resource.created";
    public static final String RESOURCE_UPDATED_ROUTING_KEY = "resource.updated";
    public static final String RESOURCE_DELETED_ROUTING_KEY = "resource.deleted";

    // Routing keys for booking events
    public static final String BOOKING_CREATED_ROUTING_KEY = "booking.created";
    public static final String BOOKING_CANCELED_ROUTING_KEY = "booking.canceled";

    /**
     * Create topic exchanges
     */
    @Bean
    public TopicExchange resourceExchange() {
        return new TopicExchange(RESOURCE_EXCHANGE);
    }

    @Bean
    public TopicExchange auditExchange() {
        return new TopicExchange(AUDIT_EXCHANGE, true, false);
    }

    /**
     * Create queue for resource created events
     */
    @Bean
    public Queue resourceCreatedQueue() {
        return new Queue(RESOURCE_CREATED_QUEUE, true);
    }

    /**
     * Create queue for resource updated events
     */
    @Bean
    public Queue resourceUpdatedQueue() {
        return new Queue(RESOURCE_UPDATED_QUEUE, true);
    }

    /**
     * Create queue for resource deleted events
     */
    @Bean
    public Queue resourceDeletedQueue() {
        return new Queue(RESOURCE_DELETED_QUEUE, true);
    }

    /**
     * Bind resource created queue to exchange
     */
    @Bean
    public Binding resourceCreatedBinding() {
        return BindingBuilder
                .bind(resourceCreatedQueue())
                .to(resourceExchange())
                .with(RESOURCE_CREATED_ROUTING_KEY);
    }

    /**
     * Bind resource updated queue to exchange
     */
    @Bean
    public Binding resourceUpdatedBinding() {
        return BindingBuilder
                .bind(resourceUpdatedQueue())
                .to(resourceExchange())
                .with(RESOURCE_UPDATED_ROUTING_KEY);
    }

    /**
     * Bind resource deleted queue to exchange
     */
    @Bean
    public Binding resourceDeletedBinding() {
        return BindingBuilder
                .bind(resourceDeletedQueue())
                .to(resourceExchange())
                .with(RESOURCE_DELETED_ROUTING_KEY);
    }

    // =========== BOOKING EVENT LISTENERS ===========

    /**
     * Create queue for catalog service to receive booking created events
     */
    @Bean
    public Queue catalogBookingCreatedQueue() {
        return new Queue(CATALOG_BOOKING_CREATED_QUEUE, true);
    }

    /**
     * Create queue for catalog service to receive booking canceled events
     */
    @Bean
    public Queue catalogBookingCanceledQueue() {
        return new Queue(CATALOG_BOOKING_CANCELED_QUEUE, true);
    }

    /**
     * Bind booking created queue to booking exchange (exchange is created by
     * booking-service)
     */
    @Bean
    public Binding catalogBookingCreatedBinding() {
        return BindingBuilder
                .bind(catalogBookingCreatedQueue())
                .to(new TopicExchange(BOOKING_EXCHANGE))
                .with(BOOKING_CREATED_ROUTING_KEY);
    }

    /**
     * Bind booking canceled queue to booking exchange (exchange is created by
     * booking-service)
     */
    @Bean
    public Binding catalogBookingCanceledBinding() {
        return BindingBuilder
                .bind(catalogBookingCanceledQueue())
                .to(new TopicExchange(BOOKING_EXCHANGE))
                .with(BOOKING_CANCELED_ROUTING_KEY);
    }

    /**
     * JSON message converter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate with JSON converter
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
