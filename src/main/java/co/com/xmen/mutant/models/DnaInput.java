package co.com.xmen.mutant.models;

import java.util.Arrays;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Clase que contiene los atributos y operaciones del parámetro de entrada del
 * servicio HTTP POST "mutant"
 * 
 * @author Daniel Ruiz
 *
 */
public class DnaInput {

	/**
	 * Array de Strings de la secuencia de ADN a analizar
	 */
	@Valid
	@NotNull
	String[] dna;

	/**
	 * Crea una instancia de la clase DnaInput
	 */
	public DnaInput() {
		super();
	}

	/**
	 * Crea una instancia de la clase DnaInput a partir de los parámetros recibidos
	 * 
	 * @param dna Array de Strings de la secuencia de ADN a analizar
	 */
	public DnaInput(String[] dna) {
		super();
		this.dna = dna;
	}

	/**
	 * @return Array de Strings de la secuencia de ADN a analizar
	 */
	public String[] getDna() {
		return dna;
	}

	/**
	 * @param dna Array de Strings de la secuencia de ADN a analizar
	 */
	public void setDna(String[] dna) {
		this.dna = dna;
	}

	@Override
	public String toString() {
		return "dnaInput [dna=" + Arrays.toString(dna) + "]";
	}

}
