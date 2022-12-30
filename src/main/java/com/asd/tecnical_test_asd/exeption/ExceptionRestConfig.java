package com.asd.tecnical_test_asd.exeption;

import com.asd.tecnical_test_asd.dto.RestResponse;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.jsonwebtoken.JwtException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


/**
 *
 */
@RestControllerAdvice
public class ExceptionRestConfig {

	private static Logger log = LoggerFactory.getLogger(ExceptionRestConfig.class);
	private RestResponse restResponse;

	/**
	 * @description función encargada de capturar las excepciones globales del backend
	 * @author Luis Hernandez
	 * @date(26/12/2022)
	 * @param exception
	 * @param webRequest
	 * @return ResponseEntity
	 * @see RestResponse
	 * @see Exception
	 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> globalException(Exception exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
        restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error general", webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restResponse);
    }

	/**
	 * @description función encargada de capturar las excepciones personalizadas del backend
	 * @author Luis Hernandez
	 * @date(26/12/2022)
	 * @param exception
	 * @param webRequest
	 * @return ResponseEntity
	 * @see RestResponse
	 * @see CustomException
	 */
	@ExceptionHandler(CustomException.class)
    public ResponseEntity<RestResponse> customException(CustomException exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getCause().getClass().toString());
        RestResponse restResponse = new RestResponse(exception.getStatus().value(), exception.getMessage(), webRequest.getDescription(false));
        return ResponseEntity.status(exception.getStatus()).body(restResponse);
    }

	/**
	 * @description función encargada de capturar las excepciones de acceso denegados del backend
	 * @author Luis Hernandez
	 * @date(26/12/2022)
	 * @param e
	 * @return ResponseEntity
	 * @see RestResponse
	 * @see AccessDeniedException
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<RestResponse> exepcionAccessDeniedException(AccessDeniedException e){
		log.error(e.getMessage());
		restResponse = new RestResponse(HttpStatus.OK.value(), MessagesHandler.TOKEN, null);
		log.warn(MessagesHandler.RECORDS_LISTED);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(restResponse);
	}

//	@ExceptionHandler(NamingException.class)
//	public ResponseEntity<RestResponse> exceptionNaming(NamingException exception, WebRequest webRequest){
//		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
//		restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno", webRequest.getDescription(false));
//		return ResponseEntity.status(HttpStatus.OK).body(restResponse);
//	}

	/**
	 * @description función encargada de capturar las excepciones de SQL del backend
	 * @author Luis Hernandez
	 * @date(26/12/2022)
	 * @param exception
	 * @param webRequest
	 * @return ResponseEntity
	 * @see RestResponse
	 * @see DataIntegrityViolationException
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<RestResponse> exceptionSQL(DataIntegrityViolationException exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
		restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se pudo almacenar el registro en base de datos", webRequest.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
	}

	/**
	 * @description función encargada de capturar las excepciones de SQL del backend
	 * @author Luis Hernandez
	 * @date(26/12/2022)
	 * @param exception
	 * @param webRequest
	 * @return ResponseEntity
	 * @see RestResponse
	 * @see
	 */
//	@ExceptionHandler(PageNotFound.class)
//	public ResponseEntity<RestResponse> exceptionSQL(DataIntegrityViolationException exception, WebRequest webRequest){
//		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
//		restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se pudo almacenar el registro en base de datos", webRequest.getDescription(false));
//		return ResponseEntity.status(HttpStatus.OK).body(restResponse);
//	}
//
 	@ExceptionHandler(JwtException.class)
	public ResponseEntity<RestResponse> exceptionJWT(DataIntegrityViolationException exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
		restResponse = new RestResponse(HttpStatus.FORBIDDEN.value(), "Forbidden token invalido", webRequest.getDescription(false));
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(restResponse);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<RestResponse> exceptionBadCredential(BadCredentialsException exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
		restResponse = new RestResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage(), webRequest.getDescription(false));
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(restResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<RestResponse> exceptionBadRequest(HttpMessageNotReadableException exception, WebRequest webRequest){
		log.error("Error: {} - Class: {}", exception.getMessage(), exception.getClass().toString());
		restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), "Debe ingresar los datos de manera correcta", webRequest.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
	}


}
