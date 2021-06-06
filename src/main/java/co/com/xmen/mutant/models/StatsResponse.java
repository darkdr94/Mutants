package co.com.xmen.mutant.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase que contiene los atributos y operaciones de la salida del servicio HTTP
 * GET "stats"
 * 
 * @author Daniel Ruiz
 *
 */
public class StatsResponse {

	/**
	 * Cantidad de secuencias de ADN que corresponden a mutantes.
	 */
	@JsonProperty("count_mutant_dna")
	int countMutantDna;

	/**
	 * Cantidad de secuencias de de ADN que corresponden a humanos.
	 */
	@JsonProperty("count_human_dna")
	int countHumanDna;

	/**
	 * Ratio entre el número de mutantes y de humanos.
	 */
	@JsonProperty("ratio")
	String ratio;

	/**
	 * Crea una instancia de la clase StatsResponse
	 */
	public StatsResponse() {
		super();
	}

	/**
	 * Crea una instancia de la clase StatsResponse a partir de los parámetros
	 * recibidos. El ratio se calcula con la cantidad de mutantes y de humanos. Si
	 * la cantidad de humanos es 0, el ratio entre estas magnitudes es indefinido.
	 * 
	 * @param countMutantDna Cantidad de secuencias de de ADN que corresponden a
	 *                       mutantes.
	 * @param countHumanDna  Cantidad de secuencias de de ADN que corresponden a
	 *                       humanos.
	 */
	public StatsResponse(int countMutantDna, int countHumanDna) {
		super();
		this.countMutantDna = countMutantDna;
		this.countHumanDna = countHumanDna;
		this.ratio = (countHumanDna > 0)
				? Double.toString(Math.floor((countMutantDna / (double) countHumanDna) * 100) / 100)
				: "undefined";
	}

	/**
	 * @return Cantidad de secuencias de de ADN que corresponden a mutantes.
	 */
	public int getCountMutantDna() {
		return countMutantDna;
	}

	/**
	 * @param countMutantDna Cantidad de secuencias de de ADN que corresponden a
	 *                       mutantes.
	 */
	public void setCountMutantDna(int countMutantDna) {
		this.countMutantDna = countMutantDna;
	}

	/**
	 * @return Cantidad de secuencias de de ADN que corresponden a humanos.
	 */
	public int getCountHumanDna() {
		return countHumanDna;
	}

	/**
	 * 
	 * @param countHumanDna Cantidad de secuencias de de ADN que corresponden a
	 *                      humanos.
	 */
	public void setCountHumanDna(int countHumanDna) {
		this.countHumanDna = countHumanDna;
	}

	/**
	 * @return Ratio entre el número de mutantes y de humanos.
	 */
	public String getRatio() {
		return ratio;
	}

	/**
	 * @param Ratio entre el número de mutantes y de humanos.
	 */
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	@Override
	public String toString() {
		return "MutantStat [countMutantDna=" + countMutantDna + ", countHumanDna=" + countHumanDna + ", ratio=" + ratio
				+ "]";
	}

}
