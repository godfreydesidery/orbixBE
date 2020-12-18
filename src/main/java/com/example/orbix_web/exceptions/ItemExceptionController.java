/**
 * 
 */
package com.example.orbix_web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author GODFREY
 *
 */
@ControllerAdvice
public class ItemExceptionController {
	@ExceptionHandler(value = NotFoundException.class)
   public ResponseEntity<Object> exception(NotFoundException exception) {
      return new ResponseEntity<>("Not found: "+exception.message, HttpStatus.NOT_FOUND);
   }

}
