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
import com.example.orbix_web.models.Barcode;
import com.example.orbix_web.repositories.BarcodeRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class BarcodeServiceController {
	@Autowired
    BarcodeRepository barcodeRepository;
    
    // Get All Barcodes
    @GetMapping("/barcodes")
    public List<Barcode> getAllBarcodes() {
        return barcodeRepository.findAll();
    }

    // Create a new Barcode
    @PostMapping(value="/barcodes")
    @ResponseBody
    public Barcode createBarcode(@Valid @RequestBody Barcode barcode) {
        return barcodeRepository.save(barcode);
    }

    // Get a Single Barcode
    @GetMapping("/barcodes/{id}")
    public Barcode getBarcodeById(@PathVariable(value = "id") Long barcodeId) {
        return barcodeRepository.findById(barcodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Barcode", "id", barcodeId));
    }

    // Update a Barcode
    @PutMapping("/barcodes/{id}")
    public Barcode updateNote(@PathVariable(value = "id") Long barcodeId,
                                            @Valid @RequestBody Barcode barcodeDetails) {

        Barcode barcode = barcodeRepository.findById(barcodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Barcode", "id", barcodeId));

        

        Barcode updatedBarcode = barcodeRepository.save(barcode);
        return updatedBarcode;
    }

    // Delete a Barcode
    @DeleteMapping("/barcodes/{id}")
    public ResponseEntity<?> deleteBarcode(@PathVariable(value = "id") Long barcodeId) {
    	Barcode barcode = barcodeRepository.findById(barcodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Barcode", "id", barcodeId));

    	barcodeRepository.delete(barcode);

        return ResponseEntity.ok().build();
    }

}
