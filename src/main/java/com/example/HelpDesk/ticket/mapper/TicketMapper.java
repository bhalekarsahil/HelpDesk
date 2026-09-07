package com.example.HelpDesk.ticket.mapper;

import com.example.HelpDesk.ticket.dtos.ReqTicketDto;
import com.example.HelpDesk.ticket.dtos.ResTicketDto;
import com.example.HelpDesk.ticket.entity.Ticket;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    @Mapping(target = "status", constant = "OPEN")
    Ticket toEntity(ReqTicketDto dto);

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.name", target = "createdByName")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "assignedTo.name", target = "assignedToName")
    ResTicketDto toRes(Ticket ticket);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromTicketDto(ReqTicketDto reqTicketDto, @MappingTarget Ticket ticket);
}
