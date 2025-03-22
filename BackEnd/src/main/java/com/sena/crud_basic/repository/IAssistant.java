package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sena.crud_basic.model.Assistant;

public interface IAssistant extends JpaRepository<Assistant, Integer> {
    
    @Query("SELECT a FROM Assistant a WHERE a.status != false")
    List<Assistant> getListUserActive();
    /*
     * C
     * R
     * U
     * D
     */
}
