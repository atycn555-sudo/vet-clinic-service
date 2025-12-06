package com.example.vet.dto;

import java.time.LocalDate;

public class MedicalHistoryRequestDTO {
    private String description;
    private LocalDate date;
    private Integer petId;

    public MedicalHistoryRequestDTO() {}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Integer getPetId() { return petId; } 
    public void setPetId(Integer petId) { this.petId = petId; }
}
