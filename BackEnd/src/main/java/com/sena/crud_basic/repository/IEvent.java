package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sena.crud_basic.model.Events;

public interface IEvent extends JpaRepository<Events, Integer> {
@Query("SELECT e FROM Events e WHERE e.status != false")
List<Events> getListEventActive();
}
