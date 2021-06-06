package co.com.xmen.mutant.service;

import java.util.List;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

/**
 * Interface que define los métodos que permiten realizar las operaciones de
 * consulta y almacenamiento en la Base de Datos.
 * 
 * @author Daniel Ruiz
 *
 */
public interface DataBaseService {

	/**
	 * Método que permite la búsqueda del resultado del análisis de la secuencia de
	 * ADN en la Base de Datos.
	 * 
	 * @param dnaArray Secuencia de ADN a buscar en la Base de Datos.
	 * @return Objeto de tipo {@link DocumentSnapshot} con el resultado del análisis
	 *         de la secuencia de ADN.
	 * @throws Exception
	 */
	public DocumentSnapshot searchDna(String[] dnaArray) throws Exception;

	/**
	 * Método que permite almacenar el resultado del análisis de la secuencia del
	 * ADN en la Base de Datos.
	 * 
	 * @param dnaArray Secuencia de ADN a guardar en la Base de Datos.
	 * @param result   Resultado del análisis de la secuencia de ADN.
	 * @throws Exception
	 */
	public void saveResult(String[] dnaArray, boolean result) throws Exception;

	/**
	 * Método que permite consultar todos los resultados de los análisis de las
	 * secuencias de ADN realizados previamente.
	 * 
	 * @return Objeto de tipo {@link QuerySnapshot} con el resultado de los análisis
	 *         de las secuencias de ADN.
	 * @throws Exception
	 */
	public List<QueryDocumentSnapshot> getDnaList() throws Exception;

}
