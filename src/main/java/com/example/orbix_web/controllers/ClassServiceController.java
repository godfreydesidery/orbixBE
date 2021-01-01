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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.exceptions.ResourceNotFoundException;
import com.example.orbix_web.models.Clas;
import com.example.orbix_web.models.Department;
import com.example.orbix_web.repositories.ClasRepository;
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
    ClasRepository clasRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    
    // Get All Classes
    @GetMapping("/classes")
    public List<Clas> getAllClasses() {
        return clasRepository.findAll();
    }
    // Get All Classes
    @RequestMapping(method = RequestMethod.GET, value = "/classes/department_name={department_name}")
    public List<Clas> getAllDepartmentClasses(@PathVariable(value = "department_name") String department_name) {
    	Department department = null;
    	try {
    		department = departmentRepository
    				.findByDepartmentName(department_name)
    				.get();
    		
    	}catch(Exception e) {
    		throw new NotFoundException("The "+department_name+" department does not exist");
    	}
    	
        return clasRepository.findByDepartment(department);
    }
    
    
    
    /**
     * 
     * @return array of classes' names
     */
    @GetMapping(value="/classes/class_names")
    public Iterable<Clas> getAllClassByNames() {
        return clasRepository.getClasNames();
    }
    
    
    
    // Get a Single Class by class name
    @GetMapping("/classes/class_name={class_name}")
    public Clas getClassByClassName(@PathVariable(value = "class_name") String clasName) {
    	
        return clasRepository.findByClasName(clasName)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "class_name", clasName));
    }
    // Get a Single Class by class code
    @GetMapping("/classes/class_code={class_code}")
    public Clas getClassByClassCode(@PathVariable(value = "class_code") String clasCode) {
        return clasRepository.findByClasCode(clasCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "class_code", clasCode));
    }

    // Create a new Class
    @RequestMapping(method = RequestMethod.POST, value="/classes")
    @ResponseBody
    public Clas createClass(@Valid @RequestBody Clas clas) {
    	
    	Department department;
    	try {
    		String departmentName = (clas.getDepartment()).getDepartmentName();
    		department = departmentRepository.findByDepartmentName(departmentName).get();
    		department.setDepartmentName(departmentName);
	    	departmentRepository.save(department);
	    	clas.setDepartment(department);
    	}catch(Exception e) {
    		clas.setDepartment(null);
    	}
    	
        return clasRepository.save(clas);
    }

    // Get a Single Class
    @GetMapping("/classes/{id}")
    public Clas getClassById(@PathVariable(value = "id") Long clasId) {
        return clasRepository.findById(clasId)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", clasId));
    }

    // Update a Class
    @PutMapping("/classes/{id}")
    public Clas updateClass(@PathVariable(value = "id") Long classId,
                                            @Valid @RequestBody Clas clasDetails) {

        Clas clas = clasRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", classId));

        clas = clasDetails;
        
    	Department department;
    	try {
    		String departmentName = (clas.getDepartment()).getDepartmentName();
    		department = departmentRepository.findByDepartmentName(departmentName).get();
    		department.setDepartmentName(departmentName);
	    	departmentRepository.save(department);
	    	clas.setDepartment(department);
    	}catch(Exception e) {
    		clas.setDepartment(null);
    	}

        Clas updatedClass = clasRepository.save(clas);
        return updatedClass;
    }

    // Delete a Department
    @DeleteMapping("/classes/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable(value = "id") Long clasId) {
    	Clas clas = clasRepository.findById(clasId)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", clasId));

    	clasRepository.delete(clas);

        return ResponseEntity.ok().build();
    }
}
