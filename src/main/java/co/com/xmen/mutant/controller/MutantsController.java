package co.com.xmen.mutant.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.xmen.mutant.models.DnaInput;
import co.com.xmen.mutant.service.MutantService;
import co.com.xmen.mutant.utils.ValidateInput;
import co.com.xmen.mutant.utils.exceptions.ApiInternalServerException;
import co.com.xmen.mutant.utils.exceptions.ApiRequestException;

/**
 * Clase que expone los servicios HTTP POST "mutant" y GET "stats"
 * 
 * @author Daniel Ruiz
 *
 */
@RestController
@RequestMapping
public class MutantsController {

	/**
	 * Inyección del servicio {@link MutantService}
	 */
	@Autowired
	private MutantService mutantService;

	/**
	 * Inyección del componente {@link ValidateInput}
	 */
	@Autowired
	private ValidateInput validInput;

	/**
	 * Método que expone el servicio HTTP POST "/mutant". Realiza la validación del
	 * parámetro de entrada y verifica si el ADN corresponde a un mutante.
	 * 
	 * @param dna           Objeto de tipo {@link DnaInput} que contiene la
	 *                      secuencia de ADN a analizar.
	 * @param bindingResult Objeto que contiene el resultado de la validación del
	 *                      parámetro de entrada y los errores que pueden haber
	 *                      ocurrido.
	 * @return Resultado del análisis del ADN.
	 *         <li>true: si es ADN de mutante,
	 *         <li>false: si es ADN de humano.
	 */
	@PostMapping("mutant")
	public ResponseEntity<Object> analyzeDNA(@Valid @RequestBody DnaInput dna, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errors = "";
			for (ObjectError error : bindingResult.getAllErrors()) {
				errors = error.getDefaultMessage();
			}
			throw new ApiRequestException("Error en la estructura del mensaje de entrada: " + errors);
		}
		if (!validInput.validInputDna(dna)) {
			throw new ApiRequestException(
					"Error en la estructura del mensaje de entrada: verifique e intente nuevamente");
		}
		try {
			return mutantService.analyzeDNA(dna.getDna());
		} catch (Exception e) {
			throw new ApiInternalServerException("Error en el ambiente:" + e.getMessage());
		}
	}

	/**
	 * Método que expone el servicio HTTP GET "/stats". Accede al método que
	 * construye las estadísticas a partir de los resultados de las secuencias de
	 * ADN que han sido analizadas previamente.
	 * 
	 * @return Estadísticas de verificaciones de ADN.
	 *         <li>Cantidad de mutantes.
	 *         <li>Cantidad de humanos.
	 *         <li>Ratio entre mutantes/humanos.
	 */
	@GetMapping("stats")
	public ResponseEntity<Object> getStats() {
		try {
			return mutantService.getStats();
		} catch (Exception e) {
			throw new ApiInternalServerException("Error en el ambiente:" + e.getMessage());
		}
	}

	/**
	 * Método que expone el servicio HTTP GET "/health". Se utiliza para comprobar
	 * la disponibilidad de la aplicación.
	 * 
	 * @return health OK
	 */
	@GetMapping("health")
	public String getHealth() {
		return "health OK";
	}
}
