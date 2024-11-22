package mondragon_course.tms_pbl.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mondragon_course.tms_pbl.model.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    
    @Query("SELECT pc.specialty AS specialty, COUNT(h) AS quantity " +
        "FROM History h " +
        "JOIN h.patientcaseEntity pc " +
        "GROUP BY pc.specialty")
    public List<Object[]> getPatientQuantityPerSpecialty();


}
