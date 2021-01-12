/**
 * 
 */
package com.example.orbix_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.orbix_web.repositories.ClasRepository;
import com.example.orbix_web.repositories.DBUpTableRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NetworkServiceController {
	
	@Autowired
    DBUpTableRepository dBUpTableRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/network_status")
	public int checkStatus() {
		int status = 0;
		try {
			dBUpTableRepository.count();
			status = 1;
		}catch(Exception e) {
			status = 0;
		}
		return status;
	}
}
