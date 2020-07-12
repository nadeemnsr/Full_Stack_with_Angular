package com.xebia.article.exception;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.xebia.article.model.ErrorMessage;

@ControllerAdvice
public class AppExcepionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { NullPointerException.class })
	public ResponseEntity<Object> handleSpecificException(Exception ex, WebRequest request) {
		String errorMessage = ex.getLocalizedMessage();
		if (errorMessage == null)
			errorMessage = ex.toString();
		ErrorMessage message = new ErrorMessage(new Date(), errorMessage);
		return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, ArticleNotFoundException.class })
	public ResponseEntity<Object> handleCityNotFoundException(ArticleNotFoundException ex, WebRequest request) {
		String errorMessage = ex.getLocalizedMessage();
		if (errorMessage == null)
			errorMessage = ex.toString();
		ErrorMessage message = new ErrorMessage(new Date(), "Id :"+errorMessage+" not Found");

		return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<Object> handleNodataFoundException(NoDataFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "No Data found");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

}
