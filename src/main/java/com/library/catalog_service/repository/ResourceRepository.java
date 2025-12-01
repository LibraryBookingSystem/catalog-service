package com.library.catalog_service.repository;

import com.library.catalog_service.entity.Resource;
import com.library.catalog_service.entity.ResourceStatus;
import com.library.catalog_service.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Resource entity
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    
    /**
     * Find resources by type
     */
    List<Resource> findByType(ResourceType type);
    
    /**
     * Find resources by floor
     */
    List<Resource> findByFloor(Integer floor);
    
    /**
     * Find resources by status
     */
    List<Resource> findByStatus(ResourceStatus status);
    
    /**
     * Find resources by type and status
     */
    List<Resource> findByTypeAndStatus(ResourceType type, ResourceStatus status);
    
    /**
     * Find resources by floor and status
     */
    List<Resource> findByFloorAndStatus(Integer floor, ResourceStatus status);
    
    /**
     * Find resources by name (case-insensitive search)
     */
    List<Resource> findByNameContainingIgnoreCase(String name);
    
    /**
     * Check if resource exists by name
     */
    boolean existsByName(String name);
}

