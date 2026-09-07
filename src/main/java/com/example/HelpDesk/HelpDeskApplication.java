package com.example.HelpDesk;

import com.example.HelpDesk.department.DepartmentRepository.DepartmentRepository;
import com.example.HelpDesk.department.DepartmentService.DepartmentService;
import com.example.HelpDesk.department.dtos.ReqDepartmentDto;
import com.example.HelpDesk.department.dtos.ResDepartmentDto;
import com.example.HelpDesk.department.entity.Department;
import com.example.HelpDesk.exception.ResourceNotFoundException;
import com.example.HelpDesk.ticket.TicketRepository.TicketRepository;
import com.example.HelpDesk.ticket.TicketService.TicketService;
import com.example.HelpDesk.ticket.dtos.ReqTicketDto;
import com.example.HelpDesk.ticket.dtos.ResTicketDto;
import com.example.HelpDesk.ticket.entity.Ticket;
import com.example.HelpDesk.user.UserRepository.UserRepository;
import com.example.HelpDesk.user.UserService.UserService;
import com.example.HelpDesk.user.dtos.ReqUserDto;
import com.example.HelpDesk.user.dtos.ResUserDto;
import com.example.HelpDesk.user.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class HelpDeskApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelpDeskApplication.class, args);
    }
}