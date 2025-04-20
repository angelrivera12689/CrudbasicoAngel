package com.sena.crud_basic.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.DTO.organizerDTO;

import com.sena.crud_basic.model.organizer;
import com.sena.crud_basic.repository.Iorganizer;

@Service
public class organizerService {

    @Autowired
    private Iorganizer data;  // Organizer Repository to interact with the database

  
   
    public ResponseDTO save(organizerDTO organizerDTO) {
        // Validar que el nombre tenga entre 1 y 100 caracteres
        if (organizerDTO.getName().length() < 1 || organizerDTO.getName().length() > 100) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El nombre debe estar entre 1 y 100 caracteres"
            );
        }

        // Validar el formato del email
        if (!isValidEmail(organizerDTO.getEmail())) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El email no tiene un formato válido"
            );
        }

        // Validar el teléfono (debe tener 10 dígitos y solo números)
        if (organizerDTO.getPhone().length() != 10 || !organizerDTO.getPhone().matches("[0-9]+")) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El número de teléfono debe tener 10 dígitos y solo puede contener números"
            );
        }

        // Convertir el DTO a modelo
        organizer organizer = convertToModel(organizerDTO);

        // Guardar en la base de datos
        data.save(organizer);

        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Organizador guardado exitosamente"
        );
    }

    // ✅ Método para obtener todos los organizadores
    public List<organizer> findAll() {
        return data.getListOrganizerActive();
    }

    // ✅ Método para buscar un organizador por ID
    public Optional<organizer> findById(int id) {
        return data.findById(id);
    }

    // ✅ Método para eliminar un organizador por ID
    public ResponseDTO deleteOrganizer(int id) {
        Optional<organizer> organizer = findById(id);
        if (!organizer.isPresent()) {
            // Si no se encuentra el empleado, devolvemos una respuesta de error
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El registro no existe"
            );
        }
    
        // Cambiar el estado del empleado a false (eliminación lógica)
        organizer.get().setStatus(false);
        data.save(organizer.get());
    
        // Devolvemos una respuesta de éxito
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Organizador eliminado correctamente"
        );
    }
    

    // ✅ Método para validar el formato del email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public List<organizer> filterorganizers(String name, String phone, String email, Boolean status) {
        return data.filterorganizers(name, phone, email, status);
    }

    public ResponseDTO update(int id, organizerDTO organizerDTO) {
        Optional<organizer> existingOrganizer = findById(id);
        if (!existingOrganizer.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El organizador no existe"
            );
        }

        if (organizerDTO.getName().length() < 1 || organizerDTO.getName().length() > 100) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El nombre debe estar entre 1 y 100 caracteres"
            );
        }

        if (!isValidEmail(organizerDTO.getEmail())) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El email no tiene un formato válido"
            );
        }

        if (organizerDTO.getPhone().length() != 10 || !organizerDTO.getPhone().matches("[0-9]+")) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El número de teléfono debe tener 10 dígitos y solo puede contener números"
            );
        }

        organizer organizerToUpdate = existingOrganizer.get();
        organizerToUpdate.setName(organizerDTO.getName());
        organizerToUpdate.setPhone(organizerDTO.getPhone());
        organizerToUpdate.setEmail(organizerDTO.getEmail());

        data.save(organizerToUpdate);

        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Organizador actualizado exitosamente"
        );
    }

   // Method to convert an organizer entity to organizerDTO
   public organizerDTO convertToDTO(organizer organizer) {
    return new organizerDTO(
        organizer.getName(),
        organizer.getPhone(),
        organizer.getEmail(),
        organizer.getImageUrl() // 👈 Añadido el campo de la imagen
    );
}


public organizer convertToModel(organizerDTO organizerDTO) {
    return new organizer(
        0,  // El ID se generará automáticamente
        organizerDTO.getName(),
        organizerDTO.getPhone(),
        organizerDTO.getEmail(),
        true,  // Estado activo por defecto
        organizerDTO.getImageUrl() // El campo de la imagen debe ir al final
    );
}
}