package co.com.xmen.mutant.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import co.com.xmen.mutant.models.StatsResponse;
import co.com.xmen.mutant.service.DataBaseService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
class StatServiceImplTest {

	@MockBean
	private DataBaseService dbserviceMock;

	@Autowired
	private MutantServiceImpl mutantsService;

	@Test
	void getDocumentsTest() throws Exception {
		when(dbserviceMock.getDnaList()).thenReturn(Collections.emptyList());
		
		ResponseEntity<Object> response = mutantsService.getStats();
		StatsResponse stats = (StatsResponse) response.getBody();
		StatsResponse statsExpected = new StatsResponse(0, 0);
		assertEquals(statsExpected.getRatio(), stats.getRatio());
	}
}
