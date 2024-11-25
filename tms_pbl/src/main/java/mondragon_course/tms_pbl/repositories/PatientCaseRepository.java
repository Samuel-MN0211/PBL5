package mondragon_course.tms_pbl.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mondragon_course.tms_pbl.model.PatientCase;

public interface PatientCaseRepository extends JpaRepository<PatientCase, Integer> {

    public Optional<PatientCase> findById(int caseId);

    public List<PatientCase> findBySpecialtyNotNullAndPriorityNotNullAndQueueIsNull();
    
}
