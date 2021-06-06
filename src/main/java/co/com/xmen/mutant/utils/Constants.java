package co.com.xmen.mutant.utils;

/**
 * Clase que contiene las constantes utilizadas en la aplicación.
 * 
 * @author Daniel Ruiz
 *
 */
public class Constants {

	/**
	 * Constructor que impide que la clase sea instanciada externamente.
	 */
	private Constants() {
		super();
	}

	/**
	 * Nombre de la colección de la Base de Datos.
	 */
	public static final String COLLECTION = "dna ";

	/**
	 * Nombre del archivo que contiene las claves de autenticación de los servicios
	 * de GCP.
	 */
	public static final String PATH_KEYS = "firestorekeys.json";

	/**
	 * Expresión regular que deben cumplir las secuencias de ADN.
	 */
	public static final String REGEX = "^[ATGC]+$";

	/**
	 * Nombre de la clave que almacena en el documento de la BD el resultado del
	 * análisis de la secuencia de ADN.
	 */
	public static final String RESULT = "result";

	/**
	 * Nombre de la clave que almacena en el documento de la BD la fecha y hora en
	 * la que se realizó el resultado del análisis de la secuencia de ADN.
	 */
	public static final String TIMESTAMP = "timestamp";

}
