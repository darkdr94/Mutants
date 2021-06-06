package co.com.xmen.mutant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import co.com.xmen.mutant.firestore.FirestoreCloud;
import co.com.xmen.mutant.service.DataBaseService;

/**
 * Clase que implementa los métodos que permiten realizar las operaciones de
 * consulta y almacenamiento en la Base de Datos, definidos en la interfaz
 * {@link DataBaseService}
 * 
 * @author Daniel Ruiz
 *
 */
@Service
public class DataBaseServiceImpl implements DataBaseService {

	/**
	 * Instancia de la Base de Datos Firestore.
	 */
	private FirestoreCloud firestore;

	/**
	 * Crea una instancia de la clase DataBaseServiceImpl.
	 */
	public DataBaseServiceImpl() {
		firestore = new FirestoreCloud();
	}

	/**
	 * Crea una instancia de la clase {@link DataBaseServiceImpl} a partir de la
	 * instancia de la Base de Datos.
	 */
	public DataBaseServiceImpl(FirestoreCloud firestore) {
		this.firestore = firestore;
	}

	/**
	 * Método que busca el resultado del análisis de la secuencia de ADN en la Base
	 * de Datos. Utiliza la secuencia de ADN como parámetro de búsqueda.
	 * 
	 * @param dnaArray Secuencia de ADN a buscar en la Base de Datos.
	 * @return Objeto de tipo {@link DocumentSnapshot} con el resultado del análisis
	 *         de la secuencia de ADN. Si no existe retorna un documento vacío.
	 * @throws Exception
	 */
	@Override
	public DocumentSnapshot searchDna(String[] dnaArray) throws Exception {
		return firestore.searchDocument(dnaArray);
	}

	/**
	 * Método que guarda el resultado del análisis de la secuencia del ADN en la
	 * Base de Datos.
	 * 
	 * @param dnaArray Secuencia del ADN a guardar.
	 * @param result   Resultado del análisis de la secuencia del ADN.
	 * @throws Exception
	 */
	@Override
	public void saveResult(String[] dnaArray, boolean result) throws Exception {
		firestore.addDocument(dnaArray, result);
	}

	/**
	 * Método que consulta todos los resultados de los análisis de las secuencias de
	 * ADN que han sido realizados previamente.
	 * 
	 * @return Objeto de tipo {@link QuerySnapshot} con el resultado de los análisis
	 *         de las secuencias de ADN.
	 * @throws Exception
	 */
	@Override
	public List<QueryDocumentSnapshot> getDnaList() throws Exception {
		return firestore.retrieveAllDocuments();
	}

}
