package co.com.xmen.mutant.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import co.com.xmen.mutant.models.DnaInput;
import co.com.xmen.mutant.models.DnaResponse;
import co.com.xmen.mutant.service.impl.MutantServiceImpl;
import co.com.xmen.mutant.utils.exceptions.ApiInternalServerException;
import co.com.xmen.mutant.utils.exceptions.ApiRequestException;

@SpringBootTest
@RunWith(SpringRunner.class) 
@TestPropertySource(locations = "classpath:application-dev.properties")
class MutantControllerTests {

	@Autowired
	private MutantsController mutantsController;

	@MockBean
	private MutantServiceImpl mutantServiceMock;

	private DnaInput dnaInput;
	private BindingResult bindingResult = mock(BindingResult.class);

	@Test
	void isMutantTrueTest() throws Exception {
		String[] dna = { "AAAA", "AAAA", "AAAA", "AAAA" };
		dnaInput = new DnaInput(dna);
		when(mutantServiceMock.analyzeDNA(dna)).thenReturn(new ResponseEntity<>(new DnaResponse(true), HttpStatus.OK));
		
		ResponseEntity<Object> response = mutantsController.analyzeDNA(dnaInput, bindingResult);
		HttpStatus statusResponse = response.getStatusCode();
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, statusResponse);
	}

	@Test
	void isMutantFalseTest() throws Exception {
		String[] dna = { "ACGG", "CGAT", "TTGC", "AGTA" };
		dnaInput = new DnaInput(dna);
		when(mutantServiceMock.analyzeDNA(dna))
				.thenReturn(new ResponseEntity<>(new DnaResponse(false), HttpStatus.BAD_REQUEST));
		
		ResponseEntity<Object> response = mutantsController.analyzeDNA(dnaInput, bindingResult);
		HttpStatus statusResponse = response.getStatusCode();
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, statusResponse);
	}

	@Test
	void incompleteInputSizeTest() {
		String[] dna = { "ACTG", "TGCA", "TGAA" };
		dnaInput = new DnaInput(dna);
		Exception exception = assertThrows(ApiRequestException.class, () -> {
			mutantsController.analyzeDNA(dnaInput, bindingResult);
		});

		String expectedMessage = "Error en la estructura del mensaje de entrada";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void incompleteInputLengthTest() {
		String[] dna = { "ACT", "TGCA", "TGAA", "AGCT" };
		dnaInput = new DnaInput(dna);

		Exception exception = assertThrows(ApiRequestException.class, () -> {
			mutantsController.analyzeDNA(dnaInput, bindingResult);
		});

		String expectedMessage = "Error en la estructura del mensaje de entrada";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void invalidRegexInputTest() {
		String[] dna = { "aCTG", "TGCA", "TGAA", "AGCT" };
		dnaInput = new DnaInput(dna);
		Exception exception = assertThrows(ApiRequestException.class, () -> {
			mutantsController.analyzeDNA(dnaInput, bindingResult);
		});

		String expectedMessage = "Error en la estructura del mensaje de entrada";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void bindingResultErrorTest() {
		String[] dna = { };
		dnaInput = new DnaInput(dna);
		when(bindingResult.hasErrors()).thenReturn(true);
		Exception exception = assertThrows(ApiRequestException.class, () -> {
			mutantsController.analyzeDNA(dnaInput, bindingResult);
		});

		String expectedMessage = "Error en la estructura del mensaje de entrada";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	
	@Test
	void exceptionTest() throws Exception {
		String[] dna = { "AAAA", "AAAA", "AAAA", "AAAA" };
		dnaInput = new DnaInput(dna);
		when(mutantServiceMock.analyzeDNA(dna)).thenThrow(ApiInternalServerException.class);		
		Exception exception = assertThrows(ApiInternalServerException.class, () -> {
			 mutantsController.analyzeDNA(dnaInput, bindingResult);
		});

		String expectedMessage = "Error en el ambiente";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));		
	}

}
