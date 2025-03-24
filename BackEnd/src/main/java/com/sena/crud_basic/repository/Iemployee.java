package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sena.crud_basic.model.employee;

public interface Iemployee extends JpaRepository<employee, Integer> {
    @Query("SELECT e FROM employee e WHERE e.status != false")
    List<employee> getListEmployeeActive();
}
