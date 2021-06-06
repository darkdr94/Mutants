package co.com.xmen.mutant.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Clase que gestiona los errores de tipo ApiRequestException,
 * ApiInternalServerException y HttpMessageNotReadableException
 * 
 * @author Daniel Ruiz
 *
 */

@ControllerAdvice
public class ApiExceptionHandler {

	/**
	 * @param e Error
	 * @return Mensaje del error y código HTTP relacionado.
	 */
	@ExceptionHandler(value = { ApiRequestException.class })
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
		ApiResponse apiException = new ApiResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param e Error
	 * @return Mensaje del error y código HTTP relacionado.
	 */
	@ExceptionHandler(value = { ApiInternalServerException.class })
	public ResponseEntity<Object> handleApiInternalServerErrorException(ApiInternalServerException e) {
		ApiResponse apiException = new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @param e Error
	 * @return Mensaje del error y código HTTP relacionado.
	 */
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		ApiResponse apiException = new ApiResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

}
