/**
 * 
 */
package com.example.orbix_web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.exceptions.MissingInformationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.models.Clas;
import com.example.orbix_web.models.Department;
import com.example.orbix_web.models.SubClass;
import com.example.orbix_web.repositories.ClasRepository;
import com.example.orbix_web.repositories.DepartmentRepository;
import com.example.orbix_web.repositories.SubClassRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubClassServiceController {
	
	@Autowired
    SubClassRepository subClassRepository;
	@Autowired
    ClasRepository clasRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    
    // Get All Classes
    @GetMapping("/sub_classes")
    public List<SubClass> getAllClasses() {
        return subClassRepository.findAll();
    }
	
	
	// Get All Classes
    @RequestMapping(method = RequestMethod.GET, value = "/sub_classes/class_name={class_name}")
    public List<SubClass> getAllClassSubClasses(@PathVariable(value = "class_name") String class_name) {
    	Clas class_ = null;
    	try {
    		class_ = clasRepository
    				.findByClasName(class_name)
    				.get();
    		
    	}catch(Exception e) {
    		throw new NotFoundException("The "+class_name+" class does not exist");
    	}
        return subClassRepository.findByClas(class_);
    }
 // Get All Classes
    @RequestMapping(method = RequestMethod.GET, value = "/sub_classes/{id}")
    public SubClass getAllClassSubClassesbyId(@PathVariable(value = "id") Long id) {
    	
        return subClassRepository.findById(id).get();
    }
 // Create a new Class
    @RequestMapping(method = RequestMethod.POST, value="/sub_classes")
    @ResponseBody
    public SubClass createSubClass(@Valid @RequestBody SubClass subClas) {
    	
    	Department department;
    	System.out.println((subClas.getDepartment()).getDepartmentName().toString());
    	try {
    		String departmentName = subClas.getDepartment().getDepartmentName();
    		department = departmentRepository.findByDepartmentName(departmentName).get();
	    	departmentRepository.save(department);
	    	subClas.setDepartment(department);
    	}catch(Exception e) {
    		throw new MissingInformationException("Department required");
    	}
    	Clas clas;
    	System.out.println(subClas.getClas().getClasName());
    	try {
    		String clasName = subClas.getClas().getClasName();
    		clas = clasRepository.findByClasName(clasName).get();
	    	clasRepository.save(clas);
	    	subClas.setClas(clas);
    	}catch(Exception e) {
    		throw new MissingInformationException("Class required");
    	}
    	
        return subClassRepository.save(subClas);
    }

}
