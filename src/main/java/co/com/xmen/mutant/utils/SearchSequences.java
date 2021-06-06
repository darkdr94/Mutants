package co.com.xmen.mutant.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Clase que expone la operación que permite realizar la búsqueda de secuencias
 * en la matriz de ADN de letras iguales seguidas en una determinada dirección.
 * 
 * @author Daniel Ruiz
 *
 */
@Component
public class SearchSequences {

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
	 * Método que recibe una letra de la matriz de ADN y busca secuencias de letras
	 * iguales seguidas a partir de esta (número determinado por la variable
	 * sequenceSize) en dirección horizontal, vertical, diagonal derecha-abajo o
	 * diagonal izquierda-abajo.
	 * 
	 * A partir de la posición que ocupa la letra en la matriz de ADN se determina
	 * si es posible llevar a cabo la búsqueda de coincidencias de secuencias en
	 * cada una de las cuatro direcciones.
	 * 
	 * Cuando se encuentra el número de coincidencias de secuencias necesarias para
	 * considerar el ADN de mutante (número determinado por la variable
	 * minCoincidence) se detiene la búsqueda y retorna el resultado.
	 * 
	 * @param xPosition posición del elemento en el primer eje de la matriz de ADN.
	 * @param yPosition posición del elemento en el segundo eje de la matriz de ADN.
	 * @param dnaMatriz matriz con las secuencias de ADN.
	 * @param totalSeq  número de coincidencias de secuencias halladas.
	 * @return número de coincidencias de secuencias halladas.
	 */
	public int searchSequence(int xPosition, int yPosition, String[][] dnaMatriz, int totalSeq) {
		int matrizSize = dnaMatriz.length;
		// Horizontal
		if (matrizSize - yPosition >= sequenceSize) {
			totalSeq = searchHorizontal(xPosition, yPosition, dnaMatriz, totalSeq);
		}
		// Vertical
		if ((totalSeq < minCoincidence) && (matrizSize - xPosition >= sequenceSize)) {
			totalSeq = searchVertical(xPosition, yPosition, dnaMatriz, totalSeq);
		}
		// Diagonal derecha
		if ((totalSeq < minCoincidence)
				&& (matrizSize - yPosition >= sequenceSize && matrizSize - xPosition >= sequenceSize)) {
			totalSeq = searchDiagRight(xPosition, yPosition, dnaMatriz, totalSeq);
		}
		// Diagonal izquierda
		if ((totalSeq < minCoincidence) && (matrizSize - xPosition >= sequenceSize && yPosition >= sequenceSize - 1)) {
			totalSeq = searchDiagLeft(xPosition, yPosition, dnaMatriz, totalSeq);
		}
		return totalSeq;
	}

	/**
	 * Método que arma un array de Strings con las letras ubicadas en dirección
	 * horizontal a partir de la letra con coordenadas [xPosition, yPosition] de la
	 * matriz de ADN, y llama al método que verifica si forman una coincidencia.
	 * 
	 * @param xPosition posición del elemento en el primer eje de la matriz de ADN.
	 * @param yPosition posición del elemento en el segundo eje de la matriz de ADN.
	 * @param dnaMatriz matriz con las secuencias de ADN.
	 * @param totalSeq  número de coincidencias de secuencias halladas.
	 * @return número de coincidencias de secuencias halladas.
	 */
	private int searchHorizontal(int xPosition, int yPosition, String[][] dnaMatriz, int totalSeq) {
		List<String> arrayAux = new ArrayList<>();
		for (int k = 0; k < sequenceSize; k++) {
			arrayAux.add(dnaMatriz[xPosition][yPosition + k]);
		}
		String[] sequenceArray = arrayAux.toArray(new String[0]);
		totalSeq += compareSequence(sequenceArray);
		return totalSeq;
	}

