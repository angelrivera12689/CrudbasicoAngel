package com.sena.crud_basic.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.crud_basic.model.Events;

public interface IEvent extends JpaRepository<Events, Integer> {
@Query("SELECT e FROM Events e WHERE e.status != false")
List<Events> getListEventActive();


@Query("""
    SELECT e FROM Events e 
    WHERE e.status = true
      AND (:event_name IS NULL OR e.eventName LIKE %:event_name%) 
      AND (:description IS NULL OR e.description LIKE %:description%) 
      AND (:date IS NULL OR e.date = :date) 
      AND (:location IS NULL OR e.location LIKE %:location%) 
      AND (:category_name IS NULL OR e.categoryEvent.name LIKE %:category_name%)
""")
List<Events> filterEvent(
    @Param("event_name") String event_name,
    @Param("description") String description,
    @Param("date") LocalDate date,
    @Param("location") String location,
    @Param("category_name") String category_name
);

}

