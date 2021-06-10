/**
 * 
 */
package com.example.orbix_web.controllers;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import com.example.orbix_web.exceptions.InvalidOperationException;
import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Day;
import com.example.orbix_web.repositories.DayRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    
    // Refresh Day
    @PostMapping(value="/days/refresh")
    @ResponseBody
    public Day refreshDay() {//@Valid)  @RequestBody Day _day) {
    	
    	//Date date = null;
    	Day day = new Day();
    	try {
    		day = dayRepository.findTopByOrderByIdDesc();
    	}catch(NullPointerException e) {
    		day = null;
    	}
    	if(day == null) {
    		day = new Day();
    		LocalDateTime currentTime = LocalDateTime.now();
    		LocalDate currentDate = LocalDate.now();
    		day.setStartedAt(currentTime);
    		day.setStatus("OPENED");
    		day.setSystemDate(currentDate);
    		
    		dayRepository.save(day);
    		day = dayRepository.findTopByOrderByIdDesc();
    	}
    	//date = day.getDate();
        return dayRepository.save(day);
    }
    
    // Get current Day
    @GetMapping(value="/days/get_current")
    @ResponseBody
    public Day getCurrentDay() {
    	
    	Day day = new Day();
    	try {
    		day = dayRepository.findTopByOrderByIdDesc();
    	}catch(NullPointerException e) {
    		day = null;
    	}
    	if(day == null) {
    		day = new Day();
    		LocalDateTime currentTime = LocalDateTime.now();
    		LocalDate currentDate = LocalDate.now();
    		day.setStartedAt(currentTime);
    		day.setStatus("OPENED");
    		day.setSystemDate(currentDate);
    		dayRepository.save(day);
    		day = dayRepository.findTopByOrderByIdDesc();
    	}
        return day;
    }
 // End current Day
    @PostMapping(value="/days/end_current")
    @ResponseBody
    public Day endCurrentDay(@Valid @RequestBody Day _currentDay) {
    	
    	Day day = new Day();
    	LocalDateTime currentTime = LocalDateTime.now();
		LocalDate currentDate = LocalDate.now();
    	try {
    		day = dayRepository.findTopByOrderByIdDesc();
    	}catch(NullPointerException e) {
    		day = null;
    	}
    	if(day == null) {
    		day = new Day();
    		
    		day.setStartedAt(currentTime);
    		day.setStatus("OPENED");
    		day.setSystemDate(currentDate);
    		dayRepository.save(day);
    		day = dayRepository.findTopByOrderByIdDesc();
    	}
    	LocalDate date1 = _currentDay.getSystemDate();
    	LocalDate date2 = day.getSystemDate();
    	
    	System.out.println(date1);
    	System.out.println(date2);
    
    	if(!date1.equals(date2)) {
    		throw new InvalidOperationException("Could not end day");
    	}
    	
    	LocalDate oldSystemDate = day.getSystemDate();
    	LocalDate newSystemDate = oldSystemDate.plusDays(1);
    	LocalDate computerDate;
    	LocalDate serverDate = LocalDate.now();
    	
    	
    	if(newSystemDate.equals(serverDate)) {
    		newSystemDate = newSystemDate;
    	}else if(!newSystemDate.equals(serverDate) && !oldSystemDate.equals(serverDate)) {
    		newSystemDate = serverDate;
    	}else if(!newSystemDate.equals(serverDate) && oldSystemDate.equals(serverDate)) {
    		newSystemDate = serverDate.plusDays(1);
    	}
    	
    	
    	
		day.setStatus("CLOSED");
		day.setClosedAt(currentTime);
		dayRepository.save(day);
		day = dayRepository.findTopByOrderByIdDesc();
		
		
				
		day = new Day();
		day.setStartedAt(currentTime);
		day.setStatus("OPENED");
		day.setSystemDate(newSystemDate);
		dayRepository.save(day);
		day = dayRepository.findTopByOrderByIdDesc();
    	
        return day;
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
    @Transactional
    public LocalDate getDate() {
    	LocalDate date = null;
    	Day day = new Day();
    	LocalDateTime currentTime = LocalDateTime.now();
    	try {
    		day = dayRepository.findTopByOrderByIdDesc();
    	}catch(NullPointerException e) {
    		day = null;
    	}
    	if(day == null) {
    		day = new Day();
    		day.setStartedAt(currentTime);
    		day.setStatus("OPENED");
    		day.setSystemDate(LocalDate.now());
    		dayRepository.save(day);
    		day = dayRepository.findTopByOrderByIdDesc();
    	}
    	date = day.getSystemDate();
    	
    	return date;
    	
    }
}
