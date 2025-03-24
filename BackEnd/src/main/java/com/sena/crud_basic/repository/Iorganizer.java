package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.sena.crud_basic.model.organizer;

public interface Iorganizer extends JpaRepository<organizer, Integer> {
    @Query("SELECT o FROM organizer o WHERE o.status != false")
    List<organizer> getListOrganizerActive();
    
}
