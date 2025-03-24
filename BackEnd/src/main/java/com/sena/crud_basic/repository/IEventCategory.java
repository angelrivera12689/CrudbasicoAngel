package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sena.crud_basic.model.CategoryEvent;

public interface IEventCategory extends JpaRepository<CategoryEvent, Integer> {
    @Query("SELECT c FROM CategoryEvent c WHERE c.status != false")
    List<CategoryEvent> getListCategoryActive();
}
