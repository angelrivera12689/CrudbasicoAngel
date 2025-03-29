package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.crud_basic.model.organizer;

public interface Iorganizer extends JpaRepository<organizer, Integer> {
    @Query("SELECT o FROM organizer o WHERE o.status != false")
    List<organizer> getListOrganizerActive();
    

     @Query("SELECT o FROM organizer o WHERE " +
           "(:name IS NULL OR o.name LIKE %:name%) AND " +
           "(:phone IS NULL OR o.phone LIKE %:phone%) AND " +
           "(:email IS NULL OR o.email LIKE %:email%) AND " +
           "(:status IS NULL OR o.status = :status)")
    List<organizer> filterorganizers(
        @Param("name") String name,
        @Param("phone") String phone,
        @Param("email") String email,
        @Param("status") Boolean status
    );
}
