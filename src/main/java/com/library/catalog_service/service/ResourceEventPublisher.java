package com.library.catalog_service.service;

import com.library.catalog_service.config.RabbitMQConfig;
import com.library.catalog_service.dto.ResourceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for publishing resource events to RabbitMQ
 */
@Service
public class ResourceEventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(ResourceEventPublisher.class);
    
    private final RabbitTemplate rabbitTemplate;
    
    public ResourceEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    /**
     * Publish resource created event
     */
    public void publishResourceCreated(ResourceResponse resource) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESOURCE_EXCHANGE,
                RabbitMQConfig.RESOURCE_CREATED_ROUTING_KEY,
                resource
            );
            logger.info("Published resource.created event for resource: {}", resource.getId());
        } catch (Exception e) {
            logger.error("Failed to publish resource.created event: {}", e.getMessage());
        }
    }
    
    /**
     * Publish resource updated event
     */
    public void publishResourceUpdated(ResourceResponse resource) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESOURCE_EXCHANGE,
                RabbitMQConfig.RESOURCE_UPDATED_ROUTING_KEY,
                resource
            );
            logger.info("Published resource.updated event for resource: {}", resource.getId());
        } catch (Exception e) {
            logger.error("Failed to publish resource.updated event: {}", e.getMessage());
        }
    }
    
    /**
     * Publish resource deleted event
     */
    public void publishResourceDeleted(Long resourceId) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESOURCE_EXCHANGE,
                RabbitMQConfig.RESOURCE_DELETED_ROUTING_KEY,
                resourceId
            );
            logger.info("Published resource.deleted event for resource: {}", resourceId);
        } catch (Exception e) {
            logger.error("Failed to publish resource.deleted event: {}", e.getMessage());
        }
    }
}




