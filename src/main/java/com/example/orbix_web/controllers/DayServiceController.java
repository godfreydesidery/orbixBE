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
import com.example.orbix_web.models.Day;
import com.example.orbix_web.repositories.DayRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class DayServiceController {

    @Autowired
    DayRepository dayRepository;
    
    // Get All Days
    @GetMapping("/days")
    public List<Day> getAllDays() {
        return dayRepository.findAll();
    }

    // Create a new Day
    @PostMapping(value="/days")
    @ResponseBody
    public Day createDay(@Valid @RequestBody Day day) {
        return dayRepository.save(day);
    }

    // Get a Single Day
    @GetMapping("/days/{id}")
    public Day getDayById(@PathVariable(value = "id") Long dayId) {
        return dayRepository.findById(dayId)
                .orElseThrow(() -> new ResourceNotFoundException("Day", "id", dayId));
    }

    // Update a Day
    @PutMapping("/days/{id}")
    public Day updateNote(@PathVariable(value = "id") Long dayId,
                                            @Valid @RequestBody Day dayDetails) {

        Day day = dayRepository.findById(dayId)
                .orElseThrow(() -> new ResourceNotFoundException("Day", "id", dayId));

        

        Day updatedDay = dayRepository.save(day);
        return updatedDay;
    }

    // Delete a Day
    @DeleteMapping("/days/{id}")
    public ResponseEntity<?> deleteDay(@PathVariable(value = "id") Long dayId) {
    	Day day = dayRepository.findById(dayId)
                .orElseThrow(() -> new ResourceNotFoundException("Day", "id", dayId));

    	dayRepository.delete(day);

        return ResponseEntity.ok().build();
    }
}
