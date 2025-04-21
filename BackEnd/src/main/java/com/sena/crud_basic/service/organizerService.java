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
    private Iorganizer data;  // Repositorio para interactuar con la base de datos

    public ResponseDTO save(organizerDTO organizerDTO) {
        // Validaciones
        if (!validateOrganizer(organizerDTO)) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "Datos inválidos");
        }

        organizer organizer = convertToModel(organizerDTO);
        data.save(organizer);

        return new ResponseDTO(HttpStatus.OK.toString(), "Organizador guardado exitosamente");
    }

    public List<organizer> findAll() {
        return data.getListOrganizerActive();
    }

    public Optional<organizer> findById(int id) {
        return data.findById(id);
    }

    public ResponseDTO deleteOrganizer(int id) {
        Optional<organizer> organizer = findById(id);
        if (!organizer.isPresent()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "El registro no existe");
        }

        organizer.get().setStatus(false);
        data.save(organizer.get());

        return new ResponseDTO(HttpStatus.OK.toString(), "Organizador eliminado correctamente");
    }

    public ResponseDTO update(int id, organizerDTO organizerDTO) {
        Optional<organizer> existingOrganizer = findById(id);
        if (!existingOrganizer.isPresent()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "El organizador no existe");
        }

        // Validaciones
        if (!validateOrganizer(organizerDTO)) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "Datos inválidos");
        }

        // 🔹 Actualizar valores incluyendo `imageUrl`
        organizer organizerToUpdate = existingOrganizer.get();
        organizerToUpdate.setName(organizerDTO.getName());
        organizerToUpdate.setPhone(organizerDTO.getPhone());
        organizerToUpdate.setEmail(organizerDTO.getEmail());
        organizerToUpdate.setImageUrl(organizerDTO.getImageUrl()); // ✅ Ahora también se actualiza la imagen

        // 🔹 Guardar cambios en la base de datos
        data.save(organizerToUpdate);

        return new ResponseDTO(HttpStatus.OK.toString(), "✅ Organizador actualizado correctamente");
    }

    private boolean validateOrganizer(organizerDTO organizerDTO) {
        return organizerDTO.getName().length() >= 1 && organizerDTO.getName().length() <= 100 &&
               isValidEmail(organizerDTO.getEmail()) &&
               organizerDTO.getPhone().length() == 10 && organizerDTO.getPhone().matches("[0-9]+");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }
    
    public List<organizer> filterorganizers(String name, String phone, String email, Boolean status) {
        return data.filterorganizers(name, phone, email, status);
    }
    

    public organizer convertToModel(organizerDTO organizerDTO) {
        return new organizer(
            0,  // ID generado automáticamente
            organizerDTO.getName(),
            organizerDTO.getPhone(),
            organizerDTO.getEmail(),
            true,  // Estado activo por defecto
            organizerDTO.getImageUrl() // ✅ Incluyendo la imagen
        );
    }
}
