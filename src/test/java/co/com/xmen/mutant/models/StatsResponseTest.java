package co.com.xmen.mutant.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-dev.properties")
class StatsResponseTest {
	
	StatsResponse stats;

	@Test
	void statsResponseTest() {
		stats = new StatsResponse();
		stats.setCountHumanDna(0);
		stats.setCountMutantDna(0);
		stats.setRatio("undefined");
		int humans = stats.getCountHumanDna();
		int mutants = stats.getCountMutantDna();
		String ratio = stats.getRatio();
		assertEquals(humans, mutants);
		assertNotNull(ratio);
	}

	@Test
	void statsResponseConstructorTest() {
		stats = new StatsResponse(100, 40);
		String result = stats.toString();
		assertNotNull(result);
	}

}
