package mondragon_course.tms_pbl.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mondragon_course.tms_pbl.model.Queue;
import mondragon_course.tms_pbl.repositories.QueueRepository;

@RestController
@RequestMapping("/Queue")
public class QueueController {
    @Autowired
    private QueueRepository queueRepository;
    
    @GetMapping(value = "/show", produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<Queue>> getQueue() {
        List<Queue> queue_list = queueRepository.findAll();

        if (queue_list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(queue_list, HttpStatus.OK);
        }
    }
}
