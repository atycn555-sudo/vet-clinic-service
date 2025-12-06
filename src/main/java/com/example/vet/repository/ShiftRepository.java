package com.example.vet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vet.model.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    
List<Shift> findByVeterinarian_Id(Integer veterinarianId);
}
