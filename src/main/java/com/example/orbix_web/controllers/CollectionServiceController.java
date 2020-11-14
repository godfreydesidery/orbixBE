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
import com.example.orbix_web.models.Collection;
import com.example.orbix_web.repositories.CollectionRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@RequestMapping(value = "/api")
@Service
public class CollectionServiceController {

    @Autowired
    CollectionRepository collectionRepository;
    
    // Get All Collections
    @GetMapping("/collections")
    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    // Create a new Collection
    @PostMapping(value="/collections")
    @ResponseBody
    public Collection createCollection(@Valid @RequestBody Collection collection) {
        return collectionRepository.save(collection);
    }

    // Get a Single Collection
    @GetMapping("/collections/{id}")
    public Collection getCollectionById(@PathVariable(value = "id") Long collectionId) {
        return collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection", "id", collectionId));
    }

    // Update a Collection
    @PutMapping("/collections/{id}")
    public Collection updateNote(@PathVariable(value = "id") Long collectionId,
                                            @Valid @RequestBody Collection collectionDetails) {

        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection", "id", collectionId));

        

        Collection updatedCollection = collectionRepository.save(collection);
        return updatedCollection;
    }

    // Delete a Collection
    @DeleteMapping("/collections/{id}")
    public ResponseEntity<?> deleteCollection(@PathVariable(value = "id") Long collectionId) {
    	Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection", "id", collectionId));

    	collectionRepository.delete(collection);

        return ResponseEntity.ok().build();
    }
}
