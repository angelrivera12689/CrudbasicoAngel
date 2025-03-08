package com.sena.crud_basic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.organizerDTO;
import com.sena.crud_basic.model.organizer;
import com.sena.crud_basic.repository.Iorganizer;

@Service
public class organizerService {

    @Autowired
    private Iorganizer data;  // Organizer Repository to interact with the database

    // Method to save an organizer from organizerDTO
    public void save(organizerDTO organizerDTO) {
        organizer organizer = convertToModel(organizerDTO);  // Convert DTO to model
        data.save(organizer);  // Save organizer to the database
    }

    // Method to convert an organizer entity to organizerDTO
    public organizerDTO convertToDTO(organizer organizer) {
        return new organizerDTO(
                organizer.getName(),
                organizer.getPhone(),
                organizer.getEmail());
    }

    // Method to convert organizerDTO to an organizer entity
    public organizer convertToModel(organizerDTO organizerDTO) {
        return new organizer(
                0,  // Assuming ID is auto-generated
                organizerDTO.getName(),
                organizerDTO.getPhone(),
                organizerDTO.getEmail());
    }

    // Other business logic methods (if needed) can be added here
}
