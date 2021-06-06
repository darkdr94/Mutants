package co.com.xmen.mutant.firestore;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import co.com.xmen.mutant.utils.Constants;

/**
 * Clase que expone las operaciones de búsqueda y guardado de la Base de Datos
 * Cloud Firestore.
 * 
 * @author Daniel Ruiz
 *
 */
public class FirestoreCloud {

	/**
	 * Instancia que representa la Base de Datos {@link Firestore}
	 */
	private Firestore db;

	/**
	 * Método que inicia la conexión a la Base de Datos Firestore, obtiene las
	 * credenciales de conexión a partir del archivo de claves de autenticación.
	 * 
	 * @throws IOException
	 */
	private void initConection() throws IOException {
		InputStream serviceAccount = getKeysFile();
		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
		Firestore dbFirestore = firestoreOptions.getService();
		this.db = dbFirestore;
	}

	/**
	 * Método que cierra la conexión asociada a la instancia de la Base de Datos y
	 * libera sus recursos.
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		this.db.close();
	}

	/**
	 * Método que busca y retorna un documento en la colección "dna" en la Base de
	 * Datos Firestore. Utiliza la secuencia de ADN como parámetro de búsqueda.
	 * 
	 * @param dna Secuencia de ADN a buscar en la Base de Datos Firestore.
	 * @return Documento con la información el resultado del análisis realizado
	 *         previamente. Si no existe retorna un documento vacío.
	 * @throws Exception
	 */
	public DocumentSnapshot searchDocument(String[] dna) throws Exception {
		initConection();
		String dnaStr = String.join("", dna);
		ApiFuture<DocumentSnapshot> query = db.collection(Constants.COLLECTION).document(dnaStr).get();
		DocumentSnapshot document = query.get();
		close();
		return document.exists() ? document: null;
	}

	/**
	 * Método que busca y retorna todos los documentos en la colección "dna" en la
	 * Base de Datos Firestore.
	 * 
	 * @return Objeto de tipo {@link QuerySnapshot} con los documentos encontrados.
	 * @throws Exception
	 */
	public List<QueryDocumentSnapshot> retrieveAllDocuments() throws Exception {
		initConection();
		ApiFuture<QuerySnapshot> query = db.collection(Constants.COLLECTION).get();
		QuerySnapshot querySnapshot = query.get();
		close();
		return querySnapshot.getDocuments();
	}

	/**
	 * Método que guarda un documento en la colección "dna" en la Base de Datos
	 * Firestore.
	 * 
	 * @param dna      Secuencia de ADN a guardar.
	 * @param isMutant Resultado del análisis de la secuencia de ADN.
	 * @throws Exception
	 */
	public void addDocument(String[] dna, boolean isMutant) throws Exception {
		initConection();
		String dnaStr = String.join("", dna);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		DocumentReference docRef = db.collection(Constants.COLLECTION).document(dnaStr);

		Map<String, Object> data = new HashMap<>();
		data.put(Constants.RESULT, isMutant);
		data.put(Constants.TIMESTAMP, timestamp);
		docRef.set(data);
		close();
	}

	/**
	 * Método que lee y retorna el contenido del archivo de claves de autenticación.
	 * 
	 * @return Contenido del archivo de claves de autenticación.
	 */
	private InputStream getKeysFile() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = classloader.getResourceAsStream(Constants.PATH_KEYS);
		return resourceStream;
	}

}