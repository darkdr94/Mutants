package co.com.xmen.mutant.utils.exceptions;

/**
 * Clase que es lanzada cuando ocurre un error relacionado con la validación de
 * los request de los servicios de la aplicación.
 * 
 * @author Daniel Ruiz
 *
 */
public class ApiRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message Detalle del mensaje de error.
	 */
	public ApiRequestException(String message) {
		super(message);
	}

	/**
	 * @param message Detalle del mensaje de error.
	 * @param cause   Causa del error.
	 */
	public ApiRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
