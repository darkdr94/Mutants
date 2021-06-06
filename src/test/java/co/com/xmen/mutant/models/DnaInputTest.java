package co.com.xmen.mutant.models;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-dev.properties")
class DnaInputTest {

	DnaInput dnaInput;

	String[] dna = { "ATCG", "AGCC", "TGGC", "GCGC" };

	@Test
	void dnaInputTest() {
		dnaInput = new DnaInput();
		dnaInput.setDna(dna);
		String[] dnaResult = dnaInput.getDna();
		assertNotNull(dnaResult);
	}

	@Test
	void dnaInputConstructorTest() {
		dnaInput = new DnaInput(dna);
		String result = dnaInput.toString();
		assertNotNull(result);
	}

}
