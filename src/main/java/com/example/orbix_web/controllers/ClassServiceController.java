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
import com.example.orbix_web.models.Class;
import com.example.orbix_web.repositories.ClassRepository;
import com.example.orbix_web.repositories.DepartmentRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClassServiceController {

    @Autowired
    ClassRepository classRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    
    // Get All Classes
    @GetMapping("/classes")
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }
    
    
    
    /**
     * 
     * @return array of classes' names
     */
    @GetMapping(value="/classes/class_names")
    public Iterable<Class> getAllClassByNames() {
        return classRepository.getClassNames();
    }
    
    
    
    // Get a Single Class by class name
    @GetMapping("/classes/class_name={class_name}")
    public Class getClassByClassName(@PathVariable(value = "class_name") String className) {
    	
        return classRepository.findByClassName(className)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "class_name", className));
    }
    // Get a Single Class by class code
    @GetMapping("/classes/class_code={class_code}")
    public Class getClassByClassCode(@PathVariable(value = "class_code") String classCode) {
        return classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "class_code", classCode));
    }

    // Create a new Class
    @PostMapping(value="/classes")
    @ResponseBody
    public Class createClass(@Valid @RequestBody Class _class) {
    	
    	Department department;
    	try {
    		String departmentName = (_class.getDepartment()).getDepartmentName();
    		department = departmentRepository.findByDepartmentName(departmentName).get();
    		department.setDepartmentName(departmentName);
	    	departmentRepository.save(department);
	    	_class.setDepartment(department);
    	}catch(Exception e) {
    		_class.setDepartment(null);
    	}
    	
        return classRepository.save(_class);
    }

    // Get a Single Class
    @GetMapping("/classes/{id}")
    public Class getClassById(@PathVariable(value = "id") Long classId) {
        return classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", classId));
    }

    // Update a Class
    @PutMapping("/classes/{id}")
    public Class updateClass(@PathVariable(value = "id") Long classId,
                                            @Valid @RequestBody Class classDetails) {

        Class _class = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", classId));

        _class = classDetails;
        
    	Department department;
    	try {
    		String departmentName = (_class.getDepartment()).getDepartmentName();
    		department = departmentRepository.findByDepartmentName(departmentName).get();
    		department.setDepartmentName(departmentName);
	    	departmentRepository.save(department);
	    	_class.setDepartment(department);
    	}catch(Exception e) {
    		_class.setDepartment(null);
    	}

        Class updatedClass = classRepository.save(_class);
        return updatedClass;
    }

    // Delete a Department
    @DeleteMapping("/classes/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable(value = "id") Long classId) {
    	Class _class = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", classId));

    	classRepository.delete(_class);

        return ResponseEntity.ok().build();
    }
}
