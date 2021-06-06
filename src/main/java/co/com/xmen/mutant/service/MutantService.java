package co.com.xmen.mutant.service;

import org.springframework.http.ResponseEntity;

/**
 * Interface que define los métodos que permiten realizar el análisis de
 * secuencias de ADN y obtener las estadísticas de verificaciones de ADN.
 * 
 * @author Daniel Ruiz
 *
 */
public interface MutantService {

	/**
	 * Método que permite realizar el análisis de la secuencia de ADN-
	 * 
	 * @param dna Secuencia de ADN a analizar.
	 * @return Resultado del análisis de la secuencias de ADN.
	 * @throws Exception
	 */
	public ResponseEntity<Object> analyzeDNA(String[] dna) throws Exception;

	/**
	 * Método que permite obtener las estadísticas de verificaciones de ADN.
	 * 
	 * @return Estadísticas de verificaciones de ADN.
	 * @throws Exception
	 */
	public ResponseEntity<Object> getStats() throws Exception;
}
