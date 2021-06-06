package co.com.xmen.mutant.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import co.com.xmen.mutant.firestore.FirestoreCloud;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
class DataBaseServiceImplTest {

	FirestoreCloud firestore;

	@Test
	void searchDnaTest() throws Exception {
		firestore = Mockito.mock(FirestoreCloud.class);
		Mockito.when(firestore.retrieveAllDocuments()).thenReturn(null);
		String[] dna = { "AAAA", "AAAA", "AAAA", "AAAA" };

		DataBaseServiceImpl dbservice = new DataBaseServiceImpl(firestore);
		DocumentSnapshot document = dbservice.searchDna(dna);
		assertNull(document);

	}
	
	@Test
	void saveResultTest() throws Exception {		
		String[] dna = { "AAAA", "AAAA", "AAAA", "AAAA" };			
		DataBaseServiceImpl dbservice = Mockito.mock(DataBaseServiceImpl.class);		
		doNothing().when(dbservice).saveResult(dna, true);
		dbservice.saveResult(dna, true);
		verify(dbservice, times(1)).saveResult(dna, true);
	}

	@Test
	void getDnaListTest() throws Exception {
		firestore = Mockito.mock(FirestoreCloud.class);
		Mockito.when(firestore.retrieveAllDocuments()).thenReturn(Collections.emptyList());

		DataBaseServiceImpl dbservice = new DataBaseServiceImpl(firestore);
		List<QueryDocumentSnapshot> dnaList = dbservice.getDnaList();
		assertTrue(dnaList.isEmpty());
	}

}
