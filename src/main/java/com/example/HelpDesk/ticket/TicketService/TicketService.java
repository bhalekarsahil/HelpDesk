package com.example.HelpDesk.ticket.TicketService;

import com.example.HelpDesk.exception.ResourceNotFoundException;
import com.example.HelpDesk.ticket.TicketRepository.TicketRepository;
import com.example.HelpDesk.ticket.dtos.ReqTicketDto;
import com.example.HelpDesk.ticket.dtos.ResTicketDto;
import com.example.HelpDesk.ticket.entity.Ticket;
import com.example.HelpDesk.ticket.mapper.TicketMapper;
import com.example.HelpDesk.user.UserRepository.UserRepository;
import com.example.HelpDesk.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private TicketRepository ticketRepository;
    private TicketMapper ticketMapper;
    private UserRepository userRepository;
    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.userRepository = userRepository;
    }

    @Transactional()
    public ResTicketDto createTicket(ReqTicketDto reqTicketDto){
        User creator = userRepository.findById(reqTicketDto.getCreatedById())
                .orElseThrow(()->new ResourceNotFoundException("User", "id", reqTicketDto.getCreatedById()));

        User assigne = null;
        if(reqTicketDto.getAssignedToId() != null){
            assigne = userRepository.findById(reqTicketDto.getAssignedToId())
                    .orElseThrow(()->new ResourceNotFoundException("User", "id", reqTicketDto.getAssignedToId()));
        }

        // MAP DTO to Entity & attach relationship
        Ticket ticket = ticketMapper.toEntity(reqTicketDto);
        ticket.setCreatedBy(creator);
        ticket.setAssignedTo(assigne);

        //4. save and return response dto
        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toRes(savedTicket);
    }
    @Transactional
    public ResTicketDto getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Ticket", "id", id));
        return ticketMapper.toRes(ticket);
    }

    @Transactional
    public List<ResTicketDto> getAllTickets() {

        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticketMapper::toRes)
                .toList();
    }

    @Transactional
    public ResTicketDto updateTicket(Long id, ReqTicketDto reqTicketDto){
        // 1. Fetch existing entity
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Ticket", "id", id));

        ticketMapper.updateEntityFromTicketDto(reqTicketDto, existingTicket);

        if (reqTicketDto.getAssignedToId() != null) {
            User newAssignee = userRepository.findById(reqTicketDto.getAssignedToId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", reqTicketDto.getAssignedToId()));
            existingTicket.setAssignedTo(newAssignee);
        }
        // 4. Save and return updated DTO
        Ticket updatedTicket = ticketRepository.save(existingTicket);
        return ticketMapper.toRes(updatedTicket);
    }

    @Transactional
    public void deleteTicket(Long id){
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id));
        ticketRepository.deleteById(id);
    }
}
