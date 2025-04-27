package com.sena.crud_basic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.crud_basic.model.Assistant;

public interface IAssistant extends JpaRepository<Assistant, Integer> {
    
    @Query("SELECT a FROM Assistant a WHERE a.status != false")
    List<Assistant> getListAssistantActive();


    @Query("SELECT a FROM Assistant a WHERE " +
    "(:id IS NULL OR a.id = :id) AND " +
    "(:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
    "(:email IS NULL OR LOWER(a.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
    "(:phone IS NULL OR a.phone = :phone) AND " +
    "(:status IS NULL OR a.status = :status)")
List<Assistant> filterAssistants(
 @Param("id") Integer id,
 @Param("name") String name,
 @Param("email") String email,
 @Param("phone") String phone,
 @Param("status") Boolean status
);
}