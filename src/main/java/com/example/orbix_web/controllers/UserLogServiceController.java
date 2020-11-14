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
import com.example.orbix_web.models.UserLog;
import com.example.orbix_web.repositories.UserLogRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class UserLogServiceController {

    @Autowired
    UserLogRepository userLogRepository;
    
    // Get All UserLogs
    @GetMapping("/user_logs")
    public List<UserLog> getAllUserLogs() {
        return userLogRepository.findAll();
    }

    // Create a new UserLog
    @PostMapping(value="/user_logs")
    @ResponseBody
    public UserLog createUserLog(@Valid @RequestBody UserLog user_log) {
        return userLogRepository.save(user_log);
    }

    // Get a Single UserLog
    @GetMapping("/user_logs/{id}")
    public UserLog getUserLogById(@PathVariable(value = "id") Long user_logId) {
        return userLogRepository.findById(user_logId)
                .orElseThrow(() -> new ResourceNotFoundException("UserLog", "id", user_logId));
    }

    // Update a UserLog
    @PutMapping("/user_logs/{id}")
    public UserLog updateNote(@PathVariable(value = "id") Long user_logId,
                                            @Valid @RequestBody UserLog user_logDetails) {

        UserLog user_log = userLogRepository.findById(user_logId)
                .orElseThrow(() -> new ResourceNotFoundException("UserLog", "id", user_logId));

        

        UserLog updatedUserLog = userLogRepository.save(user_log);
        return updatedUserLog;
    }

    // Delete a UserLog
    @DeleteMapping("/user_logs/{id}")
    public ResponseEntity<?> deleteUserLog(@PathVariable(value = "id") Long user_logId) {
    	UserLog user_log = userLogRepository.findById(user_logId)
                .orElseThrow(() -> new ResourceNotFoundException("UserLog", "id", user_logId));

    	userLogRepository.delete(user_log);

        return ResponseEntity.ok().build();
    }
}
