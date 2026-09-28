
package com.example.Festpass.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Festpass.Model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    long countByEventId(Long eventId);

    Optional<Ticket> findByTicketCode(String ticketCode);

    long countByEventIdAndStatus(Long eventId, String status);
}