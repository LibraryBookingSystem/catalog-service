package com.library.catalog_service.dto;

import com.library.catalog_service.entity.Resource;
import com.library.catalog_service.entity.ResourceStatus;
import com.library.catalog_service.entity.ResourceType;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for resource response
 */
public class ResourceResponse {
    
    private Long id;
    private String name;
    private ResourceType type;
    private Integer capacity;
    private Integer floor;
    private Float locationX;
    private Float locationY;
    private List<String> amenities;
    private ResourceStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public ResourceResponse() {}
    
    public ResourceResponse(Long id, String name, ResourceType type, Integer capacity, 
                           Integer floor, Float locationX, Float locationY, 
                           List<String> amenities, ResourceStatus status,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.floor = floor;
        this.locationX = locationX;
        this.locationY = locationY;
        this.amenities = amenities;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    /**
     * Convert Resource entity to ResourceResponse DTO
     */
    public static ResourceResponse fromResource(Resource resource) {
        return new ResourceResponse(
            resource.getId(),
            resource.getName(),
            resource.getType(),
            resource.getCapacity(),
            resource.getFloor(),
            resource.getLocationX(),
            resource.getLocationY(),
            resource.getAmenities(),
            resource.getStatus(),
            resource.getCreatedAt(),
            resource.getUpdatedAt()
        );
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public ResourceType getType() {
        return type;
    }
    
    public void setType(ResourceType type) {
        this.type = type;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public Integer getFloor() {
        return floor;
    }
    
    public void setFloor(Integer floor) {
        this.floor = floor;
    }
    
    public Float getLocationX() {
        return locationX;
    }
    
    public void setLocationX(Float locationX) {
        this.locationX = locationX;
    }
    
    public Float getLocationY() {
        return locationY;
    }
    
    public void setLocationY(Float locationY) {
        this.locationY = locationY;
    }
    
    public List<String> getAmenities() {
        return amenities;
    }
    
    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }
    
    public ResourceStatus getStatus() {
        return status;
    }
    
    public void setStatus(ResourceStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

