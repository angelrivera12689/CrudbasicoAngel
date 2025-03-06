package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.CategoryEventDTO;
import com.sena.crud_basic.model.CategoryEvent;
import com.sena.crud_basic.repository.IEventCategory;

@Service
public class CategoryEventService {

    @Autowired
    private IEventCategory data;

    public void save(CategoryEventDTO categoryEventDTO) {
        CategoryEvent categoryEvent = convertToModel(categoryEventDTO);
        data.save(categoryEvent);
    }

    public CategoryEventDTO convertToDTO(CategoryEvent categoryEvent) {
        return new CategoryEventDTO(
                categoryEvent.getId_category(),
                categoryEvent.getName(),
                categoryEvent.getDescription());
    }

    public CategoryEvent convertToModel(CategoryEventDTO categoryEventDTO) {
        return new CategoryEvent(
                0, // ID is auto-generated
                categoryEventDTO.getName(),
                categoryEventDTO.getDescription());
    }
}
