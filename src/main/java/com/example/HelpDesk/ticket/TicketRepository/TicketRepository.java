package com.example.HelpDesk.ticket.TicketRepository;

import com.example.HelpDesk.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByPriority(String priority);
    List<Ticket> findByStatus(String stauts);
    List<Ticket> findByAssignedToId(Long userId);
    List<Ticket> findByCreatedById(Long userId);;
}
