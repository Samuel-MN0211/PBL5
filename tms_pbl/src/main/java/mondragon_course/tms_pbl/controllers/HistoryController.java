package mondragon_course.tms_pbl.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mondragon_course.tms_pbl.model.History;
import mondragon_course.tms_pbl.repositories.HistoryRepository;

@RestController
@RequestMapping("/History")
public class HistoryController {
    
    @Autowired
    HistoryRepository repo;

    @GetMapping(value = "/all", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<History>> getAllHistories() {

        List<History> list = repo.findAll();

        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }
    

    @GetMapping(value = "/patientsPerSpecialty", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<Object[]>> getPatientsPerSpecialty() {

        List<Object[]> list = repo.getPatientQuantityPerSpecialty();

        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/priorityDistribution", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<Object[]>> getPriority() {

        List<Object[]> list = repo.getPriorityDistribution();

        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/avgTimeTotal", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<Object> getTotalTimeAvg() {

        Object timeAvg = repo.getAvgTime();

        if (timeAvg == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(timeAvg, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/avgTimePriority", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<Object[]>> getPriorityTimeAvg() {

        List<Object[]> list = repo.getAvgTimePerPriority();

        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/avgTimeSpecialty", produces = { "application/json", "application/xml" })
    @ResponseBody
    public ResponseEntity<List<Object[]>> getSpecialtyTimeAvg() {
        
        List<Object[]> list = repo.getAvgTimePerSpecialty();

        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }


    /*
     * 
     * 
     * /AvgTimeWaitPerSpecialty
     * /AvgTimeWaitPerPriority
     */

}
