package com.ohdoking.useful.usefultoolproject.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ohdoking.useful.usefultoolproject.exception.SearchRankNotFoundException;

/**
 * 
 * 
 * @author ohdoking
 *
 *
	@ResponseBody signals that this advice is rendered straight into the response body.
	@ExceptionHandler configures the advice to only respond if an EmployeeNotFoundException is thrown.
	@ResponseStatus says to issues an HttpStatus.NOT_FOUND, i.e. an HTTP 404.
	The body of the advice generates the content. In this case, it gives the message of the exception.
 */
@ControllerAdvice
class SearchRankNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(SearchRankNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String searchRankNotFoundHandler(SearchRankNotFoundException ex) {
		return ex.getMessage();
	}
}