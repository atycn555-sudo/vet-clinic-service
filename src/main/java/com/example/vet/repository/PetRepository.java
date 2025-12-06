package com.example.vet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vet.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByClientId(Integer clientId); 
    
}
