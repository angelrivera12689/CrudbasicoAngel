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
        if (eventDTO.getDate() == null) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La fecha del evento es obligatoria"
            );
        }

        // Validar que la hora no sea nula
        if (eventDTO.getTime() == null) {
            return new ResponseDTO(
                HttpStatus.BAD_REQUEST.toString(),
                "La hora del evento es obligatoria"
            );
        }

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
        eventToUpdate.setDate(eventDTO.getDate());
        eventToUpdate.setTime(eventDTO.getTime());
        eventToUpdate.setLocation(eventDTO.getLocation());

        eventRepository.save(eventToUpdate);

        return new ResponseDTO(
            HttpStatus.OK.toString(),
            "Evento actualizado exitosamente"
        );
    }

    public List<Events> filterEvent(String event_name, String description, LocalDate date, String location, Integer category_id) {
        return eventRepository.filterevent(event_name, description, date, location, category_id);
    }

  // ✅ Método para convertir un Events a EventsDTO
public EventsDTO convertToDTO(Events event) {
    return new EventsDTO(
            event.getEventName(),
            event.getDescription(),
            event.getDate(),
            event.getTime(),
            event.getLocation(),
            event.getCategoryEvent().getcategory_id()
    );
}

// ✅ Método para convertir un EventsDTO a Events
public Events convertToModel(EventsDTO eventDTO) {
    // Buscar la categoría del evento en la base de datos
    CategoryEvent categoryEvent = categoryRepository.findById(eventDTO.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Categoría de evento no encontrada con ID: " + eventDTO.getCategoryId()));

    // Crear y retornar el objeto Events
    return new Events(
            categoryEvent, // Categoría del evento
            0, // Suponiendo que el ID es autogenerado
            eventDTO.getEventName(),
            eventDTO.getDescription(),
            eventDTO.getDate(), // LocalDate, ya está listo para ser usado
            eventDTO.getTime(), // LocalTime, ya está listo para ser usado
            eventDTO.getLocation(),
            true // Estado activo por defecto
    );
}
}
