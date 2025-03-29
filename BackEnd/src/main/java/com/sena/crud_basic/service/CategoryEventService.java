package com.sena.crud_basic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.CategoryEventDTO;
import com.sena.crud_basic.DTO.ResponseDTO;

import com.sena.crud_basic.model.CategoryEvent;
import com.sena.crud_basic.repository.IEventCategory;

@Service
public class CategoryEventService {

    @Autowired
    private IEventCategory data;

     public ResponseDTO save(CategoryEventDTO categoryEventDTO) {
        // Validar el nombre de acuerdo a las restricciones
        if (categoryEventDTO.getName().length() < 1 || categoryEventDTO.getName().length() > 50) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El nombre debe estar entre 1 y 50 caracteres"
            );
        }

        // Validar la descripción
        if (categoryEventDTO.getDescription().length() > 255) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La descripción no puede superar los 255 caracteres"
            );
        }

        // Convertir el DTO a modelo
        CategoryEvent categoryEvent = convertToModel(categoryEventDTO);
        
        // Guardar la categoría de evento
        data.save(categoryEvent);
        
        // Retornar una respuesta exitosa
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Categoría de evento guardada exitosamente"
        );
    }

    public List<CategoryEvent> findAll() {
        return data.getListCategoryActive();
    }

    public Optional<CategoryEvent> findById(int id) {
        return data.findById(id);
    }

    public ResponseDTO delete(int id) {
        // Buscar la categoría de evento por su ID
        Optional<CategoryEvent> categoryEvent = findById(id);
        if (!categoryEvent.isPresent()) {
            // Si no se encuentra la categoría, devolvemos una respuesta de error
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El registro no existe"
            );
        }
    
        // Cambiar el estado de la categoría a false (eliminación lógica)
        categoryEvent.get().setStatus(false);
        data.save(categoryEvent.get());
    
        // Devolvemos una respuesta de éxito
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Categoría de evento eliminada correctamente"
        );
    }
    public List<CategoryEvent> filterCategory(String name, String description, Boolean status) {
        return data.filterCategory(name, description, status);
    }

    public ResponseDTO update(int id, CategoryEventDTO categoryEventDTO) {
        // Buscar la categoría por ID
        Optional<CategoryEvent> existingCategory = findById(id);
        if (!existingCategory.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La categoría no existe"
            );
        }

        // Validar el nombre de acuerdo a las restricciones
        if (categoryEventDTO.getName().length() < 1 || categoryEventDTO.getName().length() > 50) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El nombre debe estar entre 1 y 50 caracteres"
            );
        }

        // Validar la descripción
        if (categoryEventDTO.getDescription().length() < 1 || categoryEventDTO.getDescription().length() > 200) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La descripción debe estar entre 1 y 200 caracteres"
            );
        }

        // Actualizar los datos de la categoría existente
        CategoryEvent categoryToUpdate = existingCategory.get();
        categoryToUpdate.setName(categoryEventDTO.getName());
        categoryToUpdate.setDescription(categoryEventDTO.getDescription());

        // Guardar los cambios
        data.save(categoryToUpdate);

        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Categoría de evento actualizada exitosamente"
        );
    }
    
    
    public CategoryEvent convertToModel(CategoryEventDTO categoryEventDTO) {
        return new CategoryEvent(
                0, // ID is auto-generated
                categoryEventDTO.getName(),
                categoryEventDTO.getDescription(), true);
               
    }

    
}
