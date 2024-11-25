package mondragon_course.tms_pbl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalTime;
    private boolean completeFlag;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private PatientCase patientcaseEntity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public boolean isCompleteFlag() {
        return completeFlag;
    }

    public void setCompleteFlag(boolean completeFlag) {
        this.completeFlag = completeFlag;
    }

    public PatientCase getPatientCaseEntity() {
        return patientcaseEntity;
    }

    public void setPatientCaseEntity(PatientCase patientcaseEntity) {
        this.patientcaseEntity = patientcaseEntity;
    }
}
