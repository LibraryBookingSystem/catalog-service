package com.library.catalog_service.service;

import com.library.catalog_service.dto.CreateResourceRequest;
import com.library.catalog_service.dto.ResourceResponse;
import com.library.catalog_service.dto.UpdateResourceRequest;
import com.library.catalog_service.entity.Resource;
import com.library.catalog_service.entity.ResourceStatus;
import com.library.catalog_service.entity.ResourceType;
import com.library.catalog_service.exception.ResourceAlreadyExistsException;
import com.library.catalog_service.exception.ResourceNotFoundException;
import com.library.catalog_service.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for resource operations
 */
@Service
public class ResourceService {
    
    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
    
    private final ResourceRepository resourceRepository;
    private final ResourceEventPublisher eventPublisher;
    
    public ResourceService(ResourceRepository resourceRepository,
                          ResourceEventPublisher eventPublisher) {
        this.resourceRepository = resourceRepository;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Create a new resource
     */
    @Transactional
    public ResourceResponse createResource(CreateResourceRequest request) {
        logger.info("Creating resource: {}", request.getName());
        
        // Check if resource with same name already exists
        if (resourceRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Resource with name already exists: " + request.getName());
        }
        
        // Create new resource
        Resource resource = new Resource();
        resource.setName(request.getName());
        resource.setType(request.getType());
        resource.setCapacity(request.getCapacity());
        resource.setFloor(request.getFloor());
        resource.setLocationX(request.getLocationX());
        resource.setLocationY(request.getLocationY());
        resource.setAmenities(request.getAmenities());
        resource.setStatus(ResourceStatus.AVAILABLE);
        
        // Save to database
        resource = resourceRepository.save(resource);
        logger.info("Resource created successfully: {} (ID: {})", resource.getName(), resource.getId());
        
        ResourceResponse response = ResourceResponse.fromResource(resource);
        
        // Publish event
        eventPublisher.publishResourceCreated(response);
        
        return response;
    }
    
    /**
     * Get resource by ID
     */
    public ResourceResponse getResourceById(Long id) {
        Resource resource = resourceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        return ResourceResponse.fromResource(resource);
    }
    
    /**
     * Get all resources
     */
    public List<ResourceResponse> getAllResources() {
        return resourceRepository.findAll().stream()
            .map(ResourceResponse::fromResource)
            .collect(Collectors.toList());
    }
    
    /**
     * Get resources by type
     */
    public List<ResourceResponse> getResourcesByType(ResourceType type) {
        return resourceRepository.findByType(type).stream()
            .map(ResourceResponse::fromResource)
            .collect(Collectors.toList());
    }
    
    /**
     * Get resources by floor
     */
    public List<ResourceResponse> getResourcesByFloor(Integer floor) {
        return resourceRepository.findByFloor(floor).stream()
            .map(ResourceResponse::fromResource)
            .collect(Collectors.toList());
    }
    
    /**
     * Get resources by status
     */
    public List<ResourceResponse> getResourcesByStatus(ResourceStatus status) {
        return resourceRepository.findByStatus(status).stream()
            .map(ResourceResponse::fromResource)
            .collect(Collectors.toList());
    }
    
    /**
     * Search resources by name
     */
    public List<ResourceResponse> searchResourcesByName(String name) {
        return resourceRepository.findByNameContainingIgnoreCase(name).stream()
            .map(ResourceResponse::fromResource)
            .collect(Collectors.toList());
    }
    
    /**
     * Update resource
     */
    @Transactional
    public ResourceResponse updateResource(Long id, UpdateResourceRequest request) {
        logger.info("Updating resource: {}", id);
        
        Resource resource = resourceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        
        // Update fields if provided
        if (request.getName() != null && !request.getName().isBlank()) {
            // Check if new name conflicts with existing resource
            if (!resource.getName().equals(request.getName()) && 
                resourceRepository.existsByName(request.getName())) {
                throw new ResourceAlreadyExistsException("Resource with name already exists: " + request.getName());
            }
            resource.setName(request.getName());
        }
        
        if (request.getCapacity() != null) {
            resource.setCapacity(request.getCapacity());
        }
        
        if (request.getFloor() != null) {
            resource.setFloor(request.getFloor());
        }
        
        if (request.getLocationX() != null) {
            resource.setLocationX(request.getLocationX());
        }
        
        if (request.getLocationY() != null) {
            resource.setLocationY(request.getLocationY());
        }
        
        if (request.getAmenities() != null) {
            resource.setAmenities(request.getAmenities());
        }
        
        if (request.getStatus() != null) {
            resource.setStatus(request.getStatus());
        }
        
        resource = resourceRepository.save(resource);
        logger.info("Resource updated successfully: {} (ID: {})", resource.getName(), resource.getId());
        
        ResourceResponse response = ResourceResponse.fromResource(resource);
        
        // Publish event
        eventPublisher.publishResourceUpdated(response);
        
        return response;
    }
    
    /**
     * Delete resource
     */
    @Transactional
    public void deleteResource(Long id) {
        logger.info("Deleting resource: {}", id);
        
        Resource resource = resourceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        
        Long resourceId = resource.getId();
        String resourceName = resource.getName();
        
        resourceRepository.delete(resource);
        logger.info("Resource deleted successfully: {} (ID: {})", resourceName, id);
        
        // Publish event
        eventPublisher.publishResourceDeleted(resourceId);
    }
}

