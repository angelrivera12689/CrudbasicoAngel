package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.crud_basic.model.CategoryEvent;

public interface IEventCategory extends JpaRepository<CategoryEvent, Integer> {
    @Query("SELECT c FROM CategoryEvent c WHERE c.status != false")
    List<CategoryEvent> getListCategoryActive();


    @Query("SELECT c FROM CategoryEvent c WHERE " +
    "(:id IS NULL OR c.id = :id) AND " +
    "(:name IS NULL OR c.name LIKE %:name%) AND " +
    "(:description IS NULL OR c.description LIKE %:description%) AND " +
    "(:status IS NULL OR c.status = :status)")
List<CategoryEvent> filterCategory(
 @Param("id") Integer id,
 @Param("name") String name,
 @Param("description") String description,
 @Param("status") Boolean status
);

}


