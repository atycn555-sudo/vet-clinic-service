package com.example.vet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.vet.dto.VeterinarianRequestDTO;
import com.example.vet.dto.VeterinarianResponseDTO;
import com.example.vet.model.Veterinarian;
import com.example.vet.repository.VeterinarianRepository;

@Service
public class VeterinarianService {

    private final VeterinarianRepository veterinarianRepository;
    private final ModelMapper modelMapper;

    public VeterinarianService(VeterinarianRepository veterinarianRepository, ModelMapper modelMapper) {
        this.veterinarianRepository = veterinarianRepository;
        this.modelMapper = modelMapper;
    }

    public List<VeterinarianResponseDTO> getAll() {
        return veterinarianRepository.findAll().stream()
                .map(vet -> modelMapper.map(vet, VeterinarianResponseDTO.class))
                .collect(Collectors.toList());
    }

    public VeterinarianResponseDTO getById(Integer id) {
        Veterinarian vet = veterinarianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinarian not found with ID: " + id));
        return modelMapper.map(vet, VeterinarianResponseDTO.class);
    }

    public VeterinarianResponseDTO save(VeterinarianRequestDTO request) {
        Veterinarian vet = modelMapper.map(request, Veterinarian.class);

        Veterinarian savedVet = veterinarianRepository.save(vet);
        
        return modelMapper.map(savedVet, VeterinarianResponseDTO.class);
    }

    public VeterinarianResponseDTO update(Integer id, VeterinarianRequestDTO request) {
        Veterinarian vet = veterinarianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veterinarian not found with ID: " + id));
        
        modelMapper.map(request, vet);
        vet.setId(id);
        
        Veterinarian updatedVet = veterinarianRepository.save(vet);
        return modelMapper.map(updatedVet, VeterinarianResponseDTO.class);
    }

    public void delete(Integer id) {
        if (!veterinarianRepository.existsById(id)) {
            throw new RuntimeException("Veterinarian not found");
        }
        veterinarianRepository.deleteById(id);
    }
}
