/**
 * 
 */
package com.example.orbix_web.exceptions;

/**
 * @author GODFREY
 *
 */
public class OperationFailedException extends RuntimeException{
	private static final long serialVersionUID = 5L;
	public String message;
	public OperationFailedException(String message){
		this.message = message;
	}
}