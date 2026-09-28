package com.example.Festpass.Service;

import com.example.Festpass.Model.Modelclass;
import com.example.Festpass.Model.Ticket;
import com.example.Festpass.Repository.Repository;
import com.example.Festpass.Repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private Repository eventRepository;

    // CREATE TICKET
    public Ticket createTicket(Ticket ticket) {

        Modelclass event = eventRepository.findById(ticket.getEventId())
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        long issuedTickets =
                ticketRepository.countByEventId(ticket.getEventId());

        if (issuedTickets >= event.getCapacity()) {
            throw new RuntimeException("Event capacity is full");
        }

        ticket.setTicketCode(UUID.randomUUID().toString());
        ticket.setStatus("ACTIVE");

        return ticketRepository.save(ticket);
    }

    // READ ALL TICKETS
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // READ TICKET BY ID
    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    // READ TICKET BY CODE
    public Optional<Ticket> getTicketByCode(String ticketCode) {
        return ticketRepository.findByTicketCode(ticketCode);
    }

    // UPDATE TICKET
    public Optional<Ticket> updateTicket(Long id, Ticket updatedTicket) {

        Optional<Ticket> existingTicket =
                ticketRepository.findById(id);

        if (existingTicket.isEmpty()) {
            return Optional.empty();
        }

        Ticket ticket = existingTicket.get();

        if (updatedTicket.getAttendeeName() == null
                || updatedTicket.getAttendeeName().isBlank()) {
            throw new RuntimeException("Attendee name is required");
        }

        // Update attendee name only
        ticket.setAttendeeName(updatedTicket.getAttendeeName());

        return Optional.of(ticketRepository.save(ticket));
    }

    // CHECK-IN TICKET
    public Ticket checkIn(String ticketCode) {

        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() ->
                        new RuntimeException("Invalid ticket code"));

        if ("USED".equalsIgnoreCase(ticket.getStatus())) {
            throw new RuntimeException("Ticket already used");
        }

        ticket.setStatus("USED");

        return ticketRepository.save(ticket);
    }

    // GET ATTENDANCE COUNT
    public long getAttendanceCount(Long eventId) {
        return ticketRepository
                .countByEventIdAndStatus(eventId, "USED");
    }

    // GET REMAINING SEATS
    public long getRemainingSeats(Long eventId) {

        Modelclass event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        long issuedTickets =
                ticketRepository.countByEventId(eventId);

        return event.getCapacity() - issuedTickets;
    }

    // DELETE TICKET BY ID
    public boolean deleteTicketById(Long id) {

        if (!ticketRepository.existsById(id)) {
            return false;
        }

        ticketRepository.deleteById(id);
        return true;
    }
}