package mondragon_course.tms_pbl.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import mondragon_course.tms_pbl.Service.QueueService;
import mondragon_course.tms_pbl.model.PatientCase;
import mondragon_course.tms_pbl.model.Queue;
import mondragon_course.tms_pbl.repositories.PatientCaseRepository;
import mondragon_course.tms_pbl.repositories.QueueRepository;


// @RestController
// @RequestMapping("/Queue")
// public class QueueController {
//     @Autowired
//     private QueueRepository queueRepository;

//     @Autowired
//     private QueueService queueService;

//     @Autowired
//     private PatientCaseRepository patientCaseRepository;
    
//     // @GetMapping(value = "/show", produces = "application/json")
//     // @ResponseBody
//     // public ResponseEntity<List<Queue>> getQueue() {
//     //     List<Queue> queue_list = queueRepository.findAll();

//     //     if (queue_list.isEmpty()) {
//     //         return ResponseEntity.notFound().build();
//     //     } else {
//     //         return new ResponseEntity<>(queue_list, HttpStatus.OK);
//     //     }
//     // }
//     // @GetMapping(value = "/show", produces = "application/json")
//     // public ResponseEntity<List<Queue>> getQueue() {
//     //     List<Queue> queueList = queueService.getAllQueues(); // Llamar al método que devuelve todas las colas
//     //     if (queueList.isEmpty()) {
//     //         return ResponseEntity.notFound().build();
//     //     }
//     //     return new ResponseEntity<>(queueList, HttpStatus.OK);
//     // }
    
//     @GetMapping(value = "/show", produces = "application/json")
//     public ResponseEntity<List<Queue>> getQueue() {
//         List<Queue> queueList = queueService.getQueuesBySpecialty(null); // Returns all queues
//         if (queueList.isEmpty()) {
//             return ResponseEntity.notFound().build();
//         }
//         return new ResponseEntity<>(queueList, HttpStatus.OK);
//     }
// //     @GetMapping(value = "/show", produces = "application/json")
// // public ResponseEntity<List<QueueResponseDTO>> getQueue() {
// //     List<QueueResponseDTO> queueList = queueService.getAllQueuesWithDetails();
// //     if (queueList.isEmpty()) {
// //         return ResponseEntity.notFound().build();
// //     }
// //     return new ResponseEntity<>(queueList, HttpStatus.OK);
// // }

//     @GetMapping(value = "/specialty/{specialty}", produces = "application/json")
//     public ResponseEntity<List<Queue>> getQueuesBySpecialty(@PathVariable String specialty) {
//         List<Queue> queueList = queueService.getQueuesBySpecialty(specialty);
//         if (queueList.isEmpty()) {
//             return ResponseEntity.notFound().build();
//         }
//         return new ResponseEntity<>(queueList, HttpStatus.OK);
//     }

//     //When the ml process finish must call to this function
//     @PostMapping(value = "/update", consumes = "application/json")
//     public ResponseEntity<String> updateQueue(@RequestBody PatientCase updatedCase) {
//         queueService.updateQueues(updatedCase);
//         return new ResponseEntity<>("Queue updated successfully.", HttpStatus.OK);
//     }

    
// }
@RestController
@RequestMapping("/Queue")
public class QueueController {
    @Autowired
    private QueueService queueService;

    /**
     * Agrega un paciente a la cola correspondiente a su especialidad.
     */
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<String> addPatientToQueue(@RequestBody PatientCase patientCase) {
        try {
            queueService.addPatientToQueue(patientCase);
            return new ResponseEntity<>("Paciente añadido a la cola con éxito.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Muestra todas las colas existentes.
     */
    @GetMapping(value = "/show", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> getAllQueues() {
        List<Queue> queues = queueService.getAllQueues();

        if (queues.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Filtrar datos y construir respuesta personalizada
        List<Map<String, Object>> response = queues.stream().map(queue -> {
            Map<String, Object> queueData = new HashMap<>();
            queueData.put("id", queue.getId());
            queueData.put("specialty", queue.getSpecialty());
            queueData.put("patientCases", queue.getPatientCases().stream().map(patient -> {
                Map<String, Object> patientData = new HashMap<>();
                patientData.put("name", patient.getName());
                patientData.put("priority", patient.getPriority());
                patientData.put("timeEnter", queue.getTimeEnter()); // Reutilizamos el tiempo de entrada de la cola
                return patientData;
            }).collect(Collectors.toList()));
            return queueData;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/process-patients")
    public ResponseEntity<String> processPatients() {
        queueService.processPendingPatients();
        return ResponseEntity.ok("Pacientes procesados y asignados a las colas correctamente.");
    }
}