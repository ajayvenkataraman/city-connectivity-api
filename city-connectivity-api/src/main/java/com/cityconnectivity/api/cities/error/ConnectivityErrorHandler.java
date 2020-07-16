package com.cityconnectivity.api.cities.error;

import java.io.IOException;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

@ControllerAdvice
public class ConnectivityErrorHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public void handleConstraintViolationException(ConstraintViolationException exception, ServletWebRequest request)
			throws IOException {

		request.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}
}
