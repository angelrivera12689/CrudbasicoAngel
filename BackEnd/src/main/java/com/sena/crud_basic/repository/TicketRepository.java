package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sena.crud_basic.model.Ticket;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByStatus(String status);
    List<Ticket> findByAssistantId(int assistantId);
    //List<Ticket> findByEventId(int eventId);
}
