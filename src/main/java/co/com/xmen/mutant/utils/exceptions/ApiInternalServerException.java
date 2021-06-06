package co.com.xmen.mutant.utils.exceptions;

/**
 * Clase que es lanzada cuando ocurre un error relacionado en la ejecución de
 * las operaciones de los servicios de la aplicación.
 * 
 * @author Daniel Ruiz
 *
 */
public class ApiInternalServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message Mensaje de error a retornar.
	 */
	public ApiInternalServerException(String message) {
		super(message);
	}

	/**
	 * @param message Mensaje de error a retornar.
	 * @param cause   Causa del error.
	 */
	public ApiInternalServerException(String message, Throwable cause) {
		super(message, cause);
	}

}
