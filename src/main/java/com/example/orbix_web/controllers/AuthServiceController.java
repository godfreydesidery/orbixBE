/**
 * 
 */
package com.example.orbix_web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.example.orbix_web.exceptions.MissingInformationException;
import com.example.orbix_web.exceptions.NotFoundException;
import com.example.orbix_web.exceptions.OperationFailedException;
import com.example.orbix_web.models.Grn;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.models.User;
import com.example.orbix_web.repositories.ItemRepository;
import com.example.orbix_web.repositories.UserRepository;

/**
 * @author GODFREY
 *
 */
@RestController
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthServiceController {
	@Autowired
    UserRepository userRepository;
	
	
		@RequestMapping(method = RequestMethod.POST, value = "/auth/login")
	    @ResponseBody
	    @Transactional
	    public _User login(@Valid @RequestBody _User _user) {
			User user = new User();
			String username = _user.getUsername();
			String password = _user.getPassword();
			if(username == null) {
				throw new MissingInformationException("Username required");
			}
			if(password == null) {
				throw new MissingInformationException("Password required");
			}
			if(username.length() == 0) {
				throw new MissingInformationException("Username required");
			}
			if(password.length() == 0) {
				throw new MissingInformationException("Password required");
			}
			user = userRepository.findByUsername(username);
			if(user == null) {
				throw new MissingInformationException("Invalid username and password");
			}
			if(!_user.getPassword().equals(user.getPassword())) { // use hashed password instead, later after implementation
				throw new OperationFailedException("Invalid username and password");
			}
			_user.setAccessToken("123");
			_user.setPassword("");// remove password from user object sent back to client
			return _user;
		}

}
class _User{
	String username;
	String password;
	String accessToken;
	/**
	 * @return the username
	 */
	_User(){
		
	}
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
