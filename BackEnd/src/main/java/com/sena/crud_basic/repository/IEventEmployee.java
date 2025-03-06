package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_basic.model.EventEmployee;

public interface IEventEmployee extends JpaRepository<EventEmployee, Integer> {
    // You can add custom queries if needed
}
