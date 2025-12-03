package com.library.catalog_service.dto;

import com.library.catalog_service.entity.ResourceType;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * DTO for creating a new resource
 */
public class CreateResourceRequest {
    
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;
    
    @NotNull(message = "Type is required")
    private ResourceType type;
    
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
    
    @NotNull(message = "Floor is required")
    @Min(value = 0, message = "Floor must be non-negative")
    private Integer floor;
    
    private Float locationX;
    
    private Float locationY;
    
    private List<String> amenities;
    
    // Getters and Setters
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
}




