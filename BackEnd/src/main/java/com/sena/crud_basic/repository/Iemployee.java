package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_basic.model.employee;

public interface Iemployee extends JpaRepository<employee, Integer> {
    // You can add custom queries if needed
}
