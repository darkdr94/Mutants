package co.com.xmen.mutant.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import co.com.xmen.mutant.service.DataBaseService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
class MutantServiceImplTest {

	@MockBean
	private DataBaseService dbserviceMock;

	@Autowired
	private MutantServiceImpl mutantsService;

	@Test
	void isMutantTrueTest() throws Exception {
		String[] dna = { "ACTG", "AGCA", "AGAA", "AAAA" };		
		when(dbserviceMock.searchDna(dna)).thenReturn(null);
		
		ResponseEntity<Object> responseTrue = mutantsService.analyzeDNA(dna);
		HttpStatus responseHttp = responseTrue.getStatusCode();
		assertNotNull(responseTrue.getBody());
		assertEquals(HttpStatus.OK, responseHttp);
	}

	@Test
	void isMutantFalseTest() throws Exception {
		String[] dnaFalse = { "ACTG", "TGCA", "TGAA", "CCGT" };
		when(dbserviceMock.searchDna(dnaFalse)).thenReturn(null);
		
		ResponseEntity<Object> responseFalse = mutantsService.analyzeDNA(dnaFalse);
		HttpStatus responseHttp = responseFalse.getStatusCode();
		assertNotNull(responseFalse.getBody());
		assertEquals(HttpStatus.FORBIDDEN, responseHttp);
	}
}
