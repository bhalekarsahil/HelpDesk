package com.example.HelpDesk.ticket.TicketController;

import com.example.HelpDesk.common.ApiResponse;
import com.example.HelpDesk.common.security.RequireRole;
import com.example.HelpDesk.ticket.TicketService.TicketService;
import com.example.HelpDesk.ticket.dtos.ReqTicketDto;
import com.example.HelpDesk.ticket.dtos.ResTicketDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
public class ticketController {
    private final TicketService ticketService;

    public ticketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ResTicketDto>> createTicket(@Valid @RequestBody ReqTicketDto ticket){
        ResTicketDto response = ticketService.createTicket(ticket);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Ticket Created Successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResTicketDto>> getTicketById(@PathVariable Long id){
        ResTicketDto response = ticketService.getTicketById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Ticket Fetch Successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResTicketDto>>> getAllTickets(){
        List<ResTicketDto> allTickets = ticketService.getAllTickets();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Tickets Fetch Successfully", allTickets));
    }

    @PutMapping("/{id}")
    @RequireRole(role = {"ADMIN", "SUPPORT_AGENT"})
    public ResponseEntity<ApiResponse<ResTicketDto>> updateTicket(@PathVariable Long id, @Valid@RequestBody ReqTicketDto reqTicketDto){
        ResTicketDto response = ticketService.updateTicket(id, reqTicketDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Ticket Updated Successfully", response));
    }

    @DeleteMapping("/{id}")
    @RequireRole(role = {"ADMIN"})
    public ResponseEntity<ApiResponse<ResTicketDto>> deleteTicket(@PathVariable Long id){
        ticketService.deleteTicket(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Ticket Deleted Successfully", null));
    }
}
