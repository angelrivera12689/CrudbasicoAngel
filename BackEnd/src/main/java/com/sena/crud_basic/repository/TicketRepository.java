package com.sena.crud_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sena.crud_basic.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    

@Query("SELECT t FROM Ticket t WHERE t.status != false")
List<Ticket> getListTicketActive();


@Query("SELECT t FROM Ticket t " +
           "WHERE (:eventId IS NULL OR t.event.idEvent = :eventId) " +
           "AND (:assistantId IS NULL OR t.assistant.id_assistant = :assistantId) " +
           "AND (:price IS NULL OR t.price = :price) " +
           "AND (:seatNumber IS NULL OR t.seatNumber LIKE %:seatNumber%) " +
           "AND (:status IS NULL OR t.status = :status) " +
           "AND (:fromDate IS NULL OR t.purchaseDate >= :fromDate) " +
           "AND (:toDate IS NULL OR t.purchaseDate <= :toDate)")
    List<Ticket> filterTickets(
        @Param("eventId") Integer eventId,
        @Param("assistantId") Integer assistantId,
        @Param("price") Double price,
        @Param("seatNumber") String seatNumber,
        @Param("status") Boolean status,
        @Param("fromDate") LocalDateTime fromDate,
        @Param("toDate") LocalDateTime toDate
    );
}

