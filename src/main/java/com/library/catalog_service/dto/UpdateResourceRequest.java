package com.library.catalog_service.dto;

import com.library.catalog_service.entity.ResourceStatus;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * DTO for updating an existing resource
 */
public class UpdateResourceRequest {
    
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;
    
    private Integer capacity;
    
    private Integer floor;
    
    private Float locationX;
    
    private Float locationY;
    
    private List<String> amenities;
    
    private ResourceStatus status;
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
}




