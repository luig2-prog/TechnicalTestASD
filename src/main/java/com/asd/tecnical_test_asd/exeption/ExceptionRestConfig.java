package com.asd.tecnical_test_asd.exeption;

import com.asd.tecnical_test_asd.dto.RestResponse;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.naming.NamingException;

@RestControllerAdvice
public class ExceptionRestConfig {

	private static Logger log = LoggerFactory.getLogger(ExceptionRestConfig.class);
	private RestResponse restResponse;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> globalException(Exception exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
        restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno", webRequest.getDescription(false));
        return ResponseEntity.ok(restResponse);
    }

	@ExceptionHandler(CustomException.class)
    public ResponseEntity<RestResponse> customException(CustomException exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getCause().getClass().toString());
        RestResponse restResponse = new RestResponse(exception.getStatus().value(), exception.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.ok(restResponse);
    }

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<RestResponse> exepcionAccessDeniedException(AccessDeniedException e){
		log.error(e.getMessage());
		restResponse = new RestResponse(HttpStatus.OK.value(), MessagesHandler.TOKEN, null);
		log.warn(MessagesHandler.RECORDS_LISTED);
		return ResponseEntity.status(HttpStatus.OK).body(restResponse);
	}

	@ExceptionHandler(NamingException.class)
	public ResponseEntity<RestResponse> exceptionNaming(NamingException exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
		restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno", webRequest.getDescription(false));
		return ResponseEntity.status(HttpStatus.OK).body(restResponse);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<RestResponse> exceptionSQL(DataIntegrityViolationException exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
		restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se pudo almacenar el registro", webRequest.getDescription(false));
		return ResponseEntity.status(HttpStatus.OK).body(restResponse);
	}




}
