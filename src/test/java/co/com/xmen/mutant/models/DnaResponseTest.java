package co.com.xmen.mutant.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-dev.properties")
class DnaResponseTest {

	DnaResponse dnaResponse;
	
	@Test
	void dnaResponseTest() {
		dnaResponse = new DnaResponse();
		dnaResponse.setMutant(true);
		boolean isMutant = dnaResponse.getMutant();
		assertTrue(isMutant);
	}
	
	@Test
	void dnaResponseConstructorTest() {
		dnaResponse = new DnaResponse(false);		
		boolean isMutant = dnaResponse.getMutant();
		String response = dnaResponse.toString();
		assertFalse(isMutant);
		assertNotNull(response);
	}

}
