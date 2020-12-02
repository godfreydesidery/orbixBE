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
import com.example.orbix_web.models.Department;
import com.example.orbix_web.models.Supplier;
import com.example.orbix_web.repositories.DepartmentRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DepartmentServiceController {

    @Autowired
    DepartmentRepository departmentRepository;
    
    // Get All Departments
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    /**
     * 
     * @return array of departments' names
     */
    @GetMapping(value="/departments/department_names")
    public Iterable<Department> getAllDepartmentByNames() {
        return departmentRepository.getDepartmentNames();
    }
    
    // Get a Single Department by name
    @GetMapping("/departments/department_name={department_name}")
    public Department getDepartmentByDepartmentName(@PathVariable(value = "department_name") String departmentName) {
    	
        return departmentRepository.findByDepartmentName(departmentName)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "department_name", departmentName));
    }
    // Get a Single Department by code
    @GetMapping("/departments/department_code={department_code}")
    public Department getDepartmentByDepartmentCode(@PathVariable(value = "department_code") String departmentCode) {
    	System.out.println("checked");
        return departmentRepository.findByDepartmentCode(departmentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "department_code", departmentCode));
    }

    // Create a new Department
    @PostMapping(value="/departments")
    @ResponseBody
    public Department createDepartment(@Valid @RequestBody Department department) {
        return departmentRepository.save(department);
    }

    // Get a Single Department
    @GetMapping("/departments/{id}")
    public Department getDepartmentById(@PathVariable(value = "id") Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));
    }

    // Update a Department
    @PutMapping("/departments/{id}")
    public Department updateNote(@PathVariable(value = "id") Long departmentId,
                                            @Valid @RequestBody Department departmentDetails) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));

        department = departmentDetails;

        Department updatedDepartment = departmentRepository.save(department);
        return updatedDepartment;
    }

    // Delete a Department
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable(value = "id") Long departmentId) {
    	Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));

    	departmentRepository.delete(department);

        return ResponseEntity.ok().build();
    }
}
