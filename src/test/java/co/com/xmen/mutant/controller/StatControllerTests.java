package co.com.xmen.mutant.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import co.com.xmen.mutant.models.StatsResponse;
import co.com.xmen.mutant.service.impl.MutantServiceImpl;
import co.com.xmen.mutant.utils.exceptions.ApiInternalServerException;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
class StatControllerTests {

	@MockBean
	private MutantServiceImpl mutantServiceMock;

	@Autowired
	private MutantsController mutantsController;

	@Test
	void getStatsTest() throws Exception {
		when(mutantServiceMock.getStats()).thenReturn(new ResponseEntity<>(new StatsResponse(0, 0), HttpStatus.OK));

		ResponseEntity<Object> response = mutantsController.getStats();
		HttpStatus statusResponse = response.getStatusCode();
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, statusResponse);
	}

	@Test
	void exceptionTest() throws Exception {
		when(mutantServiceMock.getStats()).thenThrow(ApiInternalServerException.class);
		Exception exception = assertThrows(ApiInternalServerException.class, () -> {
			mutantsController.getStats();
		});

		String expectedMessage = "Error en el ambiente";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void okTest() {
		String response = mutantsController.getHealth();
		assertEquals("health OK", response);
	}

}
