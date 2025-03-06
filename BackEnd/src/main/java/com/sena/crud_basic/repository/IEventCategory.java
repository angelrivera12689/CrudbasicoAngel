package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sena.crud_basic.model.CategoryEvent;

public interface IEventCategory extends JpaRepository<CategoryEvent, Integer> {
    // CRUD operations (C, R, U, D) will be available by default.
}
