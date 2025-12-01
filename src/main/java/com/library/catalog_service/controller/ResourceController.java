package com.library.catalog_service.controller;

import com.library.catalog_service.dto.CreateResourceRequest;
import com.library.catalog_service.dto.ResourceResponse;
import com.library.catalog_service.dto.UpdateResourceRequest;
import com.library.catalog_service.entity.ResourceStatus;
import com.library.catalog_service.entity.ResourceType;
import com.library.catalog_service.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for resource management endpoints
 */
@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    
    private final ResourceService resourceService;
    
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    /**
     * Create a new resource
     * POST /api/resources
     */
    @PostMapping
    public ResponseEntity<ResourceResponse> createResource(@Valid @RequestBody CreateResourceRequest request) {
        ResourceResponse response = resourceService.createResource(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Get resource by ID
     * GET /api/resources/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse> getResourceById(@PathVariable Long id) {
        ResourceResponse response = resourceService.getResourceById(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get all resources
     * GET /api/resources
     */
    @GetMapping
    public ResponseEntity<List<ResourceResponse>> getAllResources(
            @RequestParam(required = false) ResourceType type,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) ResourceStatus status,
            @RequestParam(required = false) String search) {
        
        List<ResourceResponse> resources;
        
        if (search != null && !search.isBlank()) {
            resources = resourceService.searchResourcesByName(search);
        } else if (type != null && status != null) {
            resources = resourceService.getResourcesByType(type).stream()
                .filter(r -> r.getStatus() == status)
                .toList();
        } else if (type != null) {
            resources = resourceService.getResourcesByType(type);
        } else if (floor != null && status != null) {
            resources = resourceService.getResourcesByFloor(floor).stream()
                .filter(r -> r.getStatus() == status)
                .toList();
        } else if (floor != null) {
            resources = resourceService.getResourcesByFloor(floor);
        } else if (status != null) {
            resources = resourceService.getResourcesByStatus(status);
        } else {
            resources = resourceService.getAllResources();
        }
        
        return ResponseEntity.ok(resources);
    }
    
    /**
     * Update resource
     * PUT /api/resources/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponse> updateResource(
            @PathVariable Long id,
            @Valid @RequestBody UpdateResourceRequest request) {
        ResourceResponse response = resourceService.updateResource(id, request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Delete resource
     * DELETE /api/resources/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Health check endpoint
     * GET /api/resources/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Catalog Service is running!");
    }
}

