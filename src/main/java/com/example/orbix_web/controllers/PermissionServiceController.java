/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Permission;
import com.example.orbix_web.repositories.PermissionRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class PermissionServiceController {
	@Autowired
    PermissionRepository permissionRepository;
    
    // Get All Permissions
    @GetMapping("/permissions")
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // Create a new Permission
    @PostMapping(value="/permissions")
    @ResponseBody
    public Permission createPermission(@Valid @RequestBody Permission permission) {
        return permissionRepository.save(permission);
    }

    // Get a Single Permission
    @GetMapping("/permissions/{id}")
    public Permission getPermissionById(@PathVariable(value = "id") Long permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", permissionId));
    }

    // Update a Permission
    @PutMapping("/permissions/{id}")
    public Permission updateNote(@PathVariable(value = "id") Long permissionId,
                                            @Valid @RequestBody Permission permissionDetails) {

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", permissionId));

        

        Permission updatedPermission = permissionRepository.save(permission);
        return updatedPermission;
    }

    // Delete a Permission
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable(value = "id") Long permissionId) {
    	Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", permissionId));

    	permissionRepository.delete(permission);

        return ResponseEntity.ok().build();
    }
}
