package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sena.crud_basic.model.Ticket;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByStatus(String status);
    List<Ticket> findByAssistantId(int assistantId);

   @Query("SELECT t FROM Ticket t WHERE t.status != false")
List<Ticket> getListTicketActive();
}
