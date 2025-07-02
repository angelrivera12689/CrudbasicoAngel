package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.crud_basic.model.Page;

public interface IPage extends JpaRepository<Page, Integer> {
    boolean existsByName(String name);
}