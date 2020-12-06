/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.Role;
import com.example.orbix_web.repositories.RoleRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleServiceController {

    @Autowired
    RoleRepository roleRepository;
    
    // Get All Roles
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Create a new Role
    @PostMapping(value="/roles")
    @ResponseBody
    public Role createRole(@Valid @RequestBody Role role) {
        return roleRepository.save(role);
    }

    // Get a Single Role
    @GetMapping("/roles/{id}")
    public Role getRoleById(@PathVariable(value = "id") Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
    }
    
    /**
     * 
     * @return array of roles' names
     */
    @GetMapping(value="/roles/role_names")
    public Iterable<Role> getAllRolesByNames() {
    	
        return roleRepository.getRoleName();
    }
    
 // Get a Single Role by name
    @GetMapping("/roles/role_name={role_name}")
    public Role getRoleByRoleName(@PathVariable(value = "role_name") String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "rolename", roleName));
    }

    // Update a Role
    @PutMapping("/roles/{id}")
    public Role updateNote(@PathVariable(value = "id") Long roleId,
                                            @Valid @RequestBody Role roleDetails) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

        role = roleDetails;

        Role updatedRole = roleRepository.save(role);
        return updatedRole;
    }

    // Delete a Role
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") Long roleId) {
    	Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));

    	roleRepository.delete(role);

        return ResponseEntity.ok().build();
    }
}
