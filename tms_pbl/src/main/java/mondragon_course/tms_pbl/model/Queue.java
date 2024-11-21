package mondragon_course.tms_pbl.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timeEnter;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private PatientCase caseEntity;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeEnter() {
        return timeEnter;
    }

    public void setTimeEnter(LocalDateTime timeEnter) {
        this.timeEnter = timeEnter;
    }

    public PatientCase getCaseEntity() {
        return caseEntity;
    }

    public void setCaseEntity(PatientCase caseEntity) {
        this.caseEntity = caseEntity;
    }
}
