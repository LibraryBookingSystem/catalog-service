package com.library.catalog_service.service;

import com.library.catalog_service.config.RabbitMQConfig;
import com.library.catalog_service.dto.BookingEventDTO;
import com.library.catalog_service.entity.ResourceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Listener for booking events from RabbitMQ.
 * Updates resource availability when bookings are created or canceled.
 */
@Service
public class BookingEventListener {

    private static final Logger logger = LoggerFactory.getLogger(BookingEventListener.class);

    private final ResourceService resourceService;

    public BookingEventListener(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * Handle booking created event.
     * When a booking is created, mark the resource as UNAVAILABLE.
     */
    @RabbitListener(queues = RabbitMQConfig.CATALOG_BOOKING_CREATED_QUEUE)
    public void handleBookingCreated(BookingEventDTO booking) {
        try {
            logger.info("Received booking.created event: {}", booking);
            if (booking.getResourceId() != null) {
                resourceService.updateResourceStatus(booking.getResourceId(), ResourceStatus.UNAVAILABLE);
                logger.info("Updated resource {} status to UNAVAILABLE", booking.getResourceId());
            }
        } catch (Exception e) {
            logger.error("Error processing booking.created event: {}", e.getMessage(), e);
        }
    }

    /**
     * Handle booking canceled event.
     * When a booking is canceled, mark the resource as AVAILABLE.
     * Note: In a production system, you'd check if there are other active bookings
     * before marking as available.
     */
    @RabbitListener(queues = RabbitMQConfig.CATALOG_BOOKING_CANCELED_QUEUE)
    public void handleBookingCanceled(BookingEventDTO booking) {
        try {
            logger.info("Received booking.canceled event: {}", booking);
            if (booking.getResourceId() != null) {
                // In production, check if there are other active bookings for this resource
                // For now, we mark it as available
                resourceService.updateResourceStatus(booking.getResourceId(), ResourceStatus.AVAILABLE);
                logger.info("Updated resource {} status to AVAILABLE", booking.getResourceId());
            }
        } catch (Exception e) {
            logger.error("Error processing booking.canceled event: {}", e.getMessage(), e);
        }
    }
}
