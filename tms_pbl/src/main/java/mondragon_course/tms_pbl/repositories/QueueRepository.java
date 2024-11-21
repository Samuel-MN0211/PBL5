package mondragon_course.tms_pbl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mondragon_course.tms_pbl.model.Queue;

public interface QueueRepository extends JpaRepository<Queue, Integer>{

    @Query("SELECT COUNT(q) FROM Queue q WHERE q.caseEntity.caseId = :caseId")
    int findPositionByCaseId(int caseId); 
    
}
