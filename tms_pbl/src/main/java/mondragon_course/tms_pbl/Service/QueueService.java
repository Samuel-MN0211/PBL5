package mondragon_course.tms_pbl.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mondragon_course.tms_pbl.model.PatientCase;
import mondragon_course.tms_pbl.model.Queue;
import mondragon_course.tms_pbl.repositories.PatientCaseRepository;
import mondragon_course.tms_pbl.repositories.QueueRepository;

// @Service
// public class QueueService {
//     @Autowired
//     private QueueRepository queueRepository;

//     @Autowired
//     private PatientCaseRepository patientCaseRepository;

    

//     public void updateQueues(PatientCase patientCase) {
//         // Verificar si la prioridad y la especialidad no son nulas
//         if (patientCase.getPriority() != null && patientCase.getSpecialty() != null) {
//             // Crear una nueva entrada en la cola
//             Queue newQueueEntry = new Queue();
//             newQueueEntry.setCaseEntity(patientCase);
//             newQueueEntry.setTimeEnter(LocalDateTime.now());

//             // Guardar en la base de datos
//             queueRepository.save(newQueueEntry);
//         }
//     }

//     public List<Queue> getAllQueues() {
//         return queueRepository.findAll();
//     }

//     public List<Queue> getQueuesBySpecialty(String specialty) {
//         return queueRepository.findByCaseEntity_SpecialtyOrderByCaseEntity_PriorityDesc(specialty);
//     }

// //     public List<QueueResponseDTO> getAllQueuesWithDetails() {
// //     List<Queue> queues = queueRepository.findAll();
// //     return queues.stream()
// //         .map(queue -> new QueueResponseDTO(
// //             queue.getId(),
// //             queue.getCaseEntity().getCaseId(),
// //             queue.getCaseEntity().getPriority(),
// //             queue.getCaseEntity().getSpecialty()
// //         ))
// //         .collect(Collectors.toList());
// // }
// }

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private PatientCaseRepository patientCaseRepository;

    // Método que ya tienes para añadir pacientes a la cola
    public void addPatientToQueue(PatientCase patientCase) {
        Queue queue = queueRepository.findBySpecialty(patientCase.getSpecialty())
                .orElseGet(() -> {
                    Queue newQueue = new Queue();
                    newQueue.setSpecialty(patientCase.getSpecialty());
                    newQueue.setTimeEnter(LocalDateTime.now());
                    return queueRepository.save(newQueue);
                });

        // Relacionar paciente con la cola
        patientCase.setQueue(queue);
        queue.getPatientCases().add(patientCase);

        // Guardar cambios
        patientCaseRepository.save(patientCase);
        queueRepository.save(queue);
    }

    // Método para obtener todas las colas
    public List<Queue> getAllQueues() {
        return queueRepository.findAll(); // Devuelve todas las colas
    }

    @Transactional
    public void processPendingPatients() {
        // Buscar todos los pacientes que cumplen la condición
        List<PatientCase> pendingPatients = patientCaseRepository.findBySpecialtyNotNullAndPriorityNotNullAndQueueIsNull();
        for (PatientCase patient : pendingPatients) {
            // Verificar si ya existe una cola para esta especialidad
            Queue queue = queueRepository.findBySpecialty(patient.getSpecialty())
                    .orElseGet(() -> {
                        // Crear una nueva cola si no existe
                        Queue newQueue = new Queue();
                        newQueue.setSpecialty(patient.getSpecialty());
                        newQueue.setTimeEnter(LocalDateTime.now());
                        return queueRepository.save(newQueue);
                    });

            // Relacionar el paciente con la cola encontrada o creada
            patient.setQueue(queue);
            queue.getPatientCases().add(patient);

            // Guardar cambios
            patientCaseRepository.save(patient);
            queueRepository.save(queue);
        }
    }
}