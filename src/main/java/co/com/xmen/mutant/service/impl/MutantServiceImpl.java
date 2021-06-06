package co.com.xmen.mutant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import co.com.xmen.mutant.models.DnaResponse;
import co.com.xmen.mutant.models.StatsResponse;
import co.com.xmen.mutant.service.DataBaseService;
import co.com.xmen.mutant.service.MutantService;
import co.com.xmen.mutant.utils.Constants;
import co.com.xmen.mutant.utils.SearchSequences;

/**
 * Clase que implementa los métodos que permiten realizar el análisis de
 * secuencias de ADN y obtener las estadísticas de verificaciones de ADN,
 * definidos en la interfaz {@link MutantService}
 * 
 * @author Daniel Ruiz
 *
 */
@Service
public class MutantServiceImpl implements MutantService {

	/**
	 * Número de coincidencias de secuencias que deben hallarse en total para
	 * determinar que el ADN es de un mutante.
	 */
	@Value("${app.min.coincidence}")
	private int minCoincidence;

	/**
	 * Número de letras iguales seguidas que deben hallarse en la secuencia para
	 * considerar que existe una coincidencia.
	 */
	@Value("${app.sequence.length}")
	private int sequenceSize;

	/**
	 * Inyección del servicio {@link DataBaseService}
	 */
	@Autowired
	private DataBaseService firestoreService;

	/**
	 * Inyección del componente {@link SearchSequences}
	 */
	@Autowired
	private SearchSequences searchSequences;

	/**
	 * Método que verifica si ya existe el resultado del análisis de la secuencia de
	 * ADN en la Base de Datos, si existe retorna el resultado del análisis
	 * almacenado, si no existe accede al método que analiza si la secuencia de ADN
	 * corresponde a la de un mutante.
	 * 
	 * @param dna Secuencia de ADN a analizar.
	 * @return ResponseEntity con objeto de tipo {@link DnaResponse} con el
	 *         resultado del análisis de la secuencia de ADN.
	 * @throws Exception
	 */
	@Override
	public ResponseEntity<Object> analyzeDNA(String[] dna) throws Exception {
		boolean isMutant = false;
		DocumentSnapshot dnaDocument = firestoreService.searchDna(dna);
		if (dnaDocument == null) {
			isMutant = isMutant(dna);
			firestoreService.saveResult(dna, isMutant);
		} else {
			isMutant = dnaDocument.getBoolean(Constants.RESULT);
		}
		DnaResponse dnaResponse = new DnaResponse(isMutant);
		HttpStatus response = isMutant ? HttpStatus.OK : HttpStatus.FORBIDDEN;
		return new ResponseEntity<>(dnaResponse, response);
	}

	/**
	 * Método que consulta todos los resultados de los análisis de las secuencias de
	 * ADN realizados previamente y realiza el calculo de las estadísticas.
	 * 
	 * @return Estadísticas de verificaciones de ADN.
	 *         <li>Cantidad de mutantes.
	 *         <li>Cantidad de humanos.
	 *         <li>Ratio entre mutantes/humanos.
	 */
	@Override
	public ResponseEntity<Object> getStats() throws Exception {
		int mutant = 0;
		StatsResponse stats = null;
		List<QueryDocumentSnapshot> dnaList;

		dnaList = firestoreService.getDnaList();
		if (!dnaList.isEmpty()) {
			for (QueryDocumentSnapshot document : dnaList) {
				Boolean result = document.getBoolean(Constants.RESULT);
				if (Boolean.TRUE.equals(result))
					mutant++;
			}
			stats = new StatsResponse(mutant, (dnaList.size() - mutant));
		} else {
			stats = new StatsResponse(0, 0);
		}
		return new ResponseEntity<>(stats, HttpStatus.OK);
	}

	/**
	 * Método que recibe la secuencia de ADN a analizar, construye una matriz de
	 * tamaño [NXN] donde cada letra de la secuencia ocupa una posición, y recorre
	 * cada letra de la matriz en busca de coincidencias de secuencias de letras
	 * iguales seguidas (número determinado por la variable sequenceSize) en
	 * dirección horizontal, vertical y diagonal.
	 * 
	 * Cuando se encuentra el número de coincidencias de secuencias necesarias para
	 * considerar el ADN de mutante (número determinado por la variable
	 * minCoincidence) se detiene la búsqueda y retorna el resultado.
	 * 
	 * @param dnaArray Secuencia de ADN a analizar.
	 * @return
	 *         <li>true: si el ADN corresponde al de mutante.
	 *         <li>false: si el ADN corresponde al de un humano.
	 */
	private boolean isMutant(String[] dnaArray) {
		boolean isMutant = false;
		int totalSequences = 0;
		String[][] dnaMatriz = buildMatriz(dnaArray);
		for (int xPosition = 0; xPosition < dnaMatriz.length && !isMutant; xPosition++) {
			for (int yPosition = 0; yPosition < dnaMatriz[xPosition].length && !isMutant; yPosition++) {
				totalSequences = searchSequences.searchSequence(xPosition, yPosition, dnaMatriz, totalSequences);
				isMutant = (totalSequences >= minCoincidence);
			}
		}
		return isMutant;
	}

	/**
	 * Método que construye una matriz bidimensional de tamaño [NxN] a partir de la
	 * secuencia de ADN, donde cada letra de la secuencia ocupa un elemento en la
	 * matriz.
	 * 
	 * @param arrayDna Secuencias de ADN a analizar.
	 * @return Matriz de tamaño [NxN] en forma de array de arrays.
	 */
	private String[][] buildMatriz(String[] arrayDna) {
		String regex = "";
		int size = arrayDna.length;
		String[][] matriz = new String[size][size];
		for (int i = 0; i < arrayDna.length; i++) {
			matriz[i] = arrayDna[i].split(regex);
		}
		return matriz;
	}

}
