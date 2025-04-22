package com.sena.crud_basic.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.AssistantDTO;
import com.sena.crud_basic.model.Assistant;
import com.sena.crud_basic.repository.IAssistant;
import com.sena.crud_basic.DTO.ResponseDTO;

@Service
public class AssistantService {

    @Autowired
    private IAssistant data;

    // Método para Crear registros con validaciones
    public ResponseDTO save(AssistantDTO assistantDTO) {
        // Validar el nombre de acuerdo a las restricciones
        if (assistantDTO.getName().length() < 1 || assistantDTO.getName().length() > 50) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El nombre debe estar entre 1 y 50 caracteres"
            );
        }

        // Validar el formato del email
        if (!isValidEmail(assistantDTO.getEmail())) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El email no tiene un formato válido"
            );
        }
    
        // Validar el teléfono (por ejemplo, debe tener 10 dígitos)
        if (assistantDTO.getPhone().length() != 10 || !assistantDTO.getPhone().matches("[0-9]+")) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El número de teléfono debe tener 10 dígitos y solo puede contener números"
            );
        }
        
        // Validar la URL de la imagen (si se proporciona)
        if (assistantDTO.getImageUrl() != null && !assistantDTO.getImageUrl().isEmpty() && !isValidImageUrl(assistantDTO.getImageUrl())) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La URL de la imagen no es válida"
            );
        }
    
        // Convertir el DTO a modelo
        Assistant assistant = convertToModel(assistantDTO);
    
        // Guardar el asistente
        data.save(assistant);
    
        // Retornar una respuesta exitosa
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Asistente guardado exitosamente"
        );
    }

    // Método para validar el formato del email
    private boolean isValidEmail(String email) {
        // Expresión regular para validar el formato de un correo electrónico
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        // Retorna true si el correo cumple con la expresión regular, de lo contrario false
        return matcher.matches();
    }

    // Método para validar la URL de la imagen (básico)
    private boolean isValidImageUrl(String url) {
        String urlRegex = "^(http|https)://.*\\.(jpg|jpeg|png|gif)$"; // Para validar imágenes con extensiones comunes
        Pattern pattern = Pattern.compile(urlRegex);
        java.util.regex.Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
    
    // Método para consultar todos los asistentes activos
    public List<Assistant> findAll() {
        return data.getListAssistantActive();
    }

    // Método para consultar un asistente por ID
    public Optional<Assistant> findById(int id) {
        return data.findById(id);
    }

    // Método para eliminar un asistente por ID (eliminación lógica)
    public ResponseDTO delete(int id) {
        Optional<Assistant> assistant = findById(id);
        if (!assistant.isPresent()) {
            // Si no se encuentra el asistente, devolvemos una respuesta de error
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El registro no existe"
            );
        }

        // Cambiar el estado del asistente a false (eliminación lógica)
        assistant.get().setStatus(false);
        data.save(assistant.get());

        // Devolvemos una respuesta de éxito
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Asistente eliminado correctamente"
        );
    }

    // Método para filtrar asistentes
    public List<Assistant> filterAssistants(Integer id, String name, String email, String phone, Boolean status) {
        return data.filterAssistants(id, name, email, phone, status);
    }
    
    // Método para convertir un modelo a un DTO
    public AssistantDTO convertToDTO(Assistant assistant) {
        return new AssistantDTO(
            assistant.getName(),
            assistant.getEmail(),
            assistant.getPhone(),
            assistant.getImageUrl()  // Incluimos la URL de la imagen
        );
    }
    
    // Método para convertir un DTO a un modelo
    public Assistant convertToModel(AssistantDTO assistantDTO) {
        return new Assistant(
            0, // Asumimos que el ID es auto-generado en la base de datos
            assistantDTO.getName(),
            assistantDTO.getEmail(),
            assistantDTO.getPhone(),
            true, // Estado por defecto: activo
            assistantDTO.getImageUrl() // Incluir la URL de la imagen
        );
    }

    // Método para actualizar un asistente
    public ResponseDTO update(int id, AssistantDTO assistantDTO) {
        // Buscar el asistente por ID
        Optional<Assistant> existingAssistant = findById(id);
        if (!existingAssistant.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El asistente no existe"
            );
        }
    
        // Validar el nombre de acuerdo a las restricciones
        if (assistantDTO.getName().length() < 1 || assistantDTO.getName().length() > 50) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El nombre debe estar entre 1 y 50 caracteres"
            );
        }
    
        // Validar el formato del email
        if (!isValidEmail(assistantDTO.getEmail())) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El email no tiene un formato válido"
            );
        }
    
        // Validar el teléfono (por ejemplo, debe tener 10 dígitos)
        if (assistantDTO.getPhone().length() != 10 || !assistantDTO.getPhone().matches("[0-9]+")) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El número de teléfono debe tener 10 dígitos y solo puede contener números"
            );
        }
    
        // Validar la URL de la imagen (si se proporciona)
        if (assistantDTO.getImageUrl() != null && !assistantDTO.getImageUrl().isEmpty()) {
            if (!isValidImageUrl(assistantDTO.getImageUrl())) {
                return new ResponseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "La URL de la imagen no es válida"
                );
            }
        }
    
        // Actualizar los datos del asistente existente
        Assistant assistantToUpdate = existingAssistant.get();
        assistantToUpdate.setName(assistantDTO.getName());
        assistantToUpdate.setEmail(assistantDTO.getEmail());
        assistantToUpdate.setPhone(assistantDTO.getPhone());
    
        // Actualizar solo si se proporciona una URL de imagen válida, de lo contrario, dejar el campo anterior intacto
        if (assistantDTO.getImageUrl() != null && !assistantDTO.getImageUrl().isEmpty()) {
            assistantToUpdate.setImageUrl(assistantDTO.getImageUrl());
        }
    
        // Guardar los cambios
        data.save(assistantToUpdate);
    
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Asistente actualizado exitosamente"
        );
    } 
}