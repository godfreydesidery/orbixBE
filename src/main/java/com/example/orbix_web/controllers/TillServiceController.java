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

import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Till;
import com.example.orbix_web.models.User;
import com.example.orbix_web.repositories.TillRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TillServiceController {

    @Autowired
    TillRepository tillRepository;
    
    // Get All Tills
    @GetMapping("/tills")
    public List<Till> getAllTills() {
        return tillRepository.findAll();
    }

    // Create a new Till
    @PostMapping(value="/tills")
    @ResponseBody
    public Till createTill(@Valid @RequestBody Till till) {
        return tillRepository.save(till);
    }
    
 // Change input method
    @PostMapping(value="/tills/multiple_input/till_name= {till_name}")
    @ResponseBody
    public void changeInputmethod(@Valid @RequestBody boolean method, @PathVariable(value = "till_name") String tillName) {
    	Till till = tillRepository.findByTillName(tillName).get();
    	till.setMultipleInput(method);
        tillRepository.save(till);
    }

    // Get a Single Till
    @GetMapping("/tills/{id}")
    public Till getTillById(@PathVariable(value = "id") Long tillId) {
        return tillRepository.findById(tillId)
                .orElseThrow(() -> new ResourceNotFoundException("Till", "id", tillId));
    }

    // Get a Single till by till no
    @GetMapping("/tills/till_no={till_no}")
    public Till getTillByTillNo(@PathVariable(value = "till_no") String tillNo) {
        return tillRepository.findByTillNo(tillNo)
                .orElseThrow(() -> new ResourceNotFoundException("Till", "till_no", tillNo));
    }
 // Get a Single till by till no
    @GetMapping("/tills/till_name={till_name}")
    public Till getTillByTillName(@PathVariable(value = "till_name") String tillName) {
        return tillRepository.findByTillName(tillName)
                .orElseThrow(() -> new NotFoundException("Till not found"));
    }
    
    // Update a Till
    @PutMapping("/tills/{id}")
    public Till updateNote(@PathVariable(value = "id") Long tillId,
                                            @Valid @RequestBody Till tillDetails) {

        Till till = tillRepository.findById(tillId)
                .orElseThrow(() -> new ResourceNotFoundException("Till", "id", tillId));

        till = tillDetails;

        Till updatedTill = tillRepository.save(till);
        return updatedTill;
    }

    // Delete a Till
    @DeleteMapping("/tills/{id}")
    public ResponseEntity<?> deleteTill(@PathVariable(value = "id") Long tillId) {
    	Till till = tillRepository.findById(tillId)
                .orElseThrow(() -> new ResourceNotFoundException("Till", "id", tillId));

    	tillRepository.delete(till);

        return ResponseEntity.ok().build();
    }
}
