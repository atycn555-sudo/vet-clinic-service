package com.example.vet.service;

import com.example.vet.dto.ClientRequestDTO;
import com.example.vet.dto.ClientResponseDTO;
import com.example.vet.model.Address;
import com.example.vet.model.Client;
import com.example.vet.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClientResponseDTO> getAll() {
        return clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientResponseDTO.class))
                .collect(Collectors.toList());
    }

    public ClientResponseDTO getById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    public ClientResponseDTO getByEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    public ClientResponseDTO save(ClientRequestDTO request) {
        if (request.getEmail() != null && clientRepository.findByEmail(request.getEmail()).isPresent()) {
             throw new RuntimeException("The email is already registered");
        }

        Client client = modelMapper.map(request, Client.class);
        
        if (request.getAddress() != null) {
            Address address = modelMapper.map(request.getAddress(), Address.class);
            client.setAddress(address);
        }

        Client savedClient = clientRepository.save(client);
        return modelMapper.map(savedClient, ClientResponseDTO.class);
    }

    public ClientResponseDTO update(Integer id, ClientRequestDTO request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setPhone(request.getPhone());
        client.setEmail(request.getEmail());

        if (request.getAddress() != null) {
            if (client.getAddress() == null) {
                client.setAddress(new Address());
            }
            modelMapper.map(request.getAddress(), client.getAddress());
        }

        Client updatedClient = clientRepository.save(client);
        return modelMapper.map(updatedClient, ClientResponseDTO.class);
    }

    public void delete(Integer id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        clientRepository.deleteById(id);
    }
}
