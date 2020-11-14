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
import com.example.orbix_web.models.SaleDetail;
import com.example.orbix_web.repositories.SaleDetailRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class SaleDetailServiceController {

    @Autowired
    SaleDetailRepository saleDetailRepository;
    
    // Get All SaleDetails
    @GetMapping("/saleDetails")
    public List<SaleDetail> getAllSaleDetails() {
        return saleDetailRepository.findAll();
    }

    // Create a new SaleDetail
    @PostMapping(value="/saleDetails")
    @ResponseBody
    public SaleDetail createSaleDetail(@Valid @RequestBody SaleDetail saleDetail) {
        return saleDetailRepository.save(saleDetail);
    }

    // Get a Single SaleDetail
    @GetMapping("/saleDetails/{id}")
    public SaleDetail getSaleDetailById(@PathVariable(value = "id") Long saleDetailId) {
        return saleDetailRepository.findById(saleDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("SaleDetail", "id", saleDetailId));
    }

    // Update a SaleDetail
    @PutMapping("/saleDetails/{id}")
    public SaleDetail updateNote(@PathVariable(value = "id") Long saleDetailId,
                                            @Valid @RequestBody SaleDetail saleDetailDetails) {

        SaleDetail saleDetail = saleDetailRepository.findById(saleDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("SaleDetail", "id", saleDetailId));

        

        SaleDetail updatedSaleDetail = saleDetailRepository.save(saleDetail);
        return updatedSaleDetail;
    }

    // Delete a SaleDetail
    @DeleteMapping("/saleDetails/{id}")
    public ResponseEntity<?> deleteSaleDetail(@PathVariable(value = "id") Long saleDetailId) {
    	SaleDetail saleDetail = saleDetailRepository.findById(saleDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("SaleDetail", "id", saleDetailId));

    	saleDetailRepository.delete(saleDetail);

        return ResponseEntity.ok().build();
    }
}
