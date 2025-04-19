package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.EventsDTO;
import com.sena.crud_basic.DTO.ResponseDTO;
import com.sena.crud_basic.model.CategoryEvent;
import com.sena.crud_basic.model.Events;
import com.sena.crud_basic.repository.IEvent;
import com.sena.crud_basic.repository.IEventCategory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private IEvent eventRepository;  // Repositorio de eventos

    @Autowired
    private IEventCategory categoryRepository; // Repositorio de categorías

    // ✅ Método para guardar un evento con validaciones
    public ResponseDTO save(EventsDTO eventDTO) {
        // Validar que el nombre tenga entre 1 y 100 caracteres
        if (eventDTO.getEventName() == null || eventDTO.getEventName().trim().isEmpty() || eventDTO.getEventName().length() > 100) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El nombre del evento debe estar entre 1 y 100 caracteres"
            );
        }

        // Validar que la fecha no sea nula
    

        // Validar que la categoría exista y esté activa
        Optional<CategoryEvent> category = categoryRepository.findById(eventDTO.getCategoryId());

        if (!category.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La categoría asignada no existe"
            );
        }

        if (!category.get().getStatus()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La categoría asignada no está activa"
            );
        }

        // Convertir el DTO a modelo
        Events event = convertToModel(eventDTO);

        // Guardar en la base de datos
        eventRepository.save(event);

        // Respuesta de éxito
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Evento guardado exitosamente"
        );
    }

    // ✅ Método para obtener todos los eventos activos
    public List<Events> findAll() {
        return eventRepository.getListEventActive();
    }

    // ✅ Método para buscar un evento por ID
    public Optional<Events> findById(int id) {
        return eventRepository.findById(id);
    }

    // ✅ Método para eliminar un evento por ID (eliminación lógica)
    public ResponseDTO deleteEvent(int id) {
        Optional<Events> event = findById(id);
        if (!event.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El evento no existe"
            );
        }

        // Cambiar el estado del evento a false (eliminación lógica)
        event.get().setStatus(false);
        eventRepository.save(event.get());

        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Evento eliminado correctamente"
        );
    }
    public ResponseDTO update(int id, EventsDTO eventDTO) {
        Optional<Events> existingEvent = findById(id);
        if (!existingEvent.isPresent()) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "El evento no existe"
            );
        }
    
        Events eventToUpdate = existingEvent.get();
        eventToUpdate.setEventName(eventDTO.getEventName());
        eventToUpdate.setDescription(eventDTO.getDescription());
        eventToUpdate.setLocation(eventDTO.getLocation());
        eventToUpdate.setImageUrl(eventDTO.getImageUrl()); // 👈 Aquí se actualiza la imagen
    
        CategoryEvent categoryEvent = categoryRepository.findById(eventDTO.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    
        eventToUpdate.setCategoryEvent(categoryEvent);
    
        // Fecha y hora se actualizan automáticamente
        eventToUpdate.setDate(LocalDate.now());
        eventToUpdate.setTime(LocalTime.now());
    
        eventRepository.save(eventToUpdate);
    
        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Evento actualizado exitosamente"
        );
    }
    

    public List<Events> filterEvent(String event_name, String description, LocalDate date, String location, String category_name) {
        return eventRepository.filterEvent(event_name, description, date, location, category_name);
    }
    

 // ✅ Método para convertir un Events a EventsDTO
public EventsDTO convertToDTO(Events event) {
    return new EventsDTO(
            event.getEventName(),
            event.getDescription(),
            event.getDate(),
            event.getTime(),
            event.getLocation(),
            event.getCategoryEvent().getcategory_id(),
            event.getImageUrl(),
            event.getCategoryEvent().getName() // 👈 Añadido el nombre de la categoría
    );
}

// ✅ Método para convertir un EventsDTO a Events
public Events convertToModel(EventsDTO eventDTO) {
    CategoryEvent categoryEvent = categoryRepository.findById(eventDTO.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Categoría de evento no encontrada con ID: " + eventDTO.getCategoryId()));

    return new Events(
            categoryEvent,
            0,
            eventDTO.getEventName(),
            eventDTO.getDescription(),
            null, // la fecha se autogenera
            null, // la hora también
            eventDTO.getLocation(),
            eventDTO.getImageUrl(),
            true
    );
}
}