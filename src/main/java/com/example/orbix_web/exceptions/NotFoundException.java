/**
 * 
 */
package com.example.orbix_web.exceptions;

/**
 * @author GODFREY
 *
 */
public class NotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public String message;
	public NotFoundException(String message){
		this.message = message;
	}
}
