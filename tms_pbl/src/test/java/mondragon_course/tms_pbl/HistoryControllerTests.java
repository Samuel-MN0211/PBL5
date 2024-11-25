package mondragon_course.tms_pbl;

import mondragon_course.tms_pbl.controllers.HistoryController;
import mondragon_course.tms_pbl.model.History;
import mondragon_course.tms_pbl.model.PatientCase;
import mondragon_course.tms_pbl.repositories.HistoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HistoryControllerTests {

    @Mock
    private HistoryRepository mockRepo; // Mocked repository

    @InjectMocks
    private HistoryController controller; // Controller with mock dependencies injected

    @BeforeEach
    public void setUp() {
        // Initialize the mocks and inject them into the controller
        MockitoAnnotations.openMocks(this);
    }

	/*	ALL HISTORIES */

    @Test
    public void testGetAllHistories() {
		// Arrange: Mock the repository behavior
		List<History> mockHistories = List.of(
			new History(1L, 30, true, new PatientCase()),
			new History(2L, 20, true, new PatientCase())
		);
		when(mockRepo.findAll()).thenReturn(mockHistories);

		// Act: Call the controller method
		ResponseEntity<List<History>> response = controller.getAllHistories();

		// Assert: Verify the response
		assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().size()).isEqualTo(2);
		assertThat(response.getBody().get(0).getTotalTime()).isEqualTo(30);

		// Verify the interaction with the mock
		verify(mockRepo, times(1)).findAll();
    }

	@Test
    public void testGetAllHistories_WhenNoDataExists() {
        // Arrange: Mock the repository behavior for empty data
        when(mockRepo.findAll()).thenReturn(Collections.emptyList());

        // Act: Call the controller method
        ResponseEntity<List<History>> response = controller.getAllHistories();

        // Assert: Verify the response
        assertThat(response.getStatusCodeValue()).isEqualTo(404); // HTTP 404 Not Found
        assertThat(response.getBody()).isNull();

        // Verify the interaction with the mock
        verify(mockRepo, times(1)).findAll();
    }


	/*	PATIENTS PER SPECIALTY */
    @Test
    public void testGetPatientsPerSpecialty_WhenDataExists() {
        // Arrange: Mock the repository behavior
        List<Object[]> mockData = List.of(
            new Object[] { "Cardiology", 10 },
            new Object[] { "Neurology", 5 }
        );
        when(mockRepo.getPatientQuantityPerSpecialty()).thenReturn(mockData);

        // Act: Call the controller method
        ResponseEntity<List<Object[]>> response = controller.getPatientsPerSpecialty();

        // Assert: Verify the response
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0)[0]).isEqualTo("Cardiology");
        assertThat(response.getBody().get(0)[1]).isEqualTo(10);

        // Verify the interaction with the mock
        verify(mockRepo, times(1)).getPatientQuantityPerSpecialty();
    }

    @Test
    public void testGetPatientsPerSpecialtyNoData() {
        // Arrange: Mock the repository behavior for empty data
        when(mockRepo.getPatientQuantityPerSpecialty()).thenReturn(Collections.emptyList());

        // Act: Call the controller method
        ResponseEntity<List<Object[]>> response = controller.getPatientsPerSpecialty();

        // Assert: Verify the response
        assertThat(response.getStatusCodeValue()).isEqualTo(404); // HTTP 404 Not Found
        assertThat(response.getBody()).isNull();

        // Verify the interaction with the mock
        verify(mockRepo, times(1)).getPatientQuantityPerSpecialty();
    }













	/*	PRIORITY DISTRIBUTION */
	@Test
    public void testGetPriorityDistribution() {
        // Arrange: Mock the repository behavior
        List<Object[]> mockData = List.of(
            new Object[] { 4, 10 },
            new Object[] { 2, 5 }
        );
        when(mockRepo.getPriorityDistribution()).thenReturn(mockData);

        // Act: Call the controller method
        ResponseEntity<List<Object[]>> response = controller.getPriority();

        // Assert: Verify the response
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0)[0]).isEqualTo(4);
        assertThat(response.getBody().get(0)[1]).isEqualTo(10);

        // Verify the interaction with the mock
        verify(mockRepo, times(1)).getPriorityDistribution();

	}

    @Test
    public void testGetPriorityDistributionNoData() {
		// Arrange: Mock the repository behavior for empty data
		when(mockRepo.getPriorityDistribution()).thenReturn(Collections.emptyList());

		// Act: Call the controller method
		ResponseEntity<List<Object[]>> response = controller.getPriority();

		// Assert: Verify the response
		assertThat(response.getStatusCodeValue()).isEqualTo(404); // HTTP 404 Not Found
		assertThat(response.getBody()).isNull();

		// Verify the interaction with the mock
        verify(mockRepo, times(1)).getPriorityDistribution();
    }



	/*	TIME TOTAL */
	@Test
	public void testGetAvgTimeTotal() {
		// Arrange: Mock the repository behavior
		Object mockAvgTime = 25.5; // Simulate an average time value
		when(mockRepo.getAvgTime()).thenReturn(mockAvgTime);

		// Act: Call the controller method
		ResponseEntity<Object> response = controller.getTotalTimeAvg();

		// Assert: Verify the response
		assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).isEqualTo(25.5);

		// Verify the interaction with the mock
		verify(mockRepo, times(1)).getAvgTime();
	}

	@Test
	public void testGetAvgTimeTotalNoData() {
        // Arrange: Mock the repository behavior for null data
        when(mockRepo.getAvgTime()).thenReturn(null);

        // Act: Call the controller method
        ResponseEntity<Object> response = controller.getTotalTimeAvg();

        // Assert: Verify the response
        assertThat(response.getStatusCodeValue()).isEqualTo(404); // HTTP 404 Not Found
        assertThat(response.getBody()).isNull();

        // Verify the interaction with the mock
        verify(mockRepo, times(1)).getAvgTime();
	}


	/*	TIME PRIORITY */
	@Test
	public void testGetAvgTimePriority() {
		// Arrange: Mock the repository behavior
		List<Object[]> mockData = List.of(
			new Object[] { 4, 10.0 },
			new Object[] { 2, 5.0 }
		);
		when(mockRepo.getAvgTimePerPriority()).thenReturn(mockData);

		// Act: Call the controller method
		ResponseEntity<List<Object[]>> response = controller.getPriorityTimeAvg();

		// Assert: Verify the response
		assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().size()).isEqualTo(2);
		assertThat(response.getBody().get(0)[0]).isEqualTo(4);
		assertThat(response.getBody().get(0)[1]).isEqualTo(10.0);

		// Verify the interaction with the mock
		verify(mockRepo, times(1)).getAvgTimePerPriority();
	}

	@Test
	public void testGetAvgTimePriorityNoData() {

		// Arrange: Mock the repository behavior for empty data
		when(mockRepo.getAvgTimePerPriority()).thenReturn(Collections.emptyList());

		// Act: Call the controller method
		ResponseEntity<List<Object[]>> response = controller.getPriorityTimeAvg();

		// Assert: Verify the response
		assertThat(response.getStatusCodeValue()).isEqualTo(404); // HTTP 404 Not Found
		assertThat(response.getBody()).isNull();

		// Verify the interaction with the mock
        verify(mockRepo, times(1)).getAvgTimePerPriority();
	}


	/*	TIME SPECIALTY */
	@Test
	public void testGetAvgTimeSpecialty() {
		// Arrange: Mock the repository behavior
		List<Object[]> mockData = List.of(
			new Object[] { 4, 10.0 },
			new Object[] { 2, 5.0 }
		);
		when(mockRepo.getAvgTimePerSpecialty()).thenReturn(mockData);

		// Act: Call the controller method
		ResponseEntity<List<Object[]>> response = controller.getSpecialtyTimeAvg();

		// Assert: Verify the response
		assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().size()).isEqualTo(2);
		assertThat(response.getBody().get(0)[0]).isEqualTo(4);
		assertThat(response.getBody().get(0)[1]).isEqualTo(10.0);

		// Verify the interaction with the mock
		verify(mockRepo, times(1)).getAvgTimePerSpecialty();
	}

	@Test
	public void testGetAvgTimeSpecialtyNoData() {

		// Arrange: Mock the repository behavior for empty data
		when(mockRepo.getAvgTimePerSpecialty()).thenReturn(Collections.emptyList());

		// Act: Call the controller method
		ResponseEntity<List<Object[]>> response = controller.getSpecialtyTimeAvg();

		// Assert: Verify the response
		assertThat(response.getStatusCodeValue()).isEqualTo(404); // HTTP 404 Not Found
		assertThat(response.getBody()).isNull();

		// Verify the interaction with the mock
        verify(mockRepo, times(1)).getAvgTimePerSpecialty();
	}



}
