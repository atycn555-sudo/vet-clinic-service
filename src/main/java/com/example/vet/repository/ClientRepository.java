package com.example.vet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vet.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    
    List<Client> findByFirstNameContainingIgnoreCase(String firstName);

    Optional<Client> findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);

    Optional<Client> findByEmail(String email);
}
