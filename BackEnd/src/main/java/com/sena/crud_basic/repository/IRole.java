package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.crud_basic.model.Role;

public interface IRole extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);
}