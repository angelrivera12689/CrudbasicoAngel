package com.sena.crud_basic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.crud_basic.model.Assistant;

public interface IAssistant extends JpaRepository<Assistant, Integer> {
    
    @Query("SELECT a FROM Assistant a WHERE a.status != false")
    List<Assistant> getListAssistantActive();

   @Query("SELECT a FROM Assistant a " +
       "WHERE (:name IS NULL OR a.name LIKE %:name%) " +
       "AND (:email IS NULL OR a.email LIKE %:email%) " +
       "AND (:status IS NULL OR a.status = :status)")
List<Assistant> filterAssistants(@Param("name") String name, 
                                 @Param("email") String email, 
                                 @Param("status") Boolean status);

}
