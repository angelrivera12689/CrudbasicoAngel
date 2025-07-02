package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sena.crud_basic.model.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    

@Query("SELECT t FROM Ticket t WHERE t.status != false")
List<Ticket> getListTicketActive();

@Query("SELECT t FROM Ticket t " +
       "WHERE (:eventName IS NULL OR LOWER(t.event.eventName) LIKE LOWER(CONCAT('%', :eventName, '%'))) " +
       "AND (:assistantName IS NULL OR LOWER(t.assistant.name) LIKE LOWER(CONCAT('%', :assistantName, '%')))")
List<Ticket> filterTickets(
        @Param("eventName") String eventName,
        @Param("assistantName") String assistantName
);


}

