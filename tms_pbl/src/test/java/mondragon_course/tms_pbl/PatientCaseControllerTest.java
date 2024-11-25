package mondragon_course.tms_pbl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import mondragon_course.tms_pbl.controllers.PatientCaseController;
import mondragon_course.tms_pbl.model.PatientCase;
import mondragon_course.tms_pbl.model.Queue;
import mondragon_course.tms_pbl.repositories.PatientCaseRepository;
import mondragon_course.tms_pbl.repositories.QueueRepository;

class PatientCaseControllerTest {

    @InjectMocks
    private PatientCaseController patientCaseController;

    @Mock
    private PatientCaseRepository patientCaseRepository;

    @Mock
    private QueueRepository queueRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCase_success() {
        // Arrange
        PatientCase mockCase = new PatientCase();
        mockCase.setCaseId(1L);
        mockCase.setDni("12345678X");
        when(patientCaseRepository.save(any(PatientCase.class))).thenReturn(mockCase);

        // Act
        ResponseEntity<?> response = patientCaseController.addCase(mockCase);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Case ID: 1");
        verify(patientCaseRepository, times(1)).save(mockCase);
    }

    @Test
    void testGetQueueDetails_caseNotFound() {

        when(patientCaseRepository.findById(anyInt())).thenReturn(Optional.empty());


        ResponseEntity<?> response = patientCaseController.getQueueDetails(1);


        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("Case not found.");
        verify(patientCaseRepository, times(1)).findById(1);
    }

    @Test
    void testGetQueueDetails_noQueuesAssociated() {
        PatientCase mockCase = new PatientCase();
        mockCase.setQueues(Collections.emptyList());
        when(patientCaseRepository.findById(anyInt())).thenReturn(Optional.of(mockCase));


        ResponseEntity<?> response = patientCaseController.getQueueDetails(1);


        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("No queues associated with this case.");
        verify(patientCaseRepository, times(1)).findById(1);
    }

    @Test
    void testGetQueueDetails_success() {

        PatientCase mockCase = new PatientCase();
        Queue mockQueue = new Queue();
        mockQueue.setTimeEnter(LocalDateTime.now().plusMinutes(10));
        mockCase.setQueues(List.of(mockQueue));
        mockCase.setPriority(5.0f);
        when(patientCaseRepository.findById(anyInt())).thenReturn(Optional.of(mockCase));
        when(queueRepository.findPositionByCaseId(anyInt())).thenReturn(3);

    
        ResponseEntity<?> response = patientCaseController.getQueueDetails(1);


        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isInstanceOf(String.class);
        String responseBody = (String) response.getBody();
        assertThat(responseBody).contains("Remaining time:");
        assertThat(responseBody).contains("Position: 3");
        assertThat(responseBody).contains("Priority: 5");
        verify(patientCaseRepository, times(1)).findById(1);
        verify(queueRepository, times(1)).findPositionByCaseId(1);
    }
}