	/**
	 * Método que arma un array de Strings con las letras ubicadas en dirección
	 * vertical a partir de la letra con coordenadas [xPosition, yPosition] de la
	 * matriz de ADN, y llama al método que verifica si forman una coincidencia.
	 * 
	 * @param xPosition posición del elemento en el primer eje de la matriz de ADN
	 * @param yPosition posición del elemento en el segundo eje de la matriz de ADN
	 * @param dnaMatriz matriz con las secuencias de ADN
	 * @param totalSeq  número de coincidencias de secuencias halladas
	 * @return número de coincidencias de secuencias halladas
	 */
	private int searchVertical(int xPosition, int yPosition, String[][] dnaMatriz, int totalSeq) {
		List<String> arrayAux = new ArrayList<>();
		for (int k = 0; k < sequenceSize; k++) {
			arrayAux.add(dnaMatriz[xPosition + k][yPosition]);
		}
		String[] sequenceArray = arrayAux.toArray(new String[0]);
		totalSeq += compareSequence(sequenceArray);
		return totalSeq;
	}

	/**
	 * Método que arma un array de Strings con las letras ubicadas en dirección
	 * diagonal derecha a partir de la letra con coordenadas [xPosition, yPosition]
	 * de la matriz de ADN, y llama al método que verifica si forman una
	 * coincidencia.
	 * 
	 * @param xPosition posición del elemento en el primer eje de la matriz de ADN
	 * @param yPosition posición del elemento en el segundo eje de la matriz de ADN
	 * @param dnaMatriz matriz con las secuencias de ADN
	 * @param totalSeq  número de coincidencias de secuencias halladas
	 * @return número de coincidencias de secuencias halladas
	 */
	private int searchDiagRight(int xPosition, int yPosition, String[][] dnaMatriz, int totalSeq) {
		List<String> arrayAux = new ArrayList<>();
		for (int k = 0; k < sequenceSize; k++) {
			arrayAux.add(dnaMatriz[xPosition + k][yPosition + k]);
		}
		String[] sequenceArray = arrayAux.toArray(new String[0]);
		totalSeq += compareSequence(sequenceArray);
		return totalSeq;
	}

	/**
	 * Método que arma un array de Strings con las letras ubicadas en dirección
	 * diagonal izquierda a partir de la letra con coordenadas [xPosition,
	 * yPosition] de la matriz de ADN, y llama al método que verifica si forman una
	 * coincidencia.
	 * 
	 * @param xPosition posición del elemento en el primer eje de la matriz de ADN.
	 * @param yPosition posición del elemento en el segundo eje de la matriz de ADN.
	 * @param dnaMatriz matriz con las secuencias de ADN.
	 * @param totalSeq  número de coincidencias de secuencias halladas.
	 * @return número de coincidencias de secuencias halladas.
	 */
	private int searchDiagLeft(int xPosition, int yPosition, String[][] dnaMatriz, int totalSeq) {
		List<String> arrayAux = new ArrayList<>();
		for (int k = 0; k < sequenceSize; k++) {
			arrayAux.add(dnaMatriz[xPosition + k][yPosition - k]);
		}
		String[] sequenceArray = arrayAux.toArray(new String[0]);
		totalSeq += compareSequence(sequenceArray);
		return totalSeq;
	}

	/**
	 * Método que recibe un array de letras (número determinado por la variable
	 * sequenceSize) y verifica si son iguales entre sí para determinar si forman
	 * una coincidencia.
	 * 
	 * @param dnaToCompare Array de Strings que contiene una letra de ADN en cada
	 *                     posición.
	 * @return
	 *         <li>1: si todas las letras son iguales.
	 *         <li>0: si las letras no son iguales.
	 */
	private int compareSequence(String[] dnaToCompare) {
		boolean equals = true;
		for (int index = 0; index < (dnaToCompare.length - 1) && equals; index++) {
			String actualElement = dnaToCompare[index];
			String nextElement = dnaToCompare[index + 1];
			if (!actualElement.equals(nextElement)) {
				equals = false;
			}
		}
		return equals ? 1 : 0;
	}

}
