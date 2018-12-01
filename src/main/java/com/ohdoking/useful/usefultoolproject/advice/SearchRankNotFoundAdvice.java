package com.ohdoking.useful.usefultoolproject.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ohdoking.useful.usefultoolproject.exception.SearchRankNotFoundException;

@ControllerAdvice
class SearchRankNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(SearchRankNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String searchRankNotFoundHandler(SearchRankNotFoundException ex) {
		return ex.getMessage();
	}
}