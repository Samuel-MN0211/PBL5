package mondragon_course.tms_pbl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mondragon_course.tms_pbl.model.Case;

public interface CaseRepository extends JpaRepository<Case, Integer> {
    
}
