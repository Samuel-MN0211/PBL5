package mondragon_course.tms_pbl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mondragon_course.tms_pbl.model.Queue;

public interface QueueRepository extends JpaRepository<Queue, Integer>{
    
}
