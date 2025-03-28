package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.crud_basic.model.employee;

public interface Iemployee extends JpaRepository<employee, Integer> {
    @Query("SELECT e FROM employee e WHERE e.status != false")
    List<employee> getListEmployeeActive();


    @Query("""
        SELECT e 
        FROM employee e 
        WHERE (:first_name IS NULL OR e.first_name LIKE %:first_name%) 
          AND (:last_name IS NULL OR e.last_name LIKE %:last_name%) 
          AND (:address IS NULL OR e.address LIKE %:address%) 
          AND (:phone_number IS NULL OR e.phone_number LIKE %:phone_number%) 
          AND (:status IS NULL OR e.status = :status)
    """)
    List<employee> filteremployee(
        @Param("first_name") String first_name,
        @Param("last_name") String last_name,
        @Param("address") String address,
        @Param("phone_number") String phone_number,
        @Param("status") Boolean status
    );
    
        
}
