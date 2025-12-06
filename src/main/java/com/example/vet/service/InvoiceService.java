package com.example.vet.service;

import com.example.vet.dto.InvoiceRequestDTO;
import com.example.vet.dto.InvoiceResponseDTO;
import com.example.vet.model.Client;
import com.example.vet.model.Invoice;
import com.example.vet.repository.ClientRepository;
import com.example.vet.repository.InvoiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, 
                          ClientRepository clientRepository, 
                          ModelMapper modelMapper) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    public List<InvoiceResponseDTO> getAll() {
        return invoiceRepository.findAll().stream()
                .map(invoice -> modelMapper.map(invoice, InvoiceResponseDTO.class))
                .collect(Collectors.toList());
    }

    public InvoiceResponseDTO getById(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return modelMapper.map(invoice, InvoiceResponseDTO.class);
    }

    public InvoiceResponseDTO create(InvoiceRequestDTO request) {
        Client client = clientRepository.findById(request.getIdClient())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Invoice invoice = modelMapper.map(request, Invoice.class);
        invoice.setClient(client);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return modelMapper.map(savedInvoice, InvoiceResponseDTO.class);
    }

    public InvoiceResponseDTO update(Integer id, InvoiceRequestDTO request) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.getClient() != null && !invoice.getClient().getId().equals(request.getIdClient())) {
            Client client = clientRepository.findById(request.getIdClient())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            invoice.setClient(client);
        }

        modelMapper.map(request, invoice);
        invoice.setId(id);

        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return modelMapper.map(updatedInvoice, InvoiceResponseDTO.class);
    }

    public void delete(Integer id) {
        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }
    
    /*
    public List<InvoiceResponseDTO> getInvoicesByClientId(Integer clientId) {
        // Asegúrate de que este método 'findByClientId' exista en tu InvoiceRepository antes de descomentar
        // return invoiceRepository.findByClientId(clientId).stream()
        //         .map(inv -> modelMapper.map(inv, InvoiceResponseDTO.class))
        //         .collect(Collectors.toList());
        return null; 
    }
    */
}
