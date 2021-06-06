package co.com.xmen.mutant.utils.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Clase que contiene la estructura a retornar cuando ocurre un error dentro de
 * la aplicación.
 * 
 * @author Daniel Ruiz
 *
 */
public class ApiResponse {

	/**
	 * Mensaje de error.
	 */
	private String message;

	/**
	 * Código de respuesta HTTP.
	 */
	private HttpStatus httpStatus;

	/**
	 * @param message    Mensaje de error.
	 * @param httpStatus Código de respuesta HTTP.
	 */
	public ApiResponse(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	/**
	 * @return Mensaje de error.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return Código de respuesta HTTP.
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
