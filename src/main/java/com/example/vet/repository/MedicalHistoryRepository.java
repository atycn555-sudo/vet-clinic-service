package com.example.vet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vet.model.MedicalHistory;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {

    
    List<MedicalHistory> findByPetId(Integer petId);
    
}
