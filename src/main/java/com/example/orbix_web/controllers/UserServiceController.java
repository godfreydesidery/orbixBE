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
import com.example.orbix_web.models.Role;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.models.User;
import com.example.orbix_web.repositories.RoleRepository;
import com.example.orbix_web.repositories.UserRepository;

@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserServiceController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    
    // Get All Users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Create a new User
    @PostMapping(value="/users")
    @ResponseBody
    public User createUser(@Valid @RequestBody User user) {
    	
    	
    	Role role;
    	try {
    		String roleName = (user.getRole()).getRoleName();
    		role = roleRepository.findByRoleName(roleName).get();
    		role.setRoleName(roleName);
	    	roleRepository.save(role);
	    	user.setRole(role);
    	}catch(Exception e) {
    		user.setRole(null);
    	}
    	
    	
        return userRepository.save(user);
    }

    // Get a Single User
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }
    
 // Get a Single User by pay roll no
    @GetMapping("/users/pay_roll_no={pay_roll_no}")
    public User getUserByPayRollNo(@PathVariable(value = "pay_roll_no") String payRollNo) {
        return userRepository.findByPayRollNo(payRollNo)
                .orElseThrow(() -> new ResourceNotFoundException("User", "pay_roll_no", payRollNo));
    }

    // Update a User
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId,
                                            @Valid @RequestBody User userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user = userDetails;
        
        Role role;
    	try {
    		String roleName = (user.getRole()).getRoleName();
    		role = roleRepository.findByRoleName(roleName).get();
    		role.setRoleName(roleName);
	    	roleRepository.save(role);
	    	user.setRole(role);
    	}catch(Exception e) {
    		user.setRole(null);
    	}
    	

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    // Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
    	User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

    	userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
