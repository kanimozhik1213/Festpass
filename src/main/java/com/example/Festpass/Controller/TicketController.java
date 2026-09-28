package com.example.Festpass.Controller;

import com.example.Festpass.Model.Ticket;
import com.example.Festpass.Service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // CREATE TICKET
    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody Ticket ticket) {

        try {
            Ticket savedTicket = ticketService.createTicket(ticket);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedTicket);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // READ ALL TICKETS
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // READ TICKET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id) {

        Optional<Ticket> ticket = ticketService.getTicketById(id);

        if (ticket.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Ticket not found"));
        }

        return ResponseEntity.ok(ticket.get());
    }

    // UPDATE TICKET
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(
            @PathVariable Long id,
            @RequestBody Ticket updatedTicket) {

        try {
            Optional<Ticket> ticket =
                    ticketService.updateTicket(id, updatedTicket);

            if (ticket.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Ticket not found"));
            }

            return ResponseEntity.ok(ticket.get());

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // READ TICKET BY CODE
    @GetMapping("/details")
    public ResponseEntity<?> getTicketByCode(
            @RequestParam String ticketCode) {

        Optional<Ticket> ticket =
                ticketService.getTicketByCode(ticketCode);

        if (ticket.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Ticket not found"));
        }

        return ResponseEntity.ok(ticket.get());
    }

    // CHECK-IN TICKET
    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(
            @RequestBody Map<String, String> request) {

        String ticketCode = request.get("ticketCode");

        if (ticketCode == null || ticketCode.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Ticket code is required"));
        }

        try {
            Ticket ticket = ticketService.checkIn(ticketCode);
            return ResponseEntity.ok(ticket);

        } catch (RuntimeException e) {

            if ("Invalid ticket code".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", e.getMessage()));
            }

            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // GET ATTENDANCE COUNT
    @GetMapping("/attendance/{eventId}")
    public ResponseEntity<?> getAttendanceCount(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                Map.of("attendance",
                        ticketService.getAttendanceCount(eventId))
        );
    }

    // GET REMAINING SEATS
    @GetMapping("/remaining/{eventId}")
    public ResponseEntity<?> getRemainingSeats(
            @PathVariable Long eventId) {

        try {
            return ResponseEntity.ok(
                    Map.of("remainingSeats",
                            ticketService.getRemainingSeats(eventId))
            );

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // DELETE TICKET BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {

        boolean deleted = ticketService.deleteTicketById(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Ticket not found"));
        }

        return ResponseEntity.ok(
                Map.of("message", "Ticket deleted successfully")
        );
    }
}