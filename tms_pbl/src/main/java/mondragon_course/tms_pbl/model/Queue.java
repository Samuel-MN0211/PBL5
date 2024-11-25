package mondragon_course.tms_pbl.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;  
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "queue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PatientCase> patientCases = new ArrayList<>();

    // Campo adicional para registrar el tiempo de entrada de un paciente.
    private LocalDateTime timeEnter;


    private String specialty;
  
    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    @JsonBackReference  
    private PatientCase caseEntity;


    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PatientCase> getPatientCases() {
        return patientCases;
    }

    public void setPatientCases(List<PatientCase> patientCases) {
        this.patientCases = patientCases;
    }

    public LocalDateTime getTimeEnter() {
        return timeEnter;
    }

    public void setTimeEnter(LocalDateTime timeEnter) {
        this.timeEnter = timeEnter;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
