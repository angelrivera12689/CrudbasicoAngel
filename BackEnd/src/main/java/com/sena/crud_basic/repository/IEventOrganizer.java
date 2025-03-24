package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_basic.model.EventOrganizer;

public interface IEventOrganizer extends JpaRepository<EventOrganizer, Integer> {
    
    
}
