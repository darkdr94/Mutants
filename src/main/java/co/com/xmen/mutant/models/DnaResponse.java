package co.com.xmen.mutant.models;

/**
 * Clase que contiene los atributos y operaciones del objeto de salida del
 * servicio HTTP POST "mutant"
 * 
 * @author Daniel Ruiz
 *
 */
public class DnaResponse {

	/**
	 * Booleano que contiene el resultado del análisis de la secuencia de ADN.
	 */
	private boolean isMutant;

	/**
	 * Crea una instancia de la clase DnaResponse. 
	 */
	public DnaResponse() {
		super();
	}

	/**
	 * Crea una instancia de la clase DnaResponse a partir de los parámetros recibidos.
	 * 
	 * @param isMutant Booleano que contiene el resultado del análisis de la
	 *                 secuencia de ADN.
	 */
	public DnaResponse(boolean isMutant) {
		super();
		this.isMutant = isMutant;
	}

	/**
	 * @return Booleano que contiene el resultado del análisis de la secuencia de
	 *         ADN.
	 */
	public boolean getMutant() {
		return isMutant;
	}

	/**
	 * @param isMutant Booleano que contiene el resultado del análisis de la
	 *                 secuencia de ADN.
	 */
	public void setMutant(boolean isMutant) {
		this.isMutant = isMutant;
	}

	@Override
	public String toString() {
		return "DnaResponse [isMutant=" + isMutant + "]";
	}

}
