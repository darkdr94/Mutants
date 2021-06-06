package co.com.xmen.mutant.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.com.xmen.mutant.models.DnaInput;

/**
 * Clase que expone la operación que permite validar el parámetro de entrada del
 * servicio HTTP POST "mutant".
 * 
 * @author Daniel Ruiz
 *
 */
@Component
public class ValidateInput {

	/**
	 * Número de letras iguales seguidas que deben hallarse en la secuencia para
	 * considerar que existe una coincidencia
	 */
	@Value("${app.sequence.length}")
	private int sequenceSize;

	/**
	 * Método que valida que el parametro de entrada corresponda a una matriz de
	 * tamaño NxN. Debe cumplir con lo siguiente:
	 * <li>El array debe contener mínimo N elementos.
	 * <li>Cada elemento debe tener una longitud igual a la cantidad de elementos en
	 * el array.
	 * <li>Los elementos solo pueden estar formados por las letras "A", "T", "G" o
	 * "C".
	 * 
	 * @param dnaInput Objeto que contiene el array de Strings de las secuencias de
	 *                 ADN.
	 * @return
	 *         <li>true: si el parámetro es válido.
	 *         <li>false: si el parámetro es inválido.
	 */
	public boolean validInputDna(DnaInput dnaInput) {
		String[] dna = dnaInput.getDna();
		int dnaArraySize = dna.length;
		boolean response = true;
		if (dnaArraySize < sequenceSize) {
			return false;
		}
		for (String sequence : dna) {
			if (sequence.length() != dnaArraySize || !sequence.matches(Constants.REGEX)) {
				response = false;
				break;
			}
		}
		return response;
	}
}
