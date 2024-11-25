package mondragon_course.tms_pbl.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mondragon_course.tms_pbl.model.PatientCase;
import mondragon_course.tms_pbl.model.Queue;

public interface QueueRepository extends JpaRepository<Queue, Long>{

    // @Query("SELECT COUNT(q) FROM Queue q WHERE q.caseEntity.caseId = :caseId")
    // int findPositionByCaseId(int caseId); 
    // List<Queue> findByCaseEntity_SpecialtyOrderByCaseEntity_PriorityDesc(String specialty);
    Optional<Queue> findBySpecialty(String specialty);
    // Buscar pacientes con specialty y priority no nulos y que aún no están en una cola
    @Query("SELECT p FROM PatientCase p WHERE p.specialty IS NOT NULL AND p.priority IS NOT NULL AND p.queue IS NULL")
    List<PatientCase> findBySpecialtyNotNullAndPriorityNotNullAndQueueIsNull();
}
