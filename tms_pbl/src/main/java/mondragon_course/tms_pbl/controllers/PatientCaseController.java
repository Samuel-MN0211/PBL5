package mondragon_course.tms_pbl.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mondragon_course.tms_pbl.model.PatientCase;
import mondragon_course.tms_pbl.model.Queue;
import mondragon_course.tms_pbl.repositories.PatientCaseRepository;
import mondragon_course.tms_pbl.repositories.QueueRepository;

@RestController
@RequestMapping("/case")
public class PatientCaseController {

    @Autowired
    private PatientCaseRepository patientCaseRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addCase(@RequestBody PatientCase patientCase) {

        PatientCase savedCase = patientCaseRepository.save(patientCase);


        sendToNodeRed(savedCase);

        return ResponseEntity.ok("Case ID: " + savedCase.getCaseId());
    }

    private void sendToNodeRed(PatientCase patientCase) {
        System.out.println("Sent to Node-RED: " + patientCase);
    }
}